package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;

public interface OvertimeSixProcess {
	
	/**
	 * 06-02_残業時間を取得
	 * @param companyID
	 * @param employeeId
	 * @param appDate
	 * @param appType
	 * @return
	 */
	public List<CaculationTime> getCaculationOvertimeHours(String companyID,String employeeId, String appDate,int appType,List<CaculationTime> overtimeHours,List<OvertimeInputCaculation> overtimeInputCaculations);
	/**
	 * 06-02-1_事前申請を取得
	 * @return
	 */
	public List<CaculationTime> getAppOvertimeHoursPre(String companyID,String employeeId, String appDate,int appType,List<CaculationTime> overtimeHours);
	
	/**
	 * 06-03-1_加給事前申請を取得
	 * @return
	 */
	public List<CaculationTime> getAppBonustimePre(String companyID,String employeeId, String appDate,int appType,List<CaculationTime> caculationTimes);
	/**
	 * 06-04_計算実績超過チェック
	 */
	// public CaculationTime checkCaculationActualExcess(int prePostAtr,int appType,String employeeID,String companyID,ApprovalFunctionSetting approvalFunctionSetting,GeneralDate appDate,List<CaculationTime> overTimeInputs, String siftCD,List<OvertimeInputCaculation> overtimeInputCaculations);
	
	/**
	 * 06-04-1_チェック条件
	 * @param prePostAtr
	 * @return
	 */
	public boolean checkCondition(int prePostAtr, int appType,String companyID);
	
	/**
	 * 06-04-2_当日以外の場合
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param requestAppDetailSetting
	 * @param siftCD
	 * @param overtimeHours
	 * @return
	 */
	// public List<CaculationTime> checkOutSideTimeTheDay(String companyID,String employeeID,String appDate,ApprovalFunctionSetting approvalFunctionSetting, String siftCD,List<CaculationTime> overtimeHours,RecordWorkInfoImport recordWorkInfoImport,List<OvertimeInputCaculation> overtimeInputCaculations);
	
	/**
	 * 06-04-3_当日の場合
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @param requestAppDetailSetting
	 * @param siftCD
	 * @param overtimeHours
	 * @return
	 */
	// public List<CaculationTime> checkDuringTheDay(String companyID,String employeeID,String appDate,ApprovalFunctionSetting approvalFunctionSetting, String siftCD,List<CaculationTime> overtimeHours,RecordWorkInfoImport recordWorkInfoImport,List<OvertimeInputCaculation> overtimeInputCaculations);
	
	public List<OverTimeInput> convert(CaculationTime caculationTime);
}
