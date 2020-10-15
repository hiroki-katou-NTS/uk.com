package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.勤務変更申請設定.勤務変更申請設定
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppWorkChangeSet implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * コメント1
	 */
	private AppCommentSet comment1;
	
	/**
	 * コメント2
	 */
	private AppCommentSet comment2;
	
	/**
	 * 勤務時間の初期表示
	 */
	private InitDisplayWorktimeAtr initDisplayWorktimeAtr;
	
}
