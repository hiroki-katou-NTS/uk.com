package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeNameType {

	private int typeDialog;

	private List<CodeName> codeNames;
	
	public static CodeNameType create(int typeDialog, List<CodeName> codeNames){
		return new CodeNameType(typeDialog, codeNames);
	}

}
