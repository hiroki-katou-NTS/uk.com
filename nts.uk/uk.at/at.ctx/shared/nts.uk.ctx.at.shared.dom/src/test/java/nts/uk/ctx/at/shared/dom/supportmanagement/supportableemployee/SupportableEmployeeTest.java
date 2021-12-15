package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Test for SupportableEmployee
 * @author kumiko_otake
 */
public class SupportableEmployeeTest {

	/**
	 * Target	: All getter methods
	 */
	@Test
	public void test_getters() {

		NtsAssert.invokeGetters(SupportableEmployee.createAsAllday(
				new EmployeeId("dummy-emp-id")
			,	TargetOrgIdenInfor.creatIdentifiWorkplace("dummy-wkp-id")
			,	DatePeriod.oneDay(GeneralDate.today())
		));

	}


	/**
	 * Target	: createAsAllday
	 */
	@Test
	public void test_createAsAllday() {

		val employeeId = new EmployeeId( "allday-employee-id" );
		val recipient = TargetOrgIdenInfor.creatIdentifiWorkplace("allday-workplace-id");
		val period = DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 13 ) );

		// 実行
		val result = SupportableEmployee.createAsAllday(
						employeeId
					,	recipient
					,	period
				);

		// 検証
		assertThat( result.getId() ).isNotBlank();
		assertThat( result.getSupportType() ).isEqualTo( SupportType.ALLDAY );
		assertThat( result.getTimespan() ).isEmpty();

		assertThat( result ).extracting(
				SupportableEmployee::getEmployeeId
			,	SupportableEmployee::getRecipient
			,	SupportableEmployee::getPeriod
		).containsExactly(
				employeeId
			,	recipient
			,	period
		);

	}


	/**
	 * Target	: createAsTimezone
	 */
	@Test
	public void test_createAsTimezone() {

		val employeeId = new EmployeeId( "timezone-employee-id" );
		val recipient = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("timezone-workplace-group-id");
		val ymd = GeneralDate.ymd( 2021, 10, 31 );
		val timespan = Helper.createTimespan( 15, 30 , 18, 15 );

		// 実行
		val result = SupportableEmployee.createAsTimezone(
						employeeId
					,	recipient
					,	ymd
					,	timespan
				);

		// 検証
		assertThat( result.getId() ).isNotBlank();
		assertThat( result.getSupportType() ).isEqualTo( SupportType.TIMEZONE );
		assertThat( result.getTimespan() ).isPresent();

		assertThat( result ).extracting(
				SupportableEmployee::getEmployeeId
			,	SupportableEmployee::getRecipient
			,	SupportableEmployee::getPeriod
			,	SupportableEmployee::getTimespan
		).containsExactly(
				employeeId
			,	recipient
			,	DatePeriod.oneDay( ymd )
			,	Optional.of( timespan )
		);

	}


	/**
	 * Target	: changePeriod
	 */
	@Test
	public void test_changePeriod() {

		/** 応援形式: 終日応援 **/
		{
			val before = DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 13 ) );
			val after = DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 31 ) );

			val instance = SupportableEmployee.createAsAllday(
						new EmployeeId( "empId" )
					,	TargetOrgIdenInfor.creatIdentifiWorkplace("wkpId")
					,	before
				);

			// 実行
			val result = instance.changePeriod( after );

			// 検証
			assertThat( result ).extracting(
						SupportableEmployee::getId
					,	SupportableEmployee::getEmployeeId
					,	SupportableEmployee::getRecipient
					,	SupportableEmployee::getSupportType
					,	SupportableEmployee::getTimespan
				).containsExactly(
						instance.getId()
					,	instance.getEmployeeId()
					,	instance.getRecipient()
					,	instance.getSupportType()
					,	instance.getTimespan()
				);

			assertThat( result.getPeriod() ).isNotEqualTo( instance.getPeriod() );
			assertThat( result.getPeriod() ).isEqualTo( after );
		}


		/** 応援形式: 時間帯応援 **/
		{
			// 検証
			NtsAssert.businessException( "Msg_2313", () -> {
				// 実行
				SupportableEmployee.createAsTimezone(
						new EmployeeId( "empId" )
					,	TargetOrgIdenInfor.creatIdentifiWorkplace("wkpId")
					,	GeneralDate.today()
					,	Helper.createTimespan( 11,  0, 15, 30 )
				).changePeriod( DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 13 ) ) );
			} );
		}

	}


	/**
	 * Target	: changeTimespan
	 */
	@Test
	public void test_changeTimespan() {

		/** 応援形式: 終日応援 **/
		{
			// 検証
			NtsAssert.businessException( "Msg_2314", () -> {
				// 実行
				SupportableEmployee.createAsAllday(
						new EmployeeId( "empId" )
					,	TargetOrgIdenInfor.creatIdentifiWorkplace("wkpId")
					,	DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 13 ) )
				).changeTimespan( Helper.createTimespan( 11,  0, 15, 30 ) );
			} );
		}


		/** 応援形式: 時間帯応援 **/
		{
			val before = Helper.createTimespan( 18,  0, 20, 45 );
			val after = Helper.createTimespan( 16, 50, 19, 25 );

			val instance = SupportableEmployee.createAsTimezone(
					new EmployeeId( "empId" )
				,	TargetOrgIdenInfor.creatIdentifiWorkplace("wkpId")
				,	GeneralDate.today()
				,	before
			);

			// 実行
			val result = instance.changeTimespan( after );

			// 検証
			assertThat( result ).extracting(
						SupportableEmployee::getId
					,	SupportableEmployee::getEmployeeId
					,	SupportableEmployee::getRecipient
					,	SupportableEmployee::getSupportType
					,	SupportableEmployee::getPeriod
				).containsExactly(
						instance.getId()
					,	instance.getEmployeeId()
					,	instance.getRecipient()
					,	instance.getSupportType()
					,	instance.getPeriod()
				);

			assertThat( result.getTimespan().get() ).isNotEqualTo( instance.getTimespan() );
			assertThat( result.getTimespan().get() ).isEqualTo( after );
		}

	}


	/**
	 * Target	: createTicket
	 * Pattern	: SupportType=ALLDAY
	 */
	@Test
	public void test_createTicket_allday() {

		val instance = SupportableEmployee.createAsAllday(
				new EmployeeId( "empId" )
			,	TargetOrgIdenInfor.creatIdentifiWorkplace("wkpId")
			,	DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 13 ) )
		);

		/** 期間外 **/
		{
			// 検証
			assertThat( instance.createTicket( GeneralDate.ymd( 2021, 10, 12 ) ) ).isEmpty();
			assertThat( instance.createTicket( GeneralDate.ymd( 2022, 10, 13 ) ) ).isEmpty();
		}

		/** 期間内 **/
		Stream.of( GeneralDate.ymd( 2021, 10, 13 ), GeneralDate.ymd( 2022, 10, 12 ) ).forEach( ymd -> {

			// 実行
			val result = instance.createTicket(ymd);

			// 検証
			assertThat( result ).isNotEmpty();
			assertThat( result.get() ).extracting(
						SupportTicket::getEmployeeId
					,	SupportTicket::getRecipient
					,	SupportTicket::getSupportType
					,	SupportTicket::getDate
					,	SupportTicket::getTimespan
				).containsExactly(
						instance.getEmployeeId()
					,	instance.getRecipient()
					,	instance.getSupportType()
					,	ymd
					,	instance.getTimespan()
				);

		});

	}

	/**
	 * Target	: createTicket
	 * Pattern	: SupportType=TIMEZONE
	 */
	@Test
	public void test_createTicket_timezone() {

		val date = GeneralDate.ymd( 2021, 10, 31 );

		val instance = SupportableEmployee.createAsTimezone(
				new EmployeeId( "empId" )
			,	TargetOrgIdenInfor.creatIdentifiWorkplace("wkpId")
			,	date
			,	Helper.createTimespan( 21,  0, 26, 30 )
		);

		/** 期間外 **/
		{
			// 検証
			assertThat( instance.createTicket( date.addDays( -1 ) ) ).isEmpty();
			assertThat( instance.createTicket( date.addDays(  1 ) ) ).isEmpty();
		}

		/** 期間内 **/
		{
			// 実行
			val result = instance.createTicket( date );

			// 検証
			assertThat( result ).isNotEmpty();
			assertThat( result.get() ).extracting(
						SupportTicket::getEmployeeId
					,	SupportTicket::getRecipient
					,	SupportTicket::getSupportType
					,	SupportTicket::getDate
					,	SupportTicket::getTimespan
				).containsExactly(
						instance.getEmployeeId()
					,	instance.getRecipient()
					,	instance.getSupportType()
					,	date
					,	instance.getTimespan()
				);
		}

	}


	/**
	 * Target	: toTicket
	 */
	@Test
	public void test_toTickets() {

		val instance = SupportableEmployee.createAsAllday(
				new EmployeeId( "empId" )
			,	TargetOrgIdenInfor.creatIdentifiWorkplace("wkpId")
			,	new DatePeriod( GeneralDate.ymd( 2021, 10,  9 ), GeneralDate.ymd( 2021, 10, 31 ) )
		);


		// 実行
		val result = instance.toTickets();


		// 検証
		assertThat( result ).extracting( SupportTicket::getDate )
			.containsExactlyElementsOf( instance.getPeriod().datesBetween() );

		assertThat( result ).extracting(
					SupportTicket::getEmployeeId
				,	SupportTicket::getRecipient
				,	SupportTicket::getSupportType
				,	SupportTicket::getTimespan
			).containsOnly(tuple(
					instance.getEmployeeId()
				,	instance.getRecipient()
				,	instance.getSupportType()
				,	instance.getTimespan()
			));

	}



	private static class Helper {

		/**
		 * 計算時間帯を作成する
		 * @param startHour 開始(時)
		 * @param startMinute 開始(分)
		 * @param endHour 終了(時)
		 * @param endMinute 終了(分)
		 * @return 計算時間帯
		 */
		public static TimeSpanForCalc createTimespan( int startHour, int startMinute, int endHour, int endMinute ) {
			return new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute( startHour, startMinute )
						,	TimeWithDayAttr.hourMinute( endHour, endMinute )
					);
		}

	}

}
