package nts.uk.ctx.at.request.pubimp.application.recognition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationHdTimeExport;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationOvertimeExport;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationOvertimePub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationOvertimePubImpl implements ApplicationOvertimePub {
	@Inject
	private ApplicationRepository_New applicationRepository_New;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Inject
	private AppHolidayWorkRepository appHdWorkRepository;
	
	/**
	 * Request list No.236
	 */
	@Override
	public List<ApplicationOvertimeExport> acquireTotalApplicationOverTimeHours(String sId, GeneralDate startDate, GeneralDate endDate) {
		String companyId = AppContexts.user().companyId();
		List<ApplicationOvertimeExport> results = new ArrayList<>();
		/*List<Application_New> lstAppOt = applicationRepository_New.getListAppByType(companyId, sId, startDate, endDate, 
				PrePostAtr.POSTERIOR.value, ApplicationType.OVER_TIME_APPLICATION.value, Arrays.asList(ReflectedState_New.NOTREFLECTED.value,ReflectedState_New.WAITREFLECTION.value));
		List<String> lstId = new ArrayList<>();
		Map<GeneralDate, List<String>> mapApp = new HashMap<>();
		Map<String, AppOverTime> mapOver = new HashMap<>();
		List<String> lstOtID = lstAppOt.stream().map(c -> c.getAppID()).collect(Collectors.toList());
		mapOver = overtimeRepository.getListAppOvertimeFrame(companyId, lstOtID);
		for (Application_New app : lstAppOt) {
			
			if(mapApp.containsKey(app.getAppDate())){
				continue;
			}
			mapApp.put(app.getAppDate(), app.getAppID());
			lstId.add(app.getAppID());
		}
		mapOver = overtimeRepository.getListAppOvertimeFrame(companyId, lstId);
		
		for(Map.Entry<GeneralDate, String> entry : mapApp.entrySet()) {
			AppOverTime appOverTime = mapOver.get(entry.getValue());
			List<OverTimeInput> oTWorkInput = appOverTime.getOverTimeInput();
			int cal = 0;
			// 取得した「休日出勤申請」の休出時間を、日付別に集計する
			for (OverTimeInput item : oTWorkInput) {
				if((item.getAttendanceType().equals(AttendanceType.NORMALOVERTIME) && item.getFrameNo() != 11 && item.getFrameNo() != 12)
						|| (item.getAttendanceType().equals(AttendanceType.BREAKTIME))){
					cal = item.getApplicationTime() == null ? cal : cal + item.getApplicationTime().v();
				}
			}
			results.add(new ApplicationOvertimeExport(entry.getKey(), cal));
		}*/
		return results;
	}
}
