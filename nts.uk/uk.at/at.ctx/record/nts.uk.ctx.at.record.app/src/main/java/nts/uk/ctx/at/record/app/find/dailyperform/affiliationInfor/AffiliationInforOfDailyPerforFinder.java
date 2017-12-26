package nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.affiliationInfor.dto.AffiliationInforOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績の所属情報 Finder*/
@Stateless
public class AffiliationInforOfDailyPerforFinder extends FinderFacade {

	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInfoRepo;
	
	@SuppressWarnings("unchecked")
	@Override
	public AffiliationInforOfDailyPerforDto find(String employeeId, GeneralDate baseDate) {
		AffiliationInforOfDailyPerforDto dto = new AffiliationInforOfDailyPerforDto();
		AffiliationInforOfDailyPerfor domain = this.affiliationInfoRepo.findByKey(employeeId, baseDate).orElse(null);
		if(domain != null){
			dto.setClassificationCode(domain.getClsCode().v());
			dto.setEmploymentCode(domain.getEmploymentCode().v());
			dto.setJobId(domain.getJobTitleID());
			dto.setSubscriptionCode(domain.getBonusPaySettingCode().v());
			dto.setWorkplaceID(domain.getWplID());
			dto.setBaseDate(domain.getYmd());
			dto.setEmployeeId(domain.getEmployeeId());
		}
		
		return dto;
	}

}
