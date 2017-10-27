package nts.uk.ctx.bs.person.dom.person.role.auth;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.StringMaxLength;
@AllArgsConstructor
@StringMaxLength(1)
public enum PersonInfoPermissionType {
	/**  0:不可	 */
	NO(0),
	/**  1:可	 */
	YES(1);
	/**
	 * value
	 */
	 public final int value; 

}
