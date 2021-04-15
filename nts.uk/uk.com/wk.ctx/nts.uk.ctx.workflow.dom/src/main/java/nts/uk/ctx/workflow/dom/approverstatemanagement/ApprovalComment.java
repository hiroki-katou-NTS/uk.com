package nts.uk.ctx.workflow.dom.approverstatemanagement;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".ワークフロー.承認状態管理.就業承認状態管理.<<PrimitiveValue>> 承認コメント
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(400)
public class ApprovalComment extends StringPrimitiveValue<ApprovalComment> {

	private static final long serialVersionUID = 1L;

	public ApprovalComment(String rawValue) {
		super(rawValue);
	}

}
