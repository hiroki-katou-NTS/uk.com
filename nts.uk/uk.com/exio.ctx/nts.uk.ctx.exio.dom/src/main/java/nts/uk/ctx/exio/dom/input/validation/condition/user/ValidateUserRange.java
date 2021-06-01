package nts.uk.ctx.exio.dom.input.validation.condition.user;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.validation.condition.ImportingUserCondition;

/**
 *ユーザーが設定した値の範囲で検証 
 */
public class ValidateUserRange {
	/**
	 * ユーザ設定と呼ばれている方の設定で値を検証 
	 * @return 
	 */
	public static boolean validate(
			UserRequire require,
			ExecutionContext context,
			RevisedDataRecord record ){
		val masters = require.getImportingUserCondition(context.getSettingCode(), context.getGroupId());
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
	
	public static interface UserRequire{
		List<ImportingUserCondition> getImportingUserCondition(String settingCode, int groupId);
	}
}
