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
public class ReceiveInfoForBaseDateApp {

	@Inject
	private PersonalInformationRepository recordRepo;

	public List<TemporaryDispatchInformation> get(InputDispatchedInformation input){
		
		ReceiveInfoForBaseDateRequireImpl require = new ReceiveInfoForBaseDateRequireImpl(recordRepo);
		
		List<TemporaryDispatchInformation> temporaryDispatchInformations = ReceiveInfoForBaseDate.get(require,
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
				input.employeeIds);
		
		return temporaryDispatchInformations;
	}

	@AllArgsConstructor
	private class ReceiveInfoForBaseDateRequireImpl implements ReceiveInfoForBaseDate.Require {

		@Inject
		private PersonalInformationRepository recordRepo;

		@Override
		public List<PersonalInformation> getDispatchedInfoByStr10s(String contractCd, String cId,
				GeneralDate baseDate) {
			return recordRepo.getDispatchedInfoByStr10s(contractCd, cId, baseDate);
		}
	}
}