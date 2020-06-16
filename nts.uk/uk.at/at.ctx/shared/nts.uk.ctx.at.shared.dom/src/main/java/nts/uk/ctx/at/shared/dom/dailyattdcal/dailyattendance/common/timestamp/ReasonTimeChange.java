package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 時刻変更理由
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.Common.勤怠打刻.時刻変更理由
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class ReasonTimeChange implements DomainObject {
	
	//時刻変更手段
	private TimeChangeMeans timeChangeMeans;
	
	//打刻方法
	private Optional<EngravingMethod> engravingMethod;

	public ReasonTimeChange(TimeChangeMeans timeChangeMeans, EngravingMethod engravingMethod) {
		super();
		this.timeChangeMeans = timeChangeMeans;
		this.engravingMethod = Optional.ofNullable(engravingMethod);
	}
	
	

}
