/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationCode;

/**
 * @author danpv
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AffClassHistItem_ver1 extends AggregateRoot {

	private String employeeId;

	private String historyId;

	private ClassificationCode classificationCode;

	public static AffClassHistItem_ver1 createFromJavaType(String employeeId, String historyId,
			String classificationCode) {
		return new AffClassHistItem_ver1(employeeId, historyId, new ClassificationCode(classificationCode));
	}

}
