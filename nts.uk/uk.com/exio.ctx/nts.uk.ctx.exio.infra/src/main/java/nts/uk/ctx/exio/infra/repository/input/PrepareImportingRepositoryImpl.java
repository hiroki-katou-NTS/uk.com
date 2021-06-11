package nts.uk.ctx.exio.infra.repository.input;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.PrepareImportingRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;

@Stateless
public class PrepareImportingRepositoryImpl extends JpaRepository implements PrepareImportingRepository {

	@Override
	public void save(CanonicalizedDataRecord canonicalizedDataRecord) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void save(AnyRecordToChange recordToChange) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
