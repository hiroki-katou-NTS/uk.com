package nts.uk.shr.pereg.app;

import lombok.Value;

@Value
public class ComboBoxObject {
	private String optionText;
	private String optionValue;
	
	public static ComboBoxObject toComboBoxObject(String value, String leftSymbolText, String rightSymbolText){
		String optionText = leftSymbolText + " ~ " + rightSymbolText;
		return new ComboBoxObject(optionText, value);
	};
}
