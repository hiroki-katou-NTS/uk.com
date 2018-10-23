package nts.uk.ctx.pr.core.infra.data.entity.wageprovision.orginfo.salarycls.salaryclsmaster;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "QPBMT_SALARY_CLS_INFO")
public class QpbmtSalaryClsInfo extends UkJpaEntity {

    @EmbeddedId
    private QpbmtSalaryClsInfoPk pk;

    @Column(name = "SALARY_CLS_NAME")
    private String salaryClsName;

    @Column(name = "MEMO")
    private String memo;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public SalaryClsInfo toDomain() {
        return SalaryClsInfo.createFromDataType(this.pk.getCid(), this.pk.getSalaryClsCode(), this.salaryClsName, this.memo);
    }

    public static QpbmtSalaryClsInfo of(SalaryClsInfo domain) {
        val entity = new QpbmtSalaryClsInfo();
        entity.setPk(new QpbmtSalaryClsInfoPk(domain.getCompanyId().v(), domain.getSalaryClsCode().v()));
        entity.setSalaryClsName(domain.getSalaryClsName().v());
        entity.setMemo(domain.getMemo().v());
        return entity;
    }
}
