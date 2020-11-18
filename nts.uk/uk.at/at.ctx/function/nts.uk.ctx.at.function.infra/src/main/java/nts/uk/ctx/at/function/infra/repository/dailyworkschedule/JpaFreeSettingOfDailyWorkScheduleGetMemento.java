package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;

@Data
@AllArgsConstructor
public class JpaFreeSettingOfDailyWorkScheduleGetMemento
		implements FreeSettingOfOutputItemForDailyWorkSchedule.MementoGetter {

	private List<KfnmtRptWkDaiOutItem> kfnmtRptWkDaiOutItem;
	
	/** The company id. */
	private String companyId;
	
	/** The employee. */
	private String employeeId;
	
	/** The selection. */
	private int selection;

	@Override
	public List<OutputItemDailyWorkSchedule> getOutputItemDailyWorkSchedules() {
		return this.kfnmtRptWkDaiOutItem.stream()
				.map(t -> new OutputItemDailyWorkSchedule(t)).collect(Collectors.toList());
	}

}
