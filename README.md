Stanford CoreNLP tutorial
================
### Stanford NLP 是什么？
斯坦福CoreNLP是一个Java自然语言分析库，它集成了所有的自然语言处理工具，包括词性的终端（POS）标注器，命名实体识别（NER），分析器，对指代消解系统，以及情感分析工具，并提供英语分析的模型文件。

Stanford parser是stanford nlp小组提供的一系列工具之一，能够用来完成语法分析任务。支持英文、中文、德文、法文、阿拉伯文等多种语言。

Question
### 1. CoreNLP有哪几方面功能？
也就是有哪些Annotators  http://stanfordnlp.github.io/CoreNLP/annotators.html

tokenize,  分词 Tokenizes the text into a sequence of tokens. The tokenizer saves the character offsets of each token in the input text.
cleanxml, 清理 xml 标签 Remove xml tokens from the document
ssplit,  将文本分句  Splits a sequence of tokens into sentences.
truecase：判断正确的大小写应该是什么样的 Determines the likely true case of tokens in text？

pos, 词性标注 Labels tokens with their part-of-speech (POS) tag
lemma, 获取词原型
gender：根据名字判断性别 Adds likely gender information to names.

ner(Named Entity Recognition)： 命名实体识别，识别出某个词是人名，地名，组织名，时间等等 Recognizes named (PERSON, LOCATION, ORGANIZATION, MISC) and numerical (MONEY, NUMBER, DATE, TIME, DURATION, SET) entities.
parse: Provides full syntactic analysis, including both constituent and dependency representation,
dcoref:  指代消解 Implements mention detection and both pronominal and nominal coreference resolution
sentiment：情感分析 Sentiment analysis with a compositional model over trees using deep learning
regexner:用正则表达式实现命名实体识别 Implements a simple, rule-based NER over token sequences building on Java regular expressions.


### 2. CoreNLP API 怎么使用，哪些参数？
http://stanfordnlp.github.io/CoreNLP/api.html