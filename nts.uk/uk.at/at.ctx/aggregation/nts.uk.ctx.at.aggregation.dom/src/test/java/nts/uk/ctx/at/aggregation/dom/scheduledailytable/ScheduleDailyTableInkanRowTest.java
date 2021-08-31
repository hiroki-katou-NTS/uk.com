package nts.uk.ctx.at.aggregation.dom.scheduledailytable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class ScheduleDailyTableInkanRowTest {	
	
	@Test
	public void getters() {
		
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6")
				);
		
		ScheduleDailyTableInkanRow result = new ScheduleDailyTableInkanRow(NotUseAtr.USE, titleList);
		
		NtsAssert.invokeGetters(result);
	}
	
	/**
	 * method 作る
	 * input: 見出しリスト.size() = 6
	 * output: Msg_2085
	 */
	@Test
	public void create_inv1() {
		//見出しリスト.size() = 6
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6"),
				new ScheduleDailyTableInkanTitle("a7")
				);
		
		NtsAssert.businessException("Msg_2085", 
			() -> ScheduleDailyTableInkanRow.create(NotUseAtr.USE, titleList)
		);
	}
	
	/**
	 * method 作る
	 * input: 利用区分= する, 見出しリスト = empty
	 * output: Msg_2222
	 */
	@Test
	public void create_inv2() {
		
		NtsAssert.businessException("Msg_2222", 
			() -> ScheduleDailyTableInkanRow.create(NotUseAtr.USE, Collections.emptyList())
		);
	}
	
	/**
	 * method 作る
	 * input: 利用区分= する, 見出しリスト .size = 6
	 * output: create_success
	 */
	@Test
	public void create_success() {
		//見出しリスト .size = 6
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6")
				);
		
		ScheduleDailyTableInkanRow result = ScheduleDailyTableInkanRow.create(NotUseAtr.USE, titleList);
		
		assertThat( result.getNotUseAtr() ).isEqualTo( NotUseAtr.USE );
		assertThat( result.getTitleList() )
			.extracting( d -> d.v() )
			.containsExactly( "a1", "a2", "a3", "a4", "a5", "a6");
	}
	
	/**
	 * method 印鑑欄見出しを取得する
	 * input : 使用区分 = しない
	 * output: empty
	 */
	@Test
	public void getInkanRowTitle_Not_Use() {
		
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6")
				);
		
		ScheduleDailyTableInkanRow scheInkanRow = new ScheduleDailyTableInkanRow(NotUseAtr.NOT_USE, titleList);
		
		//actual
		List<ScheduleDailyTableInkanTitle> result = scheInkanRow.getInkanRowTitle();
		
		// Assert
		assertThat( result ).isEmpty();
	}
	
	/**
	 * method 印鑑欄見出しを取得する
	 * input : 使用区分 = する
	 * output: 見出しリスト
	 */
	@Test
	public void getInkanRowTitle_Use() {
		
		List<ScheduleDailyTableInkanTitle> titleList = Arrays.asList(
				new ScheduleDailyTableInkanTitle("a1"),
				new ScheduleDailyTableInkanTitle("a2"),
				new ScheduleDailyTableInkanTitle("a3"),
				new ScheduleDailyTableInkanTitle("a4"),
				new ScheduleDailyTableInkanTitle("a5"),
				new ScheduleDailyTableInkanTitle("a6")
				);
		
		ScheduleDailyTableInkanRow scheInkanRow = new ScheduleDailyTableInkanRow(NotUseAtr.USE, titleList);
		
		// Act
		List<ScheduleDailyTableInkanTitle> result = scheInkanRow.getInkanRowTitle();
		
		// Assert
		assertThat( result )
				.extracting( d -> d.v() )
				.containsExactly( "a1", "a2", "a3", "a4", "a5", "a6" );
	}
	
}
