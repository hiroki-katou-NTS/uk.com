package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance;

import java.util.Optional;

public interface FamilySocialInsuranceRepository {
	public Optional<FamilySocialInsurance> getFamilySocialInsById(String familySocialInsById);
}
