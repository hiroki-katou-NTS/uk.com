package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.職場計
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
