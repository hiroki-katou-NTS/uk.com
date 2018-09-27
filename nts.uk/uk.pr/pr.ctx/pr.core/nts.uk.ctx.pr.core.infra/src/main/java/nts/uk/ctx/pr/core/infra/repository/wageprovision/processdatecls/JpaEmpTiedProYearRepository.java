package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmploymentCode;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtEmpTiedProYear;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtEmpTiedProYearPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaEmpTiedProYearRepository extends JpaRepository implements EmpTiedProYearRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpTiedProYear f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empTiedProYearPk.cid =:cid AND  f.empTiedProYearPk.processCateNo =:processCateNo ";
    private static final String DELETE_BY_PROCESSCATENO = "DELETE FROM QpbmtEmpTiedProYear f WHERE f.empTiedProYearPk.cid =:cid AND  f.empTiedProYearPk.processCateNo =:processCateNo ";

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
        this.commandProxy().insertAll(QpbmtEmpTiedProYear.toEntity(domain));
    }

    @Override
    public void update(EmpTiedProYear oldDomain, EmpTiedProYear newDomain) {
        List<EmploymentCode> list = newDomain.getEmploymentCodes().stream().collect(Collectors.toList());
        newDomain.getEmploymentCodes().removeAll(oldDomain.getEmploymentCodes());
        oldDomain.getEmploymentCodes().removeAll(list);
        List<QpbmtEmpTiedProYearPk> pks = oldDomain.getEmploymentCodes().stream().map(x -> new QpbmtEmpTiedProYearPk(oldDomain.getCid(), oldDomain.getProcessCateNo(), x.v())).collect(Collectors.toList());
        this.commandProxy().removeAll(QpbmtEmpTiedProYear.class, pks);
        this.commandProxy().insertAll(QpbmtEmpTiedProYear.toEntity(newDomain));
    }

    @Override
    public void remove(String cid, int processCateNo) {
        this.getEntityManager().createQuery(DELETE_BY_PROCESSCATENO)
                .setParameter("cid", cid)
                .setParameter("processCateNo", processCateNo).executeUpdate();
    }

}
