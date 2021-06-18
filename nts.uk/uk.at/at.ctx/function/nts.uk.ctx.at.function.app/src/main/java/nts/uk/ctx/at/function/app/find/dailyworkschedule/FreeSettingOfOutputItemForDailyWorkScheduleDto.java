package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;

/**
 * The Class FreeSettingOfOutputItemForDailyWorkScheduleDto.
 * @author LienPTK
 */
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
	private List<OutputItemSettingDto> outputItemDailyWorkSchedules;

	@Override
	public void setOutputItemDailyWorkSchedules(List<OutputItemDailyWorkSchedule> outputItem) {
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
	 * To free setting dto.
	 *
	 * @param domain the domain
	 * @return the free setting of output item for daily work schedule dto
	 */
	public static FreeSettingOfOutputItemForDailyWorkScheduleDto toFreeSettingDto(FreeSettingOfOutputItemForDailyWorkSchedule domain) {
		FreeSettingOfOutputItemForDailyWorkScheduleDto dto = new FreeSettingOfOutputItemForDailyWorkScheduleDto();
		domain.setMemento(dto);
		return dto;
	}
}
