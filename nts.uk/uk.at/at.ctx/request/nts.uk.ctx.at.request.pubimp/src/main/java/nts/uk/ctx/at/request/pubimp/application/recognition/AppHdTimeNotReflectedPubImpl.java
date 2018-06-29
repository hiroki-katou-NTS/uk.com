package nts.uk.ctx.at.request.pubimp.application.recognition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
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
	private ApplicationRepository_New applicationRepository_New;
	
	@Inject
	private AppHolidayWorkRepository appHdWorkRepository;
	
	/**
	 * Request list No.299
	 */
	@Override
	public List<ApplicationHdTimeExport> acquireTotalAppHdTimeNotReflected(String sId, GeneralDate startDate, GeneralDate endDate) {
		String companyId = AppContexts.user().companyId();
		Optional<AppHolidayWork> appHdWork = Optional.empty();
		List<ApplicationHdTimeExport> results = new ArrayList<>();
		
		List<Application_New> appNew = applicationRepository_New.getListApp(sId, startDate, endDate);
		
		if(appNew.size() >= 1) {
			// 条件を元に、ドメインモデル「休日出勤申請」を取得する
			appHdWork = appHdWorkRepository.getAppHolidayWork(companyId, appNew.get(0).getAppID());
		}
		
		// 取得した「休日出勤申請」の休出時間を、日付別に集計する
		ApplicationHdTimeExport data = new ApplicationHdTimeExport();
		data.setDate(appNew.size() >= 1 ? appNew.get(0).getAppDate() : null);
		
		List<HolidayWorkInput> hdWorkInput = new ArrayList<>();	
		if(appHdWork.isPresent()) {
			hdWorkInput = appHdWork.get().getHolidayWorkInputs();
		} else {
			hdWorkInput = Collections.emptyList();
		}
			
		for (HolidayWorkInput item : hdWorkInput) {
			if(item.getAttendanceType() == AttendanceType.RESTTIME) {
				data.setBreakTime(item.getApplicationTime().v());
			}
		}
		
		results.add(data);
		
		if(results.size() > 0) {
			return results;
		}
		
		return Collections.emptyList();
	}

}
