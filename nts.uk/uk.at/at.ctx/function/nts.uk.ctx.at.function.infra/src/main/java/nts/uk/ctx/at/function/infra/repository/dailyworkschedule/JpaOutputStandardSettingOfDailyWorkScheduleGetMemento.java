package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;

@Data
@AllArgsConstructor
public class JpaOutputStandardSettingOfDailyWorkScheduleGetMemento
		implements OutputStandardSettingOfDailyWorkSchedule.MementoGetter {

	private List<KfnmtRptWkDaiOutItem> kfnmtRptWkDaiOutItem;
	private String companyId;
	private int selection;

	@Override
	public List<OutputItemDailyWorkSchedule> getOutputItems() {
		return this.kfnmtRptWkDaiOutItem.stream()
				.map(t -> new OutputItemDailyWorkSchedule(t)).collect(Collectors.toList());
	}

}
