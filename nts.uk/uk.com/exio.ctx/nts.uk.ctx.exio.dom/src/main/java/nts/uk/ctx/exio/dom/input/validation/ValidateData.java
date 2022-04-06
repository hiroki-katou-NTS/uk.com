package nts.uk.ctx.exio.dom.input.validation;

import lombok.val;
import nts.gul.util.Either;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;
import nts.uk.ctx.exio.dom.input.errors.ItemError;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 値の検証
 *
 * 渡された値がシステム要件を満たすか、ユーザーの要件を満たすのかをチェックする
 *
 */
public class ValidateData {

	/**
	 * 妥当な数値であるか検証する
	 */
	public static Either<List<ItemError>, RevisedDataRecord> validate(ValidateRequire require, ExecutionContext context,
			RevisedDataRecord record) {

		val correctedItems = new DataItemList();
		val errors = new ArrayList<ItemError>();

		for (val item : record.getItems()) {
			val corrected = CorrectValueByType.correct(require, context, item);
			validateBySystem(require, context, corrected)
					.mapEither(__ -> validateByUserCondition(require, context, corrected))
					.ifRight(__ -> correctedItems.add(corrected))
					.ifLeft(err -> errors.add(new ItemError(corrected.getItemNo(), err.getText())));
		}

		if (!errors.isEmpty()) {
			return Either.left(errors);
		}

		return Either.right(new RevisedDataRecord(record.getRowNo(), correctedItems));
	}

	private static Either<ErrorMessage, Void> validateBySystem(ValidateRequire require, ExecutionContext context,
			DataItem item) {

		return require.getImportableItem(context.getDomainId(), item.getItemNo())
				.validate(item)
				.map(err -> Either.leftVoid(err))
				.orElseGet(() -> Either.rightVoid());
	}

	/**
	 * ユーザ設定と呼ばれている方の設定で値を検証
	 * 
	 * @return
	 */
	private static Either<ErrorMessage, Void> validateByUserCondition(ValidateRequire require, ExecutionContext context,
			DataItem item) {

		val condition = require.getImportingUserCondition(context.getSettingCode(), item.getItemNo());

		if (condition.isPresent() && !condition.get().validate(item)) {
			return Either.left(new ErrorMessage("受入データが設定条件に適合していません。"));
		}

		return Either.rightVoid();
	}

	public interface ValidateRequire extends CorrectValueByType.Require {

		ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo);

		Optional<ImportingUserCondition> getImportingUserCondition(String settingCode, int itemNo);
	}
}
