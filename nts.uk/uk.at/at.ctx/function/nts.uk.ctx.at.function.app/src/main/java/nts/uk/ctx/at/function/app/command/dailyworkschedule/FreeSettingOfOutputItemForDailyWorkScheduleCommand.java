package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;

@Data
public class FreeSettingOfOutputItemForDailyWorkScheduleCommand
		implements FreeSettingOfOutputItemForDailyWorkSchedule.MementoGetter {

	/**
	 * 項目選択種類
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
	private List<OutputItemDailyWorkScheduleCommand> outputItemDailyWorkSchedules;
	
	public List<OutputItemDailyWorkSchedule> getOutputItemDailyWorkSchedules() {
		return this.outputItemDailyWorkSchedules.stream()
				.map(t -> new OutputItemDailyWorkSchedule(t))
				.collect(Collectors.toList());
	}

	public FreeSettingOfOutputItemForDailyWorkScheduleCommand(int selection, String companyId, String employeeId,
			List<OutputItemDailyWorkScheduleCommand> outputItemDailyWorkSchedules) {
		super();
		this.selection = selection;
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.outputItemDailyWorkSchedules = outputItemDailyWorkSchedules;
	}
}
