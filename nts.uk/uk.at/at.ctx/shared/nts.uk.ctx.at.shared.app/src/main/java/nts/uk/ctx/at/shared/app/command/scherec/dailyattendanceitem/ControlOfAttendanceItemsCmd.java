package nts.uk.ctx.at.shared.app.command.scherec.dailyattendanceitem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.HeaderBackgroundColor;

@Getter
@Setter
@NoArgsConstructor
public class ControlOfAttendanceItemsCmd {

	/**会社ID*/
	private String companyID;
	
	/**勤怠項目ID*/
	private int itemDailyID;
	
	/**日別実績のヘッダ背景色*/
	private String headerBgColorOfDailyPer;

	/**時間項目の入力単位*/
	private Integer inputUnitOfTimeItem;
	
	public static ControlOfAttendanceItems fromDomain(ControlOfAttendanceItemsCmd command) {
		return new ControlOfAttendanceItems(
				command.getCompanyID(),
				command.getItemDailyID(),
				command.getHeaderBgColorOfDailyPer()==null?null: new HeaderBackgroundColor(command.getHeaderBgColorOfDailyPer()),
				command.getInputUnitOfTimeItem()==null?null:EnumAdaptor.valueOf(command.getInputUnitOfTimeItem(), TimeInputUnit.class) 
				);
	}
}
