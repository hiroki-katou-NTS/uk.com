package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import lombok.Getter;
import lombok.Setter;

/**
 * メンバー変数情報
 * @author jinno
 */
@Getter
@Setter
public class MemberInfo {

	/**
	 * メンバー変数コメント
	 */
	private String memberNameComment;

	/**
	 * アクセス修飾子
	 */
	private boolean public_ = false;
	private boolean protected_ = false;
	private boolean private_ = false;
	private boolean final_ = false;
	private boolean static_ = false;

	/**
	 * Optionalかどうか
	 */
	private boolean optional = false;

	/**
	 * リストかどうか
	 */
	private boolean list = false;

	/**
	 * メンバー変数型
	 */
	private String memberType;

	/**
	 * メンバー変数名
	 */
	private String memberName;


	/**
	 * デバッグ用
	 * @param sb
	 */
	public void setDebugString(StringBuilder sb) {

		sb.append("memberNameComment = '");
		sb.append(memberNameComment);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("memberType = '");
		sb.append(memberType);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("memberName = '");
		sb.append(memberName);
		sb.append("'");
		sb.append(System.lineSeparator());

	}

}
