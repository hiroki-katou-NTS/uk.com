package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtEmpSocialInsGradeInfoPk;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaEmpSocialInsGradeInfoRepository extends JpaRepository implements EmpSocialInsGradeInfoRepository {

    public QqsmtEmpSocialInsGradeInfo toEntity(EmpSocialInsGradeInfo domain) {
        return new QqsmtEmpSocialInsGradeInfo(
                new QqsmtEmpSocialInsGradeInfoPk(domain.getHistId()),
                domain.getSocInsMonthlyRemune().v(),
                domain.getCalculationAtr().value,
                domain.getHealInsStandMonthlyRemune().map(x -> x.v()).orElse(null),
                domain.getHealInsGrade().map(x -> x.v()).orElse(null),
                domain.getPensionInsStandCompenMonthly().map(x -> x.v()).orElse(null),
                domain.getPensionInsGrade().map(x -> x.v()).orElse(null)
        );
    }

    public void updateEntity(EmpSocialInsGradeInfo domain, QqsmtEmpSocialInsGradeInfo entity) {
        entity.socialInsPayMonthly = domain.getSocInsMonthlyRemune().v();
        entity.calculationAtr = domain.getCalculationAtr().value;
        entity.healInsMonthlyPay = domain.getHealInsStandMonthlyRemune().map(x -> x.v()).orElse(null);
        entity.healInsGrade = domain.getHealInsGrade().map(x -> x.v()).orElse(null);
        entity.pensionInsMonthlyPay = domain.getPensionInsStandCompenMonthly().map(x -> x.v()).orElse(null);
        entity.pensionInsGrade = domain.getPensionInsGrade().map(x -> x.v()).orElse(null);
    }

    @Override
    public void add(EmpSocialInsGradeInfo domain) {
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(EmpSocialInsGradeInfo domain) {
        Optional<QqsmtEmpSocialInsGradeInfo> entity = this.queryProxy().find(domain.getHistId(),
                QqsmtEmpSocialInsGradeInfo.class);

        if (!entity.isPresent()) {
            throw new RuntimeException("invalid EmpSocialInsGradeInfo");
        }
        updateEntity(domain, entity.get());

        this.commandProxy().update(entity.get());
    }

    @Override
    public void delete(String histId) {
        Optional<QqsmtEmpSocialInsGradeHis> tempAbs = this.queryProxy().find(histId, QqsmtEmpSocialInsGradeHis.class);

        if (!tempAbs.isPresent()) {
            throw new RuntimeException("invalid EmpSocialInsGradeHis");
        }

        this.commandProxy().remove(QqsmtEmpSocialInsGradeHis.class, histId);
    }

    @Override
    public Optional<EmpSocialInsGradeInfo> getEmpSocialInsGradeInfoByHistId(String histId) {
        Optional<QqsmtEmpSocialInsGradeInfo> gradeInfo = this.queryProxy().find(histId, QqsmtEmpSocialInsGradeInfo.class);
        return gradeInfo.map(this::toDomain);
    }

    private EmpSocialInsGradeInfo toDomain(QqsmtEmpSocialInsGradeInfo entity) {
        return entity.toDomain();
    }
}
