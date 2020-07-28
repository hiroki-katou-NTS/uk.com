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
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyClassification;
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
	
	public ArrivedLateLeaveEarly toDomain(Application application) {
		List<LateCancelation> lateCancelation = new ArrayList<>();
		
		List<TimeReport> lateOrLeaveEarlies = new ArrayList<>();
		
		lateCancelation.add(new LateCancelation(1, EnumAdaptor.valueOf(this.lateCancelAtr1, LateOrEarlyClassification.class)));
		lateCancelation.add(new LateCancelation(2, EnumAdaptor.valueOf(this.lateCancelAtr2, LateOrEarlyClassification.class)));
		lateCancelation.add(new LateCancelation(1, EnumAdaptor.valueOf(this.earlyCancelAtr1, LateOrEarlyClassification.class)));
		lateCancelation.add(new LateCancelation(2, EnumAdaptor.valueOf(this.earlyCancelAtr2, LateOrEarlyClassification.class)));
		
		lateOrLeaveEarlies.add(new TimeReport(1, LateOrEarlyClassification.LATE, new TimeWithDayAttr(this.lateTime1)));
		lateOrLeaveEarlies.add(new TimeReport(2, LateOrEarlyClassification.EARLY, new TimeWithDayAttr(this.earlyTime1)));
		lateOrLeaveEarlies.add(new TimeReport(1, LateOrEarlyClassification.LATE, new TimeWithDayAttr(this.lateTime2)));
		lateOrLeaveEarlies.add(new TimeReport(2, LateOrEarlyClassification.EARLY, new TimeWithDayAttr(this.earlyTime2)));
		
		ArrivedLateLeaveEarly output = new ArrivedLateLeaveEarly(application);
		output.setLateCancelation(lateCancelation);
		output.setLateOrLeaveEarlies(lateOrLeaveEarlies);
		
		return output;
	}
}
