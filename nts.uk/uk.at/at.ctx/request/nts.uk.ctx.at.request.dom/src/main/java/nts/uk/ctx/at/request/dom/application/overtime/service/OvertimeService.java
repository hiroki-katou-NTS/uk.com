package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;

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
	 * Refactor5
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
	public DisplayInfoOverTime calculate(String companyId,
			String employeeId,
			Optional<GeneralDate> dateOp, 
			PrePostInitAtr prePostInitAtr,
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
	public CaculationOutput getCalculation(String companyId,
			String employeeId,
			Optional<GeneralDate> dateOp, 
			PrePostInitAtr prePostInitAtr,
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
	public DisplayInfoOverTime getInitData(String companyId,
			Optional<GeneralDate> dateOp,
			OvertimeAppAtr overtimeAppAtr,
			AppDispInfoStartupOutput appDispInfoStartupOutput,
			Optional<Integer> startTimeSPR,
			Optional<Integer> endTimeSPR,
			Boolean isProxy
			);
	
}
