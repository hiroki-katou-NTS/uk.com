package nts.uk.ctx.at.request.infra.entity.application.common;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.ReflectPlanScheReason;
import nts.uk.ctx.at.request.infra.entity.application.common.appapprovalphase.KrqdtAppApprovalPhase;
import nts.uk.ctx.at.request.infra.entity.application.lateorleaveearly.KrqdtAppLateOrLeave;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertime;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp;
import nts.uk.ctx.at.request.infra.entity.application.workchange.KrqdtAppWorkChange;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRQDT_APPLICATION")
@AllArgsConstructor
@NoArgsConstructor
public class KafdtApplication extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KafdtApplicationPK kafdtApplicationPK;

	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;

	/**
	 * 事前事後区分
	 */
	@Column(name = "PRE_POST_ATR")
	public int prePostAtr;

	/**
	 * 入力日
	 */
	@Column(name = "INPUT_DATE")
	public GeneralDateTime inputDate;

	/**
	 * 入力者
	 */
	@Column(name = "ENTERED_PERSON_SID")
	public String enteredPersonSID;
	/**
	 * 差戻し理由
	 */
	@Column(name = "REASON_REVERSION")
	public String reversionReason;

	/**
	 * 申請日
	 */
	@Column(name = "APP_DATE")
	public GeneralDate applicationDate;

	///
	/**
	 * 申請理由
	 */
	@Column(name = "APP_REASON")
	public String applicationReason;
	/**
	 * 申請種類
	 */
	@Column(name = "APP_TYPE")
	public int applicationType;
	/**
	 * 申請者
	 */
	@Column(name = "APPLICANTS_SID")
	public String applicantSID;
	/**
	 * 予定反映不可理由
	 */
	@Column(name = "REFLECT_PLAN_SCHE_REASON")
	public int reflectPlanScheReason;
	/**
	 * 予定反映日時
	 */
	@Column(name = "REFLECT_PLAN_TIME")
	public GeneralDate reflectPlanTime;
	/**
	 * 予定反映状態
	 */
	@Column(name = "REFLECT_PLAN_STATE")
	public int reflectPlanState;
	/**
	 * 予定強制反映
	 */
	@Column(name = "REFLECT_PLAN_ENFORCE_ATR")
	public int reflectPlanEnforce;
	/**
	 * 実績反映不可理由
	 */
	@Column(name = "REFLECT_PER_SCHE_REASON")
	public int reflectPerScheReason;
	/**
	 * 実績反映日時
	 */
	@Column(name = "REFLECT_PER_TIME")
	public GeneralDate reflectPerTime;
	/**
	 * 実績反映状態
	 */
	@Column(name = "REFLECT_PER_STATE")
	public int reflectPerState;
	/**
	 * 実績強制反映
	 */
	@Column(name = "REFLECT_PER_ENFORCE_ATR")
	public int reflectPerEnforce;

	/**
	 * 申請終了日
	 */
	@Column(name = "APP_START_DATE")
	public GeneralDate startDate;

	/**
	 * 申請開始日
	 */
	@Column(name = "APP_END_DATE")
	public GeneralDate endDate;

	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
	public List<KrqdtAppApprovalPhase> appApprovalPhases;

	@OneToOne(targetEntity = KrqdtAppStamp.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "APP_ID", referencedColumnName = "APP_ID") })
	public KrqdtAppStamp krqdtAppStamp;

	@OneToOne(targetEntity = KrqdtAppLateOrLeave.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "kafdtApplication", orphanRemoval = true)
	public KrqdtAppLateOrLeave krqdtAppLateOrLeave;

	@OneToOne(targetEntity = KrqdtAppOvertime.class, cascade = CascadeType.ALL, mappedBy = "kafdtApplication", orphanRemoval = true)
	public KrqdtAppOvertime krqdtAppOvertime;

	@OneToOne(targetEntity = KrqdtAppWorkChange.class, cascade = CascadeType.ALL, mappedBy = "kafdtApplication", orphanRemoval = true)
	public KrqdtAppWorkChange krqdtAppWorkChange;
	
	@Override
	protected Object getKey() {
		return kafdtApplicationPK;
	}

	private static final String SEPERATE_REASON_STRING = ":";

	public static KafdtApplication toEntity(Application domain) {
		return new KafdtApplication(new KafdtApplicationPK(domain.getCompanyID(), domain.getApplicationID()),
				domain.getVersion(), domain.getPrePostAtr().value, domain.getInputDate(), domain.getEnteredPersonSID(),
				domain.getReversionReason().v(), domain.getApplicationDate(), domain.getApplicationReason().v(),
				domain.getApplicationType().value, domain.getApplicantSID(), domain.getReflectPlanScheReason().value,
				domain.getReflectPlanTime(), domain.getReflectPlanState().value, domain.getReflectPlanEnforce().value,
				domain.getReflectPerScheReason().value, domain.getReflectPerTime(), domain.getReflectPerState().value,
				domain.getReflectPerEnforce().value, domain.getStartDate(), domain.getEndDate(),
				domain.getListPhase().stream().map(c -> KrqdtAppApprovalPhase.toEntity(c)).collect(Collectors.toList()),
				null, null, null, null);
	}

	public Application toDomain() {
		Application app = new Application(this.kafdtApplicationPK.companyID, this.kafdtApplicationPK.applicationID,
				EnumAdaptor.valueOf(this.prePostAtr, PrePostAtr.class), this.inputDate, this.enteredPersonSID,
				new AppReason(this.reversionReason), this.applicationDate, new AppReason(this.applicationReason),
				EnumAdaptor.valueOf(this.applicationType, ApplicationType.class), this.applicantSID,
				EnumAdaptor.valueOf(this.reflectPlanScheReason, ReflectPlanScheReason.class), this.reflectPlanTime,
				EnumAdaptor.valueOf(this.reflectPlanState, ReflectPlanPerState.class),
				EnumAdaptor.valueOf(this.reflectPlanEnforce, ReflectPlanPerEnforce.class),
				EnumAdaptor.valueOf(this.reflectPerScheReason, ReflectPerScheReason.class), this.reflectPerTime,
				EnumAdaptor.valueOf(this.reflectPerState, ReflectPlanPerState.class),
				EnumAdaptor.valueOf(this.reflectPerEnforce, ReflectPlanPerEnforce.class), this.startDate, this.endDate,
				this.appApprovalPhases.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
		app.setVersion(this.version);
		return app;
	}

}
