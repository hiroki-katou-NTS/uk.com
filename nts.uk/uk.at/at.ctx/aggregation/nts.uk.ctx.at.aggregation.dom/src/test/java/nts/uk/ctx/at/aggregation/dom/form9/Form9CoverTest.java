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
				@Injectable OutputCell cellYear
			,	@Injectable OutputCell cellMonth
			,	@Injectable OutputCell cellStartTime
			,	@Injectable OutputCell cellEndTime
			,	@Injectable OutputCell cellTitle
			,	@Injectable OutputCell cellPrintPeriod
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
	 * expect: Msg_2288
	 * 
	 */
	@Test
	public void testCreate_cellYear_duplicate() {
		
		OutputCell cellYear = new OutputCell("A");//重複
		OutputCell cellMonth = new OutputCell("B");
		OutputCell cellStartTime = new OutputCell("A");//重複
		OutputCell cellEndTime = new OutputCell("C");
		OutputCell cellTitle = new OutputCell("D");
		OutputCell cellPrintPeriod = new OutputCell("E");
		
		NtsAssert.businessException("Msg_2288", () ->{
				Form9Cover.create(
						Optional.of( cellYear ), Optional.of( cellMonth )
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 集計月, 出力情報の期間 が重複
	 * expect: Msg_2288
	 * 
	 */
	@Test
	public void testCreate_cellMonth_duplicate() {
		
		OutputCell cellYear = new OutputCell("A");
		OutputCell cellMonth = new OutputCell("B");//重複
		OutputCell cellStartTime = new OutputCell("A");
		OutputCell cellEndTime = new OutputCell("C");
		OutputCell cellTitle = new OutputCell("D");
		OutputCell cellPrintPeriod = new OutputCell("B");//重複
		
		NtsAssert.businessException("Msg_2288", () ->{
				Form9Cover.create(
						Optional.of( cellYear ), Optional.of( cellMonth )
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 開始時刻, 終了時刻 , 出力情報の期間が重複
	 * expect: Msg_2288
	 * 
	 */
	@Test
	public void testCreate_cellStartTime_duplicate() {
		
		OutputCell cellStartTime = new OutputCell("C");//重複
		OutputCell cellEndTime = new OutputCell("C");//重複
		OutputCell cellTitle = new OutputCell("D");
		OutputCell cellPrintPeriod = new OutputCell("C");//重複
		
		NtsAssert.businessException("Msg_2288", () ->{
				Form9Cover.create(
						Optional.empty(), Optional.empty()
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 開始時刻, 終了時刻 , 出力情報の期間が重複
	 * expect: Msg_2288
	 * 
	 */
	@Test
	public void testCreate_cellEndTime_duplicate() {
		
		OutputCell cellStartTime = new OutputCell("A");
		OutputCell cellEndTime = new OutputCell("B");//重複
		OutputCell cellTitle = new OutputCell("C");
		OutputCell cellPrintPeriod = new OutputCell("B");//重複
		
		NtsAssert.businessException("Msg_2288", () ->{
				Form9Cover.create(
						Optional.empty(), Optional.empty()
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 出力情報のタイトル, 集計月が重複
	 * expect: Msg_2288
	 * 
	 */
	@Test
	public void testCreate_cellTitle_duplicate() {
		
		OutputCell cellYear = new OutputCell("A");
		OutputCell cellMonth = new OutputCell("E");//重複
		OutputCell cellStartTime = new OutputCell("C");
		OutputCell cellEndTime = new OutputCell("D");
		OutputCell cellTitle = new OutputCell("E");//重複
		OutputCell cellPrintPeriod = new OutputCell("F");
		
		NtsAssert.businessException("Msg_2288", () ->{
				Form9Cover.create(
						Optional.of( cellYear ), Optional.of( cellMonth )
					,	Optional.of( cellStartTime ), Optional.of( cellEndTime )
					,	Optional.of( cellTitle ), Optional.of( cellPrintPeriod ));
		});
	}
	
	/**
	 * target: create
	 * patterns: 出力情報の期間, 集計月が重複
	 * expect: Msg_2288
	 * 
	 */
	@Test
	public void testCreate_cellPrintPeriod_duplicate() {
		
		OutputCell cellYear = new OutputCell("A");
		OutputCell cellMonth = new OutputCell("B");
		OutputCell cellStartTime = new OutputCell("F");//重複
		OutputCell cellEndTime = new OutputCell("C");
		OutputCell cellTitle = new OutputCell("D");
		OutputCell cellPrintPeriod = new OutputCell("F");//重複
		
		NtsAssert.businessException("Msg_2288", () ->{
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
		
		OutputCell cellYear = new OutputCell("A");
		OutputCell cellMonth = new OutputCell("B");
		OutputCell cellStartTime = new OutputCell("C");
		OutputCell cellEndTime = new OutputCell("D");
		OutputCell cellTitle = new OutputCell("E");
		OutputCell cellPrintPeriod = new OutputCell("F");
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
