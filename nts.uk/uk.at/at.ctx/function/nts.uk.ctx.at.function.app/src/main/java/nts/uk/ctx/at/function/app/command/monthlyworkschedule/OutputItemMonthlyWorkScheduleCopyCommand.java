package nts.uk.ctx.at.function.app.command.monthlyworkschedule;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.ItemSelectionEnum;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyAttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingCode;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingName;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.PrintSettingRemarksColumn;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.TextSizeCommonEnum;
@Data
public class OutputItemMonthlyWorkScheduleCopyCommand implements OutputItemMonthlyWorkScheduleGetMemento{
	String codeCopy;
	
	String codeSourceSerivce;
	
	int itemType;
	
	int fontSize;

	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonthlyOutputItemSettingCode getItemCode() {
		// TODO Auto-generated method stub
		return null;
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
		return TextSizeCommonEnum.valueOf(this.fontSize);
	}

	@Override
	public ItemSelectionEnum getItemSelectionEnum() {
		return ItemSelectionEnum.valueOf(this.itemType);
	}

	@Override
	public String getEmployeeID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getIsRemarkPrinted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintSettingRemarksColumn getPrintSettingRemarksColumn() {
		// TODO Auto-generated method stub
		return null;
	}
}
