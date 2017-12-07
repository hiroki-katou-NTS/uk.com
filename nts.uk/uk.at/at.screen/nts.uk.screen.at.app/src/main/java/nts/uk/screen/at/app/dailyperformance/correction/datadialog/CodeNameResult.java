package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CodeNameResult {

	List<CodeNameType> codeNameTypes;

	public CodeNameResult() {
		this.codeNameTypes = new ArrayList<>();
	}

	public void addCodeNameTypes(CodeNameType codeNameType) {
		this.codeNameTypes.add(codeNameType);
	}

}
