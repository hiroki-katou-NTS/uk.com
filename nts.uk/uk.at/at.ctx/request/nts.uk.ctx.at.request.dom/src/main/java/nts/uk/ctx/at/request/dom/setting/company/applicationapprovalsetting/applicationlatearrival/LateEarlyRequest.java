package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * refactor 4
 * @author anhnm
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.設定.会社別.申請承認設定.遅刻早退申請.遅刻早退申請
 *
 */

//遅刻早退取消申請設定
@Getter
@Setter
public class LateEarlyRequest implements DomainAggregate {
	
	//	会社ID
	private String companyId;
	
	//	取り消す設定
	private CancelCategory cancelCategory;
	
	public LateEarlyRequest(String companyId, String cancelCategory) {
		this.companyId = companyId;
		this.cancelCategory = CancelCategory.valueOf(cancelCategory);
	}
}
