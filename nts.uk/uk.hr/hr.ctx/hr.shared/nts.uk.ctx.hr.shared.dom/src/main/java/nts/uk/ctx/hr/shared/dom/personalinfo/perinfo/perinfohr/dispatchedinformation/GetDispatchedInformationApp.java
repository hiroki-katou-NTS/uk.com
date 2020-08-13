package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation;

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
public class GetDispatchedInformationApp {

	@Inject
	private PersonalInformationRepository recordRepo;

	public List<TemporaryDispatchInformation> get(InputDispatchedInformation input) {
		
		GetDispatchedInformationRequireImpl require = new GetDispatchedInformationRequireImpl(recordRepo);

		return GetDispatchedInformation.get(require,
				input.contractCode,
				input.companyId, 
				input.baseDate,
				input.employeeCode,
				input.employeeName,
				input.expirationDate,
				input.nameSelectedMaster,
				input.classification1,
				input.classification2, 
				input.classification3, 
				input.nameCompany, 
				input.address, 
				input.addressKana, 
				input.include,
				input.employeeIds) ;
	}

	@AllArgsConstructor
	private class GetDispatchedInformationRequireImpl implements GetDispatchedInformation.Require {

		@Inject
		private PersonalInformationRepository recordRepo;

		@Override
		public List<PersonalInformation> getdDispatchedInformation(String contractCd, String cId, int workId,
				GeneralDate baseDate) {
			return recordRepo.getdDispatchedInformation(contractCd, cId, workId, baseDate);
		}
	}
}