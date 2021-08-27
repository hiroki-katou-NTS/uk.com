package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;

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
	 * input: 複製先のコードが既に保存されている, 上書きするか = false
	 * output: Msg_2117
	 */
	@Test
	public void testCopy_Msg_2117() {
		
		val reProductSoure = Helper.createScheduleDailyTablePrintSetting(
					new ScheduleDailyTableCode("01")
				,	new ScheduleDailyTableName("name"));
		
		val destinationCode = new ScheduleDailyTableCode("02");
		val destinationName = new ScheduleDailyTableName("name_update");
		val overwrite = false;
		
		new Expectations() {
			{
				require.isDestinationCodeExist((ScheduleDailyTableCode) any);
				result = true;
			}
		};
		
		NtsAssert.businessException("Msg_2117", () -> {
			AtomTask persist = CopyScheduleDailyTablePrintSettingService.copy(require
					,	reProductSoure, destinationCode
					,	destinationName, overwrite);
			persist.run();
		});
	}
	
	/**
	 * input: 複製先のコードが既に保存されない
	 * output: insert
	 */
	@Test
	public void testCopy_insert(@Injectable ScheduleDailyTableItemSetting itemSetting) {
		
		val reProductSoure = new ScheduleDailyTablePrintSetting(
				new ScheduleDailyTableCode("01")
			,	new ScheduleDailyTableName("name")
			,	itemSetting);

		val destinationCode = new ScheduleDailyTableCode("02");
		val destinationName = new ScheduleDailyTableName("name_update");
		val overwrite = false;
		val reproductDestination = reProductSoure.copy(destinationCode, destinationName);
		
		new Expectations() {
			{
				require.isDestinationCodeExist((ScheduleDailyTableCode) any);
				result = false;
			}
		};
		
		NtsAssert.atomTask(
				() ->	CopyScheduleDailyTablePrintSettingService.copy(require
							,	reProductSoure, destinationCode
							,	destinationName, overwrite)
				,	any -> require.insertScheduleDailyTablePrintSetting(reproductDestination));
		
		//更新対象
		assertThat( reproductDestination.getCode() ).isEqualTo( destinationCode );
		assertThat( reproductDestination.getName() ).isEqualTo( destinationName );
		assertThat( reproductDestination.getItemSetting() ).isEqualTo( itemSetting );
		
	}
	
	/**
	 * input: 複製先のコードが既に保存されている, 上書きするか = true
	 * output: update
	 */
	@Test
	public void testCopy_update(@Injectable ScheduleDailyTableItemSetting itemSetting) {
		
		val reProductSoure = new ScheduleDailyTablePrintSetting(
				new ScheduleDailyTableCode("01")
			,	new ScheduleDailyTableName("name")
			,	itemSetting);

		val destinationCode = new ScheduleDailyTableCode("02");
		val destinationName = new ScheduleDailyTableName("name_update");
		val overwrite = true;
		val reproductDestination = reProductSoure.copy(destinationCode, destinationName);
		
		new Expectations() {
			{
				require.isDestinationCodeExist((ScheduleDailyTableCode) any);
				result = true;
			}
		};
		
		NtsAssert.atomTask(
				() ->	CopyScheduleDailyTablePrintSettingService.copy(require
							,	reProductSoure, destinationCode
							,	destinationName, overwrite)
				,	any -> require.updateScheduleDailyTablePrintSetting(reproductDestination));
		
		//更新対象
		assertThat( reproductDestination.getCode() ).isEqualTo( destinationCode );
		assertThat( reproductDestination.getName() ).isEqualTo( destinationName );
		assertThat( reproductDestination.getItemSetting() ).isEqualTo( itemSetting );
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
