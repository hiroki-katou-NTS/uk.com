package nts.uk.ctx.at.schedulealarm.infra.entity.alarmcheck;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionSchedule;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleOrder;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 勤務予定のアラームチェック条件・サブ条件
 * KSCCT_ALCHK_CATEGORY_SUB
 * @author lan_lt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCCT_ALCHK_CATEGORY_SUB")
public class KscctAlchkCategorySub extends UkJpaEntity {
	public static final JpaEntityMapper<KscctAlchkCategorySub> MAPPER = new JpaEntityMapper<>(KscctAlchkCategorySub.class);
	
	@EmbeddedId
	public KscctAlchkCategorySubPk pk;
	
	/** サブ条件リスト.説明 */
	@Column(name = "EXPLANATION")
	public String explanation;
	
	/** サブ条件リスト.メッセージ.既定のメッセージ */
	@Column(name = "DEFAULT_MSG")
	public String defaultMsg;
	
	/** 優先順リスト(index+1) */
	@Column(name = "DISPORDER")
	public int disorder;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static List<KscctAlchkCategorySub> toEntity(String contractCd, AlarmCheckConditionSchedule alarm,
			AlarmCheckConditionScheduleOrder alarmOrder) {
		return alarm.getSubConditions().stream().map(c -> {
			val pk = new KscctAlchkCategorySubPk(contractCd, alarm.getCode().v(), c.getSubCode().v());
			return new KscctAlchkCategorySub(pk, c.getExplanation(), c.getMessage().getDefaultMsg().v(),
					Integer.valueOf(c.getSubCode().v()).intValue());
		}).collect(Collectors.toList());
		
	}

}
