### maven发布说明

- 以下命令发布到MavenCentral快照（需要在版本号后加上-SNAPSHOT）

```
./gradlew publishAllPublicationsToMavenCentralRepository
```

- 以下命令发布到MavenCentral，自动完成发布的所有过程(包括close, release)

```
./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
```
