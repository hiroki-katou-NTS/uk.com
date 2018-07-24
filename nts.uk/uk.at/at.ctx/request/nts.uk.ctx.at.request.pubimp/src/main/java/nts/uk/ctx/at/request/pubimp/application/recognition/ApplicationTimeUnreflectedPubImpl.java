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
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationOvertimeExport;
import nts.uk.ctx.at.request.pub.application.recognition.ApplicationTimeUnreflectedPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationTimeUnreflectedPubImpl implements ApplicationTimeUnreflectedPub {
	@Inject
	private ApplicationRepository_New applicationRepository_New;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	
	/**
	 * Request list No.298
	 */
	@Override
	public List<ApplicationOvertimeExport> acquireTotalApplicationTimeUnreflected(String sId, GeneralDate startDate, GeneralDate endDate) {
		String companyId = AppContexts.user().companyId();
		Optional<AppOverTime> appOt = Optional.empty();
		List<ApplicationOvertimeExport> results = new ArrayList<>();
		
		List<Application_New> appNew = applicationRepository_New.getListApp(sId, startDate, endDate);
		
		if(appNew.size() >= 1) {
			// 条件を元に、ドメインモデル「残業申請」を取得する
			appOt = overtimeRepository.getAppOvertime(companyId, appNew.get(0).getAppID());
		}
		
		// 取得した「残業申請」のフレックス超過時間を、日付別に集計する
		ApplicationOvertimeExport data = new ApplicationOvertimeExport();
		data.setDate(appNew.size() >= 1 ? appNew.get(0).getAppDate() : null);
		
		Integer flexExTime = 0;
		if(appOt.isPresent()) {
			flexExTime = appOt.get().getFlexExessTime() != null? appOt.get().getFlexExessTime():0;
		}
		
		data.setTotalOtHours(flexExTime);
		results.add(data);
		
		if(results.size() > 0) {
			return results;
		}
		
		return Collections.emptyList();
	}

}
