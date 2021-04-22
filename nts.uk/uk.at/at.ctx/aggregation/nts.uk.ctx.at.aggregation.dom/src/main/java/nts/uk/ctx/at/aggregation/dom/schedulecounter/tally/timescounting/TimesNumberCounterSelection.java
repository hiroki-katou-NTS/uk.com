package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 回数集計選択
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.職場計・個人計.回数集計.回数集計選択
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class TimesNumberCounterSelection implements DomainAggregate {

	/** 回数集計種類 */
	private final TimesNumberCounterType type;

	/** 選択した項目リスト */
	private List<Integer> selectedNoList;

	/**
	 * 作る
	 * @param type
	 * @param selectedNoList
	 * @return
	 */
	public static TimesNumberCounterSelection create(
			TimesNumberCounterType type,
			List<Integer> selectedNoList) {

		if ( selectedNoList.isEmpty()) {
			throw new BusinessException("Msg_1817");
		}

		if ( type != TimesNumberCounterType.WORKPLACE && selectedNoList.size() > 10 ) {
			throw new BusinessException("Msg_1837");
		}

		return new TimesNumberCounterSelection(type, selectedNoList);
	}

}
