package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 賃金テーブル内容
 */
@Getter
@AllArgsConstructor
public class WageTableContent extends AggregateRoot {

	/**
	 * 履歴ID
	 */
	private String historyID;

	/**
	 * 支払金額
	 */
	private List<ElementsCombinationPaymentAmount> payments;

	/**
	 * 資格グループ設定
	 */
	private Optional<List<QualificationGroupSettingContent>> qualificationGroupSettings;

}
