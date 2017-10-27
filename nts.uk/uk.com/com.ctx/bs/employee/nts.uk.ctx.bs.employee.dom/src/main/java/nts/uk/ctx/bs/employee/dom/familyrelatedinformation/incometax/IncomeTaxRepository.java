package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax;

import java.util.Optional;

public interface IncomeTaxRepository {
	public Optional<IncomeTax> getIncomeTaxById(String incomeTaxID);
}
