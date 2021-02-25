package nts.uk.ctx.at.function.dom.adapter.dailyperform;

import java.util.List;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

@Data
public class DailyRecordWorkImport {

	/** Attendance items*/
	private List<ItemValue> items;
	
	/** Formatter information*/
	private Object formatter;
	
	private String employeeId;
	
	private GeneralDate date;
	
	public static DailyRecordWorkImport builder(){
		return new DailyRecordWorkImport();
	}
	
	public DailyRecordWorkImport employeeId(String employeeId){
		this.employeeId = employeeId;
		return this;
	}
	
	public DailyRecordWorkImport items(List<ItemValue> items){
		this.items = items;
		return this;
	}
	
	public DailyRecordWorkImport workingDate(GeneralDate date){
		this.date = date;
		return this;
	}
	
	public DailyRecordWorkImport completed(){
		return this;
	}
}
