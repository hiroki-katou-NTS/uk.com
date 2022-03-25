package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX 看護区分名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.医療勤務形態.看護区分名称
 */
@StringMaxLength(20)
public class NurseClassifiName extends StringPrimitiveValue<NurseClassifiName> {
	private static final long serialVersionUID = 1L;

	public NurseClassifiName(String rawValue) {
		super(rawValue);
	}

}
