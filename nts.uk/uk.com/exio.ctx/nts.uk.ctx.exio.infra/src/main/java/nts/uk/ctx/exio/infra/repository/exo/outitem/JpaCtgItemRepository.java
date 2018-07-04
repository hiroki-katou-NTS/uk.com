package nts.uk.ctx.exio.infra.repository.exo.outitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outitem.CtgItem;
import nts.uk.ctx.exio.dom.exo.outitem.CtgItemRepository;
import nts.uk.ctx.exio.infra.entity.exo.outitem.OiomtCtgItem;
import nts.uk.ctx.exio.infra.entity.exo.outitem.OiomtCtgItemPk;

@Stateless
public class JpaCtgItemRepository extends JpaRepository implements CtgItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtCtgItem f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.ctgItemPk.ctgItemNo =:ctgItemNo AND  f.ctgItemPk.cid =:cid AND  f.ctgItemPk.outItemCd =:outItemCd AND  f.ctgItemPk.condSetCd =:condSetCd ";

	@Override
	public List<CtgItem> getAllCtgItem() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtCtgItem.class).getList(item -> item.toDomain());
	}

	@Override
	public Optional<CtgItem> getCtgItemById(String ctgItemNo, String cid, String outItemCd, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtCtgItem.class).setParameter("ctgItemNo", ctgItemNo)
				.setParameter("cid", cid).setParameter("outItemCd", outItemCd).setParameter("condSetCd", condSetCd)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(CtgItem domain) {
		this.commandProxy().insert(OiomtCtgItem.toEntity(domain));
	}

	@Override
	public void update(CtgItem domain) {
		this.commandProxy().update(OiomtCtgItem.toEntity(domain));
	}

	@Override
	public void remove(String ctgItemNo, String cid, String outItemCd, String condSetCd) {
		this.commandProxy().remove(OiomtCtgItem.class, new OiomtCtgItemPk(ctgItemNo, cid, outItemCd, condSetCd));
	}
}