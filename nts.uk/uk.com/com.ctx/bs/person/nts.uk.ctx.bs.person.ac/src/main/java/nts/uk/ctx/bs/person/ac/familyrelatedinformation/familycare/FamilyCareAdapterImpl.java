package nts.uk.ctx.bs.person.ac.familyrelatedinformation.familycare;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familycare.FamilyCarePub;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familycare.FamilyCareAdapter;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familycare.FamilyCareImport;

@Stateless
public class FamilyCareAdapterImpl implements FamilyCareAdapter{

	@Inject
	private FamilyCarePub familyCarePub;
	
	@Override
	public Optional<FamilyCareImport> getFamilyCareByid(String familyCareId) {
		return familyCarePub.getFamilyCareById(familyCareId).map(x -> new FamilyCareImport(
				x.getFamilyCareId(), x.getFamilyId(), x.getSid(), 
				x.getStartDate(), x.getEndDate(), x.getCareClassifi()));
	}

}
