package nts.uk.ctx.bs.employee.pubimp.familyrelatedinformation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.FamilyCareRepository;
import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familycare.FamilyCareExport;
import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familycare.FamilyCarePub;

@Stateless
public class FamilyCarePubImpl implements FamilyCarePub{

	@Inject
	private FamilyCareRepository familyCareRepository;
	
	@Override
	public Optional<FamilyCareExport> getFamilyCareById(String familyCareId) {
		return familyCareRepository.getFamilyCareById(familyCareId).map(x -> 
		new FamilyCareExport(x.getFamilyCareId(), x.getFamilyId(), x.getSid(), 
				x.getPeriod().start(), x.getPeriod().end(), x.getCareClassifi().value));
	}

}
