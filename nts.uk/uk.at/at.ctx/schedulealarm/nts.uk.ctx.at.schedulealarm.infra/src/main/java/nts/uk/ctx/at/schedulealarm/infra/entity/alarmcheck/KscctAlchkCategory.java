package nts.uk.ctx.at.schedulealarm.infra.entity.alarmcheck;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleOrder;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 勤務予定のアラームチェック条件
 * @author lan_lt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCCT_ALCHK_CATEGORY")
public class KscctAlchkCategory extends UkJpaEntity{
	
	public static final JpaEntityMapper<KscctAlchkCategory> MAPPER = new JpaEntityMapper<>(KscctAlchkCategory.class);
	
	@EmbeddedId
	public KscctAlchkCategoryPk pk;
	
	/** 名称 */
	@Column(name = "NAME")
	public String name;
	
	/** 優先順リスト(index+1) */
	@Column(name = "DISPORDER")
	public int disorder;
	
	/** 医療オプション */
	@Column(name = "MEDICAL_OP")
	public int medicalOp;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static KscctAlchkCategory toEntity(String contractCd, AlarmCheckConditionSchedule alarm,
			AlarmCheckConditionScheduleOrder alarmOrder) {
		KscctAlchkCategoryPk pk = new KscctAlchkCategoryPk(contractCd, alarm.getCode().v());
		int order = alarmOrder.getCodes().indexOf(alarm.getCode()) + 1;
		KscctAlchkCategory entity = new KscctAlchkCategory(pk, alarm.getConditionName(), order,
				alarm.isMedicalOpt() == true ? 1 : 0);
		return entity;
	}

}
