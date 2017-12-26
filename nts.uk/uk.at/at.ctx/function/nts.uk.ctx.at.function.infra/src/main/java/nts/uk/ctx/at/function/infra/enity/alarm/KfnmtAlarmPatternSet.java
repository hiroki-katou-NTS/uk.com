package nts.uk.ctx.at.function.infra.enity.alarm;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.enity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALARM_PATTERN_SET")
public class KfnmtAlarmPatternSet extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private KfnmtAlarmPatternSetPK pk;
	
	@Column(name = "ALARM_PATTERN_NAME")
	public boolean alarmPatternName;
	
    @OneToMany(mappedBy = "alarmPatternSet", cascade = CascadeType.ALL)
    @JoinTable(name = "KFNMT_CHECK_CONDITION")
    private List<KfnmtCheckCondition> checkConList;
	
	@OneToOne(mappedBy="alarmPatternSet", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_ALARM_PER_SET")
	public KfnmtAlarmPerSet alarmPerSet;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
