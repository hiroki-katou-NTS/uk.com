package nts.uk.cnv.dom.conversiontable;

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
}
