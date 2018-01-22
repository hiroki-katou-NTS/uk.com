package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績の所属情報 Finder */
@Stateless
public class AffiliationInforOfDailyPerforFinder extends FinderFacade {

	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInfoRepo;

	@SuppressWarnings("unchecked")
	@Override
	public AffiliationInforOfDailyPerforDto find(String employeeId, GeneralDate baseDate) {
		return AffiliationInforOfDailyPerforDto
				.getDto(this.affiliationInfoRepo.findByKey(employeeId, baseDate).orElse(null));
	}

}
