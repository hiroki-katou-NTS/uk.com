package nts.uk.screen.at.app.kdw013.query;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import javax.inject.Inject;


import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;

/**
 * 
 * @author sonnlb
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.残業休出申請.残業申請.App.最新の事前残業申請を取得する.最新の事前残業申請を取得する
 */
public class GetLastOverTimeApplication {
	
	@Inject
	private ApplicationRepository appRepo;
	
	/**
	 * 取得する
	 * 
	 * @param sId 社員ID
	 * @param date 年月日
	 * @return Optional<残業申請Dto>
	 */
	public Optional<ApplicationDto> get(String sId, GeneralDate date) {
		// 1.get
		Optional<Application> OptApp = this.appRepo.getApplicationBySIDs(Arrays.asList(sId), date, date).stream()
				.filter(x -> x.getAppType().equals(ApplicationType.OVER_TIME_APPLICATION) && x.getPrePostAtr().equals(PrePostAtr.PREDICT))
				.sorted(Comparator.comparing(Application::getInputDate).reversed())
				.findFirst();
		
		return OptApp.map(x -> ApplicationDto.fromDomain(x));
	}
}
