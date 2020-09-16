package nts.uk.cnv.dom.conversiontable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.conversionsql.Join;

@Getter
@AllArgsConstructor
public class ConversionSource {

	String sourceTableName;
	String alias;
	String condition;
	String memo;
	Join join;
}
