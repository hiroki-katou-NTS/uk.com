package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.IErrorCheckBeforeRegister;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceID;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeInputRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.service.output.RecordWorkOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
@Stateless
public class OvertimeSixProcessImpl implements OvertimeSixProcess{
	final String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private IErrorCheckBeforeRegister IErrorCheckBeforeRegister;
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	@Inject
	private OvertimeService overtimeService;
	@Inject
	private ApplicationRepository applicationRepository;
	@Inject
	private OvertimeRepository overtimeRepository;
	@Inject
	private OvertimeInputRepository overtimeInputRepository;
	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	
	/* 
	 * 06-01_色表示チェック
	 * @see nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess#checkDisplayColor(java.util.List, java.util.List, int, nts.arc.time.GeneralDateTime, nts.arc.time.GeneralDate, int, java.lang.String, java.lang.String, nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting)
	 */
	@Override
	public void checkDisplayColor(List<OverTimeInput> overTimeInputs,
			List<OvertimeInputCaculation> overtimeInputCaculations,int prePostAtr,GeneralDateTime inputDate,GeneralDate appDate, int appType,String employeeID,String companyID,RequestAppDetailSetting requestAppDetailSetting) {
		
		for(OverTimeInput overtimeInput : overTimeInputs ){
			for(OvertimeInputCaculation overtimeInputCaculation : overtimeInputCaculations){
					if(overtimeInput.getFrameNo() == overtimeInputCaculation.getFrameNo()){
						if(overtimeInput.getStartTime().v() == overtimeInputCaculation.getResultCaculation()){
							if(overtimeInput.getStartTime().v() == 0){
								continue;
							}else if(overtimeInput.getStartTime().v() > 0){
								// 03-01_事前申請超過チェック
								OvertimeCheckResult overtimeCheckResult = this.IErrorCheckBeforeRegister.preApplicationExceededCheck(overtimeInput.getCompanyID(),appDate, inputDate, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), overtimeInputCaculation.getAttendanceID(), overTimeInputs);
								// 06-04_計算実績超過チェック
								checkCaculationActualExcess(prePostAtr,appType,employeeID,companyID,requestAppDetailSetting);
							}
						}else{
							// in màu
						}
					}
			}
		}
		
	}
	
	/* 
	 *  06-04_計算実績超過チェック
	 * @see nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess#checkCaculationActualExcess(int, int, java.lang.String, java.lang.String, nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting)
	 */
	@Override
	public void checkCaculationActualExcess(int prePostAtr,int appType,String employeeID,String companyID,RequestAppDetailSetting requestAppDetailSetting) {
		boolean condition = checkCondition(prePostAtr,appType,companyID);
		if(condition){
			// 08_就業時間帯取得
			List<SiftType> siftTypes = overtimeService.getSiftType(companyID, employeeID, requestAppDetailSetting);
			// TODO
		}
		
	}
	/* 
	 * 06-04-1_チェック条件
	 * @see nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess#checkCondition(int, int, java.lang.String)
	 */
	@Override
	public boolean checkCondition(int prePostAtr,int appType,String companyID) {
		if(prePostAtr == PrePostAtr.POSTERIOR.value){
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSetting = overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, appType);
			if(overtimeRestAppCommonSetting.isPresent()){
				//ドメインモデル「残業休出申請共通設定」.実績表示区分チェック
				if(overtimeRestAppCommonSetting.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
					return true;
				}
			}
		}
		return false;
	}

	/* 
	 * 06-02_残業時間を取得
	 * @see nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess#getCaculationOvertimeHours(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<CaculationTime> getCaculationOvertimeHours(String companyID, String employeeId, String appDate,
			int appType) {
		List<CaculationTime> overtimeInputs = new ArrayList<>();
		/* 01-10_0時跨ぎチェック
		* TODO
		*/
		// 06-02-1_事前申請を取得
		overtimeInputs = getAppOvertimeHoursPre(companyID,employeeId,appDate,appType);
		
		/* 06-02-2_申請時間を取得
		* TODO
		*/
		getAppOvertimeCaculation(overtimeInputs);
		return overtimeInputs;
	}
	/* 
	 * 06-02-1_事前申請を取得
	 * @see nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess#getAppOvertimeHoursPre(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<CaculationTime> getAppOvertimeHoursPre(String companyID,String employeeId, String appDate,int appType) {
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSetting = overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, appType);
		if(overtimeRestAppCommonSetting.isPresent()){
			if(overtimeRestAppCommonSetting.get().getPreDisplayAtr().value == UseAtr.USE.value){
				List<Application> application = this.applicationRepository.getApp(employeeId,  GeneralDate.fromString(appDate, DATE_FORMAT), PrePostAtr.PREDICT.value, appType);
				if(application.size() > 0){
					Optional<AppOverTime> appOvertime = this.overtimeRepository.getAppOvertime(application.get(0).getCompanyID(), application.get(0).getApplicationID());
					if(appOvertime.isPresent()){
						List<OverTimeInput> overtimeInputs = overtimeInputRepository.getOvertimeInputByAttendanceId(appOvertime.get().getCompanyID(), appOvertime.get().getAppID(),AttendanceID.NORMALOVERTIME.value);
						List<CaculationTime> caculations = convertCaculation(overtimeInputs);
						return caculations;
					}
				}
			}
		}
		return null;
	}
	/* 06-02-2_申請時間を取得 */
	@Override
	public List<CaculationTime> getAppOvertimeCaculation(List<CaculationTime> caculationTimes) {
		// TODO Auto-generated method stub
		List<OvertimeInputCaculation> overtimeInputCaculations = initOvertimeInputCaculation();
		for(CaculationTime caculationTime : caculationTimes){
			for(OvertimeInputCaculation caculation :overtimeInputCaculations){
				if(caculationTime.getFrameNo() == caculation.getFrameNo()){
					caculationTime.setCaculationTime(Integer.toString(caculation.getResultCaculation()));
				}
			}
		}
		return null;
	}

	/* 
	 * 06-03_加給時間を取得
	 * @see nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess#getCaculationBonustime(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<CaculationTime> getCaculationBonustime(String companyID, String employeeId, String appDate, int appType) {
		
		// 06-03-1_加給事前申請を取得
		List<CaculationTime> overtimeInputs = getAppBonustimePre(companyID,employeeId,appDate, appType);
		return overtimeInputs;
		// 06-03-2_加給計算時間を取得
		// TODO
	}
	/* 
	 * 06-03-1_加給事前申請を取得
	 * @see nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeSixProcess#getAppBonustimePre(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<CaculationTime> getAppBonustimePre(String companyID, String employeeId, String appDate, int appType) {
		
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSetting = overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, appType);
		if(overtimeRestAppCommonSetting.isPresent()){
			if(overtimeRestAppCommonSetting.get().getBonusTimeDisplayAtr().value == UseAtr.USE.value){
				List<Application> application = this.applicationRepository.getApp(employeeId,  GeneralDate.fromString(appDate, DATE_FORMAT), PrePostAtr.PREDICT.value, appType);
				if(application.size() > 0){
					Optional<AppOverTime> appOvertime = this.overtimeRepository.getAppOvertime(application.get(0).getCompanyID(), application.get(0).getApplicationID());
					if(appOvertime.isPresent()){
						List<OverTimeInput> overtimeInputs = overtimeInputRepository.getOvertimeInputByAttendanceId(appOvertime.get().getCompanyID(), appOvertime.get().getAppID(),AttendanceID.BONUSPAYTIME.value);
						List<CaculationTime> caculations = convertCaculation(overtimeInputs);
						return caculations;
					}
				}
			}
		}
		return null;
	}
	
	/* 06-04-2_当日以外の場合 */
	@Override
	public List<OvertimeCheckResult> checkOutSideTimeTheDay(String companyID, String employeeID, String appDate,
			RequestAppDetailSetting requestAppDetailSetting, String siftCD,List<CaculationTime> overtimeHours ) {
		// TODO
		List<OvertimeCheckResult> result = new ArrayList<>();
		OvertimeCheckResult overtimeCheckResult = new OvertimeCheckResult();
		// Imported(申請承認)「計算残業時間」を取得する :TODO
		List<OvertimeInputCaculation> overtimeInputCaculations = initOvertimeInputCaculation();
		//Imported(申請承認)「勤務実績」を取得する
		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID, GeneralDate.fromString(appDate, DATE_FORMAT));
		if(recordWorkInfoImport.getAttendanceStampTimeFirst() != -1 && recordWorkInfoImport.getLeaveStampTimeFirst() != -1){
			// 打刻あり
			if(siftCD != recordWorkInfoImport.getWorkTimeCode()){
				// Imported(申請承認)「実績内容」.就業時間帯コード != 画面上の就業時間帯
				result = printColor(overtimeHours,overtimeInputCaculations);
			}else{
				// Imported(申請承認)「実績内容」.就業時間帯コード = 画面上の就業時間帯
				
			}
		}else{
			// 出勤または退勤打刻なし
			for(CaculationTime caculationTime : overtimeHours){
				if(caculationTime.getApplicationTime() == 0){
					overtimeCheckResult.setFrameNo(caculationTime.getFrameNo());
					overtimeCheckResult.setErrorCode(2);
					result.add(overtimeCheckResult);
				}
			}
		}
		return result;
	}
	/* 06-04-3_当日の場合 */
	@Override
	public List<OvertimeCheckResult> checkDuringTheDay(String companyID, String employeeID, String appDate,
			RequestAppDetailSetting requestAppDetailSetting, String siftCD, List<CaculationTime> overtimeHours) {
		List<OvertimeCheckResult> result = new ArrayList<>();
		List<OvertimeInputCaculation> overtimeInputCaculations = initOvertimeInputCaculation();
		//Imported(申請承認)「勤務実績」を取得する
		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID, GeneralDate.fromString(appDate, DATE_FORMAT));
		if(recordWorkInfoImport.getLeaveStampTimeFirst() == -1){
			// 退勤打刻なし
			result = printColor(overtimeHours, overtimeInputCaculations);
		}else{
			// 打刻あり
			if(siftCD != recordWorkInfoImport.getWorkTimeCode()){
				result = printColor(overtimeHours, overtimeInputCaculations);
			}else{
				result = printColor(overtimeHours, overtimeInputCaculations);
			}
			
		}
		return result;
	}
	
	/**
	 * @param overtimeInputs
	 * @return
	 */
	private List<CaculationTime> convertCaculation(List<OverTimeInput> overtimeInputs){
		List<CaculationTime> caculations = new ArrayList<>();
		for(OverTimeInput overtimeInput : overtimeInputs){
			CaculationTime caculationTime = new CaculationTime(overtimeInput.getCompanyID(), 
					overtimeInput.getAppID(),
					overtimeInput.getAttendanceID().value,
					overtimeInput.getFrameNo(),
					overtimeInput.getTimeItemTypeAtr().value,
					"",
					null,
					overtimeInput.getApplicationTime().v().toString(),
					null);
			caculations.add(caculationTime);
		}
		return caculations;
	}
	
	/**
	 * @return
	 */
	private List<OvertimeInputCaculation> initOvertimeInputCaculation(){
		List<OvertimeInputCaculation> overtimeInputCaculations = new ArrayList<>();
		for(int i = 0; i < 10 ;i++){
			OvertimeInputCaculation overtimeInputCaculation = new  OvertimeInputCaculation(1,i, 0);
			overtimeInputCaculations.add(overtimeInputCaculation);
		}
		
		return overtimeInputCaculations;
	}
	private List<OvertimeCheckResult> printColor(List<CaculationTime> overtimeHours,List<OvertimeInputCaculation> overtimeInputCaculations){
		List<OvertimeCheckResult> result = new ArrayList<>();
		OvertimeCheckResult overtimeCheckResult = new OvertimeCheckResult();
		for(CaculationTime caculationTime : overtimeHours){
			for(OvertimeInputCaculation overtimeInput :overtimeInputCaculations){
				if(caculationTime.getFrameNo() == overtimeInput.getFrameNo()){
					if(caculationTime.getApplicationTime()!= null && caculationTime.getApplicationTime() > overtimeInput.getResultCaculation()){
						overtimeCheckResult.setFrameNo(caculationTime.getFrameNo());
						overtimeCheckResult.setErrorCode(2);
						result.add(overtimeCheckResult);
					}
				}
			}
		}
		return result;
	}
	
}
