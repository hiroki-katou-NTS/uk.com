package nts.uk.ctx.exio.infra.repository.qmm.paymentItemset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.qmm.paymentItemset.PaymentItemSt;
import nts.uk.ctx.exio.dom.qmm.paymentItemset.PaymentItemStRepository;
import nts.uk.ctx.exio.infra.entity.qmm.paymentItemset.QpbmtPaymentItemSt;
import nts.uk.ctx.exio.infra.entity.qmm.paymentItemset.QpbmtPaymentItemStPk;

@Stateless
public class JpaPaymentItemStRepository extends JpaRepository implements PaymentItemStRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPaymentItemSt f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.paymentItemStPk.cid =:cid AND  f.paymentItemStPk.salaryItemId =:salaryItemId ";

	@Override
	public List<PaymentItemSt> getAllPaymentItemSt() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPaymentItemSt.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<PaymentItemSt> getPaymentItemStById(String cid, String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPaymentItemSt.class).setParameter("cid", cid)
				.setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(PaymentItemSt domain) {
		this.commandProxy().insert(QpbmtPaymentItemSt.toEntity(domain));
	}

	@Override
	public void update(PaymentItemSt domain) {
		this.commandProxy().update(QpbmtPaymentItemSt.toEntity(domain));
	}

	@Override
	public void remove(String cid, String salaryItemId) {
		this.commandProxy().remove(QpbmtPaymentItemSt.class, new QpbmtPaymentItemStPk(cid, salaryItemId));
	}
}
