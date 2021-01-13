package nts.uk.ctx.at.request.pubimp.application.recognition;
/*import java.util.Collections;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationHdTimeExport;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import java.util.Optional;*/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationOvertimeExport;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationTimeUnreflectedPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationTimeUnreflectedPubImpl implements ApplicationTimeUnreflectedPub {
	@Inject
	private ApplicationRepository repoApplication;
	
	@Inject
	private OvertimeRepository repoOvertime;
	
	/**
	 * Request list No.298
	 * @return List(申請日,フレックス超過時間)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ApplicationOvertimeExport> acquireTotalApplicationTimeUnreflected(String sId, GeneralDate startDate, GeneralDate endDate) {
		String companyId = AppContexts.user().companyId();
		Map<String, AppOverTime_Old> mapOt = new HashMap<>();
		List<ApplicationOvertimeExport> results = new ArrayList<>();
		List<Application> appHd = repoApplication.getListAppByType(companyId, sId, startDate, endDate, PrePostAtr.POSTERIOR.value, 
				ApplicationType.OVER_TIME_APPLICATION.value, Arrays.asList(ReflectedState.NOTREFLECTED.value,ReflectedState.WAITREFLECTION.value));
		List<String> lstId = new ArrayList<>();
		Map<GeneralDate, String> mapApp = new HashMap<>();
		//※同じ申請日の残業申請が２件あった場合、入力日が後のもの（latest）だけSetする
		for (Application app : appHd) {
			if(mapApp.containsKey(app.getAppDate().getApplicationDate())){
				continue;
			}
			mapApp.put(app.getAppDate().getApplicationDate(), app.getAppID());
			lstId.add(app.getAppID());
		}
		mapOt = repoOvertime.getListAppOvertimeFrame(companyId, lstId);
		for(Map.Entry<GeneralDate, String> entry : mapApp.entrySet()) {
			AppOverTime_Old otDetail = mapOt.get(entry.getValue());
			int cal = otDetail.getFlexExessTime() == null ? 0 : otDetail.getFlexExessTime();
			results.add(new ApplicationOvertimeExport(entry.getKey(), cal));
		}
		return results;
	}

}
