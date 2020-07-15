package nts.uk.ctx.at.request.infra.entity.application;

import java.util.List;
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
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Entity
@Table(name = "KRQDT_APPLICATION")
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtApplication extends UkJpaEntity {
	
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
				application.getEnteredPerson(), 
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
	
}
