package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<HoldingQualificationDto> getHoldingQualification(HoldingQualificationInput input){
		
		HoldingQualificationRequireImpl require = new HoldingQualificationRequireImpl(recordRepo);
		
		List<RetentionCredentials> credentials = HoldingQualification.get(require,
				input.getBaseDate(),
				input.getCId(),
				input.getSIds(),
				input.isGetEndDate(),
				input.isGetNameMaster(),
				input.isCategory(),
				input.isGetRank(),
				input.isGetnumber(),
				input.isGetQualifiedOrganization());
		
		if (!credentials.isEmpty()) {
			List<HoldingQualificationDto> output = credentials.stream().map(m -> {
				
				HoldingQualificationDto dto = new HoldingQualificationDto();
				
				dto.setEmployeeId(m.getSId());
				
				if (!m.getEligibilitys().isEmpty()) {
					List<Eligibility> eligibilities = m.getEligibilitys().stream().map(s -> {
						Eligibility eligibility = new Eligibility(s.getQualificationCd(),
								s.getQualificationId(),
								s.getCategoryCd().map(c -> c).orElse(""),
								s.getDivisionName().map(c -> c).orElse(""),
								s.getEndDate().map(c -> c).orElse(null),
								s.getQualificationName().map(c -> c).orElse(""),
								s.getQualificationRank().map(c -> c).orElse(""),
								s.getQualificationOrganization().map(c ->c).orElse(""),
								s.getQualificationNumber().map(c ->c).orElse(""));
						return eligibility;
					}).collect(Collectors.toList());
					
					dto.setEligibility(eligibilities);
				}
				
				return dto;
			}).collect(Collectors.toList());
			
			return output;
		}
		
		return new ArrayList<>();
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