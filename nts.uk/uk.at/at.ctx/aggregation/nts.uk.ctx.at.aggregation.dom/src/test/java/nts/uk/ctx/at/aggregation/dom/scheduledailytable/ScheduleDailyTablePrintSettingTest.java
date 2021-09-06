package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 勤務計画実施表の出力設定のUTコード
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class ScheduleDailyTablePrintSettingTest {
	
	@Test
	public void testGetters( @Injectable ScheduleDailyTableItemSetting itemSetting ){
		
		ScheduleDailyTablePrintSetting printSetting = new ScheduleDailyTablePrintSetting(
				new ScheduleDailyTableCode("01")
			,	new ScheduleDailyTableName("name")
			,	itemSetting);
		
		NtsAssert.invokeGetters(printSetting);
	}
	
	@Test
	public void testCopy( @Injectable ScheduleDailyTableItemSetting itemSetting ){

		ScheduleDailyTablePrintSetting printSetting = new ScheduleDailyTablePrintSetting(
					new ScheduleDailyTableCode( "01" )
				,	new ScheduleDailyTableName( "name" )
				,	itemSetting);
		
		ScheduleDailyTableCode destinationCode = new ScheduleDailyTableCode( "02" );
		ScheduleDailyTableName destinationName = new ScheduleDailyTableName( "name_update" );
		
		// Act
		ScheduleDailyTablePrintSetting result = printSetting.copy(
					destinationCode
				,	destinationName );
		
		// Assert
		assertThat( result.getCode() ).isEqualTo( destinationCode );
		assertThat( result.getName() ).isEqualTo( destinationName );
		assertThat( result.getItemSetting() ).isEqualTo( itemSetting );
		
	}
	
	public static class Helper{
		@Injectable
		private static Optional<ScheduleDailyTableComment> comment;
		
		@Injectable
		private static List<Integer> workplaceCounter;
		
		@Injectable
		private static NotUseAtr transferDisplay;
		
		@Injectable
		private static SupporterPrintMethod supporterSchedulePrintMethod;
		
		@Injectable
		private static SupporterPrintMethod supporterDailyDataPrintMethod;
		
		public static ScheduleDailyTableItemSetting createScheduleDailyTableItemSetting(List<Integer> workplaceCounter
				,	List<Integer> personalCounter
				,	ScheduleDailyTableInkanRow inkanRow) {
			
			return new ScheduleDailyTableItemSetting(inkanRow, comment, personalCounter, workplaceCounter
					,	transferDisplay, supporterSchedulePrintMethod, supporterDailyDataPrintMethod);
		}
		
	}

}
