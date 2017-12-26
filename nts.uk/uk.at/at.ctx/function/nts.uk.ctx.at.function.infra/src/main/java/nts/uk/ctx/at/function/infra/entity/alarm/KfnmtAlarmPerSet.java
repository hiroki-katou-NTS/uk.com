package nts.uk.ctx.at.function.infra.entity.alarm;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALARM_PER_SET")
public class KfnmtAlarmPerSet extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtAlarmPerSetPK pk;
	
	@Column(name = "AUTH_SET")
	public boolean authSetting;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false)
	})
	public KfnmtAlarmPatternSet alarmPatternSet;
	
	@OneToMany(mappedBy="alarmPerSet", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_ALARM_PER_SET_ITEM")
	public List<KfnmtAlarmPerSetItem> alarmPerSetItems;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public AlarmPermissionSetting toDomain() {
		return new AlarmPermissionSetting(this.pk.alarmPatternCD, this.pk.companyID, authSetting,
				this.alarmPerSetItems.stream().map(c -> c.pk.roleID).collect(Collectors.toList()));
	}
	
}
