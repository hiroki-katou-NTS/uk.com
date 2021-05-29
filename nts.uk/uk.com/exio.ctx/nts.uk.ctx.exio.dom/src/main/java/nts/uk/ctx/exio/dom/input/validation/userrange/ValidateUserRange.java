package nts.uk.ctx.exio.dom.input.validation.userrange;

import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;

public class ValidateUserRange {
	/**
	 * ユーザ設定と呼ばれている方の設定で値を検証 
	 */
	public static void validate(UserRequire require, RevisedDataRecord record, String externalOutputCode ){
		val masters = require.getStdAcceptItem(record.getCategoryId(), externalOutputCode);
		
		record.getItems().stream().forEach(item ->{
			masters.forEach(master -> {
				if(master.getCategoryItemNo() == item.getItemNo()) {
					master.getAcceptScreenConditionSetting()
					.ifPresent(userSetting ->{
						userSetting.checkCondNumber(
								   			record, 
								   			master.getItemType());						
					});
				}
			});
		});
	}
	
	public static interface UserRequire{
		List<StdAcceptItem> getStdAcceptItem(int groupId, String externalCode);
	}
}
