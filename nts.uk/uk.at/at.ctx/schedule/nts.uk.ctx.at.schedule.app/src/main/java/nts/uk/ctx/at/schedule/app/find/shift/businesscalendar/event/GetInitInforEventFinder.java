package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEventRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEventRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL049_行事の登録ダイアログ.メニュー別OCD.起動時
 * 
 * @author HieuLt
 *
 */
@Stateless
public class GetInitInforEventFinder {

	@Inject
	private CompanyEventRepository companyEventRepo;

	@Inject
	private WorkplaceEventRepository workplaceEventRepo;

	public KDL049Dto getInitInforEvent(String workplaceID, String targetDate) {
		GeneralDate date = GeneralDate.fromString(targetDate, "yyyy/MM/dd");
		String companyID = AppContexts.user().companyId();
		WorkplaceEventDto optWorkplaceEvent = new WorkplaceEventDto();
		CompanyEventDto optCompantEvent = new CompanyEventDto();

		// get 会社行事を取得する : getCompanyevent
		Optional<CompanyEvent> compantEvent = companyEventRepo.findByPK(companyID, date);
		if(compantEvent.isPresent()){	
		optCompantEvent = CompanyEventDto.fromDomain(compantEvent.get());
		}
		// get Optional<職場行事> : getWorkplaceEvent
		if (workplaceID != null) {
			Optional<WorkplaceEvent>workplaceEvent = workplaceEventRepo.findByPK(workplaceID, date);
			if(workplaceEvent.isPresent()){
				optWorkplaceEvent = WorkplaceEventDto.fromDomain(workplaceEvent.get());
			}
		}
		KDL049Dto data = new KDL049Dto(optCompantEvent, optWorkplaceEvent);
		return data;
	}

}
