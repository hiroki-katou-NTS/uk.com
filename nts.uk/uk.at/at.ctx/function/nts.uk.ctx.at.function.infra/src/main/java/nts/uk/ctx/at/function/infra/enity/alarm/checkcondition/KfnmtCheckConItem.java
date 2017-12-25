package nts.uk.ctx.at.function.infra.enity.alarm.checkcondition;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_CHECK_CON_ITEM")
public class KfnmtCheckConItem extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	@Id
	public String companyID;
	
	@Column(name = "ALARM_PATTERN_CD")
	@Id
	public String alarmPatternCD;
	
	@Column(name = "ALARM_CATEGORY")
	@Id
	public int alarmCategory;
	
	@Column(name = "ALARM_CHECK_CONDITION_CODE")
	@Id
	public String checkConditionCD;
	
	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_CATEGORY", referencedColumnName="ALARM_CATEGORY", insertable = false, updatable = false) })
	public KfnmtCheckCondition checkCondition;

	@Override
	protected Object getKey() {
		return null;
	}
}
