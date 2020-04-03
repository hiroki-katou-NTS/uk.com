package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.RetirementReasonClsInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_RETI_REA_CLS_INFO")

public class QqsmtRetiReaClsInfo extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QqsmtRetiReaClsInfoPk qqsmtRetiReaClsInfoPk;

    /**
     * 退職解雇理由名称
     */
    @Basic(optional = false)
    @Column(name = "RETI_RES_CLS_NAME")
    public String retirementReasonClsName;

    @Override
    protected Object getKey() {
        return qqsmtRetiReaClsInfoPk;
    }

    public RetirementReasonClsInfo toDomain (){
        return new RetirementReasonClsInfo(
                this.qqsmtRetiReaClsInfoPk.cId,
                this.qqsmtRetiReaClsInfoPk.retirementReasonClsCode,
                this.retirementReasonClsName
        );
    }

    public static QqsmtRetiReaClsInfo toEntity (RetirementReasonClsInfo domain) {
        return new QqsmtRetiReaClsInfo(
                new QqsmtRetiReaClsInfoPk(domain.getCId(), domain.getRetirementReasonClsCode().toString()),
                domain.getRetirementReasonClsName().toString()
        );
    }
}
