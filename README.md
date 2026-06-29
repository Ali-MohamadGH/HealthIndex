<img width="1280" height="640" alt="w" src="https://github.com/user-attachments/assets/4630189c-6406-4b68-ada2-bb4213e5658e" />

# Installation and User Guide By: Ali Mohamad
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
      <img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 001" src="https://github.com/user-attachments/assets/cfe9ab63-02f1-402a-b247-4ab4fd17d06d" />
   1. Next, check the License Agreement, then select next again:\
      <img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 002" src="https://github.com/user-attachments/assets/abf03f9b-3e4c-441f-a27c-09de049c6ee9" />
   1. Make sure you select “Install just for you”, then select next again:\
      <img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 003" src="https://github.com/user-attachments/assets/24422cff-f96a-490c-a440-fd478ee485a9" />
   1. Select the symbol next to “Set or override JAVA\_Home variable” and make sure it is set to “Will be installed on local drive”, then select next once more:\
      <img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 004" src="https://github.com/user-attachments/assets/3227f5e5-eb47-40b0-82a5-9eb3dacb214f" />
   1. Select Install, then Finish:\
      <img width="427" height="334" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 005" src="https://github.com/user-attachments/assets/2da8ca82-bef2-4d48-8959-b8f6b6dccf0d" />
1. Download “HealthIndex-1.0.jar” file from SharePoint and put it in your local folder. Optionally, you can also download the “Current Sheet to Import” for testing purposes.
### <a name="_toc233620659"></a>Import Files Setups
There are four different import files needed to run a health index calculation. All files should be compatible with Excel and must have the “.xlsx” filename extension. The import file specifications will all be provided below:


#### <a name="_toc233620660"></a>*Batch Health Index Import File*
To get this file, you’ll need access to “SAP”. Open onto a browser and follow these steps:

1. Navigate to the app “Display Measurement Documents”. 

1. Select “Get Variant” and select “PMH Health”: 

1. Here the start date can be selected if wanted (default will be all records), as well as measuring points. It would be recommended to select multiple measuring points: 

1. Search for kinds of equipment needed to be analyzed, select all of them, and execute:

1. Select the box at the top left of the table to select all readings. Then select “Choose”:

1. Select “Execute” once more, and another table will appear select all the equipment, then right click within the table area and select spreadsheet:

<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 019" src="https://github.com/user-attachments/assets/61a13a65-75b6-49ee-b42a-8bd399b7837a" />

<img width="580" height="348" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 020" src="https://github.com/user-attachments/assets/304c60c7-9946-4158-9dfa-c1df5e2aab74" />

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
<img width="624" height="357" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 021" src="https://github.com/user-attachments/assets/56f185ff-49f4-4502-8ec5-f9d76897de55" />

Within this software, there are five tabs, each with its own function, and some require specifically formatted import files. The usage of all these tabs is identified below.


### <a name="_toc233620665"></a>Categories Panel
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 022" src="https://github.com/user-attachments/assets/216783bc-da3e-4885-b174-45bd8dff5af4" />


\
There are two buttons that appear in the categories panel:

|**Name**|**Function**|
| :- | :- |
|**Import**|This will open the file explorer for your operating system. Select the desired Categories .xlsx import file.|
|**Refresh**|If a new import is made, allows you to refresh the pane to display the new information.|

\
Sample panel after successful import:

<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 023" src="https://github.com/user-attachments/assets/44318193-e43a-4cac-a293-b7749dd97518" />
### <a name="_toc233620666"></a>Conversion Panel
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 024" src="https://github.com/user-attachments/assets/a0bf1c65-7fcd-4181-a874-7bf823437547" />

There are two buttons that appear in the categories panel:

|**Name**|**Function**|
| :- | :- |
|**Import**|This will open the file explorer for your operating system. Select the desired Conversion .xlsx import file.|
|**Refresh**|If a new import is made, allows you to refresh the pane to display the new information.|

\
Sample panel after successful import:

<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 025" src="https://github.com/user-attachments/assets/1f65d5ab-428a-4894-89c5-a1771db70e9e" />
### <a name="_toc233620667"></a>Weights Panel
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 026" src="https://github.com/user-attachments/assets/59163079-1de5-40c9-9c1e-1ac8c9c01d4f" />

There are two buttons that appear in the categories panel:

|**Name**|**Function**|
| :- | :- |
|**Import**|This will open the file explorer for your operating system. Select the desired Weights .xlsx import file.|
|**Refresh**|If a new import is made, allows you to refresh the pane to display the new information.|

\
Sample panel after successful import:

<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 027" src="https://github.com/user-attachments/assets/66efe4cb-0cb9-4b12-907a-98ead6ed2926" />
### <a name="_toc233620668"></a>Batch Health Index Panel:
<img width="529" height="303" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 028" src="https://github.com/user-attachments/assets/14e1da7b-da53-4e24-a8f5-4ec49fbcc932" />

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
<img width="624" height="357" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 029" src="https://github.com/user-attachments/assets/4c9b241f-4345-43cd-b559-3fb5327b21d3" />\
The results panel shows all calculated health indices along with their most recent inspection date, equipment number, and whether there was a gateway failure. There is one button, “Refresh”, which displays the most recent calculated data had there been any changes to the previous data.\
A sample populated Results Panel is provided below:\
<img width="624" height="357" alt="Aspose Words af598ab4-298d-48e6-9a63-4e78751667de 030" src="https://github.com/user-attachments/assets/ff4ff80f-b5e5-4f8b-a3ff-001363f2df84" />
## <a name="_toc233620670"></a>Built With/ Tech Stack

|**Name**|**Version**|
| :- | :- |
|**Java JDK**|25\.0.3|
|**Maven**|25|
|**SQLite**|3\.50.3.0|
|**FlatLaf**|3\.6|
|**Apache Log4j**|2\.25.1|
|**Apache POI**|5\.4.1|



