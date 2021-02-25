package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

@Data
public class DailyModifyResult {

	/** Attendance items*/
	private List<ItemValue> items;
	
	/** Formatter information*/
	private Object formatter;
	
	private String employeeId;
	
	private GeneralDate date;
	
	public static DailyModifyResult builder(){
		return new DailyModifyResult();
	}
	
	public DailyModifyResult employeeId(String employeeId){
		this.employeeId = employeeId;
		return this;
	}
	
	public DailyModifyResult items(List<ItemValue> items){
		this.items = items;
		return this;
	}
	
	public DailyModifyResult workingDate(GeneralDate date){
		this.date = date;
		return this;
	}
	
	public DailyModifyResult completed(){
		return this;
	}
}
