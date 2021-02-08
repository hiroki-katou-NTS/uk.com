package nts.uk.ctx.at.function.infra.entity.alarm;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingSimple;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * Entity アラームリストパターン設定
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALARM_PATTERN_SET")
public class KfnmtAlarmPatternSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAlarmPatternSetPK pk;

	@Column(name = "ALARM_PATTERN_NAME")
	public String alarmPatternName;

	@OneToMany(mappedBy = "alarmPatternSet", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_CHECK_CONDITION")
	public List<KfnmtCheckCondition> checkConList;

	@OneToOne(mappedBy = "alarmPatternSet", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_ALARM_PER_SET")
	public KfnmtAlarmPerSet alarmPerSet;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public AlarmPatternSetting toDomain() {
		return new AlarmPatternSetting(this.checkConList.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
				pk.alarmPatternCD, pk.companyID, alarmPerSet.toDomain(), alarmPatternName);
	}

	public static KfnmtAlarmPatternSet toEntity(AlarmPatternSetting domain) {

		KfnmtAlarmPerSet alarmPerSet = KfnmtAlarmPerSet.toEntity(domain.getAlarmPerSet(), domain.getCompanyID(),
				domain.getAlarmPatternCD().v());
		List<KfnmtCheckCondition> checkConList = domain.getCheckConList().stream()
				.map(c -> KfnmtCheckCondition.toEntity(c, domain.getCompanyID(), domain.getAlarmPatternCD().v()))
				.collect(Collectors.toList());
		return new KfnmtAlarmPatternSet(
				new KfnmtAlarmPatternSetPK(domain.getCompanyID(), domain.getAlarmPatternCD().v()),
				domain.getAlarmPatternName().v(), checkConList, alarmPerSet);
	}

	public void fromEntity(KfnmtAlarmPatternSet entity) {
		this.alarmPatternName = entity.alarmPatternName;
		this.checkConList.removeIf(c -> !entity.checkConList.contains(c));
		entity.checkConList.forEach(e -> {
			Optional<KfnmtCheckCondition> checkCon = this.checkConList.stream().filter(c -> c.pk.equals(e.pk))
					.findFirst();
			if (checkCon.isPresent()) {
				checkCon.get().fromEntity(e);
			} else {
				this.checkConList.add(e);
			}
		});
		this.alarmPerSet.fromEntity(entity.alarmPerSet);
	}

	public AlarmPatternSettingSimple toSimpleDomain() {
		return new AlarmPatternSettingSimple(this.pk.alarmPatternCD, this.alarmPatternName,
				this.alarmPerSet.authSetting == 1,
				this.alarmPerSet.alarmPerSetItems.stream().map(item -> item.pk.roleID).collect(Collectors.toList()));
	}

	public List<CheckCondition> toCheckCondition() {
		return this.checkConList.stream().map(KfnmtCheckCondition::toDomain).collect(Collectors.toList());
	}
}
