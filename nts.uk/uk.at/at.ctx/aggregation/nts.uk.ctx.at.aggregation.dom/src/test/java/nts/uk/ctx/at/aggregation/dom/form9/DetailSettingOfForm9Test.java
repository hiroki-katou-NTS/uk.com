package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class DetailSettingOfForm9Test {
	@Test
	public void testGetter() {
		
		val settingForm9 = new DetailSettingOfForm9(
					new OutputRow(12)
				,	new OnePageDisplayNumerOfPeople(1000)
				,	new OutputRow(11)
				,	new OutputRow(12));
		
		NtsAssert.invokeGetters( settingForm9 );
		
	}

	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2289
	 * 
	 */
	@Test
	public void testCreate_bodyStartRow_duplicate() {
		
		val bodyStartRow = new OutputRow( 5 );//重複
		val maxNumerOfPeople = new OnePageDisplayNumerOfPeople( 1000 );
		val rowDate = new OutputRow( 5 );//重複
		val rowDayOfWeek = new OutputRow( 6 );
		
		NtsAssert.businessException("Msg_2289", () ->{
			DetailSettingOfForm9.create( bodyStartRow, maxNumerOfPeople, rowDate, rowDayOfWeek );
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2289
	 * 
	 */
	@Test
	public void testCreate_rowDate_duplicate() {
		
		val bodyStartRow = new OutputRow( 5 );
		val maxNumerOfPeople = new OnePageDisplayNumerOfPeople( 1000 );
		val rowDate = new OutputRow( 10 );//重複
		val rowDayOfWeek = new OutputRow( 10 );//重複
		
		NtsAssert.businessException("Msg_2289", () ->{
			DetailSettingOfForm9.create( bodyStartRow, maxNumerOfPeople, rowDate, rowDayOfWeek );
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2289
	 * 
	 */
	@Test
	public void testCreate_rowDayOfWeek_duplicate() {
		
		val bodyStartRow = new OutputRow( 10 );//重複
		val maxNumerOfPeople = new OnePageDisplayNumerOfPeople( 1000 );
		val rowDate = new OutputRow( 9 );
		val rowDayOfWeek = new OutputRow( 10 );//重複
		
		NtsAssert.businessException("Msg_2289", () ->{
			DetailSettingOfForm9.create( bodyStartRow, maxNumerOfPeople, rowDate, rowDayOfWeek );
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複しない
	 * expect: success
	 * 
	 */
	@Test
	public void testCreate_success() {
		
		val bodyStartRow = new OutputRow( 10 );
		val maxNumerOfPeople = new OnePageDisplayNumerOfPeople( 1000 );
		val rowDate = new OutputRow( 9 );
		val rowDayOfWeek = new OutputRow( 11 );
		
		//Act
		val detailSetting = DetailSettingOfForm9.create( bodyStartRow, maxNumerOfPeople, rowDate, rowDayOfWeek );
		
		//Assert
		assertThat( detailSetting.getBodyStartRow() ).isEqualTo( bodyStartRow );
		assertThat( detailSetting.getMaxNumerOfPeople() ).isEqualTo( maxNumerOfPeople );
		assertThat( detailSetting.getRowDate() ).isEqualTo( rowDate );
		assertThat( detailSetting.getRowDayOfWeek() ).isEqualTo( rowDayOfWeek );
		
	}
	
}
