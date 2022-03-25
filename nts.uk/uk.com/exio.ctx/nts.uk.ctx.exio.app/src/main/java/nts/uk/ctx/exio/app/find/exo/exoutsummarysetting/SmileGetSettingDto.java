package nts.uk.ctx.exio.app.find.exo.exoutsummarysetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SmileGetSettingDto {
	
	private boolean settingExist;
	private OutConditionSetDto condSet;
}
