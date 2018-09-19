package nts.uk.ctx.exio.infra.repository.exo.outputitemorder;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;
import nts.uk.ctx.exio.infra.entity.exo.outputitemorder.OiomtStdOutItemOrder;
import nts.uk.ctx.exio.infra.entity.exo.outputitemorder.OiomtStdOutItemOrderPk;

@Stateless
public class JpaStandardOutputItemOrderRepository extends JpaRepository implements StandardOutputItemOrderRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdOutItemOrder f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemOrderPk.cid =:cid AND  f.stdOutItemOrderPk.outItemCd =:outItemCd AND  f.stdOutItemOrderPk.condSetCd =:condSetCd ";
	private static final String SELECT_BY_CID_AND_SET_CODE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.stdOutItemOrderPk.cid =:cid AND  f.stdOutItemOrderPk.condSetCd =:condSetCd ";
	
	private static final String DELETE_OUT_ITEM_ORDER = "DELETE FROM OiomtStdOutItemOrder f "
            + "WHERE f.stdOutItemOrderPk.cid =:cid AND f.stdOutItemOrderPk.condSetCd =:condSetCd ";

	@Override
	public List<StandardOutputItemOrder> getAllStandardOutputItemOrder() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdOutItemOrder.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<StandardOutputItemOrder> getStandardOutputItemOrderById(String cid, String outItemCd,
			String condSetCd) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdOutItemOrder.class).setParameter("cid", cid)
				.setParameter("outItemCd", outItemCd).setParameter("condSetCd", condSetCd).getSingle(c -> c.toDomain());
	}

	@Override
	public List<StandardOutputItemOrder> getStandardOutputItemOrderByCidAndSetCd(String cid, String condSetCd) {
		return this.queryProxy().query(SELECT_BY_CID_AND_SET_CODE, OiomtStdOutItemOrder.class).setParameter("cid", cid)
				.setParameter("condSetCd", condSetCd).getList(c -> c.toDomain());
	}

	@Override
	public void add(StandardOutputItemOrder domain) {
	    Optional<OiomtStdOutItemOrder> stdOutputCondSet = this.queryProxy().find(new OiomtStdOutItemOrderPk(domain.getCid(), domain.getOutputItemCode().v() ,domain.getConditionSettingCode().v()), OiomtStdOutItemOrder.class);
	    if (stdOutputCondSet.isPresent()) {
	        this.commandProxy().update(OiomtStdOutItemOrder.toEntity(domain));
	    } else {
	        this.commandProxy().insert(OiomtStdOutItemOrder.toEntity(domain));
	    }
	}
	
	@Override
	public void add(List<StandardOutputItemOrder> domain) {
		this.commandProxy().insertAll(OiomtStdOutItemOrder.toEntity(domain));
	}

	@Override
	public void update(StandardOutputItemOrder domain) {
		this.commandProxy().update(OiomtStdOutItemOrder.toEntity(domain));
	}	

	@Override
	public void remove(String cid, String outItemCd, String condSetCd) {
		this.getStandardOutputItemOrderById(cid, outItemCd, condSetCd).ifPresent(e -> {
            this.commandProxy().remove(OiomtStdOutItemOrder.class, new OiomtStdOutItemOrderPk(cid, outItemCd, condSetCd));
        });
	}
	
	@Override
	public void remove(String cid, String condSetCd) {
	    this.getEntityManager().createQuery(DELETE_OUT_ITEM_ORDER, OiomtStdOutItemOrder.class).setParameter("cid", cid).setParameter("condSetCd", condSetCd).executeUpdate();
	}
}
