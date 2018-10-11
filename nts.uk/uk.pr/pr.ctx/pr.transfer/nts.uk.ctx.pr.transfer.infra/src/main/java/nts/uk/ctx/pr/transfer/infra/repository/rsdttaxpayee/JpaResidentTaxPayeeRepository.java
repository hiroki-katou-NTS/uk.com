package nts.uk.ctx.pr.transfer.infra.repository.rsdttaxpayee;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayee;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.ResidentTaxPayeeRepository;
import nts.uk.ctx.pr.transfer.infra.entity.rsdttaxpayee.QxxmtRsdtTaxPayee;
import nts.uk.ctx.pr.transfer.infra.entity.rsdttaxpayee.QxxmtRsdtTaxPayeePk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaResidentTaxPayeeRepository extends JpaRepository implements ResidentTaxPayeeRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QxxmtRsdtTaxPayee f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.rsdtTaxPayeePk.cid =:cid AND  f.rsdtTaxPayeePk.code =:code ";
    private static final String SELECT_BY_LIST_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.rsdtTaxPayeePk.cid =:cid AND  f.rsdtTaxPayeePk.code IN :codes ";

    @Override
    public Optional<ResidentTaxPayee> getResidentTaxPayeeById(String cid, String code) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QxxmtRsdtTaxPayee.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public List<ResidentTaxPayee> getListResidentTaxPayee(String cid, List<String> codes) {
        if (codes == null || codes.isEmpty()) return Collections.emptyList();
        return this.queryProxy().query(SELECT_BY_LIST_KEY_STRING, QxxmtRsdtTaxPayee.class)
                .setParameter("cid", cid)
                .setParameter("codes", codes)
                .getList(item -> item.toDomain());
    }

    @Override
    public void add(ResidentTaxPayee domain) {
        this.commandProxy().insert(QxxmtRsdtTaxPayee.toEntity(domain));
    }

    @Override
    public void update(ResidentTaxPayee domain) {
        this.commandProxy().update(QxxmtRsdtTaxPayee.toEntity(domain));
    }

    @Override
    public void remove(String cid, String code) {
        this.commandProxy().remove(QxxmtRsdtTaxPayee.class, new QxxmtRsdtTaxPayeePk(cid, code));
    }
}
