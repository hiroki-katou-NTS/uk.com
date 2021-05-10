package nts.uk.cnv.dom.conversiontable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.TableIdentity;

@AllArgsConstructor
@Getter
public class ConversionCategoryTable {
	private String categoryName;
	private TableIdentity table;
	private int sequenceNo;
}
