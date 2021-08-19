package nts.uk.ctx.exio.dom.input.validation;

import java.util.Optional;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserCondition;

/**
 * 値の検証
 *
 * 渡された値がシステム要件を満たすか、ユーザーの要件を満たすのかをチェックする
 *
 */
public class ValidateData{

	/**
	 * 妥当な数値であるか検証する
	 */
	public static boolean validate(ValidateRequire require, ExecutionContext context,
			RevisedDataRecord record) {

		return validateBySystem(require, context, record)
			 && validateByUserCondition(require, context, record);

	}

	private static boolean validateBySystem(
			ValidateRequire require,
			ExecutionContext context,
			RevisedDataRecord record ) {

		for (val recordItem : record.getItems()) {
			if(!require.getImportableItem(context.getDomainId(), recordItem.getItemNo())
					.validate(recordItem)) {

				return false;
			}
		}
		return true;


	}

	/**
	 * ユーザ設定と呼ばれている方の設定で値を検証
	 * @return
	 */
	private static boolean validateByUserCondition(
			ValidateRequire require,
			ExecutionContext context,
			RevisedDataRecord record ){

		for(val item : record.getItems()) {

			val condition = require.getImportingUserCondition(context.getSettingCode(), item.getItemNo());
			if (condition.isPresent() && !condition.get().validate(item)) {
				return false;
			}
		}

		return true;
	}


	public static interface ValidateRequire{

		ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);

		Optional<ImportingUserCondition> getImportingUserCondition(String settingCode, int itemNo);
	}
}
