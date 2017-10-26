package nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familycare;

import java.util.Optional;

public interface FamilyCarePub {
	public Optional<FamilyCareExport> getFamilyCareById(String familyCareId);
}
