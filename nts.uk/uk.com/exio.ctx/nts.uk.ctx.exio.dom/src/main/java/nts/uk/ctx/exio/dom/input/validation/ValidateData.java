package nts.uk.ctx.exio.dom.input.validation;

import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.validation.systemrange.ValidateSystemRange;
import nts.uk.ctx.exio.dom.input.validation.userrange.ValidateUserRange;

/**
 * 値の検証　とは誰に何を指示することなのか知っている。
 */
public class ValidateData{
	
	/**
	 * 妥当な数値であるか検証する
	 */
	public static void validate(ValidateRequire require, RevisedDataRecord record, String externalOutputCode) {
		ValidateSystemRange.validate(require, record);
		ValidateUserRange.validate(require, record, externalOutputCode);
	}
	
	public static interface ValidateRequire extends ValidateUserRange.UserRequire,
																				ValidateSystemRange.SystemRequire{
	}
}
