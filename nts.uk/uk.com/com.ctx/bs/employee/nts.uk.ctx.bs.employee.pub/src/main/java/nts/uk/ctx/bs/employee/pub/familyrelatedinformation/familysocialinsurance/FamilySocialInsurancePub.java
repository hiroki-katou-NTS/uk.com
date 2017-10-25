package nts.uk.ctx.bs.employee.pub.familyrelatedinformation.familysocialinsurance;

import java.util.Optional;

public interface FamilySocialInsurancePub {
	public Optional<FamilySocialInsuranceExport> getFamilySocialInsuranceById(String id);
}
