This is a String Project for my AP Java class that I'm working on with a friend. Don't expect it to be amazing. Also this is for fun.

## TODO
- [X] Create a MVP (minimum viable product)
- [ ] Clean up code
- [ ] Add some sort of object oriented-ness
- [ ] Make a better string replace to avoid replacing words in other words, e.x. "bro" in "brought"
- [ ] Make it so that any word that isn't converted changes all vowels to "uzz"


# Brainrot Translator

A one way english to brainrot translator using basic String methods and nothing else. The brainrot terms, synonyms, and weights are located in ```main.csv``` and the list of all synonyms (without duplicates) are located in ```values.txt```.

## Usage

To run, call:

```
java Translator.java
```

If you update the ```main.csv``` file, update the program by calling:

```
java DataManager.java
```
then calling:
```
java Translator.javas
```