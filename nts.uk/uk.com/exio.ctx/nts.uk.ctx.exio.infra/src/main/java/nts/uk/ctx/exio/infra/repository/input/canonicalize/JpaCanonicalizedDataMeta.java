package nts.uk.ctx.exio.infra.repository.input.canonicalize;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMeta;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMetaRepository;

@Stateless
public class JpaCanonicalizedDataMeta implements CanonicalizedDataMetaRepository {

	@Override
	public CanonicalizedDataMeta get(String cid) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
