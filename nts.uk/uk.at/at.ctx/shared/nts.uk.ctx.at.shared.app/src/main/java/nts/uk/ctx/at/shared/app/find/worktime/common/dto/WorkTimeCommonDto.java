package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.PredetemineTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;

@Getter
@Setter
public class WorkTimeCommonDto {


	/** The predseting. */
	private PredetemineTimeSettingDto predseting;
	
	/** The worktime setting. */
	private WorkTimeSettingDto worktimeSetting;
}
