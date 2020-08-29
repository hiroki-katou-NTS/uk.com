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

public class JpaOutputItemDailyWorkScheduleGetMemento2  implements FreeSettingOfOutputItemForDailyWorkSchedule.MementoGetter
																, OutputStandardSettingOfDailyWorkSchedule.MementoGetter  {

	private List<KfnmtRptWkDaiOutnote> kfnmtRptWkDaiOutnotes;
	private List<KfnmtRptWkDaiOutatd> kfnmtRptWkDaiOutatds;
	private List<KfnmtRptWkDaiOutItem> kfnmtRptWkDaiOutItem;
	private String cid;
	private String sid;
	private int selectionType;
	
	public JpaOutputItemDailyWorkScheduleGetMemento2(List<KfnmtRptWkDaiOutnote> kfnmtRptWkDaiOutnotes,
			List<KfnmtRptWkDaiOutatd> kfnmtRptWkDaiOutatds, List<KfnmtRptWkDaiOutItem> kfnmtRptWkDaiOutItem) {
		super();
		this.kfnmtRptWkDaiOutnotes = kfnmtRptWkDaiOutnotes;
		this.kfnmtRptWkDaiOutatds = kfnmtRptWkDaiOutatds;
		this.kfnmtRptWkDaiOutItem = kfnmtRptWkDaiOutItem;
	}

	@Override
	public String getCompanyId() {	
		return cid;
	}

	@Override
	public String getEmployeeId() {
		return sid;
	}

	@Override
	public int getSelection() {
		return selectionType;
	}

	@Override
	public List<OutputItemDailyWorkSchedule> getOutputItemDailyWorkSchedules() {
		List<OutputItemDailyWorkSchedule> outputItem = this.kfnmtRptWkDaiOutItem.stream()
				.map(t -> new JpaOutputItemDailyWorkScheduleSetMemento(t
							, this.kfnmtRptWkDaiOutatds.stream()
								.filter(r -> r.getId().getLayoutId().equals(t.getLayoutId()))
								.collect(Collectors.toList())
							, this.kfnmtRptWkDaiOutnotes.stream()
								.filter(r -> r.getId().getLayoutId().equals(t.getLayoutId())
										.collect(Collectors.toList())
					)))
				.collect(Collectors.toList());
		return outputItem;
	}

}
