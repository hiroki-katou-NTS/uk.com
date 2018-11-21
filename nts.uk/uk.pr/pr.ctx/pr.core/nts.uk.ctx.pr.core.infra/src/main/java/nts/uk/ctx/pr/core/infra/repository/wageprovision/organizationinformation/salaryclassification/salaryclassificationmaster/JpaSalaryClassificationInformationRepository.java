package nts.uk.ctx.pr.core.infra.repository.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.QpbmtSalaryClsInfo;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.QpbmtSalaryClsInfoPk;

@Stateless
public class JpaSalaryClassificationInformationRepository extends JpaRepository implements SalaryClassificationInformationRepository {
    private static final String ORDER_BY = " ORDER BY f.salaryClsInfoPk.salaryClsCd ASC";
    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalaryClsInfo f WHERE f.salaryClsInfoPk.cid =:cid";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " AND f.salaryClsInfoPk.salaryClsCd =:salaryClsCd ";

    @Override
    public List<SalaryClassificationInformation> getAllSalaryClassificationInformation(String cid) {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING + ORDER_BY, QpbmtSalaryClsInfo.class)
                .setParameter("cid", cid)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalaryClassificationInformation> getSalaryClassificationInformationById(String cid, String salaryClsCd) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalaryClsInfo.class)
                .setParameter("cid", cid)
                .setParameter("salaryClsCd", salaryClsCd)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public void add(SalaryClassificationInformation domain) {
        if (getSalaryClassificationInformationById(domain.getCompanyId(), domain.getSalaryClassificationCode().v()).isPresent()) {
            throw new BusinessException("Msg_3");
        }
        this.commandProxy().insert(QpbmtSalaryClsInfo.toEntity(domain));
    }

    @Override
    public void update(SalaryClassificationInformation domain) {
        this.commandProxy().update(QpbmtSalaryClsInfo.toEntity(domain));
    }

    @Override
    public void remove(String cid, String salaryClsCd) {
        this.commandProxy().remove(QpbmtSalaryClsInfo.class, new QpbmtSalaryClsInfoPk(cid, salaryClsCd));
    }
}
