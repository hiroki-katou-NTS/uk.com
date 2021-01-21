package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformationRepository;

@Stateless
public class HoldingQualificationApp {

	@Inject
	private PersonalInformationRepository recordRepo;
	
	public List<HoldingQualificationOutput> getHoldingQualification(HoldingQualificationInput input){
		
		HoldingQualificationRequireImpl require = new HoldingQualificationRequireImpl(recordRepo);
		
		return HoldingQualification.get(require,
				input.getBaseDate(),
				input.getCId(),
				input.getSIds(),
				input.isGetEndDate(),
				input.isGetNameMaster(),
				input.isCategory(),
				input.isGetRank(),
				input.isGetnumber(),
				input.isGetQualifiedOrganization());
		
	}
	
	@AllArgsConstructor
	private class HoldingQualificationRequireImpl implements HoldingQualification.Require {

		@Inject
		private PersonalInformationRepository recordRepo;

		@Override
		public List<PersonalInformation> getLstPersonInfoByCIdSIdsWorkId(String cId, List<String> sids, int workId,
				GeneralDate baseDate) {
			return recordRepo.getLstPersonInfoByCIdSIdsWorkId(cId, sids, workId, baseDate);
		}
	}
}