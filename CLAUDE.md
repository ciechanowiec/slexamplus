# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

Slexamplus is an example application for the [Sling Rocket](https://github.com/ciechanowiec/sling_rocket) framework (Apache Sling on JCR/Oak, deployed as OSGi bundles + content packages). It serves as a working template: each module demonstrates a different Sling Rocket feature (servlets, Sling Models, OSGi factory configs, Sling Jobs, JMX MBeans, repoinit, JCR indexing, HTL rendering, custom node types, etc.). The parent POM `eu.ciechanowiec:sling.rocket.parent` is what supplies dependency versions, plugin configuration, and most build defaults — most behaviors are inherited rather than defined locally.

## Runtime / development workflow

The runtime is a Sling Rocket instance in Docker (`docker-compose.yml`), not a locally launched JVM. Always check that the container is up before deploying; deployments target a live OSGi console.

```bash
docker compose up --detach              # start Sling Rocket on http://localhost:8080 (admin/admin, debug 8081)
docker compose down                     # stop (300s grace period — JCR shutdown is slow, do not kill)
mvn clean install -P installAll         # build everything and push the combined content package to /apps/slexamplus/install
mvn clean install -P installBundle      # build + hot-deploy ONLY the Java bundle (fastest iteration loop for Java logic)
mvn clean install                       # build all modules, do not deploy
```

Per-module install profile (each content-package module has its own): `mvn clean install -P installPackageSeparately` from inside `application/`, `indices/`, `osgiconfig/`, or `static-content/` deploys just that module.

## Tests, coverage, and static analysis

All build-time quality gates live in `bundle/pom.xml` (the only module with Java code). The bundle build fails on:
- **Surefire + Failsafe** — unit and integration tests must pass; `failIfNoTests` is on
- **JaCoCo** — both INSTRUCTION and BRANCH coverage must be ≥ 0.8 (BUNDLE-level)
- **Checkstyle, PMD, SpotBugs (effort=Max, threshold=Low)** — config files under `bundle/src/main/resources/static_code_analysis/`. All three are wired to the `package` phase and fail the build on violations.

Running specific tests:
```bash
mvn -pl bundle test                                              # unit tests for the bundle only
mvn -pl bundle test -Dtest=ClassName                             # single test class
mvn -pl bundle test -Dtest=ClassName#methodName                  # single test method
mvn -pl bundle verify -DskipITs=false                            # include integration tests
mvn -pl bundle install -DskipTests                               # skip tests; auto-disables static-analysis failure AND coverage enforcement (see profiles in bundle/pom.xml)
```

The `sling-mock-oak` dependency is intentionally declared first in `bundle/pom.xml` — its initialization order matters for tests that run against a real in-memory JCR. Don't reorder it.

## Module layout

This is a multi-module Maven build (`<packaging>pom</packaging>` at root). Each module has a single responsibility and is glued together by `all/`:

| Module | Packaging | Purpose |
|---|---|---|
| `bundle/` | `jar` (OSGi bundle via bnd) | All Java logic: servlets, Sling Models, MBeans, Sling Jobs, OSGi components. The only module with `src/main/java/`. |
| `application/` | `content-package` | HTL templates and frontend resources under `/apps/slexamplus/application` (HTML pages, CSS, header/footer). |
| `osgiconfig/` | `content-package` | OSGi `.cfg.json` configs (including factory configs `~name.cfg.json` and the repoinit configuration) under `/apps/slexamplus/osgiconfig/config`. |
| `indices/` | `content-package` | Oak JCR index definitions under `/_oak_index`. |
| `static-content/` | `content-package` | Binary/static JCR content (images, audio, video) deployed via the bundle's `SLING-INF/` mechanism. |
| `all/` | `content-package` (type `container`) | Aggregator: embeds the other five modules into a single deployable package at `/apps/slexamplus/install`. Also runs `sling-feature-converter-maven-plugin` to produce a Sling Feature artifact. |

Everything lives under the JCR root `/apps/slexamplus` (the `validRoot` enforced by `jackrabbit-filter` validators in each module's POM). Touching anything outside that root will fail the build.

## Bundle structure conventions

`bundle/src/main/java/eu/ciechanowiec/slexamplus/` is organized by feature, not layer:
- root package — servlets and standalone OSGi services (each demonstrating one Sling/OSGi concept: factory configs, env-var configs, MBeans, dynamic service references, etc.)
- `bundled/` — example of a clustered/factory-component pattern (`StringProvidersCluster` aggregating `StringProvider` instances declared as factory configs)
- `models/` — Sling Models (HTL-adaptable view objects)
- `job/` — Sling Jobs (`Writer1`/`Writer2` demonstrating scheduled JCR writes)

Java is built as an OSGi bundle via `bnd-maven-plugin`. The bnd instructions in `bundle/pom.xml` register `SLING-INF/notetypes/nodetypes.cnd` (the custom `slexamplus:CustomNodeType` definition) and run the Sling models/caconfig BND plugins inline (workaround for an aem-project-archetype merge bug — comment in the POM has the link). Lombok is used; `lombok.config` is at the repo root.

## OSGi configuration & repoinit

OSGi configurations land in `osgiconfig/jcr_root/apps/slexamplus/osgiconfig/config/`:
- Factory configs use the `pid~instance.cfg.json` naming (e.g., `FactoryProvider~uno.cfg.json`, `FactoryProvider~duo.cfg.json`).
- Repoinit statements live in `org.apache.sling.jcr.repoinit.RepositoryInitializer~slexamplus.config` and are what create service users, ACLs, and bootstrap nodes on first boot.
- Logger setup is two factory configs: `LogManager.factory.config~slexamplus` (logger) + `LogManager.factory.writer~slexamplus` (writer).

## Documentation

`README.adoc` (AsciiDoc) is the source of truth; `README.pdf`, `README.html`, and `README.docx` are generated outputs (and gitignored). Vale lints `README.adoc` against custom rules in `.vale/`; the config is `.vale.ini`. Export and lint commands are documented in the README itself.
