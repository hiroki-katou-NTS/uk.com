package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRQDT_OVERTIME_INPUT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtOvertimeInput extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    protected KrqdtOvertimeInputPK krqdtOvertimeInputPK;
	
	@Column(name = "START_TIME")
    private int startTime;
	
    @Column(name = "END_TIME")
    private int endTime;
    
    @Column(name = "APPLICATION_TIME")
    private int applicationTime;

	@Override
	protected Object getKey() {
		return krqdtOvertimeInputPK;
	}

}
