package nts.uk.ctx.at.function.infra.entity.alarm;

import java.io.Serializable;
import java.util.List;
//import java.util.Optional;
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

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALARM_PER_SET")
public class KfnmtAlarmPerSet extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfnmtAlarmPerSetPK pk;
	
	@Column(name = "AUTH_SET")
	public int authSetting;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false)
	})
	public KfnmtAlarmPatternSet alarmPatternSet;
	
	@OneToMany(mappedBy="alarmPerSet", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_ALARM_PER_SET_ITEM")
	public List<KfnmtAlarmPerSetItem> alarmPerSetItems;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KfnmtAlarmPerSet(KfnmtAlarmPerSetPK pk, int authSetting, List<KfnmtAlarmPerSetItem> alarmPerSetItems) {
		super();
		this.pk = pk;
		this.authSetting = authSetting;
		this.alarmPerSetItems = alarmPerSetItems;
	}
	
	public AlarmPermissionSetting toDomain() {
		return new AlarmPermissionSetting(authSetting==1,
				this.alarmPerSetItems.stream().map(c -> c.pk.roleID).collect(Collectors.toList()));
	}
	
	public static KfnmtAlarmPerSet createFromDomain(AlarmPermissionSetting domain, String companyId, String alarmPatternCode) {
		List<KfnmtAlarmPerSetItem> alarmPerSetItems = domain.getRoleIds().stream()
				.map(r -> new KfnmtAlarmPerSetItem(
						new KfnmtAlarmPerSetItemPK(companyId, alarmPatternCode, r)))
				.collect(Collectors.toList());
		
		return new KfnmtAlarmPerSet(new KfnmtAlarmPerSetPK(companyId, alarmPatternCode),
				domain.isAuthSetting() ? 1 : 0, alarmPerSetItems);
	}

	public void fromEntity(KfnmtAlarmPerSet  newEntity) {
//		this.alarmPerSetItems = newEntity.alarmPerSetItems;
		this.alarmPerSetItems.removeIf(c->!newEntity.alarmPerSetItems.contains(c));	
		newEntity.alarmPerSetItems.forEach( item ->{
			if(!this.alarmPerSetItems.contains(item)) this.alarmPerSetItems.add(item);		
		});
		this.authSetting = newEntity.authSetting;
	}
}
