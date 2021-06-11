package nts.uk.ctx.exio.dom.input.canonicalize;

import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;

public interface PrepareImportingRepository {

	void save(CanonicalizedDataRecord canonicalizedDataRecord);

	void save(AnyRecordToChange recordToChange);

}
