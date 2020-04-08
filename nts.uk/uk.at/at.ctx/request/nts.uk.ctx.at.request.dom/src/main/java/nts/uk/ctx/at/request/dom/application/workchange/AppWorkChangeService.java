package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.request.dom.application.workchange.output.ChangeWkTypeTimeOutput;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkTypeWorkTimeSelect;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface AppWorkChangeService {
	
	public AppWorkChangeDispInfo getStartNew(String companyID, List<String> employeeIDLst, List<GeneralDate> dateLst);
	
	/**
	 * 勤務種類を取得する
	 * @param appEmploymentSetting ドメインモデル「雇用別申請承認設定」
	 * @return 勤務種類リスト
	 */
	public List<WorkType> getWorkTypeLst(AppEmploymentSetting appEmploymentSetting);
	
	/**
	 * 勤務種類・就業時間帯の初期選択項目を取得する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @param workTypeLst 勤務種類リスト
	 * @param workTimeLst 就業時間帯リスト
	 * @return
	 */
	public WorkTypeWorkTimeSelect initWorkTypeWorkTime(String companyID, String employeeID, GeneralDate date, 
			List<WorkType> workTypeLst, List<WorkTimeSetting> workTimeLst);
	
	/**
	 * 勤務種類・就業時間帯を変更する時
	 * @param companyID 会社ID
	 * @param workTypeCD 勤務種類コード
	 * @param workTimeCD 就業時間帯コード<Optional>
	 * @param appWorkChangeSet ドメインモデル「勤務変更申請設定」
	 * @return
	 */
	public ChangeWkTypeTimeOutput changeWorkTypeWorkTime(String companyID, String workTypeCD, Optional<String> workTimeCD, 
			AppWorkChangeSet appWorkChangeSet);
}
