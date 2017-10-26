package nts.uk.ctx.bs.employee.pubimp.familyrelatedinformation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance.FamilySocialInsuranceRepository;
import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familysocialinsurance.FamilySocialInsuranceExport;
import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familysocialinsurance.FamilySocialInsurancePub;

@Stateless
public class FamilySocialInsurancePubImpl implements FamilySocialInsurancePub{

	@Inject
	private FamilySocialInsuranceRepository familySocialInsuranceRepository;
	
	@Override
	public Optional<FamilySocialInsuranceExport> getFamilySocialInsuranceById(String id) {
		return familySocialInsuranceRepository.getFamilySocialInsById(id).map(x -> new FamilySocialInsuranceExport(
				x.getFamilyMemberId(), x.getSid(), x.getSocailInsuaranceId(), x.getStartDate(),
				x.getEndDate(), x.isNursingCare(), x.isHealthInsuranceDependent(), x.isNationalPensionNo3(), x.getBasicPensionNumber().v()));
	}

}
