package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * VO:指の静脈情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.指情報.指の静脈情報
 * @author chungnt
 *
 */

@Value
@AllArgsConstructor
public class FingerVeininformation implements DomainValue {
	
	/*
	 * 	指種類
	 */
	public final FingerType fingerType;
	
	/*
	 * 	静脈情報
	 */
	public VeinContent veinInformation;
	

}