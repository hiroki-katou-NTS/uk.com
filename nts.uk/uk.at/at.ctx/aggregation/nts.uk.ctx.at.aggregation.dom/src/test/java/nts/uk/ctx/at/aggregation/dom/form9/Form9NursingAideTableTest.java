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
public class Form9NursingAideTableTest {
	
	@Test
	public void testGetter(
				@Injectable DetailSettingOfForm9 detailSetting
			,	@Injectable OutputColumn fullName
			,	@Injectable OutputColumn day1StartColumn
			,	@Injectable OutputColumn hospitalWardName
			, 	@Injectable OutputColumn fullTime
			,	@Injectable OutputColumn shortTime
			,	@Injectable OutputColumn partTime
			,	@Injectable OutputColumn officeWork
			,	@Injectable OutputColumn concurrentPost
			,	@Injectable OutputColumn nightShiftOnly) {
		
		val nursingAssitantTable = new Form9NursingAideTable(
					fullName, day1StartColumn
				,	detailSetting, Optional.of(hospitalWardName)
				,	Optional.of(fullTime), Optional.of(shortTime)
				,	Optional.of(partTime), Optional.of(officeWork)
				,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		
		NtsAssert.invokeGetters(nursingAssitantTable);
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_hospitalWardName_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");//重複
		OutputColumn day1StartColumn = new OutputColumn("B");
		OutputColumn hospitalWardName = new OutputColumn("A");//重複
		OutputColumn fullTime = new OutputColumn("C");
		OutputColumn shortTime = new OutputColumn("D");
		OutputColumn partTime = new OutputColumn("E");
		OutputColumn officeWork = new OutputColumn("F");
		OutputColumn concurrentPost = new OutputColumn("G");
		OutputColumn nightShiftOnly = new OutputColumn("H");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingAideTable.create(
						fullName, day1StartColumn
					,	detailSetting, Optional.of(hospitalWardName)
					,	Optional.of(fullTime), Optional.of(shortTime)
					,	Optional.of(partTime), Optional.of(officeWork)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_fullTime_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn day1StartColumn = new OutputColumn("B");
		OutputColumn hospitalWardName = new OutputColumn("G");//重複
		OutputColumn fullTime = new OutputColumn("C");
		OutputColumn shortTime = new OutputColumn("D");
		OutputColumn partTime = new OutputColumn("E");
		OutputColumn officeWork = new OutputColumn("F");
		OutputColumn concurrentPost = new OutputColumn("G");//重複
		OutputColumn nightShiftOnly = new OutputColumn("H");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingAideTable.create(
						fullName, day1StartColumn
					,	detailSetting, Optional.of(hospitalWardName)
					,	Optional.of(fullTime), Optional.of(shortTime)
					,	Optional.of(partTime), Optional.of(officeWork)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_shortTime_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn day1StartColumn = new OutputColumn("B");
		OutputColumn hospitalWardName = new OutputColumn("C");
		OutputColumn fullTime = new OutputColumn("D");
		OutputColumn shortTime = new OutputColumn("H");//重複
		OutputColumn partTime = new OutputColumn("E");
		OutputColumn officeWork = new OutputColumn("F");
		OutputColumn concurrentPost = new OutputColumn("G");
		OutputColumn nightShiftOnly = new OutputColumn("H");//重複
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingAideTable.create(
						fullName, day1StartColumn
					,	detailSetting, Optional.of(hospitalWardName)
					,	Optional.of(fullTime), Optional.of(shortTime)
					,	Optional.of(partTime), Optional.of(officeWork)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_partTime_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn day1StartColumn = new OutputColumn("B");
		OutputColumn hospitalWardName = new OutputColumn("C");
		OutputColumn fullTime = new OutputColumn("D");
		OutputColumn shortTime = new OutputColumn("E");//重複
		OutputColumn partTime = new OutputColumn("E");//重複
		OutputColumn officeWork = new OutputColumn("F");
		OutputColumn concurrentPost = new OutputColumn("G");
		OutputColumn nightShiftOnly = new OutputColumn("H");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingAideTable.create(fullName, day1StartColumn
					,	detailSetting, Optional.of(hospitalWardName)
					,	Optional.of(fullTime), Optional.of(shortTime)
					,	Optional.of(partTime), Optional.of(officeWork)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_officeWork_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn day1StartColumn = new OutputColumn("B");
		OutputColumn hospitalWardName = new OutputColumn("G");//重複
		OutputColumn fullTime = new OutputColumn("D");
		OutputColumn shortTime = new OutputColumn("E");
		OutputColumn partTime = new OutputColumn("F");
		OutputColumn officeWork = new OutputColumn("G");//重複
		OutputColumn concurrentPost = new OutputColumn("H");
		OutputColumn nightShiftOnly = new OutputColumn("I");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingAideTable.create(fullName, day1StartColumn
					,	detailSetting, Optional.of(hospitalWardName)
					,	Optional.of(fullTime), Optional.of(shortTime)
					,	Optional.of(partTime), Optional.of(officeWork)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_concurrentPost_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn day1StartColumn = new OutputColumn("H");//重複
		OutputColumn hospitalWardName = new OutputColumn("C");
		OutputColumn fullTime = new OutputColumn("D");
		OutputColumn shortTime = new OutputColumn("E");
		OutputColumn partTime = new OutputColumn("F");
		OutputColumn officeWork = new OutputColumn("G");
		OutputColumn concurrentPost = new OutputColumn("H");//重複
		OutputColumn nightShiftOnly = new OutputColumn("I");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingAideTable.create(fullName, day1StartColumn
					,	detailSetting, Optional.of(hospitalWardName)
					,	Optional.of(fullTime), Optional.of(shortTime)
					,	Optional.of(partTime), Optional.of(officeWork)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_nightShiftOnly_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");//重複
		OutputColumn day1StartColumn = new OutputColumn("B");
		OutputColumn hospitalWardName = new OutputColumn("C");
		OutputColumn fullTime = new OutputColumn("D");
		OutputColumn shortTime = new OutputColumn("E");
		OutputColumn partTime = new OutputColumn("F");
		OutputColumn officeWork = new OutputColumn("G");
		OutputColumn concurrentPost = new OutputColumn("H");
		OutputColumn nightShiftOnly = new OutputColumn("A");//重複
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingAideTable.create(fullName, day1StartColumn
					,	detailSetting, Optional.of(hospitalWardName)
					,	Optional.of(fullTime), Optional.of(shortTime)
					,	Optional.of(partTime), Optional.of(officeWork)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複しないこと
	 * expect: create success
	 * 
	 */
	@Test
	public void testCreate_success(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn day1StartColumn = new OutputColumn("B");
		OutputColumn hospitalWardName = new OutputColumn("C");
		OutputColumn fullTime = new OutputColumn("D");
		OutputColumn shortTime = new OutputColumn("E");
		OutputColumn partTime = new OutputColumn("F");
		OutputColumn officeWork = new OutputColumn("G");
		OutputColumn concurrentPost = new OutputColumn("H");
		OutputColumn nightShiftOnly = new OutputColumn("I");
		
		val result = Form9NursingAideTable.create(fullName, day1StartColumn
				,	detailSetting, Optional.of(hospitalWardName)
				,	Optional.of(fullTime), Optional.of(shortTime)
				,	Optional.of(partTime), Optional.of(officeWork)
				,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		
		assertThat( result.getFullName() ).isEqualTo( fullName );
		assertThat( result.getDay1StartColumn() ).isEqualTo( day1StartColumn );
		assertThat( result.getDetailSetting() ).isEqualTo( detailSetting );
		assertThat( result.getFullTime().get() ).isEqualTo( fullTime );
		assertThat( result.getShortTime().get() ).isEqualTo( shortTime );
		assertThat( result.getPartTime().get() ).isEqualTo( partTime );
		assertThat( result.getOfficeWork().get() ).isEqualTo( officeWork );
		assertThat( result.getConcurrentPost().get() ).isEqualTo( concurrentPost );
		assertThat( result.getNightShiftOnly().get() ).isEqualTo( nightShiftOnly );
		
	}
	
}
