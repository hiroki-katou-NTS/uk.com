package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care;

import java.util.Optional;

public interface FamilyCareRepository {
	public Optional<FamilyCare> getFamilyCareById(String familyCareId);
}
