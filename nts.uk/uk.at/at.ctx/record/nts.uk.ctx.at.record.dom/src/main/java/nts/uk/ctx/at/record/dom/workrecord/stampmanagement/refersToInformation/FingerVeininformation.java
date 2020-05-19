package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * VO:指の静脈情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.指情報.指の静脈情報
 * 
 * @author chungnt
 *
 */

@Data
@AllArgsConstructor
public class FingerVeininformation implements DomainValue {

	/*
	 * 指種類
	 */
	private final FingerType fingerType;

	/*
	 * 静脈情報
	 */
	private VeinContent veinInformation;

	public void concatVeinContent(String veinPart) {
		if (veinInformation == null) {
			veinInformation = new VeinContent(veinPart);
		} else {
			veinInformation = new VeinContent(veinInformation.v() + veinPart);
		}
	}
}