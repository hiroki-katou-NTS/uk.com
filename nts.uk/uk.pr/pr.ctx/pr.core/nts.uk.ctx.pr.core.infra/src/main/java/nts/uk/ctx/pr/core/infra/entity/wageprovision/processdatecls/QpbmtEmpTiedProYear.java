package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmploymentCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 処理年月に紐づく雇用
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_TIED_PRO_YEAR")
public class QpbmtEmpTiedProYear extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @EmbeddedId
    public QpbmtEmpTiedProYearPk empTiedProYearPk;

    @Override
    protected Object getKey() {
        return empTiedProYearPk;
    }

    public static EmpTiedProYear toDomain(List<QpbmtEmpTiedProYear> entity) {
        if (entity.isEmpty()) return null;

        String companyId = entity.get(0).empTiedProYearPk.cid;
        int processCateNo = entity.get(0).empTiedProYearPk.processCateNo;

        return new EmpTiedProYear(companyId,
                processCateNo,
                entity.stream().map(x -> new EmploymentCode(x.empTiedProYearPk.employmentCode)).collect(Collectors.toList()));
    }

    public static List<QpbmtEmpTiedProYear> toEntity(EmpTiedProYear domain) {
        return domain.getEmploymentCodes().stream().map(x -> new QpbmtEmpTiedProYear(new QpbmtEmpTiedProYearPk(domain.getCid(), domain.getProcessCateNo(), x.v()))).collect(Collectors.toList());
    }
}
