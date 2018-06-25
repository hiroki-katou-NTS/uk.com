package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MPUpdateColWidthCommand {

	private Map<Integer, Integer> lstHeader;
	
	private List<String> formatCode;
	
//	private int sheetNo;

	public MPUpdateColWidthCommand() {
		super();
	}

	public MPUpdateColWidthCommand(Map<Integer, Integer> lstHeader, List<String> formatCode) {
		super();
		this.lstHeader = lstHeader;
		this.formatCode = formatCode;
//		this.sheetNo = sheetNo;
	}
}
