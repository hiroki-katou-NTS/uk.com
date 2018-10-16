package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtBonusHealthInsuranceRate;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtBonusHealthInsuranceRatePk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 賞与健康保険料率
 */
@Stateless
public class JpaBonusHealthInsuranceRateRepository extends JpaRepository implements BonusHealthInsuranceRateRepository {

	private static final String FIND_BY_OFFICE_CODE = "SELECT a FROM QpbmtBonusHealthInsuranceRate a WHERE a.bonusHealthInsurancePk.cid =:cid AND a.bonusHealthInsurancePk.socialInsuranceOfficeCd =:socialInsuranceOfficeCd ORDER BY a.startYearMonth DESC";
    private static final String FIND_BY_HISTORY_ID = "SELECT a FROM QpbmtBonusHealthInsuranceRate a WHERE a.bonusHealthInsurancePk.historyId =:historyId";

	@Override
	public Optional<HealthInsuranceFeeRateHistory> getHealthInsuranceHistoryByOfficeCode(String officeCode) {
		return this.fromHealthInsuranceToHistory(this.queryProxy().query(FIND_BY_OFFICE_CODE, QpbmtBonusHealthInsuranceRate.class).setParameter("cid", AppContexts.user().companyId()).setParameter("socialInsuranceOfficeCd", officeCode).getList());
	}

	@Override
    public Optional<BonusHealthInsuranceRate> getBonusHealthInsuranceRateById(String historyId) {
        return this.queryProxy().query(FIND_BY_HISTORY_ID, QpbmtBonusHealthInsuranceRate.class).setParameter("historyId", historyId).getSingle().map(this::toDomain);
    }

    private BonusHealthInsuranceRate toDomain(QpbmtBonusHealthInsuranceRate entity) {
        return new BonusHealthInsuranceRate(entity.bonusHealthInsurancePk.historyId, entity.employeeShareAmountMethod, entity.individualLongCareInsuranceRate, entity.individualBasicInsuranceRate, entity.individualHealthInsuranceRate, entity.individualFractionCls, entity.individualSpecialInsuranceRate, entity.employeeLongCareInsuranceRate, entity.employeeBasicInsuranceRate, entity.employeeHealthInsuranceRate, entity.employeeFractionCls, entity.employeeSpecialInsuranceRate);
    }

    /**
     * Convert domain to entity
     *
     * @param domain BonusHealthInsuranceRate
     * @return QpbmtBonusHealthInsuranceRate
     */
    private QpbmtBonusHealthInsuranceRate toEntity(BonusHealthInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
        return new QpbmtBonusHealthInsuranceRate(new QpbmtBonusHealthInsuranceRatePk(AppContexts.user().companyId(), officeCode, yearMonth.identifier()), yearMonth.start().v(), yearMonth.end().v(), domain.getEmployeeShareAmountMethod().value,
                domain.getIndividualBurdenRatio().getLongCareInsuranceRate().v(), domain.getIndividualBurdenRatio().getBasicInsuranceRate().v(), domain.getIndividualBurdenRatio().getHealthInsuranceRate().v(), domain.getIndividualBurdenRatio().getFractionCls().value, domain.getIndividualBurdenRatio().getSpecialInsuranceRate().v(),
                domain.getEmployeeBurdenRatio().getLongCareInsuranceRate().v(), domain.getEmployeeBurdenRatio().getBasicInsuranceRate().v(), domain.getEmployeeBurdenRatio().getHealthInsuranceRate().v(), domain.getEmployeeBurdenRatio().getFractionCls().value, domain.getEmployeeBurdenRatio().getSpecialInsuranceRate().v());
    }

	@Override
	public void deleteByHistoryIds(List<String> historyIds) {
		this.commandProxy().removeAll(QpbmtBonusHealthInsuranceRate.class, historyIds);
	}

	@Override
	public void add(BonusHealthInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().insert(toEntity(domain, officeCode, yearMonth));
	}

	@Override
	public void update(BonusHealthInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().update(toEntity(domain, officeCode, yearMonth));
	}
	
	@Override
	public void remove(BonusHealthInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().remove(toEntity(domain, officeCode, yearMonth));
	}

	public Optional<HealthInsuranceFeeRateHistory> fromHealthInsuranceToHistory (List<QpbmtBonusHealthInsuranceRate> healthInsurance) {
    	if (healthInsurance.isEmpty()) return Optional.empty();
		return Optional.of(new HealthInsuranceFeeRateHistory(healthInsurance.get(0).bonusHealthInsurancePk.cid, healthInsurance.get(0).bonusHealthInsurancePk.socialInsuranceOfficeCd,
				healthInsurance.stream().map(item -> new YearMonthHistoryItem(item.bonusHealthInsurancePk.historyId, new YearMonthPeriod(new YearMonth(item.startYearMonth), new YearMonth(item.endYearMonth)))).collect(Collectors.toList())));
	}
}
