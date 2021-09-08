package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class CopyScheduleDailyTablePrintSettingServiceTest {
	
	@Injectable
	private CopyScheduleDailyTablePrintSettingService.Require require;
	
	/**
	 * target: 複製する(copy)
	 * pattern: 
	 * 		- 複製先のコードが登録された(重複)
	 * 		- 上書きするか = false(上書きしない)
	 * except: Msg_2117
	 */	
	@Test
	public void testCopy_sourceIsExist_overwriteIsFalse() {
		
		val source = Helper.createScheduleDailyTablePrintSetting(
					new ScheduleDailyTableCode("01")
				,	new ScheduleDailyTableName("name"));
		
		val destCode = new ScheduleDailyTableCode("02");
		val destName = new ScheduleDailyTableName("name_update");
		val overwrite = false;//上書きするか = false
		
		new Expectations( source ) {
			{
				require.isDestinationCodeExist((ScheduleDailyTableCode) any);
				result = true;//複製先のコードが登録された
			}
		};
		
		NtsAssert.businessException("Msg_2117", () -> {
			AtomTask persist = CopyScheduleDailyTablePrintSettingService.copy(require
					,	source, destCode
					,	destName, overwrite);
			persist.run();
		});
	}
	
	/**
	 * target: 複製する(copy)
	 * pattern: 
	 * 		- 複製先のコードが未登録(重複なし)
	 * 		- 上書きするか = false(上書きしない)
	 * except: 複製された出力設定が登録(insert)される
	 */
	@Test
	public void testCopy_DestinationCodeCodeIsNew(@Injectable ScheduleDailyTableItemSetting itemSetting) {
		//複製先
		val source = new ScheduleDailyTablePrintSetting(
				new ScheduleDailyTableCode("01")
			,	new ScheduleDailyTableName("name")
			,	itemSetting);

		val destCode = new ScheduleDailyTableCode("02");
		val destName = new ScheduleDailyTableName("name_update");
		val overwrite = false;
		
		new Expectations( source ) {
			{
				//複製先のコードが未登録(重複なし)
				require.isDestinationCodeExist((ScheduleDailyTableCode) any);
				result = false;
				
				//[複製する]メソッドが正しく呼び出されているか
				source.copy(destCode, destName);
				times = 1;
			}
		};
		
		NtsAssert.atomTask(
				() ->	CopyScheduleDailyTablePrintSettingService.copy(require
							,	source, destCode
							,	destName, overwrite)
				,	any -> require.insertScheduleDailyTablePrintSetting(any.get()));
		
		
	}
	
	/**
	 * target: 複製する(copy)
	 * pattern: 
	 * 		- 複製先のコードが登録された(重複)
	 * 		- 上書きするか = true(上書きする)
	 * except: 複製された出力設定が登録(update)される
	 */
	@Test
	public void testCopy_DestinationCodeCodeIsUpdate(@Injectable ScheduleDailyTableItemSetting itemSetting) {
		
		val source = new ScheduleDailyTablePrintSetting(
				new ScheduleDailyTableCode("01")
			,	new ScheduleDailyTableName("name")
			,	itemSetting);

		val destCode = new ScheduleDailyTableCode("02");
		val destName = new ScheduleDailyTableName("name_update");
		val overwrite = true;//上書きするか = true
		
		new Expectations( source ) {
			{
				//複製先のコードが登録された(重複)
				require.isDestinationCodeExist((ScheduleDailyTableCode) any);
				result = true;
				
				//[複製する]メソッドが正しく呼び出されているか
				source.copy(destCode, destName);
				times = 1;
			}
		};
		
		NtsAssert.atomTask(
				() ->	CopyScheduleDailyTablePrintSettingService.copy(require
							,	source, destCode
							,	destName, overwrite)
				,	any -> require.updateScheduleDailyTablePrintSetting(any.get()));
	}
	
	public static class Helper{
		
		@Injectable
		private static ScheduleDailyTableItemSetting itemSetting;
		
		public static ScheduleDailyTablePrintSetting createScheduleDailyTablePrintSetting(
					ScheduleDailyTableCode code
				,	ScheduleDailyTableName name) {
			return new ScheduleDailyTablePrintSetting(code, name, itemSetting);
		}
	}
}
