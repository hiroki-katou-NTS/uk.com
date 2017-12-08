/**
 * 3:27:41 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem.KrcstErAlConGroup;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime.KrcstErAlWhActual;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime.KrcstErAlWhPlan;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype.KrcstErAlWtActual;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktype.KrcstErAlWtPlan;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 * 勤務実績のエラーアラームチェック
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KRCMT_ERAL_CONDITION")
public class KrcmtErAlCondition extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
    @NotNull
	@Column(name ="ERAL_CHECK_ID")
	public String eralCheckId;
	
	@Column(name ="MESSAGE_DISPLAY")
	public String messageDisplay;
	
	@Column(name ="FiLTER_BY_BUSINESS_TYPE")
	public BigDecimal filterByBusinessType;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "KRCST_ER_AL_BUSINESS_TYPE", joinColumns = {
//		@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", nullable= true)
//	})
	public List<KrcstErAlBusinessType> lstBusinessType;
	
	@Column(name ="FiLTER_BY_JOB_TITLE")
	public BigDecimal filterByJobTitle;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "KRCST_ER_AL_JOB_TITLE", joinColumns = {
//		@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", nullable= true)
//	})
	public List<KrcstErAlJobTitle> lstJobTitle;
	
	@Column(name ="FiLTER_BY_EMPLOYMENT")
	public BigDecimal filterByEmployment;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "KRCST_ER_AL_EMPLOYMENT", joinColumns = {
//		@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", nullable= true)
//	})
	public List<KrcstErAlEmployment> lstEmployment;
	
	@Column(name ="FiLTER_BY_CLASSIFICATION")
	public BigDecimal filterByClassification;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "KRCST_ER_AL_CLASS", joinColumns = {
//		@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", nullable= true)
//	})
	public List<KrcstErAlClass> lstClassification;
	
	@Column(name ="WORKTYPE_USE_ATR")
	public BigDecimal workTypeUseAtr;
	
	@Column(name ="WT_PLAN_ACTUAL_OPERATOR")
	public BigDecimal wtPlanActualOperator;
	
	@Column(name ="WT_PLAN_FILTER_ATR")
	public BigDecimal wtPlanFilterAtr;

	@Column(name ="WT_ACTUAL_FILTER_ATR")
	public BigDecimal wtActualFilterAtr;
	
	@Column(name ="WT_COMPARE_ATR")
	public BigDecimal wtCompareAtr;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "KRCST_ER_AL_WT_ACTUAL", joinColumns = {
//		@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", nullable= true)
//	})
	public List<KrcstErAlWtActual> lstWtActual;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "KRCST_ER_AL_WT_PLAN", joinColumns = {
//		@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", nullable= true)
//	})
	public List<KrcstErAlWtPlan> lstWtPlan;
	
	@Column(name ="WORKING_HOURS_USE_ATR")
	public BigDecimal workingHoursUseAtr;
	
	@Column(name ="WH_PLAN_ACTUAL_OPERATOR")
	public BigDecimal whPlanActualOperator;
	
	@Column(name ="WH_PLAN_FILTER_ATR")
	public BigDecimal whPlanFilterAtr;
	
	@Column(name ="WH_ACTUAL_FILTER_ATR")
	public BigDecimal whActualFilterAtr;
	
	@Column(name ="WH_COMPARE_ATR")
	public BigDecimal whCompareAtr;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "KRCST_ER_AL_WH_ACTUAL", joinColumns = {
//		@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", nullable= true)
//	})
	public List<KrcstErAlWhActual> lstWhActual;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "KRCST_ER_AL_WH_PLAN", joinColumns = {
//		@JoinColumn(name="ERAL_CHECK_ID", referencedColumnName="ERAL_CHECK_ID", nullable= true)
//	})
	public List<KrcstErAlWhPlan> lstWhPlan;
	
	@Column(name ="OPERATOR_BETWEEN_GROUPS")
	public BigDecimal operatorBetweenGroups;
	
	@Column(name ="ATD_ITEM_CONDITION_GROUP1")
	public String atdItemConditionGroup1;
	
//	@OneToOne(cascade = CascadeType.ALL, optional = true)
//	@JoinTable(name = "KRCST_ER_AL_CON_GROUP", joinColumns = {
//		@JoinColumn(name="ATD_ITEM_CONDITION_GROUP1", referencedColumnName="CONDITION_GROUP_ID")
//	})
	public KrcstErAlConGroup krcstErAlConGroup1;
	
	@Column(name ="ATD_ITEM_CONDITION_GROUP2")
	public String atdItemConditionGroup2;
	
//	@OneToOne(cascade = CascadeType.ALL, optional = true)
//	@JoinTable(name = "KRCST_ER_AL_CON_GROUP", joinColumns = {
//		@JoinColumn(name="ATD_ITEM_CONDITION_GROUP2", referencedColumnName="CONDITION_GROUP_ID")
//	})
	public KrcstErAlConGroup krcstErAlConGroup2;
	
	@Override
	protected Object getKey() {
		return this.eralCheckId;
	}
	
}
