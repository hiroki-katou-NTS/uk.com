package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport_Old;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.CalcStampMiss;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.OverrideSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class HolidayThreeProcessImpl implements HolidayThreeProcess {
	@Inject
	private WorkTypeRepository workTypeRep;
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	@Inject
	private PredetemineTimeSettingRepository workTimeSetRepository;
	@Inject
	private WithdrawalAppSetRepository withdrawalAppSetRepository;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private WorkdayoffFrameRepository breaktimeFrameRep;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	// 03-02_実績超過チェック
	@Override
	public CaculationTime checkCaculationActualExcess(int prePostAtr, int appType, String employeeID,
			String companyID, GeneralDate appDate,
			CaculationTime breakTime, String siftCD,Integer calTime, boolean isCalculator, boolean actualExceedConfirm) {
		if(!commonOvertimeHoliday.checkCodition(prePostAtr,companyID, isCalculator)){
			return breakTime;
		}
		String employeeName = employeeAdapter.getEmployeeName(employeeID);
		//Imported(申請承認)「勤務実績」を取得する
		RecordWorkInfoImport_Old recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID,appDate);
		Optional<PredetemineTimeSetting> workTimeSetOtp = workTimeSetRepository.findByWorkTimeCode(companyID, siftCD);
		if (workTimeSetOtp.isPresent()) {
			PredetemineTimeSetting workTimeSet = workTimeSetOtp.get();
			
			if(iOvertimePreProcess.checkTimeDay(appDate.toString("yyyy/MM/dd"),workTimeSet)){
				// 03-02-3_当日の場合
				breakTime = checkHolidayWorkOnDay(companyID, employeeID, appDate.toString("yyyy/MM/dd"), siftCD, breakTime,recordWorkInfoImport,calTime, employeeName, actualExceedConfirm);
			}else{
				// 03-02-2_当日以外の場合
				breakTime = this.checkOutSideTimeTheDay(companyID, employeeID, appDate.toString("yyyy/MM/dd"), siftCD, breakTime,recordWorkInfoImport,calTime,employeeName);
			}
		}
		return breakTime;
	}
	// 03-02-2_当日以外の場合
	@Override
	public CaculationTime checkOutSideTimeTheDay(String companyID, String employeeID, String appDate,
			String siftCD, CaculationTime breakTime,
			RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName) {
		Optional<WorkType> workType = workTypeRep.findByPK(companyID, recordWorkInfoImport.getWorkTypeCode());
		if(workType.isPresent()){
			if(workType.get().getDailyWork().isHolidayWork()){
				// 03-02-2-1_当日以外_休日出勤の場合
				this.checkOutSideTimeTheDayForHoliday(companyID, employeeID, appDate, siftCD, breakTime, recordWorkInfoImport, calTime,employeeName);
			}else{
				// 03-02-2-2_当日以外_休日の場合
				this.checkOutSideTimeTheDayNoForHoliday(companyID, employeeID, appDate, siftCD, breakTime, recordWorkInfoImport, calTime,employeeName);
			}
		}
		return breakTime;
	}
	/* (non-Javadoc)
	 * 03-02-2-1_当日以外_休日出勤の場合
	 * @see nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayThreeProcess#checkOutSideTimeTheDayForHoliday(java.lang.String, java.lang.String, java.lang.String, nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting, java.lang.String, java.util.List, nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport)
	 */
	@Override
	public CaculationTime checkOutSideTimeTheDayForHoliday(String companyID, String employeeID, String appDate,
			String siftCD, CaculationTime breakTime,
			RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName) {
		if(recordWorkInfoImport != null){
			//打刻漏れチェック
			if(recordWorkInfoImport.getAttendanceStampTimeFirst() != null && recordWorkInfoImport.getLeaveStampTimeFirst() != null){
				//就業時間帯チェック
				 if(siftCD.equals(recordWorkInfoImport.getWorkTimeCode())){
					 //Imported(申請承認)「実績内容」.就業時間帯コード=画面上の就業時間帯
					 checkTimeThanCalTimeReally(breakTime,recordWorkInfoImport,employeeName,appDate,companyID);
				 }else{
					 //Imported(申請承認)「実績内容」.就業時間帯コード != 画面上の就業時間帯
					 //画面上の申請時間＞Imported(申請承認)「計算休出時間」.休出時間
					if(breakTime.getApplicationTime() != null && breakTime.getApplicationTime() > calTime){
						Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
						throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
					}else{
						breakTime.setCaculationTime(calTime != null ? Integer.toString(calTime) : null);
					}
				 }
			}else{
				Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
				throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
			}
		}
		return breakTime;
	}
	
	/* (non-Javadoc)
	 * 03-02-2-2_当日以外_休日の場合
	 * @see nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayThreeProcess#checkOutSideTimeTheDayNoForHoliday(java.lang.String, java.lang.String, java.lang.String, java.lang.String, nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime, nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport, java.lang.Integer)
	 */
	@Override
	public CaculationTime checkOutSideTimeTheDayNoForHoliday(String companyID, String employeeID, String appDate,
			String siftCD, CaculationTime breakTime, RecordWorkInfoImport_Old recordWorkInfoImport, Integer calTime,String employeeName) {
		if(recordWorkInfoImport != null){
			//打刻漏れチェック
			if(recordWorkInfoImport.getAttendanceStampTimeFirst() != null && recordWorkInfoImport.getLeaveStampTimeFirst() != null){
				 //Imported(申請承認)「実績内容」.就業時間帯コード != 画面上の就業時間帯
				 //画面上の申請時間＞Imported(申請承認)「計算休出時間」.休出時間
				if(breakTime.getApplicationTime() != null && breakTime.getApplicationTime() > calTime){
					Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
					throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "",
							String.valueOf(breakTime.getFrameNo()), String.valueOf(breakTime.getErrorCode()));
				}else{
					breakTime.setCaculationTime(calTime != null ? Integer.toString(calTime) : null);
				}
			}else{
				Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
				throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
			}
		}
		return breakTime;
	}
	/* (non-Javadoc)
	 * 03-02-3_当日の場合
	 * @see nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayThreeProcess#checkHolidayWorkOnDay(java.lang.String, java.lang.String, java.lang.String, nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting, java.lang.String, java.util.List, nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport)
	 */
	@Override
	public CaculationTime checkHolidayWorkOnDay(String companyID, String employeeID, String appDate,
			String siftCD, CaculationTime breakTime,
			RecordWorkInfoImport_Old recordWorkInfoImport,Integer calTime,String employeeName, boolean actualExceedConfirm) {
		Optional<WorkType> workType = workTypeRep.findByPK(companyID, recordWorkInfoImport.getWorkTypeCode());
		if(workType.isPresent()){
			if(workType.get().getDailyWork().isHolidayWork()){
				// 03-02-3-1_当日_休日出勤の場合 
				this.checkOnDayTheDayForHolidayWork(companyID, employeeID, appDate, siftCD, breakTime, recordWorkInfoImport, calTime,employeeName, actualExceedConfirm);
			}else{
				//03-02-3-2_当日_休日の場合
				this.checkOutSideTimeTheDayNoForHoliday(companyID, employeeID, appDate, siftCD, breakTime, recordWorkInfoImport, calTime,employeeName);
			}
		}
		return breakTime;
	}
	// 03-02-3-1_当日_休日出勤の場合
	@Override
	public void checkOnDayTheDayForHolidayWork(String companyID, String employeeID, String appDate, String siftCD,
			CaculationTime breakTime, RecordWorkInfoImport_Old recordWorkInfoImport, Integer calTime,String employeeName, boolean actualExceedConfirm) {
		// ドメインモデル「休出申請設定」を取得
		Optional<WithdrawalAppSet> withdrawalApp =	withdrawalAppSetRepository.getWithDraw();
		
		// ドメインモデル「残業休出申請共通設定」を取得
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.HOLIDAY_WORK_APPLICATION.value);
		if(recordWorkInfoImport != null){
			//打刻漏れチェック
			if(recordWorkInfoImport.getAttendanceStampTimeFirst() != null && recordWorkInfoImport.getLeaveStampTimeFirst() != null){
				if(withdrawalApp.isPresent() && withdrawalApp.get().getOverrideSet().equals(OverrideSet.TIME_OUT_PRIORITY)){
					if(siftCD != null && siftCD.equals(recordWorkInfoImport.getWorkTimeCode())){
						this.checkTimeThanCalTimeReally(breakTime, recordWorkInfoImport,employeeName,appDate,companyID);
					}
				}
				// Imported(申請承認)「実績内容」.就業時間帯コード != 画面上の就業時間帯
				// 画面上の申請時間＞Imported(申請承認)「計算休出時間」.休出時間
				if (breakTime.getApplicationTime() != null && breakTime.getApplicationTime() > calTime) {
					Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());					
					if (AppDateContradictionAtr.CHECKNOTREGISTER.equals(overtimeRestAppCommonSet.get().getPerformanceExcessAtr())) {
						throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
					} else if (AppDateContradictionAtr.CHECKREGISTER.equals(overtimeRestAppCommonSet.get().getPerformanceExcessAtr())) {
						if (!actualExceedConfirm) {
							throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "","confirm");
						}
					}				
				} else {
					// 
					breakTime.setCaculationTime(calTime != null ? Integer.toString(calTime) : null);
				}
			}else{
				if(withdrawalApp.isPresent() && withdrawalApp.get().getCalStampMiss().equals(CalcStampMiss.CAN_NOT_REGIS)){
					Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
					throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
				}else{
					if (breakTime.getApplicationTime() != null && breakTime.getApplicationTime() > calTime) {
						//画面上の申請時間＞Imported(申請承認)「計算休出時間」.休出時間
						Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
						if (AppDateContradictionAtr.CHECKNOTREGISTER.equals(overtimeRestAppCommonSet.get().getPerformanceExcessAtr())) {
							throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
						} else if (AppDateContradictionAtr.CHECKREGISTER.equals(overtimeRestAppCommonSet.get().getPerformanceExcessAtr())) {
							if (!actualExceedConfirm) {
								throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "","confirm");
							}
						}
					} else {
						//画面上の申請時間＜＝Imported(申請承認)「計算休出時間」.休出時間
						breakTime.setCaculationTime(calTime != null ? Integer.toString(calTime) : null);
					}
				}
			}
		}
		
	}
	// 03-02-3-2_当日_休日の場合
	@Override
	public void checkDayIsHoliday(String companyID, String employeeID, String appDate, String siftCD,
			CaculationTime breakTime, RecordWorkInfoImport_Old recordWorkInfoImport, Integer calTime,String employeeName) {
		// ドメインモデル「休出申請設定」を取得
		Optional<WithdrawalAppSet> withdrawalApp =	withdrawalAppSetRepository.getWithDraw();
		if(recordWorkInfoImport.getAttendanceStampTimeFirst() != null && recordWorkInfoImport.getLeaveStampTimeFirst() != null){
			//出勤、退勤打刻あり
			if (breakTime.getApplicationTime() != null && breakTime.getApplicationTime() > calTime) {
				//画面上の申請時間＞Imported(申請承認)「計算休出時間」.休出時間
				Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
				throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
			} else {
				//画面上の申請時間＜＝Imported(申請承認)「計算休出時間」.休出時間
				breakTime.setCaculationTime(calTime != null ? Integer.toString(calTime) : null);
			}
			
		}else{
			if(withdrawalApp.isPresent() && withdrawalApp.get().getCalStampMiss().equals(CalcStampMiss.CAN_NOT_REGIS)){
				Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
				throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
			}else{
				if ( breakTime.getApplicationTime() != null &&  breakTime.getApplicationTime() > calTime) {
					//画面上の申請時間＞Imported(申請承認)「計算休出時間」.休出時間
					Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
					throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
				} else {
					//画面上の申請時間＜＝Imported(申請承認)「計算休出時間」.休出時間
					breakTime.setCaculationTime(calTime != null ? Integer.toString(calTime) : null);
				}
			}
		}
		
	}
	
	private void checkTimeThanCalTimeReally(CaculationTime breakTime,
			RecordWorkInfoImport_Old recordWorkInfoImport,String employeeName,String appDate,String companyID){
		/*for(OvertimeInputCaculation holidayCal : recordWorkInfoImport.getOvertimeHolidayCaculation()){
			 if(holidayCal.getFrameNo() == breakTime.getFrameNo()){
				 //画面上の申請時間＞Imported(申請承認)「実績内容」.休出時間
				 if(breakTime.getApplicationTime() != null &&  breakTime.getApplicationTime() > holidayCal.getResultCaculation()){
					 Optional<WorkdayoffFrame> workDayoffFrame = breaktimeFrameRep.findWorkdayoffFrame(new CompanyId(companyID), breakTime.getFrameNo());
						throw new BusinessException("Msg_423",employeeName,appDate,workDayoffFrame.isPresent() ? workDayoffFrame.get().getWorkdayoffFrName().toString() : "");
				 }else{
					 breakTime.setCaculationTime(holidayCal.getResultCaculation() != null ? Integer.toString(holidayCal.getResultCaculation()) : null);
				 }
			 }
		 }*/
	}
	@Override
	public CaculationTime checkCaculationActualExcessForApprover(int prePostAtr, int appType, String employeeID,
			String companyID, GeneralDate appDate, CaculationTime breakTimeInput, String siftCD, Integer calTime, boolean isCalculator) {
		// 03-02-1_チェック条件
		if(!commonOvertimeHoliday.checkCodition(prePostAtr, companyID, isCalculator)){
			return breakTimeInput;
		}
		//Imported(申請承認)「勤務実績」を取得する
		//RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID,appDate);
		if(breakTimeInput.getApplicationTime() != null && breakTimeInput.getApplicationTime() > 0){
			breakTimeInput.setErrorCode(2);
		}
		breakTimeInput.setCaculationTime(calTime != null ? Integer.toString(calTime) : null);
		return breakTimeInput;
	}
	
	
}
