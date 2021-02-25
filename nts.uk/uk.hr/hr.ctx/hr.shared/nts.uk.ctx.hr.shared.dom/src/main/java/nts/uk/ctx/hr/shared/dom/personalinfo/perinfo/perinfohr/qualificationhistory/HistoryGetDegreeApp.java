package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

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
	
	public List<HistoryGetDegreeOutput> get(HistoryGetDegreeInput input) {
		
		HistoryGetDegreeRequireImpl require = new HistoryGetDegreeRequireImpl(recordRepo);
		
		return HistoryGetDegree.get(require,
				input.baseDate, 
				input.cId, 
				input.qualificationIds, 
				input.getEmployeeCode, 
				input.getEmployeeName);
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