# TN 
For information about swapping your TurtleNode to TurtleNetwork, please read [How to swap $TN](https://github.com/BlackTurtle123/TurtleNetwork/wiki/TurtleNode-Gateway:-How-to-swap-$TN-from-Waves-Platform-to-Turtle-Network).
In the master branch there is a code with functions that is under development. The latest release for each network can be found in the [Releases section](https://github.com/BlackTurtle123/TurtleNetwork/releases), you can switch to the corresponding tag and build the application.

[How to configure TN node](https://documentation.turtlenetwork.eu/nodeowners/how-to-setup-a-new-node.html)

# Installation

Please read [repo wiki article](https://documentation.turtlenetwork.eu/nodeowners/how-to-setup-a-new-node.html).


## 👨‍💻 Development

```
set ThisBuild/network := Testnet

set ThisBuild/network := Mainnet
```

The node can be built and installed wherever Java can run. 
To build and test this project, you will have to follow these steps:

<details><summary><b>Show instructions</b></summary>

*1. Setup the environment.*
- Install Java for your platform:

```bash
sudo apt-get update
sudo apt-get install openjdk-8-jre                     # Ubuntu
# or
# brew cask install adoptopenjdk/openjdk/adoptopenjdk8 # Mac
```

- Install SBT (Scala Build Tool)

Please follow the SBT installation instructions depending on your platform ([Linux](https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html), [Mac](https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Mac.html), [Windows](https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Windows.html))

*2. Clone this repo*

```bash
git clone https://github.com/TurtleNetwork/TurtleNetwork.git
cd TurtleNetwork
```

*3. Compile and run tests*

```bash
sbt checkPR
```

*4. Run integration tests (optional)*

Create a Docker image before you run any test: 
```bash
sbt node-it/docker
```

- Run all tests. You can increase or decrease number of parallel running tests by changing `SBT_THREAD_NUMBER`
```bash
SBT_THREAD_NUMBER=4 sbt node-it/test
```

- Run one test:
```bash
sbt node-it/testOnly *.TestClassName
# or 
# bash node-it/testOnly full.package.TestClassName
```

*5. Build packages* 

```bash
sbt packageAll                   # Mainnet
sbt -Dnetwork=testnet packageAll # Testnet
```

`sbt packageAll` ‌produces only `deb` package along with a fat `jar`. 

*6. Install DEB package*

`deb` package is located in target folder. You can replace '*' with actual package name:

```bash
sudo dpkg -i node/target/*.deb
```


*7. Run an extension project locally during development (optional)*

```bash
sbt "extension-module/run /path/to/configuration"
```

*8. Configure IntelliJ IDEA (optional)*

The majority of contributors to this project use IntelliJ IDEA for development, if you want to use it as well please follow these steps:

1. Click `Add configuration` (or `Edit configurations...`).
2. Click `+` to add a new configuration, choose `Application`.
3. Specify:
   - Main class: `com.wavesplatform.Application`
   - Program arguments: `/path/to/configuration`
   - Use classpath of module: `extension-module`
4. Click `OK`.
5. Run this configuration.

</details>

## DISCLAIMER ##

User may never use this product if his activity related to:

- Drugs, and tools specifically intended for the production of drugs, Drug paraphernalia Illegal Drugs, substances designed to mimic illegal drugs, and/or other psycho active products (e.g.,K2, salvia divinorum, nitrate inhalers, bath salts, synthetic cannabis, herbal smoking blends, herbal incense, and HCG/HGH-like substances)
- Pyramid selling.
- Money laundering.
- Counterfeit goods/replicas or those infringing on intellectual property rights, including those designed to infringe on such intellectual property (i.e., knock-offs, imitations, bootlegs)
- Products/services that promote hate, violence, discrimination, terrorism, harassment or abuse
- Providing gambling services in jurisdictions where this is illegal or (where applicable) offering gambling services without a valid license to the relevant jurisdiction.
- Illegal products/services or any service providing peripheral support of illegal activities. Fake references and other services/products that foster deception (including fake IDs and government documents)


## 👏 Acknowledgements


Special thanks to wavesplatform for writing the base code, and the support.