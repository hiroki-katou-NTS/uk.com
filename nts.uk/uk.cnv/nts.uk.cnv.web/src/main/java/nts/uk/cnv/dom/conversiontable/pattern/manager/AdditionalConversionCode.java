package nts.uk.cnv.dom.conversiontable.pattern.manager;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdditionalConversionCode {
	String preProcessing;
	String postProcessing;

	Map<String, Map<String, List<String>>> referencedColumnList;
}
