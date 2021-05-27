package nts.uk.ctx.exio.dom.input.validation;

import java.util.Optional;

import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

public class ValidateData{

	/**
	 * 妥当な数値であるか検証する
	 */
	public static void validate(Require require, RevisedDataRecord record) {
		require.getDefinition(record.getCategoryId())
			.ifPresent(master -> master.validateSameItemNo(record.getItems()));
	}
	
	public static interface Require{
		Optional<ImportableItems> getDefinition(int categoryId);
	}
}
