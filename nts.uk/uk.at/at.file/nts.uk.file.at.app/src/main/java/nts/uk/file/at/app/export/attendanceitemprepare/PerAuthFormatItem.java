package nts.uk.file.at.app.export.attendanceitemprepare;

import lombok.Data;

@Data
public class PerAuthFormatItem {
	private String dailyCode;
	private String dailyName;
	private int availability;
	private int sheetNo;
	private String sheetName;
	private int attId;
	private int displayOder;
	
	public PerAuthFormatItem(String dailyCode,String dailyName, int attId, int displayOder){
		this.dailyCode = dailyCode;
		this.dailyName = dailyName;
		this.attId = attId;
		this.displayOder = displayOder;
	}

	public PerAuthFormatItem(String dailyCode, String dailyName, int availability, int sheetNo, String sheetName,
			int attId, int displayOder) {
		super();
		this.dailyCode = dailyCode;
		this.dailyName = dailyName;
		this.availability = availability;
		this.sheetNo = sheetNo;
		this.sheetName = sheetName;
		this.attId = attId;
		this.displayOder = displayOder;
	}
	
}
