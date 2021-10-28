# Build4Cloud

## 概要

UKのwarファイルをクラウド用にビルドするツールです。

クラウド環境はデータソースが複数あるため、それに合わせてpersistence.xmlを変更（データソース分のpersistence-unit要素を記述）する必要があります。

このツールは、既存のpersistence.xmlを元に、一時的にクラウド用に書き換えてwarをビルドします。

## 使い方

コマンドライン

```
Build4Cloud [project]
```

引数`[project]`には"com"や"at"などを指定。

対象ソースコードのフォルダは、このEXEの実行位置（ワーキングディレクトリ）から探しにいく仕様。