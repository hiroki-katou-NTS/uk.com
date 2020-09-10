package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

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
	private List<OutputItemSettingDto> outputItemDailyWorkSchedules;

	@Override
	public void setOutputItems(List<OutputItemDailyWorkSchedule> outputItem) {
		this.outputItemDailyWorkSchedules = outputItem.stream()
				.map(domain -> {
					OutputItemSettingDto dto = new OutputItemSettingDto();
					dto.setCode(String.valueOf(domain.getItemCode().v()));
					dto.setName(domain.getItemName().v());
					dto.setLayoutId(domain.getLayoutId());
					return dto;
				})
				.collect(Collectors.toList());
	}

	/**
	 * To standard dto.
	 *
	 * @param domain the domain
	 * @return the output standard setting of daily work schedule dto
	 */
	public static OutputStandardSettingOfDailyWorkScheduleDto toStandardDto(OutputStandardSettingOfDailyWorkSchedule domain) {
		OutputStandardSettingOfDailyWorkScheduleDto dto = new OutputStandardSettingOfDailyWorkScheduleDto();
		domain.setMemento(dto);
		return dto;
	}
	

}
