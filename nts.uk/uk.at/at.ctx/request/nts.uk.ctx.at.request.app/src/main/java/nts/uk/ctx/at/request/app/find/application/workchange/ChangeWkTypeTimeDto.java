package nts.uk.ctx.at.request.app.find.application.workchange;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.workchange.output.ChangeWkTypeTimeOutput;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeWkTypeTimeDto {
	/**
	 * 就業時間帯の必須区分
	 */
	private Integer setupType;
	
	/**
	 * ドメインモデル「所定時間設定」<Optional>
	 */
	private PredetemineTimeSettingDto opPredetemineTimeSetting;
	
	public static ChangeWkTypeTimeDto fromDomain(ChangeWkTypeTimeOutput changeWkTypeTimeOutput) {
		ChangeWkTypeTimeDto changeWkTypeTimeDto = new ChangeWkTypeTimeDto();
		if(changeWkTypeTimeOutput.getOpPredetemineTimeSetting().isPresent()) {
			PredetemineTimeSettingDto predetemineTimeSettingDto = new PredetemineTimeSettingDto();
			changeWkTypeTimeOutput.getOpPredetemineTimeSetting().get().saveToMemento(predetemineTimeSettingDto);
			changeWkTypeTimeDto.setOpPredetemineTimeSetting(predetemineTimeSettingDto);
		}
		changeWkTypeTimeDto.setSetupType(changeWkTypeTimeOutput.getSetupType().value);
		return changeWkTypeTimeDto;
	}
}
