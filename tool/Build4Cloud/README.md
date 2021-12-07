# Build4Cloud

## 概要

UKのwarファイルをクラウド用にビルドするツールです。

クラウド環境はデータソースが複数あるため、それに合わせてpersistence.xmlを変更（データソース分のpersistence-unit要素を記述）する必要があります。

このツールは、既存のpersistence.xmlを元に、一時的にクラウド用に書き換えてwarをビルドします。

## 使い方

コマンドライン

```
Build4Cloud [project] [datasourcesCount]
```

`[project]` :  "com"や"at"などを指定。

`[datasourcesCount]` : テナント別のデータソースの数を指定。"2"を指定した場合、`UK`, `UK1`, `UK2` の計3つのデータソースとなるので注意。

対象ソースコードのフォルダは、このEXEの実行位置（ワーキングディレクトリ）から探しにいく仕様。