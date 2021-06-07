package nts.uk.ctx.exio.infra.repository.input.canonicalize;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMeta;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMetaRepository;
import nts.uk.ctx.exio.infra.entity.input.canonicalize.OiottCanonicalizedDataMeta;

@Stateless
public class JpaCanonicalizedDataMeta extends JpaRepository implements CanonicalizedDataMetaRepository {

	@Override
	public CanonicalizedDataMeta get(String cid) {
		String sql = "SELECT cdm FROM OiottCanonicalizedDataMeta cdm"
				+ " WHERE cdm.cid = :cid";
		return this.queryProxy().query(sql, OiottCanonicalizedDataMeta.class)
				.setParameter("cid", cid)
				.getSingle(entity -> entity.toDomain())
				.get();
	}

}
