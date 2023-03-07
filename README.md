# GenomeBuddy
Java program for detecting mutations in human genomes

Credit to the following projects for supplying great resources:

*[fangyidong/json-simple](https://github.com/fangyidong/json-simple)
*[zhaofengli/snappy](https://github.com/zhaofengli/snappy/tree/master/scripts/parsegenotypes.py)

## Features
*Analyzes user-supplied genomes from a major genomics vendor.
*Scans genome and reports any known harmful mutations.
*Color-coded ascii art modelling the user's alleles.
*Search functionality.

Note: GenomeBuddy must always be supplied with a text file containing the genome in it's directory.
## Requirements
GenomeBuddy requires Java to run. An easy version to install is Amazon Corretto:

[Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html)

## Compiling and Running
GenomeBuddy can be run on Unix systems using the following command:
```
javac -cp ".:/path/to/your/repos/genomebuddy/json-simple-1.1.1.jar" *.java && java -cp ".:/path/to/your/repos/genomebuddy/json-simple-1.1.1.jar" GenomeBuddy
```

## Screenshots
![Example](https://github.com/eduffield/genomeparser/blob/main/screenshots/shot1.png)
![Example](https://github.com/eduffield/genomeparser/blob/main/screenshots/shot2.png)
![Example](https://github.com/eduffield/genomeparser/blob/main/screenshots/shot3.png)
![Example](https://github.com/eduffield/genomeparser/blob/main/screenshots/shot4.png)
