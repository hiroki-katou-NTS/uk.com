package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class ScheduleDailyTablePrintSettingTest {
	
	@Test
	public void testReproduct(
			@Injectable ScheduleDailyTableItemSetting itemSetting) {

		ScheduleDailyTablePrintSetting printSetting = new ScheduleDailyTablePrintSetting(
					new ScheduleDailyTableCode("01")
				,	new ScheduleDailyTableName("name")
				,	itemSetting);
		
		ScheduleDailyTableCode destinationCode = new ScheduleDailyTableCode("02");
		ScheduleDailyTableName distinationName = new ScheduleDailyTableName("name_update");
		
		// Act
		ScheduleDailyTablePrintSetting result = printSetting.reproduce(
					destinationCode
				,	distinationName);
		
		// Assert
		assertThat( result.getCode() ).isEqualTo( destinationCode );
		assertThat( result.getName() ).isEqualTo( distinationName );
		assertThat( result.getItemSetting() ).isEqualTo( itemSetting );
		
	}

}
