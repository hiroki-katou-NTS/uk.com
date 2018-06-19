package nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.Select;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeDay;
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
	
	@Version
	@Column(name="EXCLUS_VER")
	public Long version;
	
	@Column(name = "ACTUAL_CANCEL_ATR")
	public int actualCancelAtr;
	
	@Column(name = "EARLY1")
	public int early1;
	
	@Column(name = "EARLY_TIME1")
	public int earlyTime1;
	
	@Column(name = "LATE1")
	public int late1;
	
	@Column(name = "LATE_TIME1")
	public int lateTime1;
	
	@Column(name = "EARLY2")
	public int early2;
	
	@Column(name = "EARLY_TIME2")
	public int earlyTime2;
	
	@Column(name = "LATE2")
	public int late2;
	
	@Column(name = "LATE_TIME2")
	public int lateTime2;
	
	@Override
	protected Object getKey() {
		return krqdtAppLateOrLeavePK;
	}
	
	public LateOrLeaveEarly toDomain() {
		LateOrLeaveEarly lateOrLeaveEarly = LateOrLeaveEarly.builder()
				.application(Application_New.builder().appID(this.krqdtAppLateOrLeavePK.appID).companyID(this.krqdtAppLateOrLeavePK.companyID).build())
				.actualCancelAtr(this.actualCancelAtr)
				.early1(EnumAdaptor.valueOf(this.early1, Select.class))
				.earlyTime1(new TimeDay(this.earlyTime1))
				.late1(EnumAdaptor.valueOf(this.late1, Select.class))
				.lateTime1(new TimeDay(this.lateTime1))
				.early2(EnumAdaptor.valueOf(this.early2, Select.class))
				.earlyTime2(new TimeDay(this.earlyTime2))
				.late2(EnumAdaptor.valueOf(this.late2, Select.class))
				.lateTime2(new TimeDay(this.lateTime2))
				.build();
		lateOrLeaveEarly.setVersion(this.version);
		return lateOrLeaveEarly;
	}
	
	public static KrqdtAppLateOrLeave toEntity(LateOrLeaveEarly domain){
		return new KrqdtAppLateOrLeave (
					new KrqdtAppLateOrLeavePK(
							domain.getApplication().getCompanyID(), 
							domain.getApplication().getAppID()),
					domain.getVersion(),
					domain.getActualCancelAtr(),
					domain.getEarly1().value,
					domain.getEarlyTime1().v(),
					domain.getLate1().value,
					domain.getLateTime1().v(),
					domain.getEarly2().value,
					domain.getEarlyTime2().v(),
					domain.getLate2().value,
					domain.getLateTime2().v());
	}

}
