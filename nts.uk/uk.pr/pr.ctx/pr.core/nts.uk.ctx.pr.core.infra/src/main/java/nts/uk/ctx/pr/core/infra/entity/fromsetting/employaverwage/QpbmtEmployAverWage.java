package nts.uk.ctx.pr.core.infra.entity.fromsetting.employaverwage;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.employaverwage.EmployAverWage;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 社員平均賃金
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMPLOY_AVER_WAGE")
public class QpbmtEmployAverWage extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtEmployAverWagePk employAverWagePk;
    
    /**
    * AverageWage
    */
    @Basic(optional = false)
    @Column(name = "AVERAGE_WAGE")
    public BigDecimal averageWage;
    
    /**
    * 対象年月
    */
    @Basic(optional = false)
    @Column(name = "TARGET_DATE")
    public int targetDate;
    
    @Override
    protected Object getKey()
    {
        return employAverWagePk;
    }

    public EmployAverWage toDomain() {
        return new EmployAverWage(this.employAverWagePk.employeeId, this.averageWage, this.targetDate);
    }
    public static QpbmtEmployAverWage toEntity(EmployAverWage domain) {
        return new QpbmtEmployAverWage(new QpbmtEmployAverWagePk(domain.getEmployeeId()),BigDecimal.valueOf(domain.getAverageWage().v()), domain.getTargetDate());
    }

}
