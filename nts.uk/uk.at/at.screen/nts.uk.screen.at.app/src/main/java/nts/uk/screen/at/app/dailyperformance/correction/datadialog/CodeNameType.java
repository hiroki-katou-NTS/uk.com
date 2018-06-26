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
	
	private Boolean error;
	
	
	
	public static CodeNameType create(int typeDialog, List<CodeName> codeNames){
		return new CodeNameType(typeDialog, codeNames);
	}

	public CodeNameType(int typeDialog, List<CodeName> codeNames) {
		super();
		this.typeDialog = typeDialog;
		this.codeNames = codeNames;
		this.error = false;
	}

	public CodeNameType createError(boolean error){
		this.error = error;
		return this;
	}
}
