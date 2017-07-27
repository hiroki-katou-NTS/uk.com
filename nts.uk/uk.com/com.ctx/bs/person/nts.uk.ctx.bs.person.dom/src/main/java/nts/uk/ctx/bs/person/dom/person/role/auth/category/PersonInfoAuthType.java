package nts.uk.ctx.bs.person.dom.person.role.auth.category;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.StringMaxLength;

@AllArgsConstructor
@StringMaxLength(1)
public enum PersonInfoAuthType {
	// 1:非表示
	HIDE(1), 
	// 2:参照のみ
	REFERENCE(2),
	// 3:更新
	UPDATE(3);

	public final int value;

}
