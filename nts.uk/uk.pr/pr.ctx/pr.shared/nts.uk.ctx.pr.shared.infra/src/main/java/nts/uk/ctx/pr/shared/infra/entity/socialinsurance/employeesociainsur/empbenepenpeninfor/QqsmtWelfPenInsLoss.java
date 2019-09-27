package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIf;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 厚生年金保険喪失時情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_WELF_PEN_INS_LOSS")
public class QqsmtWelfPenInsLoss extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QqsmtWelfPenInsLossPk welfPenInsLossPk;

    /**
     * その他
     */
    @Basic(optional = false)
    @Column(name = "OTHER")
    public int other;

    /**
     * その他理由
     */
    @Basic(optional = true)
    @Column(name = "OTHER_REASON")
    public String otherReason;

    /**
     * 保険証回収添付枚数
     */
    @Basic(optional = true)
    @Column(name = "CA_INSURANCE")
    public Integer caInsurance;

    /**
     * 保険証回収返不能枚数
     */
    @Basic(optional = true)
    @Column(name = "NUM_RECOVED")
    public Integer numRecoved;

    /**
     * 資格喪失原因
     */
    @Basic(optional = true)
    @Column(name = "CAUSE")
    public Integer cause;

    @Override
    protected Object getKey() {
        return welfPenInsLossPk;
    }


   // Convert data to entity
    public static QqsmtWelfPenInsLoss toEntity(WelfPenInsLossIf welfPenInsLossIf) {
        return new QqsmtWelfPenInsLoss(new QqsmtWelfPenInsLossPk(welfPenInsLossIf.getEmpId()),
                welfPenInsLossIf.getOther(),
                welfPenInsLossIf.getOtherReason().map(i -> i.v()).orElse(null),
                welfPenInsLossIf.getCaInsuarace().map(i -> i.v()).orElse(null),
                welfPenInsLossIf.getNumRecoved().map(i -> i.v()).orElse(null),
                welfPenInsLossIf.getCause().isPresent() ? welfPenInsLossIf.getCause().get().value : null);
    }

    public  WelfPenInsLossIf toDomain(){
        return  new  WelfPenInsLossIf(this.welfPenInsLossPk.empId, this.other, this.otherReason,
                this.caInsurance, this.numRecoved, this.cause);

    }

}
