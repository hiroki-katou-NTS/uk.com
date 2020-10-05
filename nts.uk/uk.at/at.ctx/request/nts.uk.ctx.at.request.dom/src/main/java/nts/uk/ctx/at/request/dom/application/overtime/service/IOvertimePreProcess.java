package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SWkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport_Old;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * 01_初期データ取得
 * @author Doan Duy Hung
 *
 */
public interface IOvertimePreProcess {
	/**
	 * 01-01_残業通知情報を取得
	 * @param appCommonSettingOutput
	 * @return
	 */
	public OvertimeInstructInfomation getOvertimeInstruct(AppCommonSettingOutput appCommonSettingOutput,String appDate,String employeeID);
	
	/**
	 * 01-03_残業枠を取得
	 * @param overtimeAtr
	 */
	public List<OvertimeWorkFrame> getOvertimeHours(int overtimeAtr,String companyID);
	
	/**
	 * 01-03_休出時間枠を取得
	 * @param companyID
	 * @return
	 */
	public List<WorkdayoffFrame> getBreaktimeFrame(String companyID);
	
	/**
	 * 01-10_0時跨ぎチェック
	 * @return
	 */
	public boolean displayBreaktime();
	
	/**
	 * 01-18_実績の内容を表示し直す
	 * @param prePostAtr
	 * @return
	 */
	public AppOvertimeReference getResultContentActual(int prePostAtr, String workType, String siftCode,String companyID,String employeeID,String appDate);
	
	/**
	 * 01-18-1_当日の実績の場合
	 * @param employeeID 社員ID
	 * @param date 申請日
	 * @param workTime 就業時間帯コード
	 * @param recordWorkInfoImport 実績内容
	 * @return
	 */
	public AppOvertimeReference getResultCurrentDay(String employeeID, GeneralDate date, String workType, String workTime, RecordWorkInfoImport_Old recordWorkInfoImport);
	
	/**
	 * 01-18-2_当日以外の実績場合
	 * @param employeeID 社員ID
	 * @param date 申請日
	 * @param workTime 就業時間帯コード
	 * @param recordWorkInfoImport 実績内容
	 * @return
	 */
	public AppOvertimeReference getResultOtherDay(String employeeID, GeneralDate date, String workType, String workTime, RecordWorkInfoImport_Old recordWorkInfoImport);
	
	/**
	 * @param employeeID
	 * @param siftCode
	 * @param companyID
	 * @param sWkpHistImport
	 * @return
	 */
	public Optional<BonusPaySetting> getBonusPaySetting(String employeeID,String siftCode,String companyID,SWkpHistImport sWkpHistImport);
	
	/**
	 * if is 当日の場合 return true
	 * @param appDate
	 * @param workTimeSet
	 * @return
	 */
	public boolean checkTimeDay(String appDate, PredetemineTimeSetting workTimeSet);
}
