package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * 応援予定詳細Test
 * @author lan_lt
 *
 */
public class SupportScheduleDetailTest {
	
	/**
	 * Target	: All getter methods
	 */
	@Test
	public void testGetters() {
		
		NtsAssert.invokeGetters(
				new SupportScheduleDetail(
						TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
					,	SupportType.ALLDAY
					,	Optional.empty()
						
						));
	}
	
	/**
	 * Target	: equal
	 * pattern	: 終日応援予定
	 */
	@Test
	public void testEquals_case_allDay() {
		
		val object = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
				,	SupportType.ALLDAY
				,	Optional.empty()
			);
		
		//True:すべて同じ値
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
				,	SupportType.ALLDAY
				,	Optional.empty()
			);
			
			assertThat( object.equals( target ) ).isTrue();
		}
		
		// False: 応援先
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId")
				,	SupportType.ALLDAY
				,	Optional.empty()
			);
			
			assertThat( object.equals( target ) ).isFalse();
			
		}
		
		// False: 応援形式
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
				,	SupportType.TIMEZONE
				,	Optional.empty()
			);
			
			assertThat( object.equals( target ) ).isFalse();
			
		}
		
		// False: 時間帯
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
				,	SupportType.ALLDAY
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 10,  0 ), TimeWithDayAttr.hourMinute( 11, 0 ) ) )
			);
			
			assertThat( object.equals( target ) ).isFalse();
			
		}
	}
	
	/**
	 * Target	: equal
	 * pattern	: 時間帯応援予定
	 */
	@Test
	public void testEquals_case_timezone() {
		
		val object = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
				,	SupportType.TIMEZONE
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 10,  0 ), TimeWithDayAttr.hourMinute( 11, 0 ) ) )
			);
		
		//True:すべて同じ値
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
				,	SupportType.TIMEZONE
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 10,  0 ), TimeWithDayAttr.hourMinute( 11, 0 ) ) )
			);
			
			assertThat( object.equals( target ) ).isTrue();
		}
		
		// False: 応援先
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId")
				,	SupportType.TIMEZONE
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 10,  0 ), TimeWithDayAttr.hourMinute( 11, 0 ) ) )
			);
			
			assertThat( object.equals( target ) ).isFalse();
			
		}
		
		// False: 応援形式
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
				,	SupportType.ALLDAY
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 10,  0 ), TimeWithDayAttr.hourMinute( 11, 0 ) ) )
			);
			
			assertThat( object.equals( target ) ).isFalse();
			
		}
		
		// False: 時間帯
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
				,	SupportType.TIMEZONE
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 7,  0 ), TimeWithDayAttr.hourMinute( 8, 0 ) ) )
			);
			
			assertThat( object.equals( target ) ).isFalse();
			
		}
	}
	
	/**
	 * Target	: createBySupportTicket
	 * pattern	: 終日
	 */
	@Test
	public void testCreateBySupportTicket_case_all_day() {
		
		val supportTicket = new SupportTicket(
				new EmployeeId("emp-id")
			,	TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("wkp-grp-id")
			,	SupportType.ALLDAY
			,	GeneralDate.ymd( 2021, 12, 1 )
			,	Optional.empty()
		);
		
		//Act
		val result = SupportScheduleDetail.createBySupportTicket( supportTicket );
		
		//Assert
		assertThat( result.getSupportDestination()).isEqualTo( supportTicket.getRecipient() );
		assertThat( result.getSupportType() ).isEqualTo( supportTicket.getSupportType() );
		assertThat( result.getTimeSpan() ).isEmpty();
		
	}
	
	/**
	 * Target	: createBySupportTicket
	 * pattern	: 時間帯応援
	 */
	@Test
	public void testCreateBySupportTicket_case_time_zone() {
		
		val recipient = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("wkp-grp-id");
		val timespan = new TimeSpanForCalc(	TimeWithDayAttr.hourMinute( 10, 0 )
										,	TimeWithDayAttr.hourMinute( 11, 0 ));
		val supportTicket = new SupportTicket(
				new EmployeeId("emp-id")
			,	recipient
			,	SupportType.TIMEZONE
			,	GeneralDate.ymd( 2021, 12, 1 )
			,	Optional.of( timespan )
		);
		
		//Act
		val result = SupportScheduleDetail.createBySupportTicket( supportTicket );
		
		//Assert
		assertThat( result.getSupportDestination()).isEqualTo( recipient );
		assertThat( result.getSupportType() ).isEqualTo( SupportType.TIMEZONE );
		assertThat( result.getTimeSpan().get().getStart() ).isEqualTo( TimeWithDayAttr.hourMinute( 10, 0 ) );
		assertThat( result.getTimeSpan().get().getEnd() ).isEqualTo( TimeWithDayAttr.hourMinute( 11, 0 ) );
		
	}
	
	/**
	 * Target	: doesItFitInTheSpecifiedTimeSpan
	 * Input	: 応援形式 == 終日応援
	 * Output	: true
	 */
	@Test
	public void testDoesItFitInTheSpecifiedTimeSpan_case_allday() {
		
		val specifiedTimeSpans = Arrays.asList(
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 10, 0 ), TimeWithDayAttr.hourMinute( 11, 0 ) )// time = 10h -> 11h
				,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 12, 0 ), TimeWithDayAttr.hourMinute( 13, 0 ) ));//time = 12h-> 13h
		
		val target = new SupportScheduleDetail(
				TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId")
			,	SupportType.ALLDAY
			,	Optional.empty()
		);
		
		//Act
		val result = target.doesItFitInTheSpecifiedTimeSpan( specifiedTimeSpans );
		
		//Assert
		assertThat( result ).isTrue();
	}
	
	/**
	 * Target	: doesItFitInTheSpecifiedTimeSpan
	 * Input	: 応援形式 == 間帯応援
	 * Output	: true
	 */
	@Test
	public void testDoesItFitInTheSpecifiedTimeSpan_case_timezone() {
		/**
		 * 同じ時間帯
		 *				<------->
		 *				<------->				<-------->
		 */
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" )
				,	SupportType.TIMEZONE
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 8, 30 ), TimeWithDayAttr.hourMinute( 12, 00 ) ) )
			);
			
			val specifiedTimeSpans = Arrays.asList( 
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 8, 30 ), TimeWithDayAttr.hourMinute( 12, 00 ) )
				,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 13, 00 ), TimeWithDayAttr.hourMinute( 17, 30 ) )
					);
			
			//Act
			val result = target.doesItFitInTheSpecifiedTimeSpan( specifiedTimeSpans );
			
			//Assert
			assertThat( result ).isTrue();
			
		}
		
		/**
		 * 包含
						<------->
					<----------->	<-------->
		 * 
		 */
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" )
				,	SupportType.TIMEZONE
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 8, 30 ), TimeWithDayAttr.hourMinute( 17, 30 ) ) )
			);
			
			val specifiedTimeSpans = Arrays.asList( 
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 8, 0 ), TimeWithDayAttr.hourMinute( 18, 0 ) )
				,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 18,30 ), TimeWithDayAttr.hourMinute( 20, 0 ) ));
			
			//Act
			val result = target.doesItFitInTheSpecifiedTimeSpan( specifiedTimeSpans );
			
			//Assert
			assertThat( result ).isTrue();
			
		}
		
		// 

		
		/**
		 * 時間帯違う
						<------------------->
			<----------->					<-------->
		 */
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" )
				,	SupportType.TIMEZONE
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 8, 30 ), TimeWithDayAttr.hourMinute( 17, 30 ) ) )
			);
			
			val specifiedTimeSpans = Arrays.asList( 
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 7, 0 ), TimeWithDayAttr.hourMinute( 8, 30 ) )
				,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 18, 0 ), TimeWithDayAttr.hourMinute( 20, 0 ) ));
			
			//Act
			val result = target.doesItFitInTheSpecifiedTimeSpan( specifiedTimeSpans );
			
			//Assert
			assertThat( result ).isFalse();
			
		}
		
		/**
		 * 包含ない
		 *  		<-------------->
		 *  <-------------->	<-------------->
		 */
		{
			val target = new SupportScheduleDetail(
					TargetOrgIdenInfor.creatIdentifiWorkplace( "workplaceId" )
				,	SupportType.TIMEZONE
				,	Optional.of( new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 8, 30 ), TimeWithDayAttr.hourMinute( 12, 00 ) ) )
			);
			
			val specifiedTimeSpans = Arrays.asList( 
					new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 8, 0 ), TimeWithDayAttr.hourMinute( 10, 0 ) )
				,	new TimeSpanForCalc( TimeWithDayAttr.hourMinute( 11, 0 ), TimeWithDayAttr.hourMinute( 12, 0 ) ));
			//Act
			val result = target.doesItFitInTheSpecifiedTimeSpan( specifiedTimeSpans );
			
			//Assert
			assertThat( result ).isFalse();
			
		}
	}
}
