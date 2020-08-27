package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FontSizeEnum;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;

public class JpaOutputItemDailyWorkScheduleGetMemento  implements FreeSettingOfOutputItemForDailyWorkSchedule.MementoGetter
																, OutputStandardSettingOfDailyWorkSchedule.MementoGetter  {

	private List<KfnmtRptWkDaiOutnote> kfnmtRptWkDaiOutnotes;
	private List<KfnmtRptWkDaiOutatd> kfnmtRptWkDaiOutatds;
	private KfnmtRptWkDaiOutItem kfnmtRptWkDaiOutItem;

	@Override
	public String getCid() {	
		return this.kfnmtRptWkDaiOutItem.getCid();
	}

	@Override
	public String getEmployeeId() {
		return this.kfnmtRptWkDaiOutItem.getSid();
	}

	@Override
	public int getSelectionSetting() {
		return this.kfnmtRptWkDaiOutItem.getItemSelType();
	}

	@Override
	public List<OutputItemDailyWorkSchedule> getOutputItemDailyWorkSchedule() {
		OutputItemDailyWorkSchedule outputItem = new OutputItemDailyWorkSchedule(
				  this.kfnmtRptWkDaiOutItem.getLayoutId()
				, new OutputItemSettingCode(this.kfnmtRptWkDaiOutItem.getItemCode())
				, new OutputItemSettingName(this.kfnmtRptWkDaiOutItem.getItemName())
				, kfnmtRptWkDaiOutatds.stream()
									.map(r -> new AttendanceItemsDisplay((int) r.getId().getOrderNo(), r.getAtdDisplay().intValue()))
									.collect(Collectors.toList())
				, kfnmtRptWkDaiOutnotes.stream()
								  	.map(t -> new PrintRemarksContent(t.getUseCls().intValue(), (int) t.getId().getPrintItem()))
									.collect(Collectors.toList())
				, NameWorkTypeOrHourZone.valueOf(this.kfnmtRptWkDaiOutItem.getWorkTypeNameDisplay().intValue())
				, RemarkInputContent.valueOf(this.kfnmtRptWkDaiOutItem.getNoteInputNo())
				, FontSizeEnum.valueOf(this.kfnmtRptWkDaiOutItem.getCharSizeType()));
		return null;
	}

}
