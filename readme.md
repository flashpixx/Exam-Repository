# Exam-Repository

## Requirements

* [JDK & JRE 1.8 or newer](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3.1 or newer](https://maven.apache.org/)
* [Doxygen](http://www.doxygen.org) and [GraphViz](http://www.graphviz.org/)

__All tools must run from the command-line__


## Project Installation 

Within all files you can find the string ```***JAVATHESIS ...***``` which should be replaced with your thesis name, within ```*.java``` source code you finde the name ```javathesis```

1. replace title in this file
2. modify item ```PROJECT_NAME``` in ```src/site/configuration.doxygen```
3. modify the two entries in ```src/analysis/license/license.txt```
4. modify the two entries in ```src/analysis/license/licensetemplate.txt```
5. executable or non-executable configuration
    * __non-executable:__ remove the ```CMain.java``` file from the project (sometimes you need to recreate the directory structure under main again)
    * __executable jar file:__ add at the enf of the ```pom.xml``` file with the following code inside the ```plugins``` section:

        ```xml
        <!-- executable Jar file with any dependencies -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>2.4.3</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <transformers>
                            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                <manifestEntries>
                                    <Main-Class>javathesis.CMain</Main-Class>
                                </manifestEntries>
                            </transformer>
                        </transformers>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        ```

6. replace in within ```src/main/java``` and ```src/test/java``` the package directory ```javathesis``` to your thesis name (keep in mind, that all ```package``` definition within source files and ```pom.xml``` file must be also renamed, just use the _refactor mechanism_ of your IDE!)

## Good Scientific Practice

Developing code to answer research questions should also reflect the general practices of good scientific research. We think it is necessary to explain what is meant by that and how this could be put into practice.

Good scientific practice is based on the principles of *reliability*, *honesty*, *respect* and *accountability* (see [[The European Code of Conduct for Research Integrity, Revised Edition (2017)](http://www.allea.org/wp-content/uploads/2017/03/ALLEA-European-Code-of-Conduct-for-Research-Integrity-2017-1.pdf)]).
Furthermore it demands transparency of practices and reproducibility, traceability and accountability of research.
The DFG provides guidelines on how to put these principles into practice.

In their guideline [Information Infrastructures for Research Data](http://www.dfg.de/en/research_funding/announcements_proposals/2013/info_wissenschaft_13_19/index.html) the DFG states that
> Quality-assured research data are one of the cornerstones of scientific knowledge. Ensuring their long-term availability, processability and reusability are important aspects of science policy [...]

Paragraph *2.2.2 Anforderungen an die Projektergebnisse* of the guideline [PDF](http://www.dfg.de/formulare/12_14/12_14_de.pdf) (currently available in German only) states that

> Beim Aufbau und der Weiterentwicklung von Informationsinfrastrukturen für Forschungsdaten sind -- sofern vorhanden -- **einschlägige technische Standards** und disziplinspezifische Regelwerke zu berücksichtigen.
> Alle durch das Vorhaben zustande gekommenen Ergebnisse und Erkenntnisse sind in der Fachöffentlichkeit bekannt zu machen und kostenlos zur Nachnutzung auch durch Dritte zur Verfügung zu stellen. **Die Offenlegung ggf. produzierter Quellcodes ist verpflichtend, die Bereitstellung der Projektergebnisse als „open source“ an geeigneter Stelle wird vorausgesetzt. Das schließt die umfassende Dokumentation mit ein.**
(emphasis added)

The more comprehensive [Guidelines Infrastructure for Electronic Publications and Digital Scholarly Communication [09/15]](http://www.dfg.de/formulare/12_11/12_11_en.pdf), available in English, analogously state in paragraph *2.2.2 Project results*

> The content created in the projects and all publications resulting from projects must be made available via the internet to users from all over the world as open access content and remain permanently accessible. Wherever possible, licences indicate clearly to what extent the reusability of publications or parts thereof is guaranteed. [...]

> All project findings must be announced to the relevant community and made available for use free of charge, also to third parties. Disclosure of any source code produced is mandatory; project results must be made available as an open source in a suitable location. This also applies to comprehensive documentation.

In [Handling of Research Data](http://www.dfg.de/en/research_funding/proposal_review_decision/applicants/submitting_proposal/research_data/index.html) the DFG states that regarding *long-time archiving*

> [...] research data should be archived in the researcher's own institution or an appropriate nationwide infrastructure for at least 10 years.

(also see [DFG Guidelines on the Handling of Research Data](http://www.dfg.de/download/pdf/foerderung/antragstellung/forschungsdaten/guidelines_research_data.pdf)).

DFG-funded projects require participants to put the principles of good scientific research into practice by following the DFG guidelines.
We argue that these guidelines serve as a good example and should also be followed in other, open/public funded research projects.

### TL;DR

DFG demands that projects funded by them have to

* disclose any source code
* publish source code in suitable locations
* comprehensive documentation
* research data has to be published for useful reuse (raw data or structured data) and made available for at least 10 years

**Note:** The DFG explicitly mentions [github](http://www.dfg.de/formulare/12_19/12_19_en.pdf) [and](http://www.dfg.de/download/pdf/foerderung/programme/lis/161026_dfg_call_proposal_software_en.pdf) [sourceforge](http://www.dfg.de/download/pdf/dfg_im_profil/internationales/partner/guidelines_neh_dfg_bdhp_2012.pdf) in some of their call for proposals as well as [Creative-Commons](http://www.dfg.de/formulare/12_11/12_11_en.pdf) [licences](http://www.dfg.de/foerderung/info_wissenschaft/2014/info_wissenschaft_14_68/index.html) for documentation.

## Github - CircleCI - Coveralls - Zenodo Deployment

As explained in the chapter above it is important to make the source code of simulations or computations providing answers to research questions available for others, including documentation and resulting data.

In this chapter we discuss methods on how to automate this process to

1. comply with the DFG guidelines and
2. spend less time documenting and publishing and more on actual research :)

### GitHub

1. [create a repository](https://help.github.com/articles/create-a-repo/) in your GitHub account
2. add all the given files to a directory and create a ```.gitignore``` file within the root directory with the following content:

   ```bash
    *.*

    !.gitignore
    !circle.yml
    !pom.xml
    !*.java
    !*.md
    !*.properties
    
    !src/site/*.doxyfile
    !src/site/*.htm*
    !src/site/*.css
    !src/site/*.xml
    
    !src/analysis/**/*.xml
    !src/analysis/**/*.txt
    !src/analysis/**/*.md
    
    target/**
   ```

3. push the content to the repository
3. create _deployment ssh-key_ with on command-line (e.g. [Git-Bash]())

    ```bash
    ssh-keygen -C CircleCI-Deploy -f deployment
    ```
4. open ```https://github.com/<Your Github Username>/<Your Project Name>/settings/keys``` and click ```Add deploy key``` on the top-right, copy the content of the file ```deployment.pub``` to the key-field and enable the option ```Allow write access```


### Coveralls

1. open ```https://coveralls.io/sign-in``` and sign-in via GitHub
2. open the menu and call [add repo](https://coveralls.io/repos/new), search your thesis repository in the list and enable it
3. click on ```Details``` next to the repository name and copy / store the _repository token_

### CircleCI

1. open [Circle CI](https://circleci.com/) and sign-in with your Github account
2. open [add project](https://circleci.com/add-projects/gh/:org)
3. choose your project and click _build project_ and _cancel_ the build process immediatly
4. open the _SSH key settings_ site ```https://circleci.com/gh/<user name>/<project name>/edit#ssh``` and add the content of the ```deployment``` SSH key file (without file extension) and set hostname to ```github.com```
5. open _advanced settings_ site ```https://circleci.com/gh/<user name>/<project name>/edit#advanced-settings``` and set ```Auto-cancel redundant builds``` to ```on```
6. open the _environment variable_ site site ```https://circleci.com/gh/<user name>/<project name>/edit#env-vars``` and set the following varibales:
    * __COVERALL\_TOKEN__ to the stored coveralls repository token
    * __DOXYGEN\_VERSION__ add the newest Doxygen version number from the [download site](http://www.stack.nl/~dimitri/doxygen/download.html)
    * __PATH__ to ```/tmp/doxygen-${DOXYGEN_VERSION}/bin:${PATH}``` 
    * __GIT\_COMMITTER\_EMAIL__ and __GIT\_AUTHOR\_EMAIL__ to your email address
    * __GIT\_COMMITTER\_NAME__ and __GIT\_AUTHOR\_NAME__ to your name

### Zenodo


## Usage

### Documentation

The documentation with UML diagram can be build with ```mvn site``` from command line. The call generates the documentation and also all documentation reports e.g. Test-Cases Coverage, FindBugs, etc. If any tool cannot be found, the build process is stopped. The build documentation website can be found in ```target/site/index.html```

### License

The license can be added with ```mvn license:format```, removed with ```mvn license:remove``` and updated with ```mvn license:update```. The license header is checkd on each compiling execution
