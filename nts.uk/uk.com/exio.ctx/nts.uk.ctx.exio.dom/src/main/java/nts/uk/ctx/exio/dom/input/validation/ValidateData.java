package nts.uk.ctx.exio.dom.input.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.validation.system.ValidateSystemRange;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserCondition;

/**
 * 値の検証　とは誰に何を指示することなのか知っている。
 */
public class ValidateData{
	
	/**
	 * 妥当な数値であるか検証する
	 */
	public static boolean validate(ValidateRequire require, ExecutionContext context, RevisedDataRecord record) {
		return ValidateSystemRange.validate(require, context, record)
			 && validateByUserCondition(require, context, record);
	}
	
	/**
	 * ユーザ設定と呼ばれている方の設定で値を検証 
	 * @return 
	 */
	private static boolean validateByUserCondition(
			ValidateRequire require,
			ExecutionContext context,
			RevisedDataRecord record ){
		val masters = getUserConditions(require, context, record);
		
		List<Boolean> successFlags = new ArrayList<Boolean>();
		record.getItems().stream().forEach(item ->{
			masters.forEach(master -> {
				if(master.getItemNo() == item.getItemNo()) {
					successFlags.add(master.getValidation().validate(item));
				}
			});
		});
		//falseが含まれてたら失敗したことを伝えたい
		return !successFlags.contains(false);
	}

	private static List<ImportingUserCondition> getUserConditions(ValidateRequire require, ExecutionContext context, RevisedDataRecord record ) {
		val itemNoList = record.getItems().stream().map(item -> item.getItemNo()).collect(Collectors.toList());
		return require.getImportingUserCondition(context.getSettingCode(), itemNoList);
	}
	
	
	
	public static interface ValidateRequire extends ValidateSystemRange.SystemRequire{
		List<ImportingUserCondition> getImportingUserCondition(String settingCode, List<Integer> itemNo);
	}
}
