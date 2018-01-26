package nts.uk.shr.pereg.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ComboBoxObject {
	
	private String optionValue;
	
	private String optionText;
	
	@Setter
	private String firstCtgName;
	
	public ComboBoxObject(String value, String optionText) {
		this.optionText = optionText;
		this.optionValue = value;
	}
	
	private static ComboBoxObject getComBox(String value, String optionText) {
		return new ComboBoxObject(value, optionText, "");
	}
		
	public static ComboBoxObject toComboBoxObject(String value, String leftSymbolText, String rightSymbolText){
		String optionText = leftSymbolText + " ~ " + rightSymbolText;
		return getComBox(value, optionText);
	};
}
