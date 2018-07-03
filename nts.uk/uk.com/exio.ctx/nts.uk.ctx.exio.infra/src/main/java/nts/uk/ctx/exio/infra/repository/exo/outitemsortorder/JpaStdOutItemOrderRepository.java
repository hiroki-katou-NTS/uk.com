package nts.uk.ctx.exio.infra.repository.exo.outitemsortorder;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outitemsortorder.StdOutItemOrder;
import nts.uk.ctx.exio.dom.exo.outitemsortorder.StdOutItemOrderRepository;
import nts.uk.ctx.exio.infra.entity.exo.outitemsortorder.OiomtStdOutItemOrder;
import nts.uk.ctx.exio.infra.entity.exo.outitemsortorder.OiomtStdOutItemOrderPk;

@Stateless
public class JpaStdOutItemOrderRepository extends JpaRepository implements StdOutItemOrderRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdOutItemOrder f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemOrderPk.cid =:cid AND  f.stdOutItemOrderPk.outItemCd =:outItemCd AND  f.stdOutItemOrderPk.condSetCd =:condSetCd ";

	@Override
	public List<StdOutItemOrder> getAllStdOutItemOrder() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdOutItemOrder.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<StdOutItemOrder> getStdOutItemOrderById(String cid, String outItemCd, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutItemOrder.class).setParameter("cid", cid)
				.setParameter("outItemCd", outItemCd).setParameter("condSetCd", condSetCd).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(StdOutItemOrder domain) {
		this.commandProxy().insert(OiomtStdOutItemOrder.toEntity(domain));
	}

	@Override
	public void update(StdOutItemOrder domain) {
		this.commandProxy().update(OiomtStdOutItemOrder.toEntity(domain));
	}

	@Override
	public void remove(String cid, String outItemCd, String condSetCd) {
		this.commandProxy().remove(OiomtStdOutItemOrder.class, new OiomtStdOutItemOrderPk(cid, outItemCd, condSetCd));
	}
}
