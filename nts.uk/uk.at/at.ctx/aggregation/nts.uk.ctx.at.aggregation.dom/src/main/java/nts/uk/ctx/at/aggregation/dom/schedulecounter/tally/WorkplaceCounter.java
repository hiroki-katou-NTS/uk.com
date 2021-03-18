package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 職場計
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.職場計・個人計.職場計
 * @author dan_pv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkplaceCounter implements DomainAggregate{

	/**
	 * 利用カテゴリ一覧
	 */
	private List<WorkplaceCounterCategory> useCategories = new ArrayList<>();

	/**
	 * @param useCategories
	 * @return
	 */
	public static WorkplaceCounter create(List<WorkplaceCounterCategory> useCategories) {

		if ( useCategories.size() != new HashSet<>(useCategories).size()) {

			throw new RuntimeException("the list is duplicated");
		}

		return new WorkplaceCounter(useCategories);
	}

	/**
	 * 利用されているか
	 * @param category　チェックしたいカテゴリ
	 * @return
	 */
	public boolean isUsed(WorkplaceCounterCategory category) {

		return this.useCategories.contains(category);
	}

}
