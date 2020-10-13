package nts.uk.ctx.at.record.pub.dailyperform;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

@Data
public class DailyRecordWorkExport {

	/** Attendance items*/
	private List<ItemValue> items;
	
	/** Formatter information*/
	private Object formatter;
	
	private String employeeId;
	
	private GeneralDate date;
	
	public static DailyRecordWorkExport builder(){
		return new DailyRecordWorkExport();
	}
	
	public DailyRecordWorkExport employeeId(String employeeId){
		this.employeeId = employeeId;
		return this;
	}
	
	public DailyRecordWorkExport items(List<ItemValue> items){
		this.items = items;
		return this;
	}
	
	public DailyRecordWorkExport workingDate(GeneralDate date){
		this.date = date;
		return this;
	}
	
	public DailyRecordWorkExport completed(){
		return this;
	}
}
