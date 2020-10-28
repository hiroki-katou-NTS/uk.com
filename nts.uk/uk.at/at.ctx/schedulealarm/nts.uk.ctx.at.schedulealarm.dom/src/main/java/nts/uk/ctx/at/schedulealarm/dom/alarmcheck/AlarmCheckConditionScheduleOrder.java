package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
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
	
	public static AlarmCheckConditionScheduleOrder create(List<AlarmCheckConditionScheduleCode> codes) {
		// inv-2 @優先順リスト.size() > 0
		if (codes.isEmpty()) {
			throw new BusinessException("Msg_1622");
		}

		// inv-1 @優先順リストの勤務予定のアラームチェック条件コードが重複しないこと
		List<String> lstElement = codes.stream().map(c -> c.toString()).distinct().collect(Collectors.toList());
		if (lstElement.size() < codes.size()) {
			throw new BusinessException("Msg_1621");
		}

		return new AlarmCheckConditionScheduleOrder(codes);
	}
}
