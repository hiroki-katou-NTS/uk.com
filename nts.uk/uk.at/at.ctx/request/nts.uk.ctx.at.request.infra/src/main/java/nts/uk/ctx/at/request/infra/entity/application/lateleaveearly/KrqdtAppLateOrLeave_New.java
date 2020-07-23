package nts.uk.ctx.at.request.infra.entity.application.lateleaveearly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author anhnm
 *
 */
@Entity
@Table(name = "KRQDT_APP_LATE_OR_LEAVE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppLateOrLeave_New extends ContractUkJpaEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrqdtAppLateOrLeavePK_New krqdtAppLateOrLeavePK;
	
	@Column(name = "LATE_CANCEL_ATR1")
	public int lateCancelAtr1;
	
	@Column(name = "EARLY_CANCEL_ATR1")
	public int earlyCancelAtr1;
	
	@Column(name = "LATE_CANCEL_ATR2")
	public int lateCancelAtr2;
	
	@Column(name = "EARLY_CANCEL_ATR2")
	public int earlyCancelAtr2;
	
	@Column(name = "LATE_TIME1")
	public int lateTime1;
	
	@Column(name = "EARLY_TIME1")
	public int earlyTime1;
	
	@Column(name = "LATE_TIME2")
	public int lateTime2;
	
	@Column(name = "EARLY_TIME2")
	public int earlyTime2;

	@Override
	protected KrqdtAppLateOrLeavePK_New getKey() {
		return this.krqdtAppLateOrLeavePK;
	}

	public static final JpaEntityMapper<KrqdtAppLateOrLeave_New> MAPPER = new JpaEntityMapper<>(KrqdtAppLateOrLeave_New.class);
}
