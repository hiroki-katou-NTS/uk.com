package nts.uk.ctx.at.request.infra.entity.overtimeinstruct;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *
 * @author loivt
 */
@Entity
@Table(name = "KRQDT_INSTRUCT_OVERTIME")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtInstructOvertime extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqdtInstructOvertimePK krqdtInstructOvertimePK;
    
    @Column(name = "WORK_CONTENT")
    private String workContent;
    
    @Column(name = "INPUT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inputDate;
    
    @Column(name = "INSTRUCTOR")
    private String instructor;
    
    @Column(name = "OVERTIME_INSTRUCT_REASON")
    private String overtimeInstructReason;
    
    @Column(name = "OVERTIME_HOUR")
    private int overtimeHour;
    
    @Column(name = "START_CLOCK")
    private int startClock;
    
    @Column(name = "END_CLOCK")
    private int endClock;

	@Override
	protected Object getKey() {
		return krqdtInstructOvertimePK;
	}
    
}
