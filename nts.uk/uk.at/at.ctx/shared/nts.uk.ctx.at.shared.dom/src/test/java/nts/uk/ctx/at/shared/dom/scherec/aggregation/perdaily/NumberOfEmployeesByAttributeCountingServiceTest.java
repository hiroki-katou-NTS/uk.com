package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance.Require;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * Test for NumberOfEmployeesByAttributeCountingService
 * @author kumiko_otake
 */
public class NumberOfEmployeesByAttributeCountingServiceTest {

	@Injectable NumberOfEmployeesByAttributeCountingService.Require require;


	/**
	 * Target	: count
	 */
	@Test
	public void test_count() {

		// Mock: 日別勤怠の勤務情報
		new MockUp<WorkInfoOfDailyAttendance>() {
			@Mock
			public boolean isAttendanceRate(Require require, String companyId) {
				return ( this.getMockInstance().getRecordInfo() == Helper.WorkInfo.WORKING );
			}
		};


		// 値リスト
		val values = Arrays.asList(
					new AttributeToBeCounted<>( "EMPCD#04", true )
				,	new AttributeToBeCounted<>( "EMPCD#01", true )
				,	new AttributeToBeCounted<>( "EMPCD#02", false )
				,	new AttributeToBeCounted<>( "EMPCD#01", true )
				,	new AttributeToBeCounted<>( "EMPCD#03", false )
				,	new AttributeToBeCounted<>( "EMPCD#03", false )
				,	new AttributeToBeCounted<>( "EMPCD#02", true )
				,	new AttributeToBeCounted<>( "EMPCD#05", false )
				,	new AttributeToBeCounted<>( "EMPCD#03", false )
				,	new AttributeToBeCounted<>( "EMPCD#05", true )
				,	new AttributeToBeCounted<>( "EMPCD#02", true )
				,	new AttributeToBeCounted<>( "EMPCD#02", true )
			).stream()
				.map( e -> {
					return new WorkInfoWithAffiliationInfo(
									Helper.AffInfo.fromEmpCd(new EmploymentCode( e.getAttribute() ))
								,	Helper.WorkInfo.create( e.isIncluded() )
							);
				} ).collect(Collectors.toList());


		// Execute
		val result = NumberOfEmployeesByAttributeCountingService
						.count(require, "cid", AggregationUnitOfEmployeeAttribute.EMPLOYMENT, values);


		// Assertion
		assertThat( result.entrySet() )
				.extracting( Map.Entry::getKey, Map.Entry::getValue )
				.containsExactlyInAnyOrder(
						tuple( "EMPCD#01", BigDecimal.valueOf( 2 ) )
					,	tuple( "EMPCD#02", BigDecimal.valueOf( 3 ) )
					,	tuple( "EMPCD#03", BigDecimal.valueOf( 0 ) )
					,	tuple( "EMPCD#04", BigDecimal.valueOf( 1 ) )
					,	tuple( "EMPCD#05", BigDecimal.valueOf( 1 ) )
				);

	}


	protected static class Helper {

		protected static class WorkInfo {

			/** 1日出勤用勤務情報 **/
			public static final WorkInformation WORKING = new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("001"));
			/** 1日休日用勤務情報 **/
			public static final WorkInformation HOLIDAY = new WorkInformation(new WorkTypeCode("101"), null);

			/**
			 * 出勤するかどうかを指定して作成
			 * @param isWorking 出勤するか
			 * @return 日別勤怠の勤務情報
			 */
			public static WorkInfoOfDailyAttendance create(boolean isWorking) {

				return new WorkInfoOfDailyAttendance(
						isWorking ? WorkInfo.WORKING : WorkInfo.HOLIDAY	// 勤務情報
					,	CalculationState.No_Calculated	// 計算状態
					,	NotUseAttribute.Not_use			// 直行区分
					,	NotUseAttribute.Not_use			// 直帰区分
					,	DayOfWeek.WEDNESDAY				// 曜日
					,	Collections.emptyList()			// 勤務予定時間帯
					,	Optional.empty()				// 振休振出として扱う日数
				);

			}

		}

		protected static class AffInfo {

			/** テンプレート **/
			private static final AffiliationInforOfDailyAttd template = new AffiliationInforOfDailyAttd(
							new EmploymentCode("EMPCD")			// 雇用コード
						,	"JOBTITLEID"						// 職位ID
						,	"WORKPLACDID"						// 職場ID
						,	new ClassificationCode("CLASSCD")	// 分類コード
						,	Optional.empty()					// 勤務種別コード
						,	Optional.empty()					// 加給コード
						,	Optional.empty()					// 職場グループID
						,	Optional.empty()					// 免許区分
						,	Optional.empty()					// 看護管理者か
					);

			/**
			 * 雇用コードから作成
			 * @param empCd 雇用コード
			 * @return 日別勤怠の所属情報
			 */
			public static AffiliationInforOfDailyAttd fromEmpCd(EmploymentCode empCd) {
				return new AffiliationInforOfDailyAttd(
								empCd								// 雇用コード
							,	template.getJobTitleID()			// 職位ID
							,	template.getWplID()					// 職場ID
							,	template.getClsCode()				// 分類コード
							,	template.getBusinessTypeCode()		// 勤務種別コード
							,	template.getBonusPaySettingCode()	// 加給コード
							,	Optional.empty()					// 職場グループID
							,	Optional.empty()					// 免許区分
							,	Optional.empty()					// 看護管理者か
						);
			}

			/**
			 * 分類コードから作成
			 * @param clsCds 分類コード
			 * @return 日別勤怠の所属情報
			 */
			public static AffiliationInforOfDailyAttd fromClsCd(ClassificationCode clsCd) {
				return new AffiliationInforOfDailyAttd(
								template.getEmploymentCode()		// 雇用コード
							,	template.getJobTitleID()			// 職位ID
							,	template.getWplID()					// 職場ID
							,	clsCd								// 分類コード
							,	template.getBusinessTypeCode()		// 勤務種別コード
							,	template.getBonusPaySettingCode()	// 加給コード
							,	Optional.empty()					// 職場グループID
							,	Optional.empty()					// 免許区分
							,	Optional.empty()					// 看護管理者か
						);
			}

			/**
			 * 職位IDから作成
			 * @param jobTitleId 職位ID
			 * @return 日別勤怠の所属情報
			 */
			public static AffiliationInforOfDailyAttd fromJobTitleId(String jobTitleId) {
				return new AffiliationInforOfDailyAttd(
								template.getEmploymentCode()		// 雇用コード
							,	jobTitleId							// 職位ID
							,	template.getWplID()					// 職場ID
							,	template.getClsCode()				// 分類コード
							,	template.getBusinessTypeCode()		// 勤務種別コード
							,	template.getBonusPaySettingCode()	// 加給コード
							,	Optional.empty()					// 職場グループID
							,	Optional.empty()					// 免許区分
							,	Optional.empty()					// 看護管理者か
						);
			}

		}

	}


}
