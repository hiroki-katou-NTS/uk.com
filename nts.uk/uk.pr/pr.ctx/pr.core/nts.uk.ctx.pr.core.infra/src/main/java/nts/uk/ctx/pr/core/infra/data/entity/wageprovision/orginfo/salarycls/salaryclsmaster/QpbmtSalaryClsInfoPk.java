package nts.uk.ctx.pr.core.infra.data.entity.wageprovision.orginfo.salarycls.salaryclsmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QpbmtSalaryClsInfoPk {
    @Column(name = "CID")
    private String cid;

    @Column(name = "SALARY_CLS_CD")
    private String salaryClsCode;
}
