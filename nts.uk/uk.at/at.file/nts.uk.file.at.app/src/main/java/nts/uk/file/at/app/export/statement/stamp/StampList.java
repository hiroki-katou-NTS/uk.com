package nts.uk.file.at.app.export.statement.stamp;

import java.nio.charset.StandardCharsets;

import lombok.Data;

@Data
public class StampList {
	
	private String date;
	private String time;
	private String classification;
	private String mean;
	private String method;
	private String cardNo;
	private String insLocation;
	private String locationInfor;
	private String supportCard;
	private String workingHour;
	private String overtimeHour;
	private String nightTime;
	
	private boolean isAddress = false;
	
	private static final int maxCharLocation = 36; //số ký tự tối đa trên cell 打刻位置
	private static final int maxCharClassification = 24; //số ký tự tối đa trên cell 出退勤区分
	private static final int heightDefault = 48; //chiều cao mặc định của một dòng dữ liệu 
	private static final int heightAddRow = 31; //chiều cao khi thêm một dòng dữ liệu 

	public void setDate(String datetime) {
		this.date = datetime.split(" ")[0];
	}
	
	public void setTime(String datetime) {
		this.time = datetime.split(" ")[1].substring(0, 5);
	}
	
	public int rowHeigth() {
		int locationRow = isAddress ? this.locationCountRow(): 1;
		int classRow = this.classCountRow();
		
		return locationRow > classRow ? (locationRow - 1) * heightAddRow + heightDefault : (classRow - 1) * heightAddRow + heightDefault;
	}
	
	private int locationCountRow() {
		int locationLength = this.locationInfor.getBytes(StandardCharsets.UTF_8).length;
		int rowCount = locationLength / maxCharLocation;
		return locationLength % maxCharLocation > 0 ? rowCount + 1 : rowCount;
	}
	
	private int classCountRow() {
		int classLength = this.classification.getBytes(StandardCharsets.UTF_8).length;
		int rowCount = classLength / maxCharClassification;
		return classLength % maxCharClassification > 0 ? rowCount + 1 : rowCount;
	}
	
}
