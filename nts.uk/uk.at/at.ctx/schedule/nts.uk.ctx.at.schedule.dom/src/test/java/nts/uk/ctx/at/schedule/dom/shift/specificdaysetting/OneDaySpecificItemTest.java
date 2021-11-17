package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.OneDaySpecificItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;

/**
 * 一日の特定日リストのUTコード
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class OneDaySpecificItemTest {

	@Test
	public void getters() {
		
		OneDaySpecificItem target = Helper.createOneDaySpecificItem( Arrays.asList( 1, 2, 3));
		
		NtsAssert.invokeGetters(target);  
	}
	
	/**
	 * target: create
	 * pattern: 特定日項目リスト.size = 0
	 */
	@Test
	public void testCreate_size_0() {
		
		NtsAssert.businessException("Msg_2327", () -> {
			OneDaySpecificItem.create(
					Collections.emptyList()); 
		});
		
	}
	
	/**
	 * target: create
	 * pattern: 特定日項目リスト.size = 11
	 */
	@Test
	public void testCreate_size_11() {
		
		NtsAssert.businessException("Msg_2327", () -> {
			OneDaySpecificItem.create(
					Arrays.asList( 
							new SpecificDateItemNo(1),
							new SpecificDateItemNo(2),
							new SpecificDateItemNo(3),
							new SpecificDateItemNo(4),
							new SpecificDateItemNo(5),
							new SpecificDateItemNo(6),
							new SpecificDateItemNo(7),
							new SpecificDateItemNo(8),
							new SpecificDateItemNo(9),
							new SpecificDateItemNo(10),
							new SpecificDateItemNo(3)
							)
					); 
		});
	}
	
	/**
	 * target: create
	 * pattern: 特定日項目リストが重複, 0 < size <= 10
	 */
	@Test
	public void testCreate_duplicate() {
		
		NtsAssert.businessException("Msg_2328", () -> {
			OneDaySpecificItem.create(
					Arrays.asList( 
							new SpecificDateItemNo(1),
							new SpecificDateItemNo(2),
							new SpecificDateItemNo(3),//重複
							new SpecificDateItemNo(3),//重複
							new SpecificDateItemNo(4),
							new SpecificDateItemNo(5)
							)
					); 
		});
	}
	
	/**
	 * target: create
	 * pattern: 特定日項目リストが重複しない, 0 < size <= 10
	 */
	@Test
	public void testCreate_success() {
		//Act
		val domain = 	OneDaySpecificItem.create(
					Arrays.asList( 
							new SpecificDateItemNo(10),
							new SpecificDateItemNo(9),
							new SpecificDateItemNo(8),
							new SpecificDateItemNo(7),
							new SpecificDateItemNo(6),
							new SpecificDateItemNo(5),
							new SpecificDateItemNo(4),
							new SpecificDateItemNo(3),
							new SpecificDateItemNo(2),
							new SpecificDateItemNo(1)
							)
					); 
		
		//Assert
		assertThat( domain.getSpecificDayItems() )
		.extracting(c -> c.v())
		.containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		
	}
	
	public static class Helper{
		
		public static OneDaySpecificItem createOneDaySpecificItem(List<Integer> itemNo) {
			return new OneDaySpecificItem(
						itemNo.stream()
						.map(item -> new SpecificDateItemNo(item))
						.collect(Collectors.toList())
					);
		}
	}
}
