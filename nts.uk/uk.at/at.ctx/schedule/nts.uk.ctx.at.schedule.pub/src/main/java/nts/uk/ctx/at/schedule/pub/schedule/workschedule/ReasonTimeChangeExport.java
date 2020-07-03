package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 時刻変更理由
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.Common.勤怠打刻.時刻変更理由
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class ReasonTimeChangeExport implements DomainObject {
	
	//時刻変更手段
	@Setter
	private TimeChangeMeans timeChangeMeans;
	
	//打刻方法
	private Optional<EngravingMethod> engravingMethod;

	public ReasonTimeChangeExport(TimeChangeMeans timeChangeMeans, EngravingMethod engravingMethod) {
		super();
		this.timeChangeMeans = timeChangeMeans;
		this.engravingMethod = Optional.ofNullable(engravingMethod);
	}
	
	

}
