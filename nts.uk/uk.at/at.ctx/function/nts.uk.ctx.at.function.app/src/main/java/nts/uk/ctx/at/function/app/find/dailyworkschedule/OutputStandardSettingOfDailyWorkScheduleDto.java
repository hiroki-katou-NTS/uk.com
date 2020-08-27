package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;

/**
 * The class OutputStandardSettingOfDailyWorkSchedule dto
 * @author LienPTK
 *
 */
@Data
public class OutputStandardSettingOfDailyWorkScheduleDto implements OutputStandardSettingOfDailyWorkSchedule.MementoSetter {
	/**
	 *	項目選択種類
	 */
	private int selection;

	/**
	 *	会社ID
	 */
	private String companyId;

	/**
	 *	出力項目
	 */
	private List<OutputItemDailyWorkScheduleDto> outputItemDailyWorkSchedules;

	@Override
	public void setOutputItemDailyWorkSchedules(List<OutputItemDailyWorkSchedule> outputItem) {
		// TODO
	}

}
