package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.refersToInformation;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 静脈内容
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.指情報.静脈内容
 * @author chungnt
 *
 */

@StringMaxLength(30000)
public class VeinContent extends StringPrimitiveValue<VeinContent> {
	private static final long serialVersionUID = 1L;
	
	public VeinContent(String rawValue) {
		super(rawValue);
	}

}
