package nts.uk.ctx.at.schedule.app.find.shift.autocalsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalRestTimeSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.AutoCalSetting;

@Getter
@Setter
public class AutoCalRestTimeSettingDto implements AutoCalRestTimeSettingSetMemento {

	private AutoCalSettingDto restTime;

	/** The late night time. */
	// 休出深夜時間
	private AutoCalSettingDto lateNightTime;

	@Override
	public void setRestTime(AutoCalSetting restTime) {
		AutoCalSettingDto dto = new AutoCalSettingDto();
		restTime.saveToMemento(dto);
		this.restTime = dto;

	}

	@Override
	public void setLateNightTime(AutoCalSetting lateNightTime) {
		AutoCalSettingDto dto = new AutoCalSettingDto();
		lateNightTime.saveToMemento(dto);
		this.lateNightTime = dto;

	}

}
