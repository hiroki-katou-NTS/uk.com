package nts.uk.ctx.pr.core.infra.repository.wageprovision.taxexemptionlimit;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptLimit;
import nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit.TaxExemptLimitRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.taxexemptionlimit.QpbmtTaxExemptLimit;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.taxexemptionlimit.QpbmtTaxExemptLimitPk;

@Stateless
public class JpaTaxExemptLimitRepository extends JpaRepository implements TaxExemptLimitRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtTaxExemptLimit f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.taxExemptLimitPk.cid =:cid AND  f.taxExemptLimitPk.taxFreeamountCode =:taxFreeamountCode ";
	private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.taxExemptLimitPk.cid =:cid";

	@Override
	public List<TaxExemptLimit> getAllTaxExemptLimit() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtTaxExemptLimit.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public List<TaxExemptLimit> getTaxExemptLimitByCompanyId(String cid) {
		return this.queryProxy().query(SELECT_BY_CID, QpbmtTaxExemptLimit.class)
				.setParameter("cid", cid).getList(i -> i.toDomain());
	}

	@Override
	public Optional<TaxExemptLimit> getTaxExemptLimitById(String cid, String taxFreeamountCode) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtTaxExemptLimit.class).setParameter("cid", cid)
				.setParameter("taxFreeamountCode", taxFreeamountCode).getSingle(c -> c.toDomain());
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
	public void remove(String cid, String taxFreeamountCode) {
		this.commandProxy().remove(QpbmtTaxExemptLimit.class, new QpbmtTaxExemptLimitPk(cid, taxFreeamountCode));
	}



	
}
