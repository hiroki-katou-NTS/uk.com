package nts.uk.ctx.bs.person.ac.familyrelatedinformation.familysocialinsurance;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familysocialinsurance.FamilySocialInsurancePub;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familysocialinsurance.FamilySocialInsuranceAdapter;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familysocialinsurance.FamilySocialInsuranceImport;


@Stateless
public class FamilySocialInsuranceAdapterImple implements FamilySocialInsuranceAdapter{

	@Inject
	private FamilySocialInsurancePub familySocialInsurancePub;
	
	@Override
	public Optional<FamilySocialInsuranceImport> getFamilySocialInseById(String familySocialInsId) {
		return familySocialInsurancePub.getFamilySocialInsuranceById(familySocialInsId).map(x -> new FamilySocialInsuranceImport(
				x.getFamilyMemberId(), x.getSid(), x.getSocialInsuaranceId(), x.getStartDate(),
				x.getEndDate(), x.isNursingCare(), x.isHealthInsuranceDependent(), x.isNationalPensionNo3(), x.getBasicPensionNumber()));
	}

}
