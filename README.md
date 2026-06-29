<a name="_toc233620654"></a>PMH SU HEALTH INDEX SOFTWARE (VERSION 1.0)\
Installation and User Guide*\
Ali Mohamad
## <a name="_toc233620655"></a>Description
The PMU Health Index software was developed primarily for calculating and documenting the health index of pad-mounted switch gears. It could also be used for most other kinds of inventory, such that there are SAP measuring points that can be used as data points for health index calculation.
## <a name="_toc233620656"></a>Prerequisites

|Item|Description|
| :- | :- |
|Java JDK 25|Considered the most recent JDK as of software creation. If need be, JDK version can be changed in POM file of code and repackaged through Maven.|
|Health Index Jar File|This is the actual software file. Once Java is installed, you simply download and run this file, and it should start up.|
|Conversion, Category, and Weight Files|The link provides a few sample sheets. The software should be able to utilize any new sheets too, such that the xlsx files are properly formatted. More details will be provided later.|


## <a name="_toc233620657"></a>Installation and Setup
Before installation, it would be recommended to make a specific folder locally designated for the software. This is because the database that gets created upon usage will appear in the same directory of the jar file.
### <a name="_toc233620658"></a>Downloads:
1. Create local folder
   1. This can be anywhere locally, but it would be recommended to be in the documents folder of the system for ease of usage.
1. Install Java JDK 25
   1. The appropriate JDK link for the download file is attached above. This JDK was selected as it allows installation for User downloads as opposed to System wide downloads. If a newer version is desired search for JDK’s provided by “Eclipse Temurin”.
   1. Run the installation file, you’ll be met with a Setup Wizard:\
      ![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.001.png)
   1. Next, check the License Agreement, then select next again:\
      ![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.002.png)
   1. Make sure you select “Install just for you”, then select next again:\
      ![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.003.png)
   1. Select the symbol next to “Set or override JAVA\_Home variable” and make sure it is set to “Will be installed on local drive”, then select next once more:\
      ![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.004.png)
   1. Select Install, then Finish:\
      ![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.005.png) 
1. Download “HealthIndex-1.0.jar” file from SharePoint and put it in your local folder. Optionally, you can also download the “Current Sheet to Import” for testing purposes.
### <a name="_toc233620659"></a>Import Files Setups
There are four different import files needed to run a health index calculation. All files should be compatible with Excel and must have the “.xlsx” filename extension. The import file specifications will all be provided below:


#### <a name="_toc233620660"></a>*Batch Health Index Import File*
To get this file, you’ll need access to “SAP”. Open onto a browser and follow these steps:

1. Navigate to the app “Display Measurement Documents”. 

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.006.png)![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.007.png)



1. Select “Get Variant” and select “PMH Health”: 

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.008.png)![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.009.png)



1. Here the start date can be selected if wanted (default will be all records), as well as measuring points. It would be recommended to select multiple measuring points: 

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.010.png)![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.011.png)

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.012.png)

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.013.png)



1. Search for kinds of equipment needed to be analyzed, select all of them, and execute:

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.014.png)\
` `![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.015.png)



1. Select the box at the top left of the table to select all readings. Then select “Choose”:

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.016.png)![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.017.png)



1. Select “Execute” once more, and another table will appear select all the equipment, then right click within the table area and select spreadsheet:

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.018.png)![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.019.png)

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.020.png)

1. Export as an Excel “.xlsx” file, which will automatically be formatted for the health index software.


#### <a name="_toc233620661"></a>*Categories Import File*
The categories import file relies on a few sets of information:

|**Name**|**Column**|**Description**|**Examples**|
| :- | :- | :- | :- |
|**measurementName**|A|The exact strings for the wanted measuring points. |<p>OIL LEVELS</p><p>OIL LEAK</p><p>PRIMARY BUSHING</p><p>ELBOWS</p>|
|**category**|B|The main categories in which the measuring points are sorted into.|<p>INTERNAL</p><p>EXTERNAL</p>|
|**categorySet**|C|The subsections within each category which organize the measuring points in each category by impact.|MAJOR<br>MINOR|
|**maximumValue**|D|The maximum value for which an individual inspection can have.|Currently always 4|
|**gatewayTestValue**|E|The test value for a gateway measurement for which a score equal to or worse would raise a flag.|1-4|
|**gatewayFail**|F|A Boolean identifier for whether a measurement has the potential to throw a gateway fail or not.|<p>0 = FALSE</p><p>1 = TRUE</p><p>(Put in 0 or 1)</p>|

A sample table could look like the following:

|**measurementName**|**category**|**categorySet**|**maximumValue**|**gatewayTestValue**|**gatewayFail**|
| :-: | :-: | :-: | :-: | :-: | :-: |
|**OIL LEVELS**|INTERNAL|MAJOR|4|0|0|
|**OIL LEAK**|INTERNAL|MAJOR|4|3|1|
|**PRIMARY BUSHING**|INTERNAL|MAJOR|4|0|0|
|**ELBOWS**|INTERNAL|MAJOR|4|0|0|
|**TERMINATIONS**|INTERNAL|MAJOR|4|0|0|
|**INTERNAL TEMPERATURE**|INTERNAL|MAJOR|4|0|0|
|**THROUGH HOLE**|EXTERNAL|MAJOR|4|1|1|
|**TANK CORROSION**|EXTERNAL|MAJOR|4|0|0|
|**SECONDARY CONNECTIONS**|INTERNAL|MINOR|4|0|0|
|**SECONDARY CABLES**|INTERNAL|MINOR|4|0|0|
|**SECONDARY SPADE**|INTERNAL|MINOR|4|0|0|
|**GROUND CONNECTIONS**|INTERNAL|MINOR|4|0|0|
|**BAFFLE BOARDS**|INTERNAL|MINOR|4|0|0|
|**PAD CONDITION**|EXTERNAL|MINOR|4|1|1|
|**ACCESS**|EXTERNAL|MINOR|4|0|0|
|**GRADE**|EXTERNAL|MINOR|4|0|0|
|**ENCLOSURE CORROSION**|EXTERNAL|MINOR|4|0|0|
|**DOOR CORROSION EXTERNAL**|EXTERNAL|MINOR|4|0|0|
|**DOOR CORROSION INTERNAL**|EXTERNAL|MINOR|4|0|0|
|**AGE**|RELIABILITY|MINOR|4|0|0|
|**LOADING**|RELIABILITY|MAJOR|4|0|0|
#### <a name="_toc233620662"></a>*Weights Import Table*
The weights import file relies on a few sets of information:

|**Name**|**Column**|**Description**|**Examples**|
| :- | :- | :- | :- |
|**Category**|A|The main categories in which the measuring points are sorted into.|<p>INTERNAL</p><p>EXTERNAL</p>|
|**Set**|B|The subsections within each category which organize the measuring points in each category by impact.|MAJOR<br>MINOR|
|**Category Weight**|C|The designated weight that is given to the category for the health index calculation.|Can be any number.<br>For example, if INTERNAL is worth 1.5 and EXTERNAL is worth 1, INTERNAL accounts for 1.5:1 of the weight in the final calculation (or assuming those are the only categories 1.5/2.5 of the final score).|
|**Set Weight**|D|The designated weight for which a category set is given within the category weight.|Can be any number.<br>Using category weight example, if MAJOR is worth 3 and MINOR is worth 1 within INTERNAL, MAJOR would be worth 3:1 of the weight in INTERNAL (or 3/4 of the category weight). This would also be 3/4\*1.5/2.5 of the final score.|

\
A sample table could look like the following:

|**Category**|**Set**|**Category Weight**|**Set Weight**|
| :- | :- | :- | :- |
|**INTERNAL**|MAJOR|1\.5|3|
|**INTERNAL**|MINOR|1\.5|1|
|**EXTERNAL**|MAJOR|1|3|
|**EXTERNAL**|MINOR|1|0\.5|


#### <a name="_toc233620663"></a>*Conversion Table Import*
The conversion table relies on information that is available on the “Batch Health Index” import file, or straight from SAP. The scale in which SAP uses for its code groups is reversed when it comes to the Health Index calculations. To do this the converted scores range from 0 (Worst) to 4 (Best), so an imported score of 1 (which in SAP would be considered the best for most measurements) would be considered a 4. Additionally, there are situations where an invalid score or N/A score is input, which would be converted to a -1 to be ignored. For instances in which there are less than five possibilities (such as a Yes/No measurement) the conversion should still be on a 0-4 scale.

|**Name**|**Column**|**Description**|**Examples**|
| :- | :- | :- | :- |
|**Code Group**|A|The code group categorization found in the last column of the Batch Index Import file, or by navigating through SAP.|H9999-12<br>H9999-09<br>H1000-02|
|**Source Value**|B|The code number measurement found in Batch Index Import file in the column titled “Meas/TotCountrRdg   \_”.|Any number|
|**Converted Value**|C|The converted value is based off a 0-4 scale with 4 being the best possible value.|0-4<br>-1 = for any invalid measurement|

A sample table could look like the following:

|**Code Group**|**Source Value**|**Converted Value**|
| :- | :- | :- |
|**H9999-03**|1|4|
|**H9999-03**|2|3|
|**H9999-03**|3|2|
|**H9999-03**|4|1|
|**H9999-03**|5|0|
|**H9999-07**|1|4|
|**H9999-07**|2|3|
|**H9999-07**|3|2|
|**H9999-07**|4|1|
|**H9999-07**|5|0|
|**H9999-09**|1|4|
|**H9999-09**|2|3|
|**H9999-09**|3|2|
|**H9999-09**|4|1|
|**H9999-09**|5|0|
|**H9999-12**|-1|-1|
|**H9999-12**|1|4|
|**H9999-12**|5|0|


## <a name="_toc233620664"></a>Usage
To begin, simply run “HealthIndex-1.0.jar” in your local folder. You’ll be met with the following interface:\
![ref1]

Within this software, there are five tabs, each with its own function, and some require specifically formatted import files. The usage of all these tabs is identified below.


### <a name="_toc233620665"></a>Categories Panel
![ref2]

\
There are two buttons that appear in the categories panel:

|**Name**|**Function**|
| :- | :- |
|**Import**|This will open the file explorer for your operating system. Select the desired Categories .xlsx import file.|
|**Refresh**|If a new import is made, allows you to refresh the pane to display the new information.|

\
Sample panel after successful import:

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.023.png)
### <a name="_toc233620666"></a>Conversion Panel
![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.024.png)

There are two buttons that appear in the categories panel:

|**Name**|**Function**|
| :- | :- |
|**Import**|This will open the file explorer for your operating system. Select the desired Conversion .xlsx import file.|
|**Refresh**|If a new import is made, allows you to refresh the pane to display the new information.|

\
Sample panel after successful import:

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.025.png)
### <a name="_toc233620667"></a>Weights Panel
![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.026.png)

There are two buttons that appear in the categories panel:

|**Name**|**Function**|
| :- | :- |
|**Import**|This will open the file explorer for your operating system. Select the desired Weights .xlsx import file.|
|**Refresh**|If a new import is made, allows you to refresh the pane to display the new information.|

\
Sample panel after successful import:

![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.027.png)
### <a name="_toc233620668"></a>Batch Health Index Panel:
![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.028.png)

There are three buttons that appear in this panel:

|**Name**|**Function**|
| :- | :- |
|**Import Inspection Export**|This will open the file explorer for your operating system. Select the desired SAP Batch Health Index .xlsx export file. This will take longer dependent on the number of items being imported. The display will show which equipment number is currently being processed. When it is complete, it’ll display “Imported:” followed by the number of objects imported.|
|**Calculate All**|This calculates the health index for all imported equipment. The display will show which equipment number is currently being calculated. When it is complete, it’ll display “Calculated: <number of calculated assets> assets”.|
|**Export Results**|This recalculates all imported equipment and opens your file explorer to designate a save location and name for an export .xlsx file. After selected, the display will show which equipment number is currently being calculated, then which equipment number is being exported. When it is complete, it’ll display “Exported”. |

A sample exported results file is provided below:

|**Equipment Number**|**Date**|**Gateway Fail**|**External**|**Internal**|**Reliability**|**Health Index (%)**|
| :- | :- | :- | :- | :- | :- | :- |
|**20013200**|2026-03-03|FALSE|0\.87|0\.81|0\.00|83\.51%|
|**20010055**|2024-06-24|FALSE|0\.87|0\.81|0\.00|83\.51%|
|**20119158**|2026-05-12|FALSE|1\.00|0\.91|0\.00|94\.38%|
|**20010053**|2026-06-16|FALSE|0\.74|0\.65|0\.00|68\.52%|
|**20010965**|2024-07-05|FALSE|0\.85|0\.69|0\.00|75\.06%|
|**20114027**|2026-06-16|FALSE|0\.87|0\.81|0\.00|83\.51%|
|**20010964**|2026-06-22|TRUE|0\.74|0\.41|0\.00|53\.90%|

### <a name="_toc233620669"></a>Results Panel:
![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.029.png)\
The results panel shows all calculated health indices along with their most recent inspection date, equipment number, and whether there was a gateway failure. There is one button, “Refresh”, which displays the most recent calculated data had there been any changes to the previous data.\
A sample populated Results Panel is provided below:\
![](Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.030.png)
## <a name="_toc233620670"></a>Built With/ Tech Stack

|**Name**|**Version**|
| :- | :- |
|**Java JDK**|25\.0.3|
|**Maven**|25|
|**SQLite**|3\.50.3.0|
|**FlatLaf**|3\.6|
|**Apache Log4j**|2\.25.1|
|**Apache POI**|5\.4.1|


[ref1]: Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.021.png
[ref2]: Aspose.Words.af598ab4-298d-48e6-9a63-4e78751667de.022.png


<img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 001" src="https://github.com/user-attachments/assets/a49eafdd-0a6a-4d95-af62-b6b0bed0e4a2" /><img width="624" height="357" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 030" src="https://github.com/user-attachments/assets/f3f7bda4-2f99-4a55-91ae-5a7f224ae82b" />
<img width="624" height="357" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 029" src="https://github.com/user-attachments/assets/7489bb53-4247-410b-b40d-d02478881c33" />
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 028" src="https://github.com/user-attachments/assets/136687a0-4435-4dc7-933a-f2936bc91c48" />
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 027" src="https://github.com/user-attachments/assets/3939e6b0-d209-41aa-a49e-87f02b602d65" />
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 026" src="https://github.com/user-attachments/assets/059e2919-411c-4685-8e7d-157c1c2bf0fe" />
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 025" src="https://github.com/user-attachments/assets/0985561d-ae50-4474-aaaf-0e1e656147fa" />
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 024" src="https://github.com/user-attachments/assets/84ab337c-0ddb-46ce-be99-f3ebe5ae8ece" />
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 023" src="https://github.com/user-attachments/assets/6efc7b3d-d1c4-47ca-9e9d-d7aa89fa283f" />
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 022" src="https://github.com/user-attachments/assets/cc6bbd4c-1602-4dbe-a657-8d0db7571751" />
<img width="624" height="357" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 021" src="https://github.com/user-attachments/assets/610d650d-3510-4c32-85d1-d3d6be9f268d" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 020" src="https://github.com/user-attachments/assets/0202515b-bfa7-44ad-bfd7-4fe1cf21a79a" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 019" src="https://github.com/user-attachments/assets/d525e180-5aad-40ef-8ab8-7bb10493dd7c" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 018" src="https://github.com/user-attachments/assets/b2e6299f-ffa8-4a47-913e-34c12532ae91" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 017" src="https://github.com/user-attachments/assets/feb9c786-1551-4f95-ab2e-53a9cfe0dbf7" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 016" src="https://github.com/user-attachments/assets/50f965d1-3549-46cd-9da8-b31db7af0eea" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 015" src="https://github.com/user-attachments/assets/995044dd-7d82-454d-afb1-4b55bd665dfa" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 014" src="https://github.com/user-attachments/assets/959db03c-beaf-483a-92a2-1e3f20a4b7a7" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 013" src="https://github.com/user-attachments/assets/7c2645ca-f53c-4ed6-9e15-7963813a653b" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 012" src="https://github.com/user-attachments/assets/448e8abf-8ab8-431b-934d-f294d4752647" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 011" src="https://github.com/user-attachments/assets/eeff73de-7fe8-4826-b907-ed5dafb81f09" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 010" src="https://github.com/user-attachments/assets/51fd5d55-7509-4bc1-b93a-ac3196c9cc6f" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 009" src="https://github.com/user-attachments/assets/1fd3e13a-4e49-4c8b-bc60-24c1694a8cfa" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 008" src="https://github.com/user-attachments/assets/08b2ca59-f29b-4da4-838b-b80cab059e55" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 007" src="https://github.com/user-attachments/assets/6cfc24c2-7f1d-412b-b0c2-58b241c057f4" />
<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 006" src="https://github.com/user-attachments/assets/7fbceb66-cf5f-47c5-ad05-7468974bf020" />
<img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 005" src="https://github.com/user-attachments/assets/fe3d226e-c716-4688-92b2-3976c78b6566" />
<img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 004" src="https://github.com/user-attachments/assets/3cf26518-c561-4dab-983c-a95a56c3f4a1" />
<img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 003" src="https://github.com/user-attachments/assets/ba036054-694f-4834-966a-5d88b508de51" />
<img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 002" src="https://github.com/user-attachments/assets/47664562-1edc-4f5a-9e57-9dbc5d56f728" />
