package nts.uk.ctx.pr.shared.infra.entity.employaverwage;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.employaverwage.EmployAverWage;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 社員平均賃金
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_AVERAGE_WAGE")
public class QqsmtEmployAverWage extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QqsmtEmployAverWagePk employAverWagePk;

    /**
     * 平均賃金
     */
    @Basic(optional = false)
    @Column(name = "AVERAGE_WAGE")
    public BigDecimal averageWage;

    @Override
    protected Object getKey()
    {
        return employAverWagePk;
    }

    public EmployAverWage toDomain() {
        return new EmployAverWage(this.employAverWagePk.employeeId, this.employAverWagePk.targetDate, this.averageWage);
    }
    public static QqsmtEmployAverWage toEntity(EmployAverWage domain) {
        return new QqsmtEmployAverWage(new QqsmtEmployAverWagePk(domain.getEmployeeId(), domain.getTargetDate()),BigDecimal.valueOf(domain.getAverageWage().v()));
    }


}
