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
	public void testGetters() {
		
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
	 * target:: 作る
	 * pattern: 見出しリスト.size() = 7
	 * except: Msg_2085
	 */
	@Test
	public void testCreate_inv1() {
		//見出しリスト.size() = 7
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
	 * target: 作る
	 * pattern: 利用区分= する, 見出しリスト = empty
	 * except: Msg_2222
	 */
	@Test
	public void testCreate_inv2() {
		
		NtsAssert.businessException("Msg_2222", 
			() -> ScheduleDailyTableInkanRow.create(NotUseAtr.USE, Collections.emptyList())
		);
	}
	
	/**
	 * target: 作る
	 * pattern: 利用区分= する, 見出しリスト .size = 6
	 * except: create_success
	 */
	@Test
	public void testCreate_success() {
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
	 * target: 印鑑欄見出しを取得する
	 * pattern : 使用区分 = しない
	 * except: empty
	 */
	@Test
	public void testGetInkanRowTitle_Not_Use() {
		
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
	 * target: 印鑑欄見出しを取得する
	 * pattern : 使用区分 = する
	 * except: 見出しリスト
	 */
	@Test
	public void testGetInkanRowTitle_Use() {
		
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
