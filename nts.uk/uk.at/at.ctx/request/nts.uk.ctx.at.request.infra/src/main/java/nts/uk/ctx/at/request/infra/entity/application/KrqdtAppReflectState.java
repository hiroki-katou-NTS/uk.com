package nts.uk.ctx.at.request.infra.entity.application;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.DailyAttendanceUpdateStatus;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Entity
@Table(name = "KRQDT_APP_REFLECT_STATE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KrqdtAppReflectState extends ContractUkJpaEntity {
	
	@EmbeddedId
	private KrqdpAppReflectState pk;
	
	@Column(name="REFLECT_PLAN_STATE")
	private int scheReflectStatus;
	
	@Column(name="REFLECT_PER_STATE")
	private int actualReflectStatus;

	@Column(name="REFLECT_PLAN_SCHE_REASON")
	private Integer opReasonScheCantReflect;
	
	@Column(name="REFLECT_PLAN_TIME")
	private GeneralDateTime opScheReflectDateTime;
	
	@Column(name="REFLECT_PER_SCHE_REASON")
	private Integer opReasonActualCantReflect;
	
	@Column(name="REFLECT_PER_TIME")
	private GeneralDateTime opActualReflectDateTime;
	
	@Column(name="CANCEL_PLAN_SCHE_REASON")
	private Integer opReasonScheCantReflectCancel;
	
	@Column(name="CANCEL_PLAN_TIME")
	private GeneralDateTime opScheReflectDateTimeCancel;
	
	@Column(name="CANCEL_PER_SCHE_REASON")
	private Integer opReasonActualCantReflectCancel;
	
	@Column(name="CANCEL_PER_TIME")
	private GeneralDateTime opActualReflectDateTimeCancel;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID"),
    	@PrimaryKeyJoinColumn(name="APP_ID", referencedColumnName="APP_ID")
    })
	public KrqdtApplication krqdtApplication;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KrqdtAppReflectState fromDomain(ReflectionStatusOfDay reflectionStatusOfDay, String companyID, String appID) {
		return new KrqdtAppReflectState(
				new KrqdpAppReflectState(
						companyID, 
						appID, 
						reflectionStatusOfDay.getTargetDate()),
				reflectionStatusOfDay.getScheReflectStatus().value, 
				reflectionStatusOfDay.getActualReflectStatus().value, 
				reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> x.getOpReasonScheCantReflect().map(y -> y.value).orElse(null)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> x.getOpScheReflectDateTime().orElse(null)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> x.getOpReasonActualCantReflect().map(y -> y.value).orElse(null)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppReflect().map(x -> x.getOpActualReflectDateTime().orElse(null)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> x.getOpReasonScheCantReflect().map(y -> y.value).orElse(null)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> x.getOpScheReflectDateTime().orElse(null)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> x.getOpReasonActualCantReflect().map(y -> y.value).orElse(null)).orElse(null), 
				reflectionStatusOfDay.getOpUpdateStatusAppCancel().map(x -> x.getOpActualReflectDateTime().orElse(null)).orElse(null),
				null);
	}
	
	public ReflectionStatusOfDay toDomain() {
		Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppReflect = Optional.empty();
		if(opReasonScheCantReflect != null || opScheReflectDateTime != null ||
				opReasonActualCantReflect != null || opActualReflectDateTime != null) {
			opUpdateStatusAppReflect = Optional.of(new DailyAttendanceUpdateStatus(
					opActualReflectDateTime == null ? Optional.empty() : Optional.of(opActualReflectDateTime), 
					opScheReflectDateTime == null ? Optional.empty() : Optional.of(opScheReflectDateTime), 
					opReasonActualCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonActualCantReflect, ReasonNotReflectDaily.class)), 
					opReasonScheCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonScheCantReflect, ReasonNotReflect.class))));
		}
		Optional<DailyAttendanceUpdateStatus> opUpdateStatusAppCancel = Optional.empty();
		if(opReasonScheCantReflectCancel != null || opScheReflectDateTimeCancel != null ||
				opReasonActualCantReflectCancel != null || opActualReflectDateTimeCancel != null) {
			opUpdateStatusAppCancel = Optional.of(new DailyAttendanceUpdateStatus(
					opActualReflectDateTime == null ? Optional.empty() : Optional.of(opActualReflectDateTime), 
					opScheReflectDateTime == null ? Optional.empty() : Optional.of(opScheReflectDateTime), 
					opReasonActualCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonActualCantReflect, ReasonNotReflectDaily.class)), 
					opReasonScheCantReflect == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(opReasonScheCantReflect, ReasonNotReflect.class))));
		}
		ReflectionStatusOfDay reflectionStatusOfDay = new ReflectionStatusOfDay(
				EnumAdaptor.valueOf(actualReflectStatus, ReflectedState.class), 
				EnumAdaptor.valueOf(scheReflectStatus, ReflectedState.class), 
				pk.getTargetDate(), 
				opUpdateStatusAppReflect, 
				opUpdateStatusAppCancel);
		return reflectionStatusOfDay;
	}

}
