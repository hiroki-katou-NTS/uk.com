package nts.uk.ctx.at.request.infra.entity.application.common;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="KRQDT_APPLICATION")
@Builder
public class KrqdtApplication_New extends UkJpaEntity {
	
	@EmbeddedId
	public KrqdpApplicationPK_New krqdpApplicationPK;

	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;
	
	@Column(name = "PRE_POST_ATR")
	public Integer prePostAtr;

	@Column(name = "INPUT_DATE")
	public GeneralDateTime inputDate;

	@Column(name = "ENTERED_PERSON_SID")
	public String enteredPersonID;

	@Column(name = "REASON_REVERSION")
	public String reversionReason;

	@Column(name = "APP_DATE")
	public GeneralDate appDate;

	@Column(name = "APP_REASON")
	public String appReason;
	
	@Column(name = "APP_TYPE")
	public Integer appType;
	
	@Column(name = "APPLICANTS_SID")
	public String employeeID;
	
	@Column(name = "APP_START_DATE")
	public GeneralDate startDate;

	@Column(name = "APP_END_DATE")
	public GeneralDate endDate;
	
	@Column(name = "REFLECT_PLAN_STATE")
	public Integer stateReflection;
	
	@Column(name = "REFLECT_PER_STATE")
	public Integer stateReflectionReal;
	
	@Column(name = "REFLECT_PLAN_ENFORCE_ATR")
	public Integer forcedReflection;
	
	@Column(name = "REFLECT_PER_ENFORCE_ATR")
	public Integer forcedReflectionReal;
	
	@Column(name = "REFLECT_PLAN_SCHE_REASON")
	public Integer notReason;
	
	@Column(name = "REFLECT_PER_SCHE_REASON")
	public Integer notReasonReal;
	
	@Column(name = "REFLECT_PLAN_TIME")
	public GeneralDateTime dateTimeReflection;
	
	@Column(name = "REFLECT_PER_TIME")
	public GeneralDateTime dateTimeReflectionReal;

	@Override
	protected Object getKey() {
		return krqdpApplicationPK;
	}
	
	public static KrqdtApplication_New fromDomain(Application_New application){
		return KrqdtApplication_New.builder()
				.krqdpApplicationPK(new KrqdpApplicationPK_New(application.getCompanyID(), application.getAppID()))
				.prePostAtr(application.getPrePostAtr().value)
				.inputDate(application.getInputDate())
				.enteredPersonID(application.getEnteredPersonID())
				.reversionReason(application.getReversionReason().v())
				.appDate(application.getAppDate())
				.appReason(application.getAppReason().v())
				.appType(application.getAppType().value)
				.employeeID(application.getEmployeeID())
				.startDate(application.getStartDate().map(x -> x).orElse(null))
				.endDate(application.getEndDate().map(x -> x).orElse(null))
				.stateReflection(application.getReflectionInformation().getStateReflection().value)
				.stateReflectionReal(application.getReflectionInformation().getStateReflectionReal().value)
				.forcedReflection(application.getReflectionInformation().getForcedReflection().value)
				.forcedReflectionReal(application.getReflectionInformation().getForcedReflectionReal().value)
				.notReason(application.getReflectionInformation().getNotReason().map(x -> x.value).orElse(null))
				.notReasonReal(application.getReflectionInformation().getNotReasonReal().map(x -> x.value).orElse(null))
				.dateTimeReflection(application.getReflectionInformation().getDateTimeReflection().map(x -> x).orElse(null))
				.dateTimeReflectionReal(application.getReflectionInformation().getDateTimeReflectionReal().map(x -> x).orElse(null))
				.build();
	}
	
	public Application_New toDomain(){
		return Application_New.builder()
				.version(this.version)
				.companyID(this.krqdpApplicationPK.companyID)
				.appID(this.krqdpApplicationPK.appID)
				.prePostAtr(EnumAdaptor.valueOf(this.prePostAtr, PrePostAtr.class))
				.inputDate(this.inputDate)
				.enteredPersonID(this.enteredPersonID)
				.reversionReason(new AppReason(this.reversionReason))
				.appDate(this.appDate)
				.appReason(new AppReason(this.appReason))
				.appType(EnumAdaptor.valueOf(this.appType, ApplicationType.class))
				.employeeID(this.employeeID)
				.startDate(Optional.ofNullable(this.startDate))
				.endDate(Optional.ofNullable(this.endDate))
				.reflectionInformation(this.toReflectionInformationDomain())
				.build();
	}
	
	private ReflectionInformation_New toReflectionInformationDomain(){
		return ReflectionInformation_New.builder()
					.stateReflection(EnumAdaptor.valueOf(this.stateReflection, ReflectedState_New.class))
					.stateReflectionReal(EnumAdaptor.valueOf(this.stateReflectionReal, ReflectedState_New.class))
					.forcedReflection(EnumAdaptor.valueOf(this.forcedReflection, NotUseAtr.class))
					.forcedReflectionReal(EnumAdaptor.valueOf(this.forcedReflectionReal, NotUseAtr.class))
					.notReason(Optional.ofNullable(this.notReason).map(x -> EnumAdaptor.valueOf(x, ReasonNotReflect.class)))
					.notReasonReal(Optional.ofNullable(this.notReasonReal).map(x -> EnumAdaptor.valueOf(x, ReasonNotReflectDaily.class)))
					.dateTimeReflection(Optional.ofNullable(this.dateTimeReflection))
					.dateTimeReflectionReal(Optional.ofNullable(this.dateTimeReflectionReal))
					.build();
	}
}
