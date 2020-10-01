package nts.uk.ctx.at.record.dom.daily.itemvalue;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

@Data
public class DailyItemValue {
	
	private List<ItemValue> items = new ArrayList<>();
	
	private String employeeId;
	
	private GeneralDate date;
	
	public static DailyItemValue build(){
		return new DailyItemValue();
	}

	public DailyItemValue createEmpAndDate(String employeeId, GeneralDate date){
		this.employeeId = employeeId;
		this.date = date;
		return this;
	}

	public DailyItemValue createItems(List<ItemValue> items){
		this.items = items;
		return this;
	}
}
