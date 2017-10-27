package nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.familycare;

import java.util.Optional;

public interface FamilyCareAdapter {
	public Optional<FamilyCareImport> getFamilyCareByid(String familyCareId);
}
