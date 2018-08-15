package nts.uk.screen.at.app.monthlyperformance.correction.param;

import java.util.List;

import lombok.Data;

@Data
public class PSheet {
	/** シートNO */
	private String sheetNo;
	/** シート名 */
	private String sheetName;
	/** 項目一覧 */
	private List<PAttendanceItem> displayItems;
	
	public PSheet(String sheetNo, String sheetName, List<PAttendanceItem> displayItems) {
		super();
		this.sheetNo = sheetNo;
		this.sheetName = sheetName;
		this.displayItems = displayItems;
	}
	
	public PSheet() {
		super();
	}
}
