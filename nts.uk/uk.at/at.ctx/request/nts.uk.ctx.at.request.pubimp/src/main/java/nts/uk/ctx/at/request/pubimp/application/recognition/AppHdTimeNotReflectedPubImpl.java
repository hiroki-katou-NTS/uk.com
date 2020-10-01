package nts.uk.ctx.at.request.pubimp.application.recognition;

/*import java.util.Collections;
import java.util.Optional;
import java.lang.reflect.Array;*/
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
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.pub.application.recognition.AppHdTimeNotReflectedPub;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationHdTimeExport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppHdTimeNotReflectedPubImpl implements AppHdTimeNotReflectedPub {
	@Inject
	private ApplicationRepository repoApplication;
	
	@Inject
	private AppHolidayWorkRepository appHdWorkRepository;
	
	/**
	 * Request list No.299
	 * @return List (年月日, 休出時間)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ApplicationHdTimeExport> acquireTotalAppHdTimeNotReflected(String sId, GeneralDate startDate, GeneralDate endDate) {
		String companyId = AppContexts.user().companyId();
		Map<String, AppHolidayWork> mapHd = new HashMap<>();
		List<ApplicationHdTimeExport> results = new ArrayList<>();
		List<Application> appHd = repoApplication.getListAppByType(companyId, sId, startDate, endDate, PrePostAtr.POSTERIOR.value,
				ApplicationType.HOLIDAY_WORK_APPLICATION.value, Arrays.asList(ReflectedState.NOTREFLECTED.value,ReflectedState.WAITREFLECTION.value));
		List<String> lstId = new ArrayList<>();
		Map<GeneralDate, String> mapApp = new HashMap<>();
		// 条件を元に、ドメインモデル「休日出勤申請」を取得する
		//※同じ申請日の休日出勤申請が２件あった場合、入力日が後のもの（latest）だけ集計する
		for (Application app : appHd) {
			if(mapApp.containsKey(app.getAppDate().getApplicationDate())){
				continue;
			}
			mapApp.put(app.getAppDate().getApplicationDate(),app.getAppID());
			lstId.add(app.getAppID());
		}
		mapHd = appHdWorkRepository.getListAppHdWorkFrame(companyId, lstId);
		for(Map.Entry<GeneralDate, String> entry : mapApp.entrySet()) {
			AppHolidayWork hdDetail = mapHd.get(entry.getValue());
			List<HolidayWorkInput> hdWorkInput = hdDetail.getHolidayWorkInputs();
			int cal = 0;
			// 取得した「休日出勤申請」の休出時間を、日付別に集計する
			for (HolidayWorkInput item : hdWorkInput) {
				if(item.getAttendanceType().equals(AttendanceType.BREAKTIME)){
					cal = item.getApplicationTime() == null ? cal : cal + item.getApplicationTime().v();
				}
			}
			results.add(new ApplicationHdTimeExport(entry.getKey(), cal));
		}
		return results;
	}

}
