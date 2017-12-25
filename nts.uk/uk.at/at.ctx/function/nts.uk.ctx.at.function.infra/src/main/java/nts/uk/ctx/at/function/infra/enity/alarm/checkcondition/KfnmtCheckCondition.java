package nts.uk.ctx.at.function.infra.enity.alarm.checkcondition;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.enity.alarm.KfnmtAlarmPatternSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_CHECK_CONDITION")
public class KfnmtCheckCondition extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtCheckConditionPK pk;
	
	@Column(name = "EXTRACTION_ID")
	public String extractionId;
	
	@Column(name = "EXTRACTION_RANGE")
	public int extractionRange;
	
	@ManyToOne(optional = false)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false)})
	public KfnmtAlarmPatternSet alarmPatternSet;
	
	@OneToMany(mappedBy="checkCondition", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_CHECK_CON_ITEM")
	public List<KfnmtCheckConItem> checkConItems;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
}
