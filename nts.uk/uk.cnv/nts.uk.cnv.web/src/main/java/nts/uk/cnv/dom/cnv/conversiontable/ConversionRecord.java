package nts.uk.cnv.dom.cnv.conversiontable;

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
