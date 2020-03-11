package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anhdt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkInfoDto {
	private WorkTimeSettingDto workTime;
	private WorkTypeDto workType;
}
