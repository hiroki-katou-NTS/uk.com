package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;

/**
 * 勤務計画実施表の出力設定を複製する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.勤務計画実施表.勤務計画実施表の出力設定を複製する
 * @author lan_lt
 *
 */
public class CopyScheduleDailyTablePrintSettingService {
	
/**
 * 複製する
 * @param require
 * @param reproductSource 複製元
 * @param destinationCode 複製先のコード
 * @param destinationName 複製先の名称
 * @param overwrite 上書きするか
 * @return
 */
	public static AtomTask copy(Require require
			,	ScheduleDailyTablePrintSetting reproductSource
			,	ScheduleDailyTableCode destinationCode
			,	ScheduleDailyTableName destinationName
			,	boolean overwrite) {
		
		boolean isExist = require.isDestinationCodeExist(destinationCode);
		
		if(isExist && !overwrite) {
			throw new BusinessException("Msg_2117");
		}
		
		val reproductDestination =  reproductSource.copy(destinationCode, destinationName);
		
		return AtomTask.of(() -> {
			if(isExist) {
				require.updateScheduleDailyTablePrintSetting(reproductDestination);
			}else {
				require.insertScheduleDailyTablePrintSetting(reproductDestination);
			}
		});
		
	}
	
	public static interface Require{
		
		/**
		 * 複製先のコードが既に保存されているか
		 * @param destinationCode 勤務計画実施表のコード
		 * @return
		 */
		boolean isDestinationCodeExist(ScheduleDailyTableCode destinationCode);
		
		/**
		 * 勤務計画実施表の出力設定を追加する
		 * @param printSetting 勤務計画実施表の出力設定
		 */
		void insertScheduleDailyTablePrintSetting(ScheduleDailyTablePrintSetting printSetting);
		
		/**
		 * 勤務計画実施表の出力設定を変更する
		 * @param printSetting 勤務計画実施表の出力設定
		 */
		void updateScheduleDailyTablePrintSetting(ScheduleDailyTablePrintSetting printSetting);
	
	}

}
