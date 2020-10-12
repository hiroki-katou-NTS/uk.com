package nts.uk.ctx.at.record.pub.monthly.agreement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementTimeByEmp;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.PeriodAtrOfAgreement;

@Getter
@Setter
@NoArgsConstructor
public class AgreementTimeByEmpExport {
    /**
     * 社員ID
     */
    private String employeeId;
    /**
     * 期間区分
     */
    private PeriodAtrOfAgreement periodAtr;
    /**
     * 指定期間36協定時間
     */
    private AgreementTimeOfManagePeriodExport agreementTime;

    public static AgreementTimeByEmpExport fromDomain(AgreementTimeByEmp domain) {
        AgreementTimeByEmpExport time = new AgreementTimeByEmpExport();
        time.employeeId = domain.getEmployeeId();
        time.periodAtr = domain.getPeriodAtr();
        time.agreementTime = AgreementTimeOfManagePeriodExport.builder()
        		.sid(domain.getAgreementTime().getSid())
        		.ym(domain.getAgreementTime().getYm())
        		.agreementTime(AgreementTimeOfMonthlyExport.copy(domain.getAgreementTime().getAgreementTime()))
        		.legalMaxTime(AgreementTimeOfMonthlyExport.copy(domain.getAgreementTime().getLegalMaxTime()))
        		.status(domain.getAgreementTime().getStatus().value)
        		.breakdown(AgreementTimeBreakdownExport.copy(domain.getAgreementTime().getBreakdown()))
        		.build();
        return time;
    }
}
