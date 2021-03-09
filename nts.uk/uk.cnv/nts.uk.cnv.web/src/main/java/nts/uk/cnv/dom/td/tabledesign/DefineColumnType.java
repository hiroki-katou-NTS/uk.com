package nts.uk.cnv.dom.td.tabledesign;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;

/**
 * 列の型定義
 *
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class DefineColumnType {
	DataType type;
	int length;
	int scale;
	boolean nullable;
	String defaultValue;
	String checkConstaint;
}
