package nts.uk.ctx.pr.core.infra.data.repository.wageprovision.orginfo.salarycls.salaryclsmaster;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfo;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfoRepository;
import nts.uk.ctx.pr.core.infra.data.entity.wageprovision.orginfo.salarycls.salaryclsmaster.QpbmtSalaryClsInfo;
import nts.uk.ctx.pr.core.infra.data.entity.wageprovision.orginfo.salarycls.salaryclsmaster.QpbmtSalaryClsInfoPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSalaryClsInfoRepository extends JpaRepository implements SalaryClsInfoRepository {
    private static final String SELECT = "SELECT e FROM QpbmtSalaryClsInfo e WHERE e.pk.cid = :cid";
    private static final String ORDER_BY = " ORDER BY e.pk.salaryClsCode ASC";
    private static final String FIND_ALL = SELECT + ORDER_BY;
    private static final String FIND_ONE = SELECT + " and e.pk.salaryClsCode = :salaryClsCode" + ORDER_BY;

    @Override
    public List<SalaryClsInfo> findAll() {
        String companyId = AppContexts.user().companyId();
        return this.queryProxy().query(FIND_ALL, QpbmtSalaryClsInfo.class)
                                .setParameter("cid", companyId)
                                .getList(entity -> entity.toDomain());
    }

    @Override
    public Optional<SalaryClsInfo> get(String salaryClsCode) {
        String companyId = AppContexts.user().companyId();
        return this.queryProxy().query(FIND_ONE, QpbmtSalaryClsInfo.class)
                                .setParameter("cid", companyId)
                                .setParameter("salaryClsCode", salaryClsCode)
                                .getSingle(entity -> entity.toDomain());
    }

    @Override
    public void insert(SalaryClsInfo salaryClsInfo) {
        this.commandProxy().insert(QpbmtSalaryClsInfo.of(salaryClsInfo));
    }

    @Override
    public void delete(String salaryClsCode) {
        String companyId = AppContexts.user().companyId();
        val objKey = new QpbmtSalaryClsInfoPk(companyId, salaryClsCode);
        this.commandProxy().remove(QpbmtSalaryClsInfo.class, objKey);
    }

    @Override
    public void update(SalaryClsInfo salaryClsInfo) {
        this.commandProxy().update(QpbmtSalaryClsInfo.of(salaryClsInfo));
    }
}
