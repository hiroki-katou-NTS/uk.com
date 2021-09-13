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
public class Form9NursingStaffTableTest {

	@Test
	public void testGetter(
				@Injectable OutputColumn fullName
			,	@Injectable OutputColumn startColumn
			,	@Injectable DetailSettingOfForm9 detailSetting
			,	@Injectable OutputColumn license
			,	@Injectable OutputColumn hospitalWardName
			, 	@Injectable OutputColumn fullTime
			,	@Injectable OutputColumn shortTime
			,	@Injectable OutputColumn partTime
			,	@Injectable OutputColumn concurrentPost
			,	@Injectable OutputColumn nightShiftOnly) {
		
		val result = new Form9NursingStaffTable(fullName, startColumn
				,	detailSetting, Optional.of(license)
				,	Optional.of(hospitalWardName), Optional.of(fullTime)
				,	Optional.of(shortTime), Optional.of(partTime)
				,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		
		NtsAssert.invokeGetters(result);
		
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
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn startColumn = new OutputColumn("B");
		OutputColumn license = new OutputColumn("F");
		OutputColumn hospitalWardName = new OutputColumn("C");//重複
		OutputColumn fullTime = new OutputColumn("C");//重複
		OutputColumn shortTime = new OutputColumn("D");
		OutputColumn partTime = new OutputColumn("E");
		OutputColumn concurrentPost = new OutputColumn("G");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingStaffTable.create(
						fullName, startColumn
					,	detailSetting, Optional.of(license)
					,	Optional.of(hospitalWardName), Optional.of(fullTime)
					,	Optional.of(shortTime), Optional.of(partTime)
					,	Optional.of(concurrentPost), Optional.empty());
		}); 
	}
	/**
	 * target: create
	 * patterns: 重複すること
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn startColumn = new OutputColumn("A");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingStaffTable.create(fullName, startColumn
					,	detailSetting, Optional.empty()
					,	Optional.empty(), Optional.empty()
					,	Optional.empty(), Optional.empty()
					,	Optional.empty(), Optional.empty());
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
		OutputColumn startColumn = new OutputColumn("B");
		OutputColumn license = new OutputColumn("C");
		OutputColumn hospitalWardName = new OutputColumn("D");
		OutputColumn fullTime = new OutputColumn("E");//重複
		OutputColumn shortTime = new OutputColumn("D");
		OutputColumn partTime = new OutputColumn("E");//重複
		OutputColumn concurrentPost = new OutputColumn("G");
		OutputColumn nightShiftOnly = new OutputColumn("H");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingStaffTable.create(
						fullName, startColumn
					,	detailSetting, Optional.of(license)
					,	Optional.of(hospitalWardName), Optional.of(fullTime)
					,	Optional.of(shortTime), Optional.of(partTime)
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
	public void testCreate_license_duplicate(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");//重複
		OutputColumn startColumn = new OutputColumn("B");
		OutputColumn license = new OutputColumn("F");
		OutputColumn hospitalWardName = new OutputColumn("A");//重複
		OutputColumn fullTime = new OutputColumn("C");
		OutputColumn shortTime = new OutputColumn("D");
		OutputColumn partTime = new OutputColumn("E");
		OutputColumn concurrentPost = new OutputColumn("G");
		OutputColumn nightShiftOnly = new OutputColumn("H");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingStaffTable.create(
						fullName, startColumn
					,	detailSetting, Optional.of(license)
					,	Optional.of(hospitalWardName), Optional.of(fullTime)
					,	Optional.of(shortTime), Optional.of(partTime)
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
		OutputColumn startColumn = new OutputColumn("B");
		OutputColumn license = new OutputColumn("C");
		OutputColumn hospitalWardName = new OutputColumn("H");//重複
		OutputColumn fullTime = new OutputColumn("C");
		OutputColumn shortTime = new OutputColumn("D");
		OutputColumn partTime = new OutputColumn("E");
		OutputColumn concurrentPost = new OutputColumn("F");
		OutputColumn nightShiftOnly = new OutputColumn("H");//重複
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingStaffTable.create(
						fullName, startColumn
					,	detailSetting, Optional.of(license)
					,	Optional.of(hospitalWardName), Optional.of(fullTime)
					,	Optional.of(shortTime), Optional.of(partTime)
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
		
		OutputColumn fullName = new OutputColumn("E");//重複
		OutputColumn startColumn = new OutputColumn("B");
		OutputColumn license = new OutputColumn("C");
		OutputColumn hospitalWardName = new OutputColumn("D");
		OutputColumn fullTime = new OutputColumn("F");
		OutputColumn shortTime = new OutputColumn("K");
		OutputColumn partTime = new OutputColumn("E");//重複
		OutputColumn concurrentPost = new OutputColumn("L");
		OutputColumn nightShiftOnly = new OutputColumn("M");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingStaffTable.create(
						fullName, startColumn
					,	detailSetting, Optional.of(license)
					,	Optional.of(hospitalWardName), Optional.of(fullTime)
					,	Optional.of(shortTime), Optional.of(partTime)
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
		OutputColumn startColumn = new OutputColumn("B");
		OutputColumn license = new OutputColumn("L");//重複
		OutputColumn hospitalWardName = new OutputColumn("D");
		OutputColumn fullTime = new OutputColumn("E");
		OutputColumn shortTime = new OutputColumn("K");
		OutputColumn partTime = new OutputColumn("E");//重複
		OutputColumn concurrentPost = new OutputColumn("L");
		OutputColumn nightShiftOnly = new OutputColumn("M");
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingStaffTable.create(
						fullName, startColumn
					,	detailSetting, Optional.of(license)
					,	Optional.of(hospitalWardName), Optional.of(fullTime)
					,	Optional.of(shortTime), Optional.of(partTime)
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
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn startColumn = new OutputColumn("L");//重複
		OutputColumn license = new OutputColumn("C");
		OutputColumn hospitalWardName = new OutputColumn("D");
		OutputColumn fullTime = new OutputColumn("E");
		OutputColumn shortTime = new OutputColumn("F");
		OutputColumn partTime = new OutputColumn("G");
		OutputColumn concurrentPost = new OutputColumn("L");
		OutputColumn nightShiftOnly = new OutputColumn("M");//重複
		
		NtsAssert.businessException("Msg_2244", () ->{
			Form9NursingStaffTable.create(
						fullName, startColumn
					,	detailSetting, Optional.of(license)
					,	Optional.of(hospitalWardName), Optional.of(fullTime)
					,	Optional.of(shortTime), Optional.of(partTime)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		}); 
	}
	
	/**
	 * target: create
	 * patterns: 重複しないこと
	 * expect: Msg_2244
	 * 
	 */
	@Test
	public void testCreate_success(
			@Injectable DetailSettingOfForm9 detailSetting) {
		
		OutputColumn fullName = new OutputColumn("A");
		OutputColumn startColumn = new OutputColumn("B");
		OutputColumn license = new OutputColumn("C");
		OutputColumn hospitalWardName = new OutputColumn("D");
		OutputColumn fullTime = new OutputColumn("E");
		OutputColumn shortTime = new OutputColumn("F");
		OutputColumn partTime = new OutputColumn("G");
		OutputColumn concurrentPost = new OutputColumn("H");
		OutputColumn nightShiftOnly = new OutputColumn("K");
		
		val result = Form9NursingStaffTable.create(
						fullName, startColumn
					,	detailSetting, Optional.of(license)
					,	Optional.of(hospitalWardName), Optional.of(fullTime)
					,	Optional.of(shortTime), Optional.of(partTime)
					,	Optional.of(concurrentPost), Optional.of(nightShiftOnly));
		
		assertThat( result.getFullName() ).isEqualTo( fullName );
		assertThat( result.getStartColumn() ).isEqualTo( startColumn );
		assertThat( result.getDetailSetting() ).isEqualTo( detailSetting );
		assertThat( result.getLicense().get() ).isEqualTo( license );
		assertThat( result.getFullTime().get() ).isEqualTo( fullTime );
		assertThat( result.getShortTime().get() ).isEqualTo( shortTime );
		assertThat( result.getPartTime().get() ).isEqualTo( partTime );
		assertThat( result.getConcurrentPost().get() ).isEqualTo( concurrentPost );
		assertThat( result.getNightShiftOnly().get() ).isEqualTo( nightShiftOnly );
	}
	
}
