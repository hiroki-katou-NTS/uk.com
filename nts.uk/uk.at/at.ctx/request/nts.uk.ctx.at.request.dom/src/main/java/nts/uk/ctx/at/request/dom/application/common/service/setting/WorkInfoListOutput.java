package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkInfoListOutput {
	
	// 勤務種類リスト：List<勤務種類>
	
	private List<WorkType> workTypes = new ArrayList<>();
	
	// 就業時間帯リスト：List<就業時間帯の設定>
	
	private List<WorkTimeSetting> workTimes = new ArrayList<>();
}
