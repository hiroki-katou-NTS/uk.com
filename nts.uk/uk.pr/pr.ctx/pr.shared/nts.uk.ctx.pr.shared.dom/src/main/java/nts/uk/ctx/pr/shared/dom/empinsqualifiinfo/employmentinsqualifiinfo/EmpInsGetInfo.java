package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
 * 雇用保険取得時情報
 */
@Getter
public class EmpInsGetInfo extends AggregateRoot {

    /**
     * 社員ID
     */
    private String sid;

    /**
     * 労働時間
     */
    private Optional<WorkingTime> workingTime;

    /**
     * 取得区分
     */
    private Optional<AcquisitionAtr> acquisitionAtr;

    /**
     * 契約期間の印刷区分
     */
    private Optional<ContractPeriodPrintAtr> printAtr;

    /**
     * 就職経路
     */
    private Optional<JobPath> jobPath;

    /**
     * 支払賃金
     */
    private Optional<PayWage> payWage;

    /**
     * 職種
     */
    private Optional<JobAtr> jobAtr;

    /**
     * 被保険者原因
     */
    private Optional<InsuranceCauseAtr> insCauseAtr;

    /**
     * 賃金支払態様
     */
    private Optional<WagePaymentMode> paymentMode;

    /**
     * 雇用形態
     */
    private Optional<EmploymentStatus> employmentStatus;

    public EmpInsGetInfo(String sid, Integer workingTime, Integer acquisitionAtr, Integer printAtr, Integer jobPath, Integer payWage, Integer jobAtr, Integer insCauseAtr, Integer paymentMode, Integer employmentStatus) {
        this.sid = sid;
    }

}
