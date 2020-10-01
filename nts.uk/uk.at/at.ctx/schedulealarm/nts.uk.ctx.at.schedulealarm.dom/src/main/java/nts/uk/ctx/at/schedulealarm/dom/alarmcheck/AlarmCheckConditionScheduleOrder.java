package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 勤務予定のアラームチェック条件の並び順
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定のアラームチェック.アラームチェック条件.勤務予定のアラームチェック条件並び順
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class AlarmCheckConditionScheduleOrder implements DomainAggregate{
	/** 優先順リスト */
	private List<AlarmCheckConditionScheduleCode> codes;
}
