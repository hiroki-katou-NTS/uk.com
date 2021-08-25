package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
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
	public void testCreate_Msg_2083(
				@Injectable List<Integer> workplaceCounter
			,	@Injectable ScheduleDailyTableInkanRow inkanRow) {
		
		List<Integer> personalCounter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
		val itemSetting = Helper.createScheduleDailyTableItemSetting(workplaceCounter, personalCounter, inkanRow);
		
		NtsAssert.businessException("Msg_2083", () -> ScheduleDailyTablePrintSetting
				.create(new ScheduleDailyTableCode("01"), new ScheduleDailyTableName("name"), itemSetting));
	}
	
	@Test
	public void testCreate_Msg_2084(
				@Injectable List<Integer> personalCounter
			,	@Injectable ScheduleDailyTableInkanRow inkanRow) {
		
		List<Integer> workplaceCounter = Arrays.asList(1, 2, 3, 4, 5, 6);
		val itemSetting = Helper.createScheduleDailyTableItemSetting(workplaceCounter, personalCounter, inkanRow);
		
		NtsAssert.businessException("Msg_2084", () -> ScheduleDailyTablePrintSetting
				.create(new ScheduleDailyTableCode("01"), new ScheduleDailyTableName("name"), itemSetting));
	}
	
	@Test
	public void testCreate_Msg_2084(
				@Injectable List<Integer> personalCounter
			,	@Injectable List<Integer> workplaceCounter) {
		val inKanRow = new ScheduleDailyTableInkanRow(
					NotUseAtr.USE
				,	new ArrayList<>(Arrays.asList(new ScheduleDailyTableInkanTitle("a1"),
							new ScheduleDailyTableInkanTitle("a2")
						,	new ScheduleDailyTableInkanTitle("a3")
						,	new ScheduleDailyTableInkanTitle("a4")
						,	new ScheduleDailyTableInkanTitle("a5")
						,	new ScheduleDailyTableInkanTitle("a6")
						,	new ScheduleDailyTableInkanTitle("a7")
						)));
		
		val itemSetting = Helper.createScheduleDailyTableItemSetting(workplaceCounter, personalCounter, inKanRow);
		
		NtsAssert.businessException("Msg_2085", () -> ScheduleDailyTablePrintSetting
				.create(new ScheduleDailyTableCode("01"), new ScheduleDailyTableName("name"), itemSetting));
	}
	
	@Test
	public void testCreate_Msg_Msg_2222(@Injectable List<Integer> personalCounter
			,	@Injectable List<Integer> workplaceCounter) {
		
		val inKanRow = new ScheduleDailyTableInkanRow(NotUseAtr.USE, Collections.emptyList());
		
		val itemSetting = Helper.createScheduleDailyTableItemSetting(workplaceCounter, personalCounter, inKanRow);
		
		NtsAssert.businessException("Msg_2222", () -> ScheduleDailyTablePrintSetting
				.create(new ScheduleDailyTableCode("01"), new ScheduleDailyTableName("name"), itemSetting));
		
	}	
	
	
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
		ScheduleDailyTablePrintSetting result = printSetting.clone(
					destinationCode
				,	distinationName);
		
		// Assert
		assertThat( result.getCode() ).isEqualTo( destinationCode );
		assertThat( result.getName() ).isEqualTo( distinationName );
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
