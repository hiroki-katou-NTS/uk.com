package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 雇用保険取得時情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_INS_GET_INFO")
public class QqsmtEmpInsGetInfo extends UkJpaEntity implements Serializable {

    /**
     * ID
     */
    @EmbeddedId
    public QqsmtEmpInsGetInfoPk empInsGetInfoPk;

    /**
     * 労働時間
     */
    @Basic(optional = true)
    @Column(name = "WORKING_TIME")
    public Integer workingTime;

    /**
     *取得区分
     */
    @Basic(optional = true)
    @Column(name = "ACQUI_ATR")
    public  Integer acquiAtr;

    /**
     *契約期間の印刷区分
     */
    @Basic(optional = true)
    @Column(name = "CONTR_PERI_PRINT_ATR")
    public  Integer contrPeriPrintAtr;

    /**
     *就職経路
     */
    @Basic(optional = true)
    @Column(name = "JOB_PATH")
    public  Integer jobPath;

    /**
     *支払賃金
     */
    @Basic(optional = true)
    @Column(name = "PAY_WAGE")
    public  Integer payWage;

    /**
     *職種
     */
    @Basic(optional = true)
    @Column(name = "JOB_ATR")
    public  Integer jobAtr;

    /**
     *被保険者原因
     */
    @Basic(optional = true)
    @Column(name = "INS_CAUSE_ATR")
    public  Integer insCauseAtr;

    /**
     *賃金支払態様
     */
    @Basic(optional = true)
    @Column(name = "WAGE_PAYMENT_MODE")
    public  Integer wagePaymentMode;

    /**
     *雇用形態
     */
    @Basic(optional = true)
    @Column(name = "EMPLOYMENT_STATUS")
    public  Integer employmentStatus;

    @Override
    protected Object getKey() {
        return null;
    }

    public EmpInsGetInfo toDomain() {
        return new EmpInsGetInfo(
                this.empInsGetInfoPk.cId,
                this.empInsGetInfoPk.sId,
                this.workingTime,
                this.acquiAtr,
                this.contrPeriPrintAtr,
                this.jobPath,
                this.payWage,
                this.jobAtr,
                this.insCauseAtr,
                this.wagePaymentMode,
                this.employmentStatus);
    }
    public static QqsmtEmpInsGetInfo toEntity(EmpInsGetInfo domain) {
        return new QqsmtEmpInsGetInfo(new QqsmtEmpInsGetInfoPk(AppContexts.user().companyId(), domain.getSId()),
                domain.getWorkingTime().map(PrimitiveValueBase::v).orElse(null),
                domain.getAcquisitionAtr().isPresent() ? domain.getAcquisitionAtr().get().value : null,
                domain.getPrintAtr().isPresent() ? domain.getPrintAtr().get().value : null,
                domain.getJobPath().isPresent() ? domain.getJobPath().get().value : null,
                domain.getPayWage().map(PrimitiveValueBase::v).orElse(null),
                domain.getJobAtr().isPresent() ? domain.getJobAtr().get().value : null,
                domain.getInsCauseAtr().isPresent() ? domain.getInsCauseAtr().get().value : null,
                domain.getPaymentMode().isPresent() ? domain.getPaymentMode().get().value : null,
                domain.getEmploymentStatus().isPresent() ? domain.getEmploymentStatus().get().value : null);
    }
}
