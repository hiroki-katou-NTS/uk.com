package nts.uk.screen.at.app.query.kcp013;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireWorkingHoursDto {

	private List<WorkTimeSetting> listWorkTime;

	private List<PredetemineTimeSetting> predetemineTimeSettings;

}
