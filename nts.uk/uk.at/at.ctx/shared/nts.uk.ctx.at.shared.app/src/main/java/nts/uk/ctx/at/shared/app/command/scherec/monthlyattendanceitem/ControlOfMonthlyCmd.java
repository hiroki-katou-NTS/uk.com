package nts.uk.ctx.at.shared.app.command.scherec.monthlyattendanceitem;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.HeaderBackgroundColor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItems;

@Getter
@Setter
@NoArgsConstructor
public class ControlOfMonthlyCmd {
	/**会社ID*/
	private String companyID;
	
	/**勤怠項目ID*/
	private int itemMonthlyID;
	
	/**日別実績のヘッダ背景色*/
	private String headerBgColorOfMonthlyPer;

	/**時間項目の入力単位*/
	private BigDecimal inputUnitOfTimeItem;
	
	public static ControlOfMonthlyItems toDomain(ControlOfMonthlyCmd command) {
		return new ControlOfMonthlyItems(
				command.getCompanyID(),
				command.getItemMonthlyID(),
				command.getHeaderBgColorOfMonthlyPer()==null?null: new HeaderBackgroundColor(command.getHeaderBgColorOfMonthlyPer()),
				command.getInputUnitOfTimeItem()==null? null : command.getInputUnitOfTimeItem() 
				);
	}
	
}
