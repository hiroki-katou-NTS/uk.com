package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * AR: 応援カード編集設定
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.応援.応援カード編集設定
 * @author nws_namnv2
 */
@Getter
public class SupportCardEdit implements DomainAggregate {
	
	// 編集方法
	private EditMethod editMethod;
	
	public SupportCardEdit(int editMethod) {
		super();
		this.editMethod = EditMethod.valueOf(editMethod);
	}

}
