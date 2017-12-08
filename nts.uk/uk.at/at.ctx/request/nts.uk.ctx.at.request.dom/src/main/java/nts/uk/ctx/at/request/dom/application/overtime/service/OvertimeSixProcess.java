package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;

public interface OvertimeSixProcess {
	
	/**
	 * 06-01_色表示チェック
	 * @param overTimeInputs
	 * @param overtimeInputCaculations
	 */
	public void checkDisplayColor(List<OverTimeInput> overTimeInputs,
			List<OvertimeInputCaculation> overtimeInputCaculations,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate,int appType,String employeeID,String companyID,RequestAppDetailSetting requestAppDetailSetting);
	/**
	 * 06-02_残業時間を取得
	 * @param companyID
	 * @param employeeId
	 * @param appDate
	 * @param appType
	 * @return
	 */
	public List<CaculationTime> getCaculationOvertimeHours(String companyID,String employeeId, String appDate,int appType);
	/**
	 * 06-02-1_事前申請を取得
	 * @return
	 */
	public List<CaculationTime> getAppOvertimeHoursPre(String companyID,String employeeId, String appDate,int appType);
	/**
	 * @return
	 * 06-02-2_申請時間を取得
	 */
	public List<CaculationTime> getAppOvertimeCaculation(List<CaculationTime> caculationTimes);
	/**
	 * 06-03_加給時間を取得
	 * @param companyID
	 * @param employeeId
	 * @param appDate
	 * @param appType
	 */
	public List<CaculationTime> getCaculationBonustime(String companyID,String employeeId, String appDate,int appType);
	
	/**
	 * 06-03-1_加給事前申請を取得
	 * @return
	 */
	public List<CaculationTime> getAppBonustimePre(String companyID,String employeeId, String appDate,int appType);
	/**
	 * 06-04_計算実績超過チェック
	 */
	public void checkCaculationActualExcess(int prePostAtr,int appType,String employeeID,String companyID,RequestAppDetailSetting requestAppDetailSetting);
	
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
	public List<OvertimeCheckResult> checkOutSideTimeTheDay(String companyID,String employeeID,String appDate,RequestAppDetailSetting requestAppDetailSetting, String siftCD,List<CaculationTime> overtimeHours);
	
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
	public List<OvertimeCheckResult> checkDuringTheDay(String companyID,String employeeID,String appDate,RequestAppDetailSetting requestAppDetailSetting, String siftCD,List<CaculationTime> overtimeHours);
}
