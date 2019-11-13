package nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfo;

@AllArgsConstructor
@Value
public class EmpInsGetInfoDto {
    /**
     * 社員ID
     */
    public String sid;
    /**
     * 労働時間
     */
    public Integer workingTime;

    /**
     *取得区分
     */
    public  Integer acquiAtr;

    /**
     *契約期間の印刷区分
     */
    public  Integer contrPeriPrintAtr;

    /**
     *就職経路
     */
    public  Integer jobPath;

    /**
     *支払賃金
     */
    public  Integer payWage;

    /**
     *職種
     */
    public  Integer jobAtr;

    /**
     *被保険者原因
     */
    public  Integer insCauseAtr;

    /**
     *賃金支払態様
     */
    public  Integer wagePaymentMode;

    /**
     *雇用形態
     */
    public  Integer employmentStatus;

    public static EmpInsGetInfoDto fromDomain(EmpInsGetInfo domain){
        return new EmpInsGetInfoDto(
                domain.getSalaryId(),
                domain.getWorkingTime().map(PrimitiveValueBase::v).orElse(null),
                domain.getAcquisitionAtr().isPresent() ? domain.getAcquisitionAtr().get().value : null,
                domain.getPrintAtr().isPresent() ? domain.getPrintAtr().get().value : null,
                domain.getJobPath().isPresent() ? domain.getJobPath().get().value : null,
                domain.getPayWage().map(PrimitiveValueBase::v).orElse(null),
                domain.getJobAtr().isPresent() ? domain.getJobAtr().get().value : null,
                domain.getInsCauseAtr().isPresent() ? domain.getInsCauseAtr().get().value : null,
                domain.getPaymentMode().isPresent() ? domain.getPaymentMode().get().value : null,
                domain.getEmploymentStatus().isPresent() ? domain.getEmploymentStatus().get().value : null
                );
    }
}
