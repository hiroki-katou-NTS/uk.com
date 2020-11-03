package nts.uk.ctx.at.function.infra.entity.alarm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingSimple;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity アラームリストパターン設定
 *
 * @author nws-minhnb
 */
@Data
@Entity
@Table(name = "KFNMT_ALARM_PATTERN_SET")
@EqualsAndHashCode(callSuper = true)
public class KfnmtAlarmPatternSet extends UkJpaEntity
		implements AlarmPatternSetting.MementoGetter, AlarmPatternSetting.MementoSetter, Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KfnmtAlarmPatternSetPK pk;

	@Column(name = "ALARM_PATTERN_NAME")
	private String alarmPatternName;

	@OneToMany(mappedBy = "alarmPatternSet", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_CHECK_CONDITION")
	private List<KfnmtCheckCondition> checkConList;

	@OneToOne(mappedBy = "alarmPatternSet", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_ALARM_PER_SET")
	private KfnmtAlarmPerSet alarmPerSet;

	/**
	 * Gets primary key of entity.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.pk;
	}

	/**
	 * No args constructor.
	 */
	protected KfnmtAlarmPatternSet() {
	}

	/**
	 * Creates new entity from domain and memento setter.
	 *
	 * @param domain the domain require <code>not null</code>
	 */
	public KfnmtAlarmPatternSet(@NonNull AlarmPatternSetting domain) {
		domain.setMemento(this);
	}

	/**
	 * Sets check condition list.
	 *
	 * @param checkConList the check condition list
	 */
	@Override
	public void setCheckConList(List<CheckCondition> checkConList) {
		this.checkConList = checkConList.stream()
										.map(domain -> KfnmtCheckCondition.createFromDomain(domain,
																							this.getCompanyID(),
																							this.getAlarmPatternCD()))
										.collect(Collectors.toList());
	}

	/**
	 * Sets alarm pattern code.
	 *
	 * @param alarmPatternCD the alarm pattern code
	 */
	@Override
	public void setAlarmPatternCD(String alarmPatternCD) {
		this.pk.alarmPatternCD = alarmPatternCD;
	}

	/**
	 * Sets company id.
	 *
	 * @param companyID the company id
	 */
	@Override
	public void setCompanyID(String companyID) {
		this.pk.companyID = companyID;
	}

	/**
	 * Sets alarm permission setting.
	 *
	 * @param alarmPerSet the alarm permission setting
	 */
	@Override
	public void setAlarmPerSet(AlarmPermissionSetting alarmPerSet) {
		this.alarmPerSet = KfnmtAlarmPerSet.createFromDomain(alarmPerSet, this.getCompanyID(), this.getAlarmPatternCD());
	}

	/**
	 * Gets check condition list.
	 *
	 * @return the check condition list
	 */
	@Override
	public List<CheckCondition> getCheckConList() {
		return this.checkConList.stream()
								.map(KfnmtCheckCondition::toDomain)
								.collect(Collectors.toList());
	}

	/**
	 * Gets alarm pattern code.
	 *
	 * @return the alarm pattern code
	 */
	@Override
	public String getAlarmPatternCD() {
		return this.pk.alarmPatternCD;
	}

	/**
	 * Gets company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyID() {
		return this.pk.companyID;
	}

	/**
	 * Gets alarm permission setting.
	 *
	 * @return the alarm permission setting
	 */
	@Override
	public AlarmPermissionSetting getAlarmPerSet() {
		return this.alarmPerSet.toDomain();
	}

	/**
	 * Update entity from new entity.
	 *
	 * @param newEntity the new entity アラームリストパターン設定
	 * @return the entity アラームリストパターン設定
	 */
	public KfnmtAlarmPatternSet updateEntity(KfnmtAlarmPatternSet newEntity) {
		if (newEntity != null) {
			this.alarmPatternName = newEntity.alarmPatternName;
			this.checkConList = newEntity.checkConList;
			this.alarmPerSet = newEntity.alarmPerSet;
		}
		return this;
	}

	/**
	 * To simple domain.
	 *
	 * @return the domain alarm pattern setting simple
	 */
	public AlarmPatternSettingSimple toSimpleDomain() {
		return new AlarmPatternSettingSimple(this.pk.alarmPatternCD,
											 this.alarmPatternName,
											 this.alarmPerSet.authSetting == 1,
											 this.alarmPerSet.alarmPerSetItems
															 .stream()
															 .map(item -> item.pk.roleID)
															 .collect(Collectors.toList()));
	}

}
