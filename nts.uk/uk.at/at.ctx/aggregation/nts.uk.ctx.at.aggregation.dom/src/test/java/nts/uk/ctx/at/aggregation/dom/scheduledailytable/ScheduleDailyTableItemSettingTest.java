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

	/**
	 * method 作る
	 * input: 個人計.size = 11
	 * output: Msg_2083
	 */
	@Test
	public void create_inv1(
			@Injectable ScheduleDailyTableInkanRow inkanRow,
			@Injectable Optional<ScheduleDailyTableComment> comment,
			@Injectable List<Integer> workplaceCounter,
			@Injectable NotUseAtr transferDisplay,
			@Injectable SupporterPrintMethod supporterSchedulePrintMethod,
			@Injectable SupporterPrintMethod supporterDailyDataPrintMethod) {

		List<Integer> personalCounter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
		
		NtsAssert.businessException("Msg_2083", () -> 
			ScheduleDailyTableItemSetting.create(
				inkanRow, 
				comment, 
				personalCounter, 
				workplaceCounter, 
				transferDisplay, 
				supporterSchedulePrintMethod, 
				supporterDailyDataPrintMethod));
	}
	
	/**
	 * method 作る
	 * input: 職場計.size = 6
	 * output: Msg_2084
	 */
	@Test
	public void create_inv2(
			@Injectable ScheduleDailyTableInkanRow inkanRow,
			@Injectable Optional<ScheduleDailyTableComment> comment,
			@Injectable List<Integer> personalCounter,
			@Injectable NotUseAtr transferDisplay,
			@Injectable SupporterPrintMethod supporterSchedulePrintMethod,
			@Injectable SupporterPrintMethod supporterDailyDataPrintMethod) {

		List<Integer> workplaceCounter = Arrays.asList(1, 2, 3, 4, 5, 6);
		
		NtsAssert.businessException("Msg_2084", () -> 
			ScheduleDailyTableItemSetting.create(
				inkanRow, 
				comment, 
				personalCounter, 
				workplaceCounter, 
				transferDisplay, 
				supporterSchedulePrintMethod, 
				supporterDailyDataPrintMethod));
	}
	
	/**
	 * method 作る
	 * input:  職場計.size() = 5, 個人計.size() = 11
	 * output: create success
	 */
	@Test
	public void create_success(
			@Injectable ScheduleDailyTableInkanRow inkanRow,
			@Injectable Optional<ScheduleDailyTableComment> comment,
			@Injectable NotUseAtr transferDisplay,
			@Injectable SupporterPrintMethod supporterSchedulePrintMethod,
			@Injectable SupporterPrintMethod supporterDailyDataPrintMethod) {

		List<Integer> personalCounter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		List<Integer> workplaceCounter = Arrays.asList(1, 2, 3, 4, 5);
		
		ScheduleDailyTableItemSetting result = ScheduleDailyTableItemSetting.create(
				inkanRow, 
				comment, 
				personalCounter, 
				workplaceCounter, 
				transferDisplay, 
				supporterSchedulePrintMethod, 
				supporterDailyDataPrintMethod);
		
		assertThat( result.getInkanRow() ).isEqualTo( inkanRow );
		assertThat( result.getComment() ).isEqualTo( comment );
		assertThat( result.getPersonalCounter() ).containsExactly( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );
		assertThat( result.getWorkplaceCounter() ).containsExactly( 1, 2, 3, 4, 5 );
		assertThat( result.getTransferDisplay() ).isEqualTo( transferDisplay );
		assertThat( result.getSupporterSchedulePrintMethod() ).isEqualTo( supporterSchedulePrintMethod );
		assertThat( result.getSupporterDailyDataPrintMethod() ).isEqualTo( supporterDailyDataPrintMethod );
		
	}

}
