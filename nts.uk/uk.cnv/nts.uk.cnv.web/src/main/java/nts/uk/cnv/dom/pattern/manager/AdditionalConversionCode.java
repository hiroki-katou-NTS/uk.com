package nts.uk.cnv.dom.pattern.manager;

import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdditionalConversionCode {
	String preProcessing;
	String postProcessing;

	Map<String, Set<String>> referencedColumnList;
}
