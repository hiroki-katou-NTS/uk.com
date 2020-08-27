package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;

@Data
public class FreeSettingOfOutputItemForDailyWorkScheduleDto
		implements FreeSettingOfOutputItemForDailyWorkSchedule.MementoSetter {
	/**
	 *	項目選択種類
	 */
	private int selection;

	/**
	 *	会社ID
	 */
	private String companyId;

	/**
	 * 	社員ID
	 */
	private String employeeId;

	/**
	 *	出力項目
	 */
	private List<OutputItemDailyWorkScheduleDto> outputItemDailyWorkSchedules;

	@Override
	public void setOutputItemDailyWorkSchedules(List<OutputItemDailyWorkSchedule> outputItem) {
		// TODO Auto-generated method stub
		
	}
}
