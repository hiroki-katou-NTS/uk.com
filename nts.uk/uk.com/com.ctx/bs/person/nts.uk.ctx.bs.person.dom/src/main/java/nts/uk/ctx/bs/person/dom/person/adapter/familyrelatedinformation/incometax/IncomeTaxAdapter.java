package nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.incometax;

import java.util.Optional;

public interface IncomeTaxAdapter {
	public Optional<IncomeTaxImport> getInComeTaxById(String IncomeTaxId);
}
