package nts.uk.ctx.pr.core.app.vacation.setting.retentionyearly.command.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly.UpperLimitSettingGetMemento;

@Getter
@Setter
public class UpperLimitSettingDto {
	private Integer retentionYearsAmount;
	private Integer maxDaysCumulation;
	
	public UpperLimitSetting toDomain() {
		return new UpperLimitSetting(new UpperLimitSettingGetMementoImpl(this));
	}
	
	public class UpperLimitSettingGetMementoImpl implements UpperLimitSettingGetMemento {
		private UpperLimitSettingDto dto;


		public UpperLimitSettingGetMementoImpl(UpperLimitSettingDto dto) {
			super();
			this.dto = dto;
		}

		@Override
		public RetentionYearsAmount getRetentionYearsAmount() {
			return new RetentionYearsAmount(dto.getRetentionYearsAmount());
		}

		@Override
		public MaxDaysRetention getMaxDaysCumulation() {
			return new MaxDaysRetention(dto.getMaxDaysCumulation());
		}

		
	}
}
