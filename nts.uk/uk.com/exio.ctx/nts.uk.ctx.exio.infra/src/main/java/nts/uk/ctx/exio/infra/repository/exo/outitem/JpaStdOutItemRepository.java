package nts.uk.ctx.exio.infra.repository.exo.outitem;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exo.outitem.OiomtStdOutItem;
import nts.uk.ctx.exio.infra.entity.exo.outitem.OiomtStdOutItemPk;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItemRepository;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItem;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaStdOutItemRepository extends JpaRepository implements StdOutItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdOutItem f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemPk.cid =:cid AND  f.stdOutItemPk.outItemCd =:outItemCd AND  f.stdOutItemPk.condSetCd =:condSetCd ";
	private static final String SELECT_BY_CID_AND_SET_CODE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemPk.cid =:cid AND  f.stdOutItemPk.condSetCd =:condSetCd ";

	@Override
	public List<StdOutItem> getAllStdOutItem() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdOutItem.class).getList(item -> item.toDomain());
	}

	@Override
	public Optional<StdOutItem> getStdOutItemById(String cid, String outItemCd, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutItem.class).setParameter("cid", cid)
				.setParameter("outItemCd", outItemCd).setParameter("condSetCd", condSetCd).getSingle(c -> c.toDomain());
	}

	@Override
	public Optional<StdOutItem> getStdOutItemByCidAndSetCd(String cid, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_CID_AND_SET_CODE, OiomtStdOutItem.class).setParameter("cid", cid)
				.setParameter("condSetCd", condSetCd).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(StdOutItem domain) {
		this.commandProxy().insert(OiomtStdOutItem.toEntity(domain));
	}

	@Override
	public void update(StdOutItem domain) {
		this.commandProxy().update(OiomtStdOutItem.toEntity(domain));
	}

	@Override
	public void remove(String cid, String outItemCd, String condSetCd) {
		this.commandProxy().remove(OiomtStdOutItem.class, new OiomtStdOutItemPk(cid, outItemCd, condSetCd));
	}

}
