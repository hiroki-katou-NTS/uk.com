package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 申請コメント
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
