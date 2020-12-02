package nts.uk.ctx.at.function.app.command.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;

/**
 * The Class OutputStandardSettingOfDailyWorkScheduleCommand.
 *
 * @author LienPTK
 */
@Data
public class OutputStandardSettingOfDailyWorkScheduleCommand implements OutputStandardSettingOfDailyWorkSchedule.MementoGetter {
	
	/** 	項目選択種類. */
	private int selection;

	/** 	会社ID. */
	private String companyId;

	/** 	出力項目. */
	private List<OutputItemDailyWorkScheduleCommand> outputItems;

	/**
	 * Gets the output items.
	 *
	 * @return the output items
	 */
	@Override
	public List<OutputItemDailyWorkSchedule> getOutputItems() {
		return this.outputItems.stream()
				.map(t -> new OutputItemDailyWorkSchedule(t))
				.collect(Collectors.toList());
	}

	public OutputStandardSettingOfDailyWorkScheduleCommand(int selection, String companyId,
			List<OutputItemDailyWorkScheduleCommand> outputItems) {
		super();
		this.selection = selection;
		this.companyId = companyId;
		this.outputItems = outputItems;
	}
}
