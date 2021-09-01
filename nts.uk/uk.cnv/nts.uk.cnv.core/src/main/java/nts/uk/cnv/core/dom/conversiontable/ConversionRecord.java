package nts.uk.cnv.core.dom.conversiontable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConversionRecord {
	String categoryName;
	String tableName;
	int recordNo;
	String sourceId;
	String explanation;
	boolean removeDuplicate;
}
