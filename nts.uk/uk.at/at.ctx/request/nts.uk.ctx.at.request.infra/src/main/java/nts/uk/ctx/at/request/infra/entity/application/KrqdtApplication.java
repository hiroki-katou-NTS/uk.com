package nts.uk.ctx.at.request.infra.entity.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Entity
@Table(name = "KRQDT_APPLICATION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KrqdtApplication extends ContractUkJpaEntity {
	
	@EmbeddedId
	private KrqdpApplication pk;
	
	@Version
	@Column(name="EXCLUS_VER")
	private int version;
	
	@Column(name="PRE_POST_ATR")
	private int prePostAtr;
	
	@Column(name="INPUT_DATE")
	private GeneralDateTime inputDate;
	
	@Column(name="ENTERED_PERSON_SID")
	private String enteredPerson;
	
	@Column(name="REASON_REVERSION")
	private String opReversionReason;
	
	@Column(name="APP_DATE")
	private GeneralDate appDate;
	
	@Column(name="FIXED_REASON")
	private Integer opAppStandardReasonCD;
	
	@Column(name="APP_REASON")
	private String opAppReason;
	
	@Column(name="APP_TYPE")
	private int appType;
	
	@Column(name="APPLICANTS_SID")
	private String employeeID;
	
	@Column(name="APP_START_DATE")
	private GeneralDate opAppStartDate;
	
	@Column(name="APP_END_DATE")
	private GeneralDate opAppEndDate;
	
	@Column(name="STAMP_OPTION_ATR")
	private Integer opStampRequestMode;
	
	@OneToMany(targetEntity = KrqdtAppReflectState.class, mappedBy = "krqdtApplication", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	@JoinTable(name = "KRQDT_APP_REFLECT_STATE")
	private List<KrqdtAppReflectState> krqdtAppReflectStateLst;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KrqdtApplication fromDomain(Application application) {
		String companyID = AppContexts.user().companyId();
		return new KrqdtApplication(
				new KrqdpApplication(companyID, application.getAppID()), 
				application.getVersion(), 
				application.getPrePostAtr().value, 
				application.getInputDate(), 
				application.getEnteredPersonID(), 
				application.getOpReversionReason().map(x -> x.v()).orElse(null), 
				application.getAppDate().getApplicationDate(), 
				application.getOpAppStandardReasonCD().map(x -> x.v()).orElse(null), 
				application.getOpAppReason().map(x -> x.v()).orElse(null), 
				application.getAppType().value, 
				application.getEmployeeID(),
				application.getOpAppStartDate().map(x -> x.getApplicationDate()).orElse(null), 
				application.getOpAppEndDate().map(x -> x.getApplicationDate()).orElse(null), 
				application.getOpStampRequestMode().map(x -> x.value).orElse(null), 
				application.getReflectionStatus().getListReflectionStatusOfDay().stream()
					.map(x -> KrqdtAppReflectState.fromDomain(x, companyID, application.getAppID())).collect(Collectors.toList()));
	}
	
	public Application toDomain() {
		Application application = new Application(
				version,
				pk.getAppID(), 
				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
				employeeID, 
				EnumAdaptor.valueOf(appType, ApplicationType.class), 
				new ApplicationDate(appDate), 
				enteredPerson, 
				inputDate,
				new ReflectionStatus(krqdtAppReflectStateLst.stream().map(x -> x.toDomain()).collect(Collectors.toList())));
		if(opReversionReason != null) {
			application.setOpReversionReason(Optional.of(new ReasonForReversion(opReversionReason)));
		}
		if(opAppStandardReasonCD != null) {
			application.setOpAppStandardReasonCD(Optional.of(new AppStandardReasonCode(opAppStandardReasonCD)));
		}
		if(opAppReason != null) {
			application.setOpAppReason(Optional.of(new AppReason(opAppReason)));
		}
		if(opAppStartDate != null) {
			application.setOpAppStartDate(Optional.of(new ApplicationDate(opAppStartDate)));
		}
		if(opAppEndDate != null) {
			application.setOpAppEndDate(Optional.of(new ApplicationDate(opAppEndDate)));
		}
		if(opStampRequestMode != null) {
			application.setOpStampRequestMode(Optional.of(EnumAdaptor.valueOf(opStampRequestMode, StampRequestMode.class)));
		}
		return application;
	}
	
}
