package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class Form9CoverTest {
	
	@Test
	public void testGetter(
				@Injectable OutputColumn cellYear
			,	@Injectable OutputColumn cellMonth
			,	@Injectable OutputColumn cellStartTime
			,	@Injectable OutputColumn cellEndTime
			,	@Injectable OutputColumn cellTitle
			,	@Injectable OutputColumn cellPrintPeriod
				){
		
		val cover = new Form9Cover(
					Optional.of(cellYear)
				,	Optional.of(cellMonth)
				,	Optional.of(cellStartTime)
				,	Optional.of(cellEndTime)
				,	Optional.of(cellTitle)
				,	Optional.of(cellPrintPeriod));
		
		NtsAssert.invokeGetters(cover);
		
	}
	
	/**
	 * target: create
	 * patterns: 集計年, 開始時刻が重複
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_cellYear_duplicate() {
		
		OutputColumn cellYear = new OutputColumn("A");//重複
		OutputColumn cellMonth = new OutputColumn("B");
		OutputColumn cellStartTime = new OutputColumn("A");//重複
		OutputColumn cellEndTime = new OutputColumn("C");
		OutputColumn cellTitle = new OutputColumn("D");
		OutputColumn cellPrintPeriod = new OutputColumn("E");
		
		NtsAssert.businessException("Msg_2244", () ->{
				Form9Cover.create(
						Optional.of( cellYear ), Optional.of( cellMonth )
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 集計月, 出力情報の期間 が重複
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_cellMonth_duplicate() {
		
		OutputColumn cellYear = new OutputColumn("A");
		OutputColumn cellMonth = new OutputColumn("B");//重複
		OutputColumn cellStartTime = new OutputColumn("A");
		OutputColumn cellEndTime = new OutputColumn("C");
		OutputColumn cellTitle = new OutputColumn("D");
		OutputColumn cellPrintPeriod = new OutputColumn("B");//重複
		
		NtsAssert.businessException("Msg_2244", () ->{
				Form9Cover.create(
						Optional.of( cellYear ), Optional.of( cellMonth )
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 開始時刻, 終了時刻 , 出力情報の期間が重複
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_cellStartTime_duplicate() {
		
		OutputColumn cellStartTime = new OutputColumn("C");//重複
		OutputColumn cellEndTime = new OutputColumn("C");//重複
		OutputColumn cellTitle = new OutputColumn("D");
		OutputColumn cellPrintPeriod = new OutputColumn("C");//重複
		
		NtsAssert.businessException("Msg_2244", () ->{
				Form9Cover.create(
						Optional.empty(), Optional.empty()
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 開始時刻, 終了時刻 , 出力情報の期間が重複
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_cellEndTime_duplicate() {
		
		OutputColumn cellStartTime = new OutputColumn("A");//重複
		OutputColumn cellEndTime = new OutputColumn("B");
		OutputColumn cellTitle = new OutputColumn("C");
		OutputColumn cellPrintPeriod = new OutputColumn("B");//重複
		
		NtsAssert.businessException("Msg_2244", () ->{
				Form9Cover.create(
						Optional.empty(), Optional.empty()
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 出力情報のタイトル, 集計月が重複
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_cellTitle_duplicate() {
		
		OutputColumn cellYear = new OutputColumn("A");
		OutputColumn cellMonth = new OutputColumn("E");//重複
		OutputColumn cellStartTime = new OutputColumn("C");
		OutputColumn cellEndTime = new OutputColumn("D");
		OutputColumn cellTitle = new OutputColumn("E");//重複
		OutputColumn cellPrintPeriod = new OutputColumn("F");
		
		NtsAssert.businessException("Msg_2244", () ->{
				Form9Cover.create(
						Optional.of( cellYear ), Optional.of( cellMonth )
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 出力情報の期間, 集計月が重複
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_cellPrintPeriod_duplicate() {
		
		OutputColumn cellYear = new OutputColumn("A");
		OutputColumn cellMonth = new OutputColumn("B");//重複
		OutputColumn cellStartTime = new OutputColumn("F");
		OutputColumn cellEndTime = new OutputColumn("C");
		OutputColumn cellTitle = new OutputColumn("D");
		OutputColumn cellPrintPeriod = new OutputColumn("F");//重複
		
		NtsAssert.businessException("Msg_2244", () ->{
				Form9Cover.create(
						Optional.of( cellYear ), Optional.of( cellMonth )
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 全部重複しない
	 * expect: success
	 */
	@Test
	public void testCreate_success() {
		
		OutputColumn cellYear = new OutputColumn("A");
		OutputColumn cellMonth = new OutputColumn("B");
		OutputColumn cellStartTime = new OutputColumn("C");
		OutputColumn cellEndTime = new OutputColumn("D");
		OutputColumn cellTitle = new OutputColumn("E");
		OutputColumn cellPrintPeriod = new OutputColumn("F");
		val cover = Form9Cover.create(
						Optional.of( cellYear ), Optional.of( cellMonth )
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		
		assertThat( cover.getCellYear().get() ).isEqualTo( cellYear );
		assertThat( cover.getCellMonth().get() ).isEqualTo( cellMonth );
		assertThat( cover.getCellStartTime().get() ).isEqualTo( cellStartTime );
		assertThat( cover.getCellEndTime().get() ).isEqualTo( cellEndTime );
		assertThat( cover.getCellTitle().get() ).isEqualTo( cellTitle );
		assertThat( cover.getCellPrintPeriod().get() ).isEqualTo( cellPrintPeriod );
	}
	
	/**
	 * target: create
	 * patterns: 全部empty
	 * expect: success
	 */
	@Test
	public void testCreate_success_empty() {
		
		val cover = Form9Cover.create(
						Optional.empty(), Optional.empty()
					,	Optional.empty(), Optional.empty()
					,	Optional.empty(), Optional.empty());
		
		assertThat( cover.getCellYear() ).isEmpty();
		assertThat( cover.getCellMonth() ).isEmpty();
		assertThat( cover.getCellStartTime() ).isEmpty();
		assertThat( cover.getCellEndTime() ).isEmpty();
		assertThat( cover.getCellTitle() ).isEmpty();
		assertThat( cover.getCellPrintPeriod() ).isEmpty();
		
	}

}
