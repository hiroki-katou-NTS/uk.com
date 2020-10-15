package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請コメント
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(50)
public class Comment extends StringPrimitiveValue<Comment>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7693298589206570971L;

	public Comment(String rawValue) {
		super(rawValue);
	}

}
