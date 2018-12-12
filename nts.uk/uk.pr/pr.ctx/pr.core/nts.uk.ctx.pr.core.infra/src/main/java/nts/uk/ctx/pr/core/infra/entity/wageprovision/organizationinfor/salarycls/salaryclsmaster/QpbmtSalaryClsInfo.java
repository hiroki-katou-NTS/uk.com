package nts.uk.ctx.pr.core.infra.entity.wageprovision.organizationinfor.salarycls.salaryclsmaster;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与分類情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SALARY_CLS_INFO")
public class QpbmtSalaryClsInfo extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSalaryClsInfoPk salaryClsInfoPk;
    
    /**
    * 給与分類名称
    */
    @Basic(optional = false)
    @Column(name = "SALARY_CLS_NAME")
    public String salaryClsName;
    
    /**
    * メモ
    */
    @Column(name = "MEMO")
    public String memo;
    
    @Override
    protected Object getKey()
    {
        return salaryClsInfoPk;
    }

    public SalaryClassificationInformation toDomain() {
        return new SalaryClassificationInformation(this.salaryClsInfoPk.cid, this.salaryClsInfoPk.salaryClsCd, this.salaryClsName, this.memo);
    }
    public static QpbmtSalaryClsInfo toEntity(SalaryClassificationInformation domain) {
        return new QpbmtSalaryClsInfo(new QpbmtSalaryClsInfoPk(domain.getCompanyId(), domain.getSalaryClassificationCode().v()), domain.getSalaryClassificationName().v(), domain.getMemo().map(PrimitiveValueBase::v).orElse(null));
    }

}
