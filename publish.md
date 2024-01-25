### Maven发布步骤

1. A staging repository is created on Sonatype OSS
2. artifacts are uploaded/published to this staging repository
3. The staging repository is closed
4. The staging repository is released
5. All artifacts in the released repository will be synchronized to maven central

官网：https://vanniktech.github.io/gradle-maven-publish-plugin/central/

### 项目发布说明

- 以下命令发布到MavenCentral快照（需要在版本号后加上-SNAPSHOT）

```
./gradlew publishAllPublicationsToMavenCentralRepository
```

- 以下命令发布到MavenCentral，但是需要手动点击发布（怎么实际使用效果跟官网说的不一致？）

```
./gradlew publishToMavenCentral --no-configuration-cache
```

- 以下命令发布到MavenCentral，自动完成发布的所有过程(包括close, release)

```
./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
```

- 发布到远程仓库：私服（${rootDir}/repo）

```
./gradlew publishAllPublicationsToLocal
```
