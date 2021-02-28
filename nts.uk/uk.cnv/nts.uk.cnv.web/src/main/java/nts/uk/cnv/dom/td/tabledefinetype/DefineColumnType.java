package nts.uk.cnv.dom.td.tabledefinetype;

import lombok.EqualsAndHashCode;

/**
 * 列の型定義
 *
 */
@EqualsAndHashCode
public class DefineColumnType {
	UkDataType type;
	int length;
	int scale;
	boolean nullable;
	String defaultValue;
	String checkConstaint;
}
