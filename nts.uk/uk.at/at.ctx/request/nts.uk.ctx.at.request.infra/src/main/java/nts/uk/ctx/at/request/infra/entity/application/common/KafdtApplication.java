package nts.uk.ctx.at.request.infra.entity.application.common;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRQDT_APPLICATION")
@AllArgsConstructor
@NoArgsConstructor
public class KafdtApplication extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KafdtApplicationPK kafdtApplicationPK;

	/**
	 * 事前事後区分
	 */
	@Column(name="PRE_POST_ATR")
	public int prePostAtr; 

	/**
	 * 入力日
	 */
	@Column(name="INPUT_DATE")
	public GeneralDate inputDate; 
	
	/**
	 * 入力者
	 */
	@Column(name="ENTERED_PERSON_SID")
	public String enteredPersonSID; 
	/**
	 * 差戻し理由
	 */
	@Column(name="REASON_REVERSION")
	public String reversionReason; 
	
	/**
	 * 申請日
	 */
	@Column(name="APP_DATE")
	public GeneralDate applicationDate; 
	
	///
	/**
	 * 申請理由
	 */
	@Column(name="APP_REASON")
	public String applicationReason;
	/**
	 * 申請種類
	 */
	@Column(name="APP_TYPE")
	public int applicationType;
	/**
	 * 申請者
	 */
	@Column(name="APPLICANTS_SID")
	public String  applicantSID;
	/**
	 * 予定反映不可理由
	 */
	@Column(name="REFLECT_PLAN_SCHE_REASON")
	public int reflectPlanScheReason;
    /**
     * 予定反映日時
     */
	@Column(name="REFLECT_PLAN_TIME")
	public GeneralDate reflectPlanTime;
	/**
	 * 予定反映状態
	 */
	@Column(name="REFLECT_PLAN_STATE")
	public int reflectPlanState;
	/**
	 * 予定強制反映
	 */
	@Column(name="REFLECT_PLAN_ENFORCE_ATR")
	public int reflectPlanEnforce;
	/**
	 * 実績反映不可理由
	 */
	@Column(name="REFLECT_PER_SCHE_REASON")
	public int  reflectPerScheReason;
	/**
	 * 実績反映日時
	 */
	@Column(name="REFLECT_PER_TIME")
	public GeneralDate reflectPerTime;
	/**
	 * 予定反映状態
	 */
	@Column(name="REFLECT_PER_STATE")
	public int reflectPerState;
	/**
	 * 実績強制反映
	 */
	@Column(name="REFLECT_PER_ENFORCE_ATR")
	public int reflectPerEnforce;
	
	@Override
	protected Object getKey() {
		return kafdtApplicationPK;
	}
}
