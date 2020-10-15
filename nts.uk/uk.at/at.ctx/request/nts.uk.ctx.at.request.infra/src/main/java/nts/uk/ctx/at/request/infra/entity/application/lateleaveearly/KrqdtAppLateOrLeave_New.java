package nts.uk.ctx.at.request.infra.entity.application.lateleaveearly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeReport;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

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
	public Integer lateCancelAtr1;
	
	@Column(name = "EARLY_CANCEL_ATR1")
	public Integer earlyCancelAtr1;
	
	@Column(name = "LATE_CANCEL_ATR2")
	public Integer lateCancelAtr2;
	
	@Column(name = "EARLY_CANCEL_ATR2")
	public Integer earlyCancelAtr2;
	
	@Column(name = "LATE_TIME1")
	public Integer lateTime1;
	
	@Column(name = "EARLY_TIME1")
	public Integer earlyTime1;
	
	@Column(name = "LATE_TIME2")
	public Integer lateTime2;
	
	@Column(name = "EARLY_TIME2")
	public Integer earlyTime2;

	@Override
	protected KrqdtAppLateOrLeavePK_New getKey() {
		return this.krqdtAppLateOrLeavePK;
	}

	public static final JpaEntityMapper<KrqdtAppLateOrLeave_New> MAPPER = new JpaEntityMapper<>(KrqdtAppLateOrLeave_New.class);
	
	public ArrivedLateLeaveEarly toDomain(Application application) {
		List<LateCancelation> lateCancelation = new ArrayList<>();
		
		List<TimeReport> lateOrLeaveEarlies = new ArrayList<>();
		
		if(this.lateCancelAtr1 != null) {
			lateCancelation.add(new LateCancelation(1, LateOrEarlyAtr.LATE));			
		}
		if(this.lateCancelAtr2 != null) {
			lateCancelation.add(new LateCancelation(2, LateOrEarlyAtr.LATE));
		}
		if(this.earlyCancelAtr1 != null) {
			lateCancelation.add(new LateCancelation(1, LateOrEarlyAtr.EARLY));
		}
		if(this.earlyCancelAtr2 != null) {
			lateCancelation.add(new LateCancelation(2, LateOrEarlyAtr.EARLY));
		}
		
		if(lateTime1 != null) {
			lateOrLeaveEarlies.add(new TimeReport(1, LateOrEarlyAtr.LATE, new TimeWithDayAttr(this.lateTime1)));
		}
		if(earlyTime1 != null) {
			lateOrLeaveEarlies.add(new TimeReport(1, LateOrEarlyAtr.EARLY, new TimeWithDayAttr(this.earlyTime1)));
		}
		if(lateTime2 != null) {
			lateOrLeaveEarlies.add(new TimeReport(2, LateOrEarlyAtr.LATE, new TimeWithDayAttr(this.lateTime2)));
		}
		if(earlyTime2 != null) {
			lateOrLeaveEarlies.add(new TimeReport(2, LateOrEarlyAtr.EARLY, new TimeWithDayAttr(this.earlyTime2)));
		}
		
		ArrivedLateLeaveEarly output = new ArrivedLateLeaveEarly(application);
		output.setLateCancelation(lateCancelation);
		output.setLateOrLeaveEarlies(lateOrLeaveEarlies);
		
		return output;
	}
}
