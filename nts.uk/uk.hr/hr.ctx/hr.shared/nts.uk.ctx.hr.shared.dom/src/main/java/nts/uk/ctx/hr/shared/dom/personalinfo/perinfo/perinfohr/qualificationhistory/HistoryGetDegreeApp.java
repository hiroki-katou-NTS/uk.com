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

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class HistoryGetDegreeApp {

	@Inject
	private PersonalInformationRepository recordRepo;
	
	public List<HistoryGetDegreeDto> get(HistoryGetDegreeInput input) {
		List<HistoryGetDegreeDto> degreeDtos = new ArrayList<>();	
		
		HistoryGetDegreeRequireImpl require = new HistoryGetDegreeRequireImpl(recordRepo);
		
		List<QualificationHolderInformation> holderInformations = HistoryGetDegree.get(require,
				input.baseDate, 
				input.cId, 
				input.qualificationIds, 
				input.getEmployeeCode, 
				input.getEmployeeName);
		
		if (!holderInformations.isEmpty()) {
			degreeDtos = holderInformations.stream().map(m -> {
				HistoryGetDegreeDto degreeDto = new HistoryGetDegreeDto();
				
				degreeDto.setQualificationId(m.getQualificationId());
				
				List<Holders> holders = new ArrayList<>();
				
				if (!m.getHolders().isEmpty()) {
					holders = m.getHolders().stream().map(s -> {
						Holders holder = new Holders();
						
						holder.setEmployeeCd(s.getEmployeeCd());
						holder.setEmployeeId(s.getEmployeeId());
						holder.setEmployeeName(s.getEmployeeName().map(c -> c).orElse(null));
						
						return holder;
					}).collect(Collectors.toList());
				}
				
				degreeDto.setHolders(holders);
				
				return degreeDto;
			}).collect(Collectors.toList());
		}
		
		return degreeDtos;
	}
	
	@AllArgsConstructor
	private class HistoryGetDegreeRequireImpl implements HistoryGetDegree.Require {

		@Inject
		private PersonalInformationRepository recordRepo;

		@Override
		public List<PersonalInformation> getLstPersonInfoByQualificationIds(String cId, List<String> qualificationIds,
				int workId, GeneralDate baseDate) {
			return recordRepo.getLstPersonInfoByQualificationIds(cId, qualificationIds, workId, baseDate);
		}

	}	
}