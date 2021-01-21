package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻カード.指定社員リストの打刻カード番号を生成する.打刻カード生成結果
 * @author chungnt
 *
 */

@AllArgsConstructor
@Getter
public class ImprintedCardGenerationResult {

	// 社員コード
	private final String employeeCd;
	
	// 生成した打刻カード
	private final StampCard cardNumber;
	
	// 重複する打刻カード
	private final Optional<StampCard> duplicateCards;
}
