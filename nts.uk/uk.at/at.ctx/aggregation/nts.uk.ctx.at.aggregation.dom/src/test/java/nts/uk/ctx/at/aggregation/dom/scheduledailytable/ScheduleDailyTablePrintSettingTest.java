package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
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
	
}
