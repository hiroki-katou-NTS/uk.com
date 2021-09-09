/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;

/**
 * @author laitv 
 * 打刻反映状態 
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻.打刻反映状態
 */
@Getter
@Setter
@AllArgsConstructor
public class ImprintReflectionState implements DomainValue, Cloneable {

	//反映済み区分
	private boolean reflectedCategory;
	
	// 反映された年月日
	private Optional<GeneralDate> reflectedDate;
	
	//[1] 対象日に反映できるか	
	public boolean canReflectedOnTargetDate(GeneralDate  baseDate) {
		if(!this.reflectedCategory)
			return true;
		
		if(this.reflectedDate.isPresent() && this.reflectedDate.get().afterOrEquals(baseDate))
			return true;
		
		return false;
	}
	
	// [2] 反映された年月日を更新する	
	public void updateReflectedDate(GeneralDate  baseDate) {
		if(!this.reflectedCategory)
			return;
		this.reflectedDate = Optional.of(baseDate);
	}
	
	@Override
	public ImprintReflectionState clone() {
		return new ImprintReflectionState(reflectedCategory, reflectedDate);
	}
	
}
