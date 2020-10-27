package nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeDay;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * author hieult
 */
@Entity
@Table(name = "KRQDT_APP_LATE_OR_LEAVE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KrqdtAppLateOrLeave  extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrqdtAppLateOrLeavePK krqdtAppLateOrLeavePK;
	
	@Column(name = "ACTUAL_CANCEL_ATR")
	public int actualCancelAtr;
	
	@Column(name = "EARLY1")
	public int early1;
	
	@Column(name = "EARLY_TIME1")
	public Integer earlyTime1;
	
	@Column(name = "LATE1")
	public int late1;
	
	@Column(name = "LATE_TIME1")
	public Integer lateTime1;
	
	@Column(name = "EARLY2")
	public int early2;
	
	@Column(name = "EARLY_TIME2")
	public Integer earlyTime2;
	
	@Column(name = "LATE2")
	public int late2;
	
	@Column(name = "LATE_TIME2")
	public Integer lateTime2;
	
	@Override
	protected Object getKey() {
		return krqdtAppLateOrLeavePK;
	}
	
	public LateOrLeaveEarly toDomain() {
//		LateOrLeaveEarly lateOrLeaveEarly = LateOrLeaveEarly.builder()
//				.application(Application_New.builder().appID(this.krqdtAppLateOrLeavePK.appID).companyID(this.krqdtAppLateOrLeavePK.companyID).build())
//				.actualCancelAtr(this.actualCancelAtr)
//				.early1(EnumAdaptor.valueOf(this.early1, Select.class))
//				.earlyTime1(getEarlyTime1())
//				.late1(EnumAdaptor.valueOf(this.late1, Select.class))
//				.lateTime1(getLateTime1())
//				.early2(EnumAdaptor.valueOf(this.early2, Select.class))
//				.earlyTime2(getEarlyTime2())
//				.late2(EnumAdaptor.valueOf(this.late2, Select.class))
//				.lateTime2(getLateTime2())
//				.build();
//		return lateOrLeaveEarly;
		return null;
	}
	
	public static KrqdtAppLateOrLeave toEntity(LateOrLeaveEarly domain){
//		return new KrqdtAppLateOrLeave (
//					new KrqdtAppLateOrLeavePK(
//							domain.getApplication().getCompanyID(), 
//							domain.getApplication().getAppID()),
//					domain.getActualCancelAtr(),
//					domain.getEarly1().value,
//					domain.getEarlyTime1AsMinutes(),
//					domain.getLate1().value,
//					domain.getLateTime1AsMinutes(),
//					domain.getEarly2().value,
//					domain.getEarlyTime2AsMinutes(),
//					domain.getLate2().value,
//					domain.getLateTime2AsMinutes());
		return null;
	}
	public TimeDay getLateTime1() {
		return lateTime1 == null ? null : new TimeDay(this.lateTime1);
	}
	public TimeDay getLateTime2() {
		return lateTime2 == null ? null : new TimeDay(this.lateTime2);
	}
	public TimeDay getEarlyTime1() {
		return earlyTime1 == null ? null : new TimeDay(this.earlyTime1);
	}
	public TimeDay getEarlyTime2() {
		return earlyTime2 == null ? null : new TimeDay(this.earlyTime2);
	}

}
