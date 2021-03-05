package nts.uk.cnv.dom.td.tabledefinetype;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 列の型定義
 *
 */
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
