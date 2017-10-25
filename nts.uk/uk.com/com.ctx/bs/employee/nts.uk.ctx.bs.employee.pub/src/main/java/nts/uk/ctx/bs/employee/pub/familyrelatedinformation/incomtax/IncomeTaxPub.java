package nts.uk.ctx.bs.employee.pub.familyrelatedinformation.incomtax;

import java.util.Optional;

public interface IncomeTaxPub {
	public Optional<IncomeTaxExport> getIncomeTaxById(String incomeTaxId);
}
