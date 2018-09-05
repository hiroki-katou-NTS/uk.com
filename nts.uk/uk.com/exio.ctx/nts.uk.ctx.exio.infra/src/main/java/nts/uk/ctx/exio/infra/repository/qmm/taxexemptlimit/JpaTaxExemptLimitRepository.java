package nts.uk.ctx.exio.infra.repository.qmm.taxexemptlimit;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.qmm.taxexemptlimit.TaxExemptLimit;
import nts.uk.ctx.exio.dom.qmm.taxexemptlimit.TaxExemptLimitRepository;
import nts.uk.ctx.exio.infra.entity.qmm.taxexemptlimit.QpbmtTaxExemptLimit;
import nts.uk.ctx.exio.infra.entity.qmm.taxexemptlimit.QpbmtTaxExemptLimitPk;

@Stateless
public class JpaTaxExemptLimitRepository extends JpaRepository implements TaxExemptLimitRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtTaxExemptLimit f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.taxExemptLimitPk.cid =:cid ";

	@Override
	public List<TaxExemptLimit> getAllTaxExemptLimit() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtTaxExemptLimit.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<TaxExemptLimit> getTaxExemptLimitById(String cid) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtTaxExemptLimit.class).setParameter("cid", cid)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void add(TaxExemptLimit domain) {
		this.commandProxy().insert(QpbmtTaxExemptLimit.toEntity(domain));
	}

	@Override
	public void update(TaxExemptLimit domain) {
		this.commandProxy().update(QpbmtTaxExemptLimit.toEntity(domain));
	}

	@Override
	public void remove(String cid) {
		this.commandProxy().remove(QpbmtTaxExemptLimit.class, new QpbmtTaxExemptLimitPk(cid));
	}
}
