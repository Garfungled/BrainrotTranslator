This is a String Project for my AP Java class that I'm working on with a friend. Don't expect it to be amazing. Also this is for fun.

# Brainrot Translator

A one way english to brainrot translator using basic String methods and nothing else. The brainrot terms, synonyms, and weights are located in ```main.csv``` and the list of all synonyms (without duplicates) are located in ```values.txt```.

## Crowd Sourcing: How you can contribute!

If you'd like to be a help to our project, you can contribute to the dataset! Doing so is pretty straight forward.
1. Go to [brainrot google spreadsheet](https://docs.google.com/spreadsheets/d/1wXt3BZlrV8dCR_GeXwsCgYEyPFZaEsNBSJPdGWTs8Rg/edit?gid=453750728#gid=453750728)
2. Add a new row
3. First column is for the weight (think of it as how often it should replace), second is for the brainrot term, and all other proceeding columns are for any synonyms (word replacements) you can think of for the brainrot term.

```
// example: 
 ______ ________ _________ _________ __
|weight|brainrot|synonym 1|synonym 2|...
| 100  |on skib |for real |actually |
```

> [!CAUTION]
> To avoid any errors, make sure each entry has **no preceeding or proceeding spaces**, **commas**, **negative numbers for weights (largest should be 100 but it makes no difference)**, or **anything else extraneous**. Each addition should be a simple alphanumeric entry. Also don't use capital letters, nothing will go wrong but everything is turned into lowercase anyway so it's futile. Oh, and this is a school project so keep things school appropriate thanks!

4. To finalize your changes, run 
```
java DataManager.java
```
to LOCALLY update your dataset.

## Usage

To run, call:

```
java Translator.java
```