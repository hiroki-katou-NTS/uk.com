package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻カード.対象者
 * @author chungnt
 *
 */

@AllArgsConstructor
@Getter
public class TargetPerson {
	
	// 社員コード
	private final String employeeCd;
	
	// 社員ID
	private final String sid;
}
