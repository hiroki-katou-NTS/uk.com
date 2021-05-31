package nts.uk.ctx.exio.dom.input.validation.userrange;

import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

public class ValidateUserRange {
	/**
	 * ユーザ設定と呼ばれている方の設定で値を検証 
	 */
	public static void validate(
			UserRequire require,
			ExecutionContext context,
			RevisedDataRecord record ){
		
//		val masters = require.getStdAcceptItem(context.getGroupId(), context.getSettingCode());
//		
//		record.getItems().stream().forEach(item ->{
//			masters.forEach(master -> {
//				if(master.getCategoryItemNo() == item.getItemNo()) {
//					master.getAcceptScreenConditionSetting()
//					.ifPresent(userSetting ->{
//						userSetting.checkCondition(item);						
//					});
//				}
//			});
//		});
	}
	
	public static interface UserRequire{
	}
}
