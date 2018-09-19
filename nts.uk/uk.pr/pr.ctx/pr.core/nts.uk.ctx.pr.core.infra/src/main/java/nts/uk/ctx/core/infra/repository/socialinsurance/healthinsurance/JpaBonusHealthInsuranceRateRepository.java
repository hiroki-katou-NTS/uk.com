package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRateRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtBonusHealthInsuranceRate;

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

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
		this.commandProxy().removeAll(QpbmtBonusHealthInsuranceRate.class, historyIds);
	}

	@Override
	public void add(BonusHealthInsuranceRate domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(BonusHealthInsuranceRate domain) {
		this.commandProxy().update(toEntity(domain));
	}
	
	@Override
	public void remove(BonusHealthInsuranceRate domain) {
		this.commandProxy().remove(toEntity(domain));
	}
}
