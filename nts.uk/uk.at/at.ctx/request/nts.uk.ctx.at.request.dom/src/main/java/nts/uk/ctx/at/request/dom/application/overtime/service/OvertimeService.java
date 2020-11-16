package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.CheckBeforeOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public interface OvertimeService {
	/**
	 * 02_残業区分チェック 
	 * @param url
	 * @return
	 */
	public int checkOvertimeAtr(String url);
	
	/**
	 * 07_勤務種類取得
	 * @param companyID
	 * @param employeeID
	 * @param personalLablorCodition
	 * @param requestAppDetailSetting
	 * @return
	 */
	// public List<WorkTypeOvertime> getWorkType(String companyID,String employeeID,ApprovalFunctionSetting approvalFunctionSetting,Optional<AppEmploymentSetting> appEmploymentSettings);
	
	/**
	 * 08_就業時間帯取得
	 * @param companyID
	 * @param employeeID
	 * @param personalLablorCodition
	 * @param requestAppDetailSetting
	 * @return
	 */
	// public List<SiftType> getSiftType(String companyID,String employeeID,ApprovalFunctionSetting approvalFunctionSetting,GeneralDate baseDate);
	
	/**
	 * 09_勤務種類就業時間帯の初期選択をセットする
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @param workTypes
	 * @param siftTypes
	 * @return
	 */
	public WorkTypeAndSiftType getWorkTypeAndSiftTypeByPersonCon(String companyID,String employeeID,GeneralDate baseDate,List<WorkTypeOvertime> workTypes, List<SiftType> siftTypes);
	
	
	void CreateOvertime(AppOverTime_Old domain, Application newApp);
	
	/**
	 * 起動時の36協定時間の状態を取得する
	 * @param appOvertimeDetail
	 * @return
	 */
	public AgreementTimeStatusOfMonthly getTime36Detail(AppOvertimeDetail appOvertimeDetail);
	
	/**
	 * Refactor5 計算を実行する
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.ユースケース
	 * @param companyId
	 * @param employeeId
	 * @param dateOp
	 * @param overtimeAppAtr
	 * @param overtimeLeaveAppCommonSet
	 * @param advanceApplicationTime
	 * @param achieveApplicationTime
	 * @param workContent
	 * @return 残業申請の表示情報
	 */
	public DisplayInfoOverTime calculate(
			String companyId,
			String employeeId,
			Optional<GeneralDate> dateOp, 
			PrePostAtr prePostInitAtr,
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet,
			ApplicationTime advanceApplicationTime, 
			ApplicationTime achieveApplicationTime,
			WorkContent workContent);
	/**
	 * Refactor5 19_計算処理
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.アルゴリズム.19_計算処理
	 * @param companyId
	 * @param employeeId
	 * @param dateOp
	 * @param overtimeAppAtr
	 * @param overtimeLeaveAppCommonSet
	 * @param advanceApplicationTime
	 * @param achieveApplicationTime
	 * @param workContent
	 * @return
	 */
	public CaculationOutput getCalculation(
			String companyId,
			String employeeId,
			Optional<GeneralDate> dateOp, 
			PrePostAtr prePostInitAtr,
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet,
			ApplicationTime advanceApplicationTime, 
			ApplicationTime achieveApplicationTime,
			WorkContent workContent);
	
	/**
	 * Refactor5 01_初期起動の処理
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.アルゴリズム.01_初期起動の処理
	 * @param companyId
	 * @param employeeId
	 * @param overtimeAppAtr
	 * @return
	 */
	public DisplayInfoOverTime getInitData(
			String companyId,
			Optional<GeneralDate> dateOp,
			OvertimeAppAtr overtimeAppAtr,
			AppDispInfoStartupOutput appDispInfoStartupOutput,
			Optional<Integer> startTimeSPR,
			Optional<Integer> endTimeSPR,
			Boolean isProxy
			);
	/**
	 * Refactor5 16_勤務種類・就業時間帯を選択する
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.アルゴリズム.16_勤務種類・就業時間帯を選択する
	 * @param companyId
	 * @param dateOp
	 * @param workTypeCode
	 * @param workTimeCode
	 * @param startTimeSPR
	 * @param endTimeSPR
	 * @param actualContentDisplay
	 * @param overtimeAppSet
	 * @return 
	 */
	public SelectWorkOutput selectWork(
			String companyId,
			String employeeId,
			Optional<GeneralDate> dateOp,
			WorkTypeCode workTypeCode,
			WorkTimeCode workTimeCode,
			Optional<Integer> startTimeSPR,
			Optional<Integer> endTimeSPR,
			Optional<ActualContentDisplay> actualContentDisplay,
			OvertimeAppSet overtimeAppSet
			);
	
	
	/**
	 * Refactor5 申請時の乖離時間をチェックする
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.申請時の乖離時間をチェックする
	 * @param require
	 * @param appType
	 * @param appOverOptional
	 * @param appHolidayOptional
	 */
	public void checkDivergenceTime(
			Boolean require,
			ApplicationType appType,
			Optional<AppOverTime> appOverOptional,
			Optional<AppOverTime> appHolidayOptional,
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet
			);
	/**
	 * Refactor5 03_登録前エラーチェック
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.アルゴリズム.03_登録前エラーチェック
	 * @param require
	 * @param companyId
	 * @param displayInfoOverTime
	 * @param appOverTime
	 * @return
	 */
	public CheckBeforeOutput checkErrorRegister(
			Boolean require,
			String companyId,
			DisplayInfoOverTime displayInfoOverTime,
			AppOverTime appOverTime
			);
	/**
	 * Refactor5 01_詳細画面起動の処理
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.B：残業申請（詳細・照会）.アルゴリズム.01_詳細画面起動の処理
	 * @param companyId
	 * @param appId
	 * @param appDispInfoStartupOutput
	 * @return
	 */
	public DetailOutput  getDetailData(
			String companyId,
			String appId,
			AppDispInfoStartupOutput appDispInfoStartupOutput);
	/**
	 * Refactor5
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.B：残業申請（詳細・照会）.ユースケース
	 * @param companyId
	 * @param appId
	 * @param appDispInfoStartupOutput
	 * @return
	 */
	public DetailOutput initDetailScreen(
			String companyId,
			String appId,
			AppDispInfoStartupOutput appDispInfoStartupOutput);
	/**
	 * Refactor5 15_詳細画面の登録前エラーチェック
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.B：残業申請（詳細・照会）.アルゴリズム.15_詳細画面の登録前エラーチェック
	 * @param require
	 * @param companyId
	 * @param appOverTime
	 * @param displayInfoOverTime
	 * @return
	 */
	public CheckBeforeOutput checkBeforeUpdate(
			Boolean require,
			String companyId,
			AppOverTime appOverTime,
			DisplayInfoOverTime displayInfoOverTime);
	/**
	 * Refactor5 
	 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.ユースケース
	 * @param companyId
	 * @param dateOp
	 * @param overtimeAppAtr
	 * @param appDispInfoStartupOutput
	 * @param startTimeSPR
	 * @param endTimeSPR
	 * @param isProxy
	 * @return
	 */
	public DisplayInfoOverTime startA(
			String companyId,
			Optional<GeneralDate> dateOp,
			OvertimeAppAtr overtimeAppAtr,
			AppDispInfoStartupOutput appDispInfoStartupOutput,
			Optional<Integer> startTimeSPR,
			Optional<Integer> endTimeSPR,
			Boolean isProxy
			);
	public DisplayInfoOverTime changeDate(
			String companyId,
			String employeeId,
			Optional<GeneralDate> dateOp,
			OvertimeAppAtr overtimeAppAtr,
			AppDispInfoStartupOutput appDispInfoStartupOutput,
			Optional<Integer> startTimeSPR,
			Optional<Integer> endTimeSPR,
			OvertimeAppSet overtimeAppSet,
			List<WorkType> worktypes
			);
}
