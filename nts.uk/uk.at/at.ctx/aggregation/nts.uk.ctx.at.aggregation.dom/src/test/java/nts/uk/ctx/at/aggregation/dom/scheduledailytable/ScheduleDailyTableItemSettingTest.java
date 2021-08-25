package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class ScheduleDailyTableItemSettingTest {

	@Test
	public void getters(
			@Injectable ScheduleDailyTableInkanRow inkanRow,
			@Injectable Optional<ScheduleDailyTableComment> comment,
			@Injectable NotUseAtr transferDisplay,
			@Injectable SupporterPrintMethod supporterSchedulePrintMethod,
			@Injectable SupporterPrintMethod supporterDailyDataPrintMethod) {

		List<Integer> personalCounter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<Integer> workplaceCounter = Arrays.asList(1, 2, 3, 4, 5);
		
		ScheduleDailyTableItemSetting result = new ScheduleDailyTableItemSetting(
				inkanRow, 
				comment, 
				personalCounter, 
				workplaceCounter, 
				transferDisplay, 
				supporterSchedulePrintMethod, 
				supporterDailyDataPrintMethod);
		
		NtsAssert.invokeGetters(result);
		
	}
	
	@Test
	public void testClone(
			@Injectable ScheduleDailyTableInkanRow inkanRow,
			@Injectable Optional<ScheduleDailyTableComment> comment,
			@Injectable NotUseAtr transferDisplay,
			@Injectable SupporterPrintMethod supporterSchedulePrintMethod,
			@Injectable SupporterPrintMethod supporterDailyDataPrintMethod) {
		
		List<Integer> personalCounter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<Integer> workplaceCounter = Arrays.asList(1, 2, 3, 4, 5);
		
		ScheduleDailyTableItemSetting itemSetting = new ScheduleDailyTableItemSetting(
				inkanRow, 
				comment, 
				personalCounter, 
				workplaceCounter, 
				transferDisplay, 
				supporterSchedulePrintMethod, 
				supporterDailyDataPrintMethod);
		
		// Act
		ScheduleDailyTableItemSetting result = itemSetting.clone();
		
		// Assert
		assertThat( result.getInkanRow() ).isEqualTo( inkanRow );
		assertThat( result.getComment() ).isEqualTo( comment );
		assertThat( result.getPersonalCounter() ).containsExactly( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );
		assertThat( result.getWorkplaceCounter() ).containsExactly( 1, 2, 3, 4, 5 );
		assertThat( result.getTransferDisplay() ).isEqualTo( transferDisplay );
		assertThat( result.getSupporterSchedulePrintMethod() ).isEqualTo( supporterSchedulePrintMethod );
		assertThat( result.getSupporterDailyDataPrintMethod() ).isEqualTo( supporterDailyDataPrintMethod );		

	}

}
