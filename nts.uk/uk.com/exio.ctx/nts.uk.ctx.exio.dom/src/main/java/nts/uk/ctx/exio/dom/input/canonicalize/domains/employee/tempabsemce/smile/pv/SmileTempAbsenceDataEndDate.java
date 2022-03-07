package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.smile.pv;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.time.GeneralDate;

/**
 * Smile休職情報　終了日
 * Smile仕様では終了日が未選択の場合[99999999] が来る
 */
@StringMaxLength(8)
@StringCharType(CharType.NUMERIC)
public class SmileTempAbsenceDataEndDate extends StringPrimitiveValue<SmileTempAbsenceDataEndDate>{

	private static final long serialVersionUID = 1L;

	public SmileTempAbsenceDataEndDate(String rawValue) {
		super(rawValue); 
	}
	
	private static final String smileDateFormat = "yyyyMMdd";
	
	public GeneralDate getGeneralDate() {
		if(isNull()) {
			return GeneralDate.max();
		}
		return GeneralDate.fromString(this.v(), smileDateFormat);
	}
	
	private boolean isNull() {
		return v().equals("99999999");
	}
	
	
}
