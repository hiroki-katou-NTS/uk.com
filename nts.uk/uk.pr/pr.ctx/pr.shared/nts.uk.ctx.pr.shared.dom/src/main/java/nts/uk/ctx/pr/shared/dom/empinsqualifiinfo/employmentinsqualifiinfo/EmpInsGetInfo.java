package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
 * 雇用保険取得時情報
 */
@Getter
public class EmpInsGetInfo extends AggregateRoot {

    /**
     * 会社ID
     */
    private String cId;

    /**
     * 社員ID
     */
    private String sId;

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

    public EmpInsGetInfo(String cId, String sId, Integer workingTime, Integer acquisitionAtr, Integer printAtr, Integer jobPath, Integer payWage, Integer jobAtr, Integer insCauseAtr, Integer paymentMode, Integer employmentStatus) {
        this.cId = cId;
        this.sId = sId;
        this.workingTime = workingTime == null ? Optional.empty() : Optional.of(new WorkingTime(workingTime));
        this.acquisitionAtr = acquisitionAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(acquisitionAtr, AcquisitionAtr.class));
        this.printAtr = printAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(printAtr, ContractPeriodPrintAtr.class));
        this.jobPath = jobPath == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(jobPath, JobPath.class));
        this.payWage = payWage == null ? Optional.empty() : Optional.of(new PayWage(payWage));
        this.jobAtr = jobAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(jobAtr, JobAtr.class));
        this.insCauseAtr = insCauseAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(insCauseAtr, InsuranceCauseAtr.class));
        this.paymentMode = paymentMode == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(paymentMode, WagePaymentMode.class));
        this.employmentStatus = employmentStatus == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(employmentStatus, EmploymentStatus.class));
    }

}
