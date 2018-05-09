package nts.uk.ctx.at.shared.app.command.scherec.dailyattendanceitem;

import java.util.Optional;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.ControlOfAttendanceItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.HeaderBackgroundColor;

@Value
public class ControlOfAttendanceItemsCmd {

	/**会社ID*/
	private String companyID;
	
	/**勤怠項目ID*/
	private int itemDailyID;
	
	/**日別実績のヘッダ背景色*/
	private Optional<String> headerBgColorOfDailyPer;

	/**時間項目の入力単位*/
	private Optional<Integer> inputUnitOfTimeItem;
	
	public static ControlOfAttendanceItems fromDomain(ControlOfAttendanceItemsCmd command) {
		return new ControlOfAttendanceItems(
				command.getCompanyID(),
				command.getItemDailyID(),
				command.getHeaderBgColorOfDailyPer()==null?null: new HeaderBackgroundColor(command.getHeaderBgColorOfDailyPer().get()),
				command.getInputUnitOfTimeItem()==null?null:EnumAdaptor.valueOf(command.getInputUnitOfTimeItem().get(), TimeInputUnit.class) 
				);
	}
}
