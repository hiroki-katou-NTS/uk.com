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
	
	@Test
	public void testReproduct_Msg_2117() {
		
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
	
	@Test
	public void testReproduct_insert(@Injectable ScheduleDailyTableItemSetting itemSetting) {
		
		val reProductSoure = new ScheduleDailyTablePrintSetting(
				new ScheduleDailyTableCode("01")
			,	new ScheduleDailyTableName("name")
			,	itemSetting);

		val destinationCode = new ScheduleDailyTableCode("02");
		val destinationName = new ScheduleDailyTableName("name_update");
		val overwrite = false;
		val reproductDestination = reProductSoure.clone(destinationCode, destinationName);
		
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
		
	}
	
	@Test
	public void testReproduct_update(@Injectable ScheduleDailyTableItemSetting itemSetting) {
		
		val reProductSoure = new ScheduleDailyTablePrintSetting(
				new ScheduleDailyTableCode("01")
			,	new ScheduleDailyTableName("name")
			,	itemSetting);

		val destinationCode = new ScheduleDailyTableCode("02");
		val destinationName = new ScheduleDailyTableName("name_update");
		val overwrite = true;
		val reproductDestination = reProductSoure.clone(destinationCode, destinationName);
		
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
