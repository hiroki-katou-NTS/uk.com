package nts.uk.cnv.dom.tabledesign;

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
	String jpName;
	String type;
	int maxLength;
	int scale;
	boolean nullable;
	String defaultValue;
	String comment;
	String checkConstraint;
	int dispOrder;
}
