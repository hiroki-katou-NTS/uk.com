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
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.pub.application.recognition.AppNotReflectedPub;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationOvertimeExport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppNotReflectedPubImpl implements AppNotReflectedPub {
	@Inject
	private ApplicationRepository_New applicationRepository_New;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Inject
	private AppHolidayWorkRepository appHdWorkRepository;
	
	/**
	 * Request list No.300
	 */
	@Override
	public List<ApplicationOvertimeExport> acquireAppNotReflected(String sId, GeneralDate startDate, GeneralDate endDate) {
		String companyId = AppContexts.user().companyId();
		Optional<AppOverTime> appOt = Optional.empty();
		Optional<AppHolidayWork> appHdWork = Optional.empty();
		int totalHours = 0;
		List<ApplicationOvertimeExport> results = new ArrayList<>();
		
		List<Application_New> appNew = applicationRepository_New.getListApp(sId, startDate, endDate);
		
		if(appNew.size() >= 1) {
			// 条件を元に、ドメインモデル「残業申請」を取得する
			appOt = overtimeRepository.getAppOvertime(companyId, appNew.get(0).getAppID());
			
			// 条件を元に、ドメインモデル「休日出勤申請」を取得する
			appHdWork = appHdWorkRepository.getAppHolidayWork(companyId, appNew.get(0).getAppID());
		}
		
		Integer otShiftNight = 0;
		if(appOt.isPresent()) {
			otShiftNight = appOt.get().getOverTimeShiftNight()!=null?appOt.get().getOverTimeShiftNight():0;
		}
		
		int hdShiftNight = 0;
		if(appHdWork.isPresent()) {
			hdShiftNight = appHdWork.get().getHolidayShiftNight();
		}
		
		// 取得した「残業申請」と「休日出勤申請」の就業時間外深夜時間を、日付別に集計する
		totalHours = otShiftNight + hdShiftNight;
		
		ApplicationOvertimeExport data = new ApplicationOvertimeExport();
		
		data.setDate(appNew.size() >= 1 ? appNew.get(0).getAppDate() : null);
		data.setTotalOtHours(totalHours);
		results.add(data);
		
		if(results.size() > 0) {
			return results;
		}
		
		return Collections.emptyList();
	}

}
