package nts.uk.ctx.core.infra.repository.socialinsurance.healthinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsurancePerGradeFee;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsuranceMonthlyFee;
import nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance.QpbmtHealthInsurancePerGradeFee;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class JpaHealthInsuranceMonthlyFeeRepository extends JpaRepository implements HealthInsuranceMonthlyFeeRepository {

    /**
     * Convert entity to domain
     *
     * @param entity QpbmtHealthInsuranceMonthlyFee
     * @return HealthInsuranceMonthlyFee
     */
    private HealthInsuranceMonthlyFee toDomain(QpbmtHealthInsuranceMonthlyFee entity, List<QpbmtHealthInsurancePerGradeFee> details) {
        return new HealthInsuranceMonthlyFee(entity.historyId, entity.employeeShareAmountMethod,
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
                        x.insuredBasicInsuranceFee,
                        x.insuredBasicInsuranceFee
                )).collect(Collectors.toList())
        );
    }
}
