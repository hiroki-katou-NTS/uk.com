package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workinformation.enums.ClassificationReflect;

/**
 * @author ThanhNX
 *
 *         予実反映
 */
@Getter
public class Reflection implements DomainAggregate {

	/**
	 * 会社ID
	 */
	private final String companyId;

	/**
	 * するしない区分
	 */
	private final ClassificationReflect classifiReflect;

	public Reflection(String companyId, ClassificationReflect classifiReflect) {
		super();
		this.companyId = companyId;
		this.classifiReflect = classifiReflect;
	}

}
