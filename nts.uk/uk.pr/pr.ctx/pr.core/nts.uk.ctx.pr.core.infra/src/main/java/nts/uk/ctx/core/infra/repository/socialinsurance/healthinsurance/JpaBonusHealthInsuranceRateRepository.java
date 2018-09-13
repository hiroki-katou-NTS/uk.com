package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRateRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtBonusHealthInsuranceRate;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 賞与健康保険料率
 */
@Stateless
public class JpaBonusHealthInsuranceRateRepository extends JpaRepository implements BonusHealthInsuranceRateRepository {

    @Override
    public Optional<BonusHealthInsuranceRate> getBonusHealthInsuranceRateById(String historyId) {
        return this.queryProxy().find(historyId, QpbmtBonusHealthInsuranceRate.class).map(this::toDomain);
    }

    private BonusHealthInsuranceRate toDomain(QpbmtBonusHealthInsuranceRate entity) {
        return new BonusHealthInsuranceRate(entity.historyId, entity.employeeShareAmountMethod, entity.individualLongCareInsuranceRate, entity.individualBasicInsuranceRate, entity.individualHealthInsuranceRate, entity.individualFractionCls, entity.individualSpecialInsuranceRate, entity.employeeLongCareInsuranceRate, entity.employeeBasicInsuranceRate, entity.employeeHealthInsuranceRate, entity.employeeFractionCls, entity.employeeSpecialInsuranceRate);
    }

    /**
     * Convert domain to entity
     *
     * @param domain BonusHealthInsuranceRate
     * @return QpbmtBonusHealthInsuranceRate
     */
    private QpbmtBonusHealthInsuranceRate toEntity(BonusHealthInsuranceRate domain) {
        return new QpbmtBonusHealthInsuranceRate(domain.getHistoryID(), domain.getEmployeeShareAmountMethod().value,
                domain.getIndividualBurdenRatio().getLongCareInsuranceRate().v(), domain.getIndividualBurdenRatio().getBasicInsuranceRate().v(), domain.getIndividualBurdenRatio().getHealthInsuranceRate().v(), domain.getIndividualBurdenRatio().getFractionCls().value, domain.getIndividualBurdenRatio().getSpecialInsuranceRate().v(),
                domain.getEmployeeBurdenRatio().getLongCareInsuranceRate().v(), domain.getEmployeeBurdenRatio().getBasicInsuranceRate().v(), domain.getEmployeeBurdenRatio().getHealthInsuranceRate().v(), domain.getEmployeeBurdenRatio().getFractionCls().value, domain.getEmployeeBurdenRatio().getSpecialInsuranceRate().v());
    }
}
