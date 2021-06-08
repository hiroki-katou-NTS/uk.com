package nts.uk.ctx.exio.infra.repository.input.canonicalize;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMetaRepository;
import nts.uk.ctx.exio.infra.entity.input.canonicalize.OiottImportingItem;

@Stateless
public class JpaCanonicalizedDataMeta extends JpaRepository implements CanonicalizedDataMetaRepository {

	@Override
	public List<String> get(String cid) {
		String sql = "SELECT ii FROM OiottImportingItem ii"
				+ " WHERE ii.cid = :cid";
		return this.queryProxy().query(sql, OiottImportingItem.class)
				.setParameter("cid", cid)
				.getList(entity -> entity.name);
	}

}
