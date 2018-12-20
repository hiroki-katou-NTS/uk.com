package nts.uk.ctx.pr.transfer.infra.repository.rsdttaxpayee;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayee;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayeeRepository;
import nts.uk.ctx.pr.transfer.infra.entity.rsdttaxpayee.QbtmtRsdtTaxPayee;
import nts.uk.ctx.pr.transfer.infra.entity.rsdttaxpayee.QbtmtRsdtTaxPayeePk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaResidentTaxPayeeRepository extends JpaRepository implements ResidentTaxPayeeRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QbtmtRsdtTaxPayee f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.rsdtTaxPayeePk.cid =:cid AND  f.rsdtTaxPayeePk.code =:code ";
    private static final String SELECT_BY_LIST_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.rsdtTaxPayeePk.cid =:cid AND  f.rsdtTaxPayeePk.code IN :codes ";

    @Override
    public Optional<ResidentTaxPayee> getResidentTaxPayeeById(String cid, String code) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QbtmtRsdtTaxPayee.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public List<ResidentTaxPayee> getListResidentTaxPayee(String cid, List<String> codes) {
        if (codes == null || codes.isEmpty()) return Collections.emptyList();
        return this.queryProxy().query(SELECT_BY_LIST_KEY_STRING, QbtmtRsdtTaxPayee.class)
                .setParameter("cid", cid)
                .setParameter("codes", codes)
                .getList(item -> item.toDomain());
    }

    @Override
    public void add(ResidentTaxPayee domain) {
        this.commandProxy().insert(QbtmtRsdtTaxPayee.toEntity(domain));
    }

    @Override
    public void update(ResidentTaxPayee domain) {
        this.commandProxy().update(QbtmtRsdtTaxPayee.toEntity(domain));
    }

    @Override
    public void remove(String cid, String code) {
        this.commandProxy().remove(QbtmtRsdtTaxPayee.class, new QbtmtRsdtTaxPayeePk(cid, code));
    }

	@Override
	public List<ResidentTaxPayee> getAllResidentTaxPayee(String companyId) {
		String query = SELECT_ALL_QUERY_STRING + " WHERE  f.rsdtTaxPayeePk.cid =:cid ORDER BY f.rsdtTaxPayeePk.code";
		return this.queryProxy().query(query, QbtmtRsdtTaxPayee.class).setParameter("cid", companyId)
				.getList(item -> item.toDomain());
	}

	@Override
	public List<ResidentTaxPayee> getResidentTaxPayeeWithReportCd(String cid, String reportCode) {
		String query = SELECT_ALL_QUERY_STRING
				+ " WHERE  f.rsdtTaxPayeePk.cid =:cid AND f.reportCd = :reportCode ORDER BY f.rsdtTaxPayeePk.code";
		return this.queryProxy().query(query, QbtmtRsdtTaxPayee.class).setParameter("cid", cid)
				.setParameter("reportCode", reportCode).getList(item -> item.toDomain());
	}
	
}
