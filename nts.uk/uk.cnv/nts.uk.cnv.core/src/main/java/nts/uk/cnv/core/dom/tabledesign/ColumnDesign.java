package nts.uk.cnv.core.dom.tabledesign;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * （こんぶの）列定義
 */
@Getter
@AllArgsConstructor
public class ColumnDesign {
	String id;
	String name;
	String type;
	boolean nullable;
	String defaultValue;
	String comment;
	int dispOrder;
	boolean isPk;
}
