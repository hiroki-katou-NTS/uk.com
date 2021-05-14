package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.nursingcareworkstyle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 夜勤職員配置加算表用備考
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.介護勤務形態.夜勤職員配置加算表用備考
 * @author HieuLt
 *
 */
@StringMaxLength(200)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class NightShiftRemarks extends StringPrimitiveValue<NightShiftRemarks> {

	private static final long serialVersionUID = 1L;

	public NightShiftRemarks(String rawValue) {
		super(rawValue);
	}

}
