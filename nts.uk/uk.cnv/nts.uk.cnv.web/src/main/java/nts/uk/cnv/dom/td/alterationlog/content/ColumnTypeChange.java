package nts.uk.cnv.dom.td.alterationlog.content;

import nts.uk.cnv.dom.td.tabledefinetype.DefineColumnType;

public class ColumnTypeChange extends AlterationContent {
	String columnName;
	DefineColumnType afterType;
}
