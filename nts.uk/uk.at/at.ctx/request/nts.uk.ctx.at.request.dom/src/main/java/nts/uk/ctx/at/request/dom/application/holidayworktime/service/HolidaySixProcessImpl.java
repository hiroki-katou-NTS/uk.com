package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;

@Stateless
public class HolidaySixProcessImpl implements HolidaySixProcess{
	@Inject
	private HolidayThreeProcess holidayThreeProcess;

	@Override
	public List<CaculationTime> checkDisplayColor(List<CaculationTime> breakTimeInputs,
			List<OvertimeInputCaculation> overtimeInputCaculations, int prePostAtr, GeneralDateTime inputDate,
			GeneralDate appDate, int appType, String employeeID, String companyID,
			ApprovalFunctionSetting approvalFunctionSetting, String siftCD) {
		
		for(CaculationTime overtimeInput : breakTimeInputs){
			for(OvertimeInputCaculation overtimeInputCaculation : overtimeInputCaculations){
					if(overtimeInput.getFrameNo() == overtimeInputCaculation.getFrameNo()){
						if(overtimeInput.getApplicationTime() != null && overtimeInput.getApplicationTime() != overtimeInputCaculation.getResultCaculation()){
							overtimeInput.setErrorCode(3); // 色定義名：計算値
						}
						if(overtimeInputCaculation.getResultCaculation() == 0){
							continue;
						}else if(overtimeInputCaculation.getResultCaculation() > 0){
							// 03-01_事前申請超過チェック
							OvertimeCheckResult overtimeCheckResult = this.holidayThreeProcess.preApplicationExceededCheck(overtimeInput.getCompanyID(),appDate, inputDate, EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), overtimeInputCaculation.getAttendanceID(), convert(overtimeInput));
							if(overtimeCheckResult.getErrorCode() != 0){
								overtimeInput.setErrorCode(overtimeCheckResult.getErrorCode());
							}
							// 06-04_計算実績超過チェック
//							List<CaculationTime> caculations = new ArrayList<>();
//							caculations.add(overtimeInput);
//							overtimeInput.setErrorCode(checkCaculationActualExcess(prePostAtr,appType,employeeID,companyID,approvalFunctionSetting,appDate,caculations,siftCD).getErrorCode());
						}
					}
			}
		}
		return breakTimeInputs;
	}
	private List<HolidayWorkInput> convert(CaculationTime caculationTime){
	List<HolidayWorkInput> holidayInputs = new ArrayList<>();
		if(caculationTime .getApplicationTime() != null){
			HolidayWorkInput holidayInput = HolidayWorkInput.createSimpleFromJavaType(caculationTime.getCompanyID(),
					caculationTime.getAppID(),
					caculationTime.getAttendanceID(), 
					caculationTime.getFrameNo(),
					-1,
					-1,
					caculationTime.getApplicationTime());
			holidayInputs.add(holidayInput);
		}
	
	return holidayInputs;
}

}
