package nts.uk.ctx.at.request.infra.entity.application.common.lateorleaveearly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * author hieult
 */
@Entity
@Table(name = "KRQDT_APP_LATE_OR_LEAVE")
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppLateOrLeave  extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrqdtAppLateOrLeavePK krqdtAppLateOrLeavePK;
	
	@Column(name = "ACTUAL_CANCEL_ATR")
	public String actualCancelAtr;
	
	@Column(name = "EARLY1")
	public String early1;
	
	@Column(name = "EARLY_TIME1")
	public String earlyTime1;
	
	@Column(name = "LATE1")
	public String late1;
	
	@Column(name = "LATE_TIME1")
	public String lateTime1;
	
	@Column(name = "EARLY2")
	public String early2;
	
	@Column(name = "EARLY_TIME2")
	public String earlyTime2;
	
	@Column(name = "LATE2")
	public String late2;
	
	@Column(name = "LATE_TIME2")
	public String lateTime2;

	
	@Override
	protected Object getKey() {
		
		return krqdtAppLateOrLeavePK;
	}

}
