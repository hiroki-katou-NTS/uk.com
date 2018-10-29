package nts.uk.ctx.pr.core.infra.repository.socialinsurance.healthinsurance;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.HealthInsurancePerGradeFee;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.*;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceMonthlyFee;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceMonthlyFeePk;
import nts.uk.ctx.pr.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsurancePerGradeFee;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaHealthInsuranceMonthlyFeeRepository extends JpaRepository implements HealthInsuranceMonthlyFeeRepository {

    private static final String GET_HEALTH_INSURANCE_MONTHLY_BY_HISTORY_ID = "SELECT a FROM QpbmtHealthInsuranceMonthlyFee a WHERE a.bonusHealthInsurancePk.historyId=:historyId";
    private static final String GET_HEALTH_INSURANCE_PER_GRADE_FEE_BY_HISTORY_ID = "SELECT a FROM QpbmtHealthInsurancePerGradeFee a WHERE a.healthMonPerGraPk.historyId=:historyId";
    private static final String DELETE_HEALTH_INSURANCE_MONTHLY_BY_HISTORY_ID = "DELETE FROM QpbmtHealthInsuranceMonthlyFee a WHERE a.bonusHealthInsurancePk.historyId IN :historyId";
    private static final String DELETE_HEALTH_INSURANCE_PER_GRADE_BY_HISTORY_ID = "DELETE FROM QpbmtHealthInsurancePerGradeFee a WHERE a.healthMonPerGraPk.historyId IN :historyId";
    
    @Override
    public Optional<HealthInsuranceMonthlyFee> getHealthInsuranceMonthlyFeeById(String historyId) {
        val entity = this.queryProxy().query(GET_HEALTH_INSURANCE_MONTHLY_BY_HISTORY_ID, QpbmtHealthInsuranceMonthlyFee.class).setParameter("historyId", historyId).getSingle();

        if (!entity.isPresent())
            return Optional.empty();

        val details = this.queryProxy().query(GET_HEALTH_INSURANCE_PER_GRADE_FEE_BY_HISTORY_ID, QpbmtHealthInsurancePerGradeFee.class).setParameter("historyId", historyId).getList();

        return Optional.of(toDomain(entity.get(), details));
    }

    /**
     * Convert entity to domain
     *
     * @param entity QpbmtHealthInsuranceMonthlyFee
     * @return HealthInsuranceMonthlyFee
     */
    private HealthInsuranceMonthlyFee toDomain(QpbmtHealthInsuranceMonthlyFee entity, List<QpbmtHealthInsurancePerGradeFee> details) {
        return new HealthInsuranceMonthlyFee(entity.bonusHealthInsurancePk.historyId, entity.employeeShareAmountMethod,
                entity.individualLongCareInsuranceRate, entity.individualBasicInsuranceRate, entity.individualHealthInsuranceRate, entity.individualFractionCls, entity.individualSpecialInsuranceRate,
                entity.employeeLongCareInsuranceRate, entity.employeeBasicInsuranceRate, entity.employeeHealthInsuranceRate, entity.employeeFractionCls, entity.employeeSpecialInsuranceRate,
                entity.autoCalculationCls,
                details.stream().map(x -> new HealthInsurancePerGradeFee(
                        x.healthMonPerGraPk.healthInsuranceGrade,
                        x.employeeHealthInsuranceFee,
                        x.employeeNursingCareInsuranceFee,
                        x.employeeSpecificInsuranceFee,
                        x.employeeBasicInsuranceFee,
                        x.insuredHealthInsuranceFee,
                        x.insuredNursingCareInsuranceFee,
                        x.insuredSpecificInsuranceFee,
                        x.insuredBasicInsuranceFee
                )).collect(Collectors.toList())
        );
    }

    @Override
    public void deleteByHistoryIds(List<String> historyIds) {
        if(!historyIds.isEmpty()) {
            this.getEntityManager().createQuery(DELETE_HEALTH_INSURANCE_MONTHLY_BY_HISTORY_ID, HealthInsuranceMonthlyFee.class).setParameter("historyId", historyIds).executeUpdate();
        	 this.deleteHealthInsurancePerGradeByHistoryId(historyIds);
        }
    }

    @Override
	public void add(HealthInsuranceMonthlyFee domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().insert(QpbmtHealthInsuranceMonthlyFee.toEntity(domain, officeCode, yearMonth));
		this.commandProxy().insertAll(QpbmtHealthInsurancePerGradeFee.toEntity(domain, officeCode, yearMonth));
	}

	@Override
	public void update(HealthInsuranceMonthlyFee domain, String officeCode, YearMonthHistoryItem yearMonth) {
		this.commandProxy().update(QpbmtHealthInsuranceMonthlyFee.toEntity(domain, officeCode, yearMonth));
		if (domain.getAutoCalculationCls() == AutoCalculationExecutionCls.AUTO){
			this.deleteHealthInsurancePerGradeByHistoryId(Arrays.asList(domain.getHistoryId()));
			this.commandProxy().insertAll(QpbmtHealthInsurancePerGradeFee.toEntity(domain, officeCode, yearMonth));
		}
		
	}

    @Override
    public void delete(HealthInsuranceMonthlyFee domain, String officeCode, YearMonthHistoryItem yearMonth) {
        this.commandProxy().remove(QpbmtHealthInsurancePerGradeFee.toEntity(domain, officeCode, yearMonth));
        this.commandProxy().removeAll(QpbmtHealthInsurancePerGradeFee.toEntity(domain, officeCode, yearMonth));
    }

	@Override
	public void updateGraFee(HealthInsuranceMonthlyFee domain) {
        Optional<QpbmtHealthInsuranceMonthlyFee> entity = this.queryProxy().query(GET_HEALTH_INSURANCE_MONTHLY_BY_HISTORY_ID,QpbmtHealthInsuranceMonthlyFee.class)
                .setParameter("historyId",domain.getHistoryId())
                .getSingle();
		if (!entity.isPresent()) return;
        this.commandProxy().updateAll(this.toEntityWithOldData(domain, entity.get()));
	}
	
	@Override
	public void insertGraFee(HealthInsuranceMonthlyFee domain) {
        Optional<QpbmtHealthInsuranceMonthlyFee> entity = this.queryProxy().query(GET_HEALTH_INSURANCE_MONTHLY_BY_HISTORY_ID,QpbmtHealthInsuranceMonthlyFee.class)
                .setParameter("historyId", domain.getHistoryId())
                .getSingle();
        if (!entity.isPresent()) return;
        this.commandProxy().insertAll(this.toEntityWithOldData(domain, entity.get()));
	}

	@Override
	public void deleteHealthInsurancePerGradeByHistoryId (List<String> historyIds){
    	this.getEntityManager().createQuery(DELETE_HEALTH_INSURANCE_PER_GRADE_BY_HISTORY_ID, QpbmtHealthInsurancePerGradeFee.class).setParameter("historyId", historyIds).executeUpdate();
    }

    private List<QpbmtHealthInsurancePerGradeFee> toEntityWithOldData(HealthInsuranceMonthlyFee domain, QpbmtHealthInsuranceMonthlyFee entity) {
        return QpbmtHealthInsurancePerGradeFee.toEntity(domain, entity.bonusHealthInsurancePk.socialInsuranceOfficeCd, new YearMonthHistoryItem(entity.bonusHealthInsurancePk.historyId, new YearMonthPeriod(new YearMonth(entity.startYearMonth), new YearMonth(entity.endYearMonth))));
    }

    @Override
    public void updateHistory(String officeCode, YearMonthHistoryItem history) {
       this.updateHealthInsuranceMonthlyHistory(officeCode, history);
        this.updateHealthInsurancePerGradeHistory(officeCode, history);
    }

    private void updateHealthInsuranceMonthlyHistory (String officeCode, YearMonthHistoryItem history) {
        Optional<QpbmtHealthInsuranceMonthlyFee> opt_entity = this.queryProxy().find(new QpbmtHealthInsuranceMonthlyFeePk(AppContexts.user().companyId(), officeCode, history.identifier()), QpbmtHealthInsuranceMonthlyFee.class);
        if (!opt_entity.isPresent()) return;
        QpbmtHealthInsuranceMonthlyFee entity = opt_entity.get();
        entity.startYearMonth = history.start().v();
        entity.endYearMonth = history.end().v();
        this.commandProxy().update(entity);
    }

    private void updateHealthInsurancePerGradeHistory (String officeCode, YearMonthHistoryItem history) {
        List<QpbmtHealthInsurancePerGradeFee> entities = this.queryProxy().query(GET_HEALTH_INSURANCE_PER_GRADE_FEE_BY_HISTORY_ID, QpbmtHealthInsurancePerGradeFee.class).setParameter("historyId", history.identifier()).getList();
        for(QpbmtHealthInsurancePerGradeFee entity: entities){
            entity.startYearMonth = history.start().v();
            entity.endYearMonth = history.end().v();
        }
        this.commandProxy().updateAll(entities);
    }
}
