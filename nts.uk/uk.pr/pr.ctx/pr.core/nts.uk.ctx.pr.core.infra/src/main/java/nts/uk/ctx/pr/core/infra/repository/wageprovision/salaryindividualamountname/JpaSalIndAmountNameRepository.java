package nts.uk.ctx.pr.core.infra.repository.wageprovision.salaryindividualamountname;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountName;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.salaryindividualamountname.QpbmtSalIndAmountName;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.salaryindividualamountname.QpbmtSalIndAmountNamePk;

@Stateless
public class JpaSalIndAmountNameRepository extends JpaRepository implements SalIndAmountNameRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalIndAmountName f";
    private static final String SELECT_BY_CID_QUERY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salIndAmountNamePk.cid =:cid";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salIndAmountNamePk.cid =:cid AND  f.salIndAmountNamePk.cateIndicator =:cateIndicator AND  f.salIndAmountNamePk.individualPriceCode =:individualPriceCode  ";
    private static final String SELECT_ALL_BY_CATEINDICATOR = SELECT_ALL_QUERY_STRING + " WHERE  f.salIndAmountNamePk.cid =:cid AND  f.salIndAmountNamePk.cateIndicator =:cateIndicator ORDER BY f.salIndAmountNamePk.individualPriceCode ASC";

    @Override
    public List<SalIndAmountName> getAllSalIndAmountName() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSalIndAmountName.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<SalIndAmountName> getSalIndAmountName(String cid) {
        return this.queryProxy().query(SELECT_BY_CID_QUERY_STRING, QpbmtSalIndAmountName.class)
                .setParameter("cid", cid)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalIndAmountName> getSalIndAmountNameById(String cid, String individualPriceCode, int cateIndicator) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalIndAmountName.class)
                .setParameter("cid", cid)
                .setParameter("individualPriceCode", individualPriceCode)
                .setParameter("cateIndicator", cateIndicator)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public List<SalIndAmountName> getAllSalIndAmountNameByCateIndi(String cid, int cateIndicator) {
        return this.queryProxy().query(SELECT_ALL_BY_CATEINDICATOR, QpbmtSalIndAmountName.class)
                .setParameter("cid", cid)
                .setParameter("cateIndicator", cateIndicator)
                .getList(c -> c.toDomain());
    }

    @Override
    public void add(SalIndAmountName domain) {
        this.commandProxy().insert(QpbmtSalIndAmountName.toEntity(domain));
    }

    @Override
    public void update(SalIndAmountName domain) {
        this.commandProxy().update(QpbmtSalIndAmountName.toEntity(domain));
    }

    @Override
    public void remove(String cid, String individualPriceCode, int cateIndicator) {
        this.commandProxy().remove(QpbmtSalIndAmountName.class, new QpbmtSalIndAmountNamePk(cid, cateIndicator, individualPriceCode));
    }


}
