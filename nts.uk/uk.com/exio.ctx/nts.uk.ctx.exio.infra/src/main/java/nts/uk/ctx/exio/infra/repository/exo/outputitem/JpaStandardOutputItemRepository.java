package nts.uk.ctx.exio.infra.repository.exo.outputitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtStdOutItem;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtStdOutItemPk;
import nts.uk.ctx.exio.infra.entity.exo.outputitemorder.OiomtStdOutItemOrder;

@Stateless
public class JpaStandardOutputItemRepository extends JpaRepository implements StandardOutputItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdOutItem f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemPk.cid =:cid AND  f.stdOutItemPk.outItemCd =:outItemCd AND  f.stdOutItemPk.condSetCd =:condSetCd ";
	private static final String SELECT_BY_CID_AND_SET_CODE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemPk.cid =:cid AND  f.stdOutItemPk.condSetCd =:condSetCd ORDER BY f.stdOutItemPk.condSetCd";

	private static final String DELETE_BY_CID_CNDSETCD = "DELETE f FROM OiomtStdOutItem f "
			+ "WHERE f.stdOutItemOrderPk.cid =:cid AND  f.stdOutItemOrderPk.condSetCd =:condSetCd";

	@Override
	public List<StandardOutputItem> getAllStdOutItem() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdOutItem.class).getList(item -> item.toDomain());
	}

	@Override
	public Optional<StandardOutputItem> getStdOutItemById(String cid, String outItemCd, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutItem.class).setParameter("cid", cid)
				.setParameter("outItemCd", outItemCd).setParameter("condSetCd", condSetCd).getSingle(c -> c.toDomain());
	}

	@Override
	public List<StandardOutputItem> getStdOutItemByCidAndSetCd(String cid, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_CID_AND_SET_CODE, OiomtStdOutItem.class).setParameter("cid", cid)
				.setParameter("condSetCd", condSetCd).getList(c -> c.toDomain());
	}

	@Override
	public void add(StandardOutputItem domain) {
		this.commandProxy().insert(OiomtStdOutItem.toEntity(domain));
	}

	@Override
	public void update(StandardOutputItem domain) {
		this.commandProxy().update(OiomtStdOutItem.toEntity(domain));
	}

	@Override
	public void remove(String cid, String outItemCd, String condSetCd) {
		this.commandProxy().remove(OiomtStdOutItem.class, new OiomtStdOutItemPk(cid, outItemCd, condSetCd));
	}

	@Override
	public void remove(String cid, String condSetCd) {
		this.queryProxy().query(DELETE_BY_CID_CNDSETCD, OiomtStdOutItemOrder.class).setParameter("cid", cid)
				.setParameter("condSetCd", condSetCd);
	}
	
}
