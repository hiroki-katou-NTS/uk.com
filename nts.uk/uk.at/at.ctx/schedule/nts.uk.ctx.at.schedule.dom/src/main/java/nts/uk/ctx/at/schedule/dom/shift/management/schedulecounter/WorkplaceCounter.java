package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.職場計
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class WorkplaceCounter implements DomainAggregate{
	
	/**
	 * カテゴリ一覧
	 */
	private Map<WorkplaceCounterCategory, NotUseAtr> categories;
	
	/**
	 * @param categories 作成したいカテゴリ一覧
	 * @return
	 */
	public static WorkplaceCounter create( Map<WorkplaceCounterCategory, NotUseAtr> categories){
		
		if (categories.isEmpty()) {
			throw new RuntimeException("invalid data");
		}
		
		return new WorkplaceCounter(categories);
	}
	
	/**
	 * 利用されているか
	 * @param category　チェックしたいカテゴリ
	 * @return
	 */
	public boolean isUsed(WorkplaceCounterCategory category) {
		
		return this.categories.getOrDefault(category, NotUseAtr.NOT_USE) == NotUseAtr.USE;
	}

}
