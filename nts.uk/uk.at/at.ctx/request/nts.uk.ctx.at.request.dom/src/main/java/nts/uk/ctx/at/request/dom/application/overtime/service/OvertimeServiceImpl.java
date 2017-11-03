package nts.uk.ctx.at.request.dom.application.overtime.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;

@Stateless
public class OvertimeServiceImpl implements OvertimeService {
	final String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private OvertimeInstructRepository overtimeInstructRepository;

	@Override
	public int checkOvertime(String url) {
		
		return 0;
	}

	@Override
	public OverTimeInstruct getOvertimeInstruct(AppCommonSettingOutput appCommonSettingOutput,String appDate,String employeeID) {
		OverTimeInstruct overtimeInstruct = new OverTimeInstruct();
		if(appCommonSettingOutput != null){
			int useAtr = appCommonSettingOutput.requestOfEachCommon.getRequestAppDetailSettings().get(0).getUserAtr().value;
			if(useAtr == UseAtr.USE.value){
				if(appDate != null){
					overtimeInstruct = overtimeInstructRepository.getOvertimeInstruct(GeneralDate.fromString(appDate, DATE_FORMAT), employeeID);
				}
			}
		}
		return overtimeInstruct;
	}

	

}
