package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author HungTT
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_AL_CHK_TARGET_COND")
public class KfnmtAlarmCheckTargetCondition extends UkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	public String id;
	
	@Basic
	@Column(name = "FILTER_BY_EMP")
	public boolean filterByEmployment;
	
	@Basic
	@Column(name = "FILTER_BY_CLS")
	public boolean filterByClassification;
	
	@Basic
	@Column(name = "FILTER_BY_JOB")
	public boolean filterByJobTitle;
	
	@Basic
	@Column(name = "FILTER_BY_BUSINESSTYPE")
	public boolean filterByBusinessType;
	
	@OneToOne
	@JoinColumns({
        @JoinColumn(name = "ID", referencedColumnName = "EXTRACT_TARGET_COND_ID", insertable = false, updatable = false)
    })
	public KfnmtAlarmCheckConditionCategory alarmCheckConditionByCategory;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="targetCondition", orphanRemoval = true)
	public List<KfnmtAlarmCheckTargetEmployment> listEmployment;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="targetCondition", orphanRemoval = true)
	public List<KfnmtAlarmCheckTargetClassification> listClassification;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="targetCondition", orphanRemoval = true)
	public List<KfnmtAlarmCheckTargetJobTitle> listJobTitle;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="targetCondition", orphanRemoval = true)
	public List<KfnmtAlarmCheckTargetBusinessType> listBusinessType;
	
	@Override
	protected Object getKey() {
		return this.id;
	}

	public KfnmtAlarmCheckTargetCondition(String id, boolean filterByEmployment, boolean filterByClassification,
			boolean filterByJobTitle, boolean filterByBusinessType,
			List<KfnmtAlarmCheckTargetEmployment> listEmployment,
			List<KfnmtAlarmCheckTargetClassification> listClassification,
			List<KfnmtAlarmCheckTargetJobTitle> listJobTitle,
			List<KfnmtAlarmCheckTargetBusinessType> listBusinessType) {
		super();
		this.id = id;
		this.filterByEmployment = filterByEmployment;
		this.filterByClassification = filterByClassification;
		this.filterByJobTitle = filterByJobTitle;
		this.filterByBusinessType = filterByBusinessType;
		this.listEmployment = listEmployment;
		this.listClassification = listClassification;
		this.listJobTitle = listJobTitle;
		this.listBusinessType = listBusinessType;
	}
	
	

}
