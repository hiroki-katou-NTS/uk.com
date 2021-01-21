package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

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
	
	//時刻変更手段 enum TimeChangeMeans
	@Setter
	private int timeChangeMeans;
	
	//打刻方法 enum EngravingMethod
	private Integer engravingMethod;

	public ReasonTimeChangeExport(int timeChangeMeans, Integer engravingMethod) {
		super();
		this.timeChangeMeans = timeChangeMeans;
		this.engravingMethod = engravingMethod;
	}
	
	

}
