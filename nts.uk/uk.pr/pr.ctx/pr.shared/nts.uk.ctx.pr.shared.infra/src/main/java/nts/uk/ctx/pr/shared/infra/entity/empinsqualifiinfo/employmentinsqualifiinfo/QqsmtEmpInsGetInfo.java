package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
}
