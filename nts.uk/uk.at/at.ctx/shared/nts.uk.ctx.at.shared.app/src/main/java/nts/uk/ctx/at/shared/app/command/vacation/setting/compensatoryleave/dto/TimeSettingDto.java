package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.command.worktime.common.dto.DesignatedTimeDto;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CertainPeriodOfTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EnumTimeDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TimeSetting;

/** 時間設定 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeSettingDto {
	
	/** 一定時間 **/
	private int  certainPeriodofTime;
	
	/**指定時間 **/
	private DesignatedTimeDto designatedTime;
	
	/** 時間区分 **/
	private int enumTimeDivision;
	
	public TimeSetting toDomain() {
		return new TimeSetting(
				new CertainPeriodOfTime(new TimeOfDay(this.certainPeriodofTime)),
				designatedTime.toDomain(),
				EnumTimeDivision.valueOf(this.enumTimeDivision));
	}
	public static TimeSettingDto toDto(TimeSetting domain) {
		return new TimeSettingDto(
				domain.getCertainPeriodofTime().getCertainPeriodofTime().valueAsMinutes(),
				DesignatedTimeDto.toDto(domain.getDesignatedTime()),
				domain.getEnumTimeDivision().value);
	}
	
}
