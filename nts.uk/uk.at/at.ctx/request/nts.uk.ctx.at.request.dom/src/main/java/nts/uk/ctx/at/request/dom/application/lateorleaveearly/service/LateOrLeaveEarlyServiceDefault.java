package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.Deadline;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.RequiredFlg;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class LateOrLeaveEarlyServiceDefault implements LateOrLeaveEarlyService {

	@Inject
	LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;

	@Inject
	ApplicationRepository applicationRepository;

	@Inject
	ApplicationSettingRepository applicationSettingRepository;

	@Inject
	ApplicationSettingRepository applicationSetting;
	
	@Inject 
	EmployeeAdapter employeeAdapter;
	
	@Inject
	ApplicationDeadlineRepository deadlineRepository;	
	
	
	@Override
	public boolean isExist(String companyID, String appID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly) {

		/** 逕ｳ隲狗炊逕ｱ縺悟ｿ�鬆� */

		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository
				.getApplicationSettingByComID(lateOrLeaveEarly.getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();
		int prePost = lateOrLeaveEarly.getPrePostAtr().value;
		int lateTime1 = lateOrLeaveEarly.getLateTime1().valueAsMinutes();
		int earlyTime1 = lateOrLeaveEarly.getEarlyTime1().valueAsMinutes();
		int lateTime2 = lateOrLeaveEarly.getLateTime2().valueAsMinutes();
		int earlyTime2 = lateOrLeaveEarly.getEarlyTime2().valueAsMinutes();
		int late1 = lateOrLeaveEarly.getLate1().value;
		int late2 = lateOrLeaveEarly.getLate2().value;
		int early1 = lateOrLeaveEarly.getEarly1().value;
		int early2 = lateOrLeaveEarly.getEarly2().value;

		if (applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
				&& Strings.isEmpty(lateOrLeaveEarly.getApplicationReason().v())) {
			throw new BusinessException("Msg_115");
		}

		// 驕�蛻ｻ譎ょ綾譌ｩ騾�譎ょ綾縺後→繧ゅ↓險ｭ螳壹＆繧後※縺�繧九→縺阪��驕�蛻ｻ譎ょ綾竕ｧ譌ｩ騾�譎ょ綾 (#Msg_381#)

		if (lateTime1 >= earlyTime1 && lateTime2 >= earlyTime2 && prePost == 0) {
			throw new BusinessException("Msg_381");
		}
		// 驕�蛻ｻ縲∵掠騾�縲�驕�蛻ｻ2縲∵掠騾�2縺ｮ縺�縺壹ｌ縺具ｼ代▽縺ｯ繝√ぉ繝�繧ｯ蠢�鬆�(#Msg_382#)

		int checkSelect = late1 + late2 + early1 + early2;
		if (checkSelect == 0) {
			throw new BusinessException("Msg_382");
		}

		// [逕ｻ髱｢B縺ｮ縺ｿ]驕�蛻ｻ縲∵掠騾�縲�驕�蛻ｻ2縲∵掠騾�2縺ｮ繝√ぉ繝�繧ｯ縺後≠繧矩≦蛻ｻ譎ょ綾縲∵掠騾�譎ょ綾縺ｯ蜈･蜉帛ｿ�鬆�(#Msg_470#)
		int checkInputTime = lateTime1 + lateTime2 + earlyTime1 + earlyTime2;
		if (checkInputTime <= 0 && prePost == 0 ) {
			throw new BusinessException("Msg_470");
		}
		// Add LateOrLeaveEarly
		lateOrLeaveEarlyRepository.add(lateOrLeaveEarly);
	}

	@Override
	public void updateLateOrLeaveEarly(LateOrLeaveEarly lateOrLeaveEarly) {
		
		Optional<ApplicationSetting> applicationSettingOp = applicationSettingRepository
				.getApplicationSettingByComID(lateOrLeaveEarly.getCompanyID());
		ApplicationSetting applicationSetting = applicationSettingOp.get();

		int lateTime1 = lateOrLeaveEarly.getLateTime1().valueAsMinutes();
		int earlyTime1 = lateOrLeaveEarly.getEarlyTime1().valueAsMinutes();
		int lateTime2 = lateOrLeaveEarly.getLateTime2().valueAsMinutes();
		int earlyTime2 = lateOrLeaveEarly.getEarlyTime2().valueAsMinutes();
		int prePost = lateOrLeaveEarly.getPrePostAtr().value;
		int late1 = lateOrLeaveEarly.getLate1().value;
		int late2 = lateOrLeaveEarly.getLate2().value;
		int early1 = lateOrLeaveEarly.getEarly1().value;
		int early2 = lateOrLeaveEarly.getEarly2().value;
		int checkInputTime = lateTime1 + lateTime2 + earlyTime1 + earlyTime2;
		// 驕�蛻ｻ譎ょ綾譌ｩ騾�譎ょ綾縺後→繧ゅ↓險ｭ螳壹＆繧後※縺�繧九→縺阪��驕�蛻ｻ譎ょ綾竕ｧ譌ｩ騾�譎ょ綾 (#Msg_381#)
		if (lateTime1 >= earlyTime1 && lateTime2 >= earlyTime2 && prePost == 0) {
			throw new BusinessException("Msg_381");
		}
		// 驕�蛻ｻ縲∵掠騾�縲�驕�蛻ｻ2縲∵掠騾�2縺ｮ縺�縺壹ｌ縺具ｼ代▽縺ｯ繝√ぉ繝�繧ｯ蠢�鬆�(#Msg_382#)

		int checkSelect = late1 + late2 + early1 + early2;
		if (checkSelect == 0) {
			throw new BusinessException("Msg_382");
		}
		//逕ｳ隲区価隱崎ｨｭ螳�->逕ｳ隲玖ｨｭ螳�->逕ｳ隲句宛髯占ｨｭ螳�.逕ｳ隲狗炊逕ｱ縺悟ｿ�鬆茨ｼ掖rue縺ｮ縺ｨ縺阪�∫筏隲狗炊逕ｱ縺梧悴蜈･蜉� (#Msg_115#)
		if (applicationSetting.getRequireAppReasonFlg().equals(RequiredFlg.REQUIRED)
				&& Strings.isEmpty(lateOrLeaveEarly.getApplicationReason().v())) {
			throw new BusinessException("Msg_115");
		}
		if (checkInputTime <= 0 && prePost == 0) {
			throw new BusinessException("Msg_470");
		}
		
		lateOrLeaveEarlyRepository.update(lateOrLeaveEarly);
	}

	@Override
	public void deleteLateOrLeaveEarly(String companyID, String appID) {
		// 5-2.隧ｳ邏ｰ逕ｻ髱｢蜑企勁蠕後�ｮ蜃ｦ逅�
		//TODO

	}

	@Override
	public void registerLateOrLeaveEarly(String companyID) {
	
	}

	@Override
	public void changeApplication(String companyID, String appID, GeneralDate applicationDate, int actualCancelAtr,
			int early1, int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2, int late2, int lateTime2,
			String reasonTemp, String appReason) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApplicantName(String employeeID) {
		return employeeAdapter.getEmployeeName(employeeID);
	}

}