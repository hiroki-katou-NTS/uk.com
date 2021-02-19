package nts.uk.ctx.at.function.app.command.monthlyworkschedule;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.ItemSelectionEnum;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyAttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingCode;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingName;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.TextSizeCommonEnum;
@Data
public class OutputItemMonthlyWorkScheduleDeleteCommand implements OutputItemMonthlyWorkScheduleGetMemento {
	/** The item code. */
	private String itemCode;
		
	/** The Item selection type */
	private int itemType;

	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonthlyOutputItemSettingCode getItemCode() {

		return new MonthlyOutputItemSettingCode(this.itemCode);
	}

	@Override
	public String getEmployeeID() {
		return null;
	}
	
	@Override
	public ItemSelectionEnum getItemSelectionEnum() {
		return ItemSelectionEnum.valueOf(this.itemType);
	}

	@Override
	public MonthlyOutputItemSettingName getItemName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MonthlyAttendanceItemsDisplay> getLstDisplayedAttendance() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RemarkInputContent getRemarkInputNo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLayoutID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextSizeCommonEnum getTextSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getIsRemarkPrinted() {
		// TODO Auto-generated method stub
		return null;
	}
}
