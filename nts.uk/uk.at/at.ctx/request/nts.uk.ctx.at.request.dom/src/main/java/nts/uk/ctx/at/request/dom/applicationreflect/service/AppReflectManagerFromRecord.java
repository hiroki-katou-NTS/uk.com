package nts.uk.ctx.at.request.dom.applicationreflect.service;
import java.util.List;
/*import java.util.Optional;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.SetInforReflAprResultImport;*/

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.applicationreflect.service.ProcessStateReflect;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing.ExecutionTypeExImport;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.arc.time.calendar.period.DatePeriod;

public interface AppReflectManagerFromRecord {
	/**
	 * 申請反映Mgrクラス
	 * @param workId: 実行ID
	 * @param workAtr 実行区分
	 * @param workDate ・対象期間開始日 ・対象期間終了日
	 * @return True: 完了処理、False：　中断
	 */
	@SuppressWarnings("rawtypes")
	ProcessStateReflect applicationRellect(String workId, DatePeriod workDate, AsyncCommandHandlerContext asyncContext);	
	/**
	 * 社員の申請を反映
	 * @param workId
	 * @param sid
	 * @param datePeriod
	 * @return
	 */
	void reflectAppOfEmployee(String workId, String sid, DatePeriod datePeriod, RequestSetting requesSetting,
			ExecutionTypeExImport refAppResult, InformationSettingOfEachApp reflectSetting );
	/**
	 * 申請の取得_OLD
	 * @param sid 社員ID
	 * @param datePeriod 期間
	 * @param exeType 実行種別
	 * @return
	 */
	List<Application> getApps(String sid, DatePeriod datePeriod, ExecutionTypeExImport exeType);
	/**
	 * 社員の申請を反映
	 * @param workId
	 * @param sid
	 * @param datePeriod
	 * @return
	 */
	ProcessStateReflect reflectAppOfEmployeeTotal(String workId, String sid, DatePeriod datePeriod);
	void reflectAppOfAppDate(String workId, String sid, ExecutionTypeExImport refAppResult,
			InformationSettingOfEachApp reflectSetting, DatePeriod appDatePeriod);
	
	void reflectApplication(List<String> lstID);
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請反映.申請反映Mgrクラス.アルゴリズム.申請の取得.申請の取得
	 * @param sid
	 * @param datePeriod
	 * @param exeType
	 * @return
	 */
	List<Application> getAppsForReflect(String sid, DatePeriod datePeriod, ExecutionTypeExImport exeType);
}
