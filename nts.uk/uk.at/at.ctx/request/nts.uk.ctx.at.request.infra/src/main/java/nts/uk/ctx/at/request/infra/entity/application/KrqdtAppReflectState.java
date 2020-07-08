package nts.uk.ctx.at.request.infra.entity.application;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Entity
@Table(name = "KRQDT_APP_REFLECT_STATE")
@AllArgsConstructor
public class KrqdtAppReflectState extends UkJpaEntity {
	
	@EmbeddedId
	private KrqdpAppReflectState pk;
	
	@Column(name="REFLECT_PLAN_STATE")
	public int scheReflectStatus;
	
	@Column(name="REFLECT_PER_STATE")
	public int actualReflectStatus;

	@Column(name="REFLECT_PLAN_SCHE_REASON")
	public Integer opReasonScheCantReflect;
	
	@Column(name="REFLECT_PLAN_TIME")
	public GeneralDateTime opScheReflectDateTime;
	
	@Column(name="REFLECT_PER_SCHE_REASON")
	public Integer opReasonActualCantReflect;
	
	@Column(name="REFLECT_PER_TIME")
	public GeneralDateTime opActualReflectDateTime;
	
	@Column(name="CANCEL_PLAN_SCHE_REASON")
	public Integer opReasonScheCantReflectCancel;
	
	@Column(name="CANCEL_PLAN_TIME")
	public GeneralDateTime opScheReflectDateTimeCancel;
	
	@Column(name="CANCEL_PER_SCHE_REASON")
	public Integer opReasonActualCantReflectCancel;
	
	@Column(name="CANCEL_PER_TIME")
	public GeneralDateTime opActualReflectDateTimeCancel;
	
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

}
