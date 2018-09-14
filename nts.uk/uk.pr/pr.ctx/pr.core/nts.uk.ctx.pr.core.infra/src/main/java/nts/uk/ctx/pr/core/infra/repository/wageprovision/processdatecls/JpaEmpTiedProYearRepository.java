package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtEmpTiedProYear;

@Stateless
public class JpaEmpTiedProYearRepository extends JpaRepository implements EmpTiedProYearRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpTiedProYear f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empTiedProYearPk.cid =:cid AND  f.empTiedProYearPk.processCateNo =:processCateNo ";


    @Override
    public Optional<EmpTiedProYear> getEmpTiedProYearById(String cid, int processCateNo) {
        List<QpbmtEmpTiedProYear> item = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpTiedProYear.class)
                .setParameter("cid", cid)
                .setParameter("processCateNo", processCateNo)
                .getList();
        if (item.isEmpty()) return Optional.empty();
        return Optional.of(QpbmtEmpTiedProYear.toDomain(item));
    }

    @Override
    public void add(EmpTiedProYear domain) {
        this.commandProxy().insert(QpbmtEmpTiedProYear.toEntity(domain));
    }

    @Override
    public void update(EmpTiedProYear domain) {
        this.commandProxy().update(QpbmtEmpTiedProYear.toEntity(domain));
    }


}
