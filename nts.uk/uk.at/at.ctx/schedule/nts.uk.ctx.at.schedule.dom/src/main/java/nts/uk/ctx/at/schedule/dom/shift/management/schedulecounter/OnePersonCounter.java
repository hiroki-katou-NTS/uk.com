package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 個人計
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.個人計
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class OnePersonCounter implements DomainAggregate{

	/**
	 * カテゴリ一覧
	 */
	private Map<OnePersonCounterCategory, NotUseAtr> categories;
	
	/**
	 * 利用されているか
	 * @param category　チェックしたい個人計カテゴリ
	 * @return
	 */
	public boolean isUsed(OnePersonCounterCategory category) {
		return this.categories.get(category) == NotUseAtr.USE;
	}
	
	
}
