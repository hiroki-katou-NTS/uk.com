package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 応援予定Test
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class SupportScheduleTest {
	
	@Injectable
	private SupportSchedule.Require require;
	
	@Test
	public void testGetter(
				@Injectable SupportScheduleDetail detail1
			,	@Injectable SupportScheduleDetail detail2) {
		
		SupportSchedule  target = new SupportSchedule(Arrays.asList(detail1, detail2));
		NtsAssert.invokeGetters(target);
		
	}
	
	public static class SupportScheduleTest_create {
		
		@Injectable
		private SupportSchedule.Require require;
		
		private SupportOperationSetting supportSetting;
		
		@Before
		public void initData() {
			
			supportSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(3)//一日の最大応援回数 = 3
					);
		}
		
		/**
		 * Target	: createWithEmptyList
		 * Output	: 詳細リスト = empty
		 */
		@Test
		public void testCreateWithEmptyList() {
			
			SupportSchedule target = SupportSchedule.createWithEmptyList();
			
			assertThat(target.getDetails()).isEmpty();
			
		}
		
		/**
		 * Target	: create
		 * Input	: 詳細リスト = empty
		 * Output	: 詳細リスト = empty
		 */
		@Test
		public void testCreate_empty() {
			
			SupportSchedule target = SupportSchedule.create(require, Collections.emptyList());
			
			assertThat(target.getDetails()).isEmpty();
			
		}
		
		/**
		 * Target	: create
		 * Input	: 詳細リスト.size() > $最大応援回数
		 * Output	: Msg_2315
		 */
		@Test
		public void testCreate_Msg_2315(
					@Injectable SupportScheduleDetail detail1
				,	@Injectable SupportScheduleDetail detail2
				,	@Injectable SupportScheduleDetail detail3
				,	@Injectable SupportScheduleDetail detail4) {
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
			NtsAssert.businessException("Msg_2315",
					() -> SupportSchedule.create(require, Arrays.asList(detail1, detail2, detail3, detail4))
				);
		}

		/**
		 * Target	: create
		 * Input	: @詳細リストの中に終日応援があったら、@詳細リスト.size() > 1
		 * Output	: Msg_2315
		 */
		@Test
		public void testCreate_Msg_2277(
				@Injectable TimeSpanForCalc time1
			,	@Injectable TimeSpanForCalc time2
				) {
			
			List<SupportScheduleDetail> details = Arrays.asList(
					Helper.createSupportScheduleDetail(SupportType.ALLDAY, Optional.empty())
				,	Helper.createSupportScheduleDetail(SupportType.TIMEZONE, Optional.of(time1))
				,	Helper.createSupportScheduleDetail(SupportType.TIMEZONE, Optional.of(time2))
					);
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
			NtsAssert.businessException("Msg_2277",
					() -> SupportSchedule.create(require, details)
				);
		}
	
		/**
		 * Target	: create
		 * Input	: 応援時間帯が重複する。		
		 * Output	: Msg_2278
		 */
		@Test
		public void testCreate_Msg_2278() {
			
			List<SupportScheduleDetail> details = new ArrayList<>(Arrays.asList(
					Helper.createSupportScheDetailTimeZone(
						new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(8, 0), 
							TimeWithDayAttr.hourMinute(9, 0)))
				,	Helper.createSupportScheDetailTimeZone(
						new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 0), 
							TimeWithDayAttr.hourMinute(10, 0)))
				,	Helper.createSupportScheDetailTimeZone(
						new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 30), 
							TimeWithDayAttr.hourMinute(11, 0)))
					));
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
			NtsAssert.businessException("Msg_2278",
					() -> SupportSchedule.create(require, details)
				);
			
		}
	
		@Test
		public void testCreate_sortSuccess(
				@Injectable TargetOrgIdenInfor supportDestination1
			,	@Injectable TargetOrgIdenInfor supportDestination2
			,	@Injectable TargetOrgIdenInfor supportDestination3
				
				) {
			
			List<SupportScheduleDetail> details = new ArrayList<>(Arrays.asList(
					new SupportScheduleDetail (supportDestination1, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(9, 0), 
												TimeWithDayAttr.hourMinute(10, 0))))
				,	new SupportScheduleDetail (supportDestination2, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(8, 0), 
												TimeWithDayAttr.hourMinute(9, 0))))
				,	new SupportScheduleDetail (supportDestination3, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(10, 0), 
								TimeWithDayAttr.hourMinute(11, 0))))
					));
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
			SupportSchedule result = SupportSchedule.create(require, details);
			
			assertThat(result.getDetails())
			.extracting(
					d -> d.getSupportDestination()
				,	d -> d.getTimeSpan().get().getStart()
				,	d -> d.getTimeSpan().get().getEnd())
			.containsExactly(
					tuple(supportDestination2, TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(9, 0))
				,	tuple(supportDestination1, TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(10, 0))
				,	tuple(supportDestination3, TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0))
					);
		}
		
	}
	
	public static class SupportSchedule_createFromSupportTicketList{
		
		@Injectable
		private SupportSchedule.Require require;
		
		@Test
		public void testCreateFromSupportTicketList_case_timezone(
					@Injectable TargetOrgIdenInfor recipient1
				,	@Injectable TargetOrgIdenInfor recipient2
				,	@Injectable TargetOrgIdenInfor recipient3
				) {
			
			val supportOperationSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(3)//一日の最大応援回数
					);
			
			List<SupportTicket> supportTickets = new ArrayList<>(Arrays.asList(
					Helper.createSupportTicket(recipient1, SupportType.TIMEZONE , Optional.of(
							new TimeSpanForCalc(
									TimeWithDayAttr.hourMinute(8, 0)
								,	TimeWithDayAttr.hourMinute(9, 0))))
				,	Helper.createSupportTicket(recipient2, SupportType.TIMEZONE , Optional.of(
						new TimeSpanForCalc(
									TimeWithDayAttr.hourMinute(9, 0)
								, 	TimeWithDayAttr.hourMinute(10, 0))))
				,	Helper.createSupportTicket(recipient3, SupportType.TIMEZONE , Optional.of(
						new TimeSpanForCalc(
									TimeWithDayAttr.hourMinute(10, 0) 
								,	TimeWithDayAttr.hourMinute(11, 0))))
					));
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportOperationSetting;
				
			}};
			
			val result = SupportSchedule.createFromSupportTicketList(require, supportTickets);
			
			assertThat(result.getDetails())
			.extracting(
					d -> d.getSupportDestination()
				,	d -> d.getTimeSpan().get().getStart()
				,	d -> d.getTimeSpan().get().getEnd())
			.containsExactly(
					tuple(recipient1, TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(9, 0))
				,	tuple(recipient2, TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(10, 0))
				,	tuple(recipient3, TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0))
					);
		}
		
		@Test
		public void testCreateFromSupportTicketList_case_allday(@Injectable TargetOrgIdenInfor recipient1) {
			
			val supportSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(3)//一日の最大応援回数
					);
			
			List<SupportTicket> supportTickets = new ArrayList<>(Arrays.asList(
					Helper.createSupportTicket(recipient1, SupportType.ALLDAY , Optional.empty())
					));
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
			val result = SupportSchedule.createFromSupportTicketList(require, supportTickets);
			
			assertThat(result.getDetails())
			.extracting(
					d -> d.getSupportDestination()
				,	d -> d.getTimeSpan())
			.containsExactly(
					tuple(recipient1, Optional.empty())
					);
		}
	}
	
	public static class SupportSchedule_add{
		
		@Injectable
		private SupportSchedule.Require require;
		
		SupportOperationSetting supportSetting;
		
		@Before
		public void initData() {
			
			supportSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(3)//一日の最大応援回数
					);
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
		}
		
		@Test
		public void testAdd_case_timezone(
					@Injectable TargetOrgIdenInfor supportDestination1
				,	@Injectable TargetOrgIdenInfor supportDestination2
				,	@Injectable TargetOrgIdenInfor recipient1
				) {
			
			val details = new ArrayList<>(Arrays.asList(
					new SupportScheduleDetail (supportDestination1, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(9, 0), 
												TimeWithDayAttr.hourMinute(10, 0))))
				,	new SupportScheduleDetail (supportDestination2, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(8, 0), 
												TimeWithDayAttr.hourMinute(9, 0))))
					));
			
			val supportSchedule = SupportSchedule.create(require, details);
			
			//全部時間帯応援を追加して、成功
			{
				val ticket = Helper.createSupportTicket(recipient1, SupportType.TIMEZONE , Optional.of(
						new TimeSpanForCalc(
								TimeWithDayAttr.hourMinute(11, 0)
							,	TimeWithDayAttr.hourMinute(12, 0))));
				//act
				val result = supportSchedule.add(require, ticket);
				
				//assert
				assertThat(result.getDetails())
				.extracting(
						d -> d.getSupportDestination()
					,	d -> d.getTimeSpan().get().getStart()
					,	d -> d.getTimeSpan().get().getEnd())
				.containsExactly(
						tuple(supportDestination2, TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(9, 0))
					,	tuple(supportDestination1, TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(10, 0))
					,	tuple(recipient1, TimeWithDayAttr.hourMinute(11, 0), TimeWithDayAttr.hourMinute(12, 0))
						);
			}
			
			//時間帯応援が存在して、終日を追加, Msg_2277が発生
			{
				val ticket = Helper.createSupportTicket(recipient1, SupportType.ALLDAY , Optional.empty());
				
				//assert
				NtsAssert.businessException("Msg_2277",
						() -> supportSchedule.add(require, ticket)
					);
			}

		}
		
		@Test
		public void testAdd_case_allDay(
					@Injectable TargetOrgIdenInfor supportDestination1
				,	@Injectable TargetOrgIdenInfor supportDestination2
				,	@Injectable TargetOrgIdenInfor recipient1
				) {
			
			val ticket = Helper.createSupportTicket(recipient1, SupportType.ALLDAY , Optional.empty());
			
			// 詳細リスト = empty
			{
				val supportSchedule = SupportSchedule.create(require, Collections.emptyList());
				
				//act
				val result = supportSchedule.add(require, ticket);
				
				//assert
				assertThat(result.getDetails())
				.extracting(
						d -> d.getSupportDestination()
					,	d -> d.getTimeSpan())
				.containsExactly(
						tuple(recipient1, Optional.empty())
						);
				
			}
			
			// 詳細リスト not empty
			{
				val supportSchedule = SupportSchedule.create(require, Arrays.asList(
						new SupportScheduleDetail (supportDestination1, SupportType.ALLDAY,	Optional.empty())
						));
				
				//act
				NtsAssert.businessException("Msg_2277",
						() -> supportSchedule.add(require, ticket)
					);
			}

		}
		
	}
	
	public static class SupportSchedule_update{
		
		@Injectable
		private SupportSchedule.Require require;
		
		@Test
		public void testUpdate_case_timezone(
					@Injectable TargetOrgIdenInfor supportDestination1
				,	@Injectable TargetOrgIdenInfor supportDestination2
				,	@Injectable TargetOrgIdenInfor supportDestination3) {
			
			val details = new ArrayList<>(Arrays.asList(
					// 変更前の対象と同じ
					new SupportScheduleDetail (supportDestination1, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(9, 0), 
												TimeWithDayAttr.hourMinute(10, 0))))
					// 変更前の対象 が違う
				,	new SupportScheduleDetail (supportDestination2, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(8, 0), 
												TimeWithDayAttr.hourMinute(9, 0))))
					// 変更前の対象 が違う
				,	new SupportScheduleDetail (supportDestination3, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(10, 0), 
												TimeWithDayAttr.hourMinute(11, 0))))
					));
			
			// 変更前の対象
			val beforeUpdateTarget = Helper.createSupportTicket(supportDestination1, SupportType.TIMEZONE
					,	Optional.of(new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 0), 
							TimeWithDayAttr.hourMinute(10, 0))));
			
			//変更後の対象
			val afterUpdateTarget = Helper.createSupportTicket(supportDestination1, SupportType.TIMEZONE
					,	Optional.of(new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 30), 
							TimeWithDayAttr.hourMinute(10, 0))));
			
			val supportSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(5)//一日の最大応援回数
					);
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
			val supportSchedule = SupportSchedule.create(require, details);
			
			//act
			val result = supportSchedule.update(require, beforeUpdateTarget, afterUpdateTarget);
			
			//assert
			assertThat(result.getDetails())
			.extracting(
					d -> d.getSupportDestination()
				,	d -> d.getTimeSpan().get().getStart()
				,	d -> d.getTimeSpan().get().getEnd())
			.containsExactly(
					tuple(supportDestination2, TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(9, 0))
				,	tuple(supportDestination1, TimeWithDayAttr.hourMinute(9, 30), TimeWithDayAttr.hourMinute(10, 0))
				,	tuple(supportDestination3, TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0))
					);
		}
	}
	
	public static class SupportSchedule_getSupportType{
		
		@Injectable
		private SupportSchedule.Require require;
		
		/**
		 * Target	: getSupportType
		 * Input	: 詳細リスト = empty
		 * Output	: empty
		 */
		@Test
		public void testGetSupportType_empty() {
			val supportSche = SupportSchedule.createWithEmptyList();
			
			//act
			val result = supportSche.getSupportType();
			
			//assert
			assertThat(result).isEmpty();
		}
		
		/**
		 * Target	: getSupportType
		 * Input	: 終日
		 * Output	: 終日
		 */
		@Test
		public void testGetSupportType_case_allday(@Injectable TargetOrgIdenInfor supportDestination) {
			
			val supportSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(5)//一日の最大応援回数
					);
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
			val supportSche = SupportSchedule.create(require, Arrays.asList(
					new SupportScheduleDetail (supportDestination, SupportType.ALLDAY, Optional.empty())));
			
			//act
			Optional<SupportType> result = supportSche.getSupportType();
			
			//assert
			assertThat(result.get()).isEqualTo(SupportType.ALLDAY);
			
		}
		
		/**
		 * Target	: getSupportType
		 * Input	: 時間帯応援
		 * Output	: 時間帯応援
		 */
		@Test
		public void testGetSupportType_case_timezone(
					@Injectable TargetOrgIdenInfor supportDestination
				,	@Injectable TimeSpanForCalc time1
				,	@Injectable TimeSpanForCalc time2) {
			
			val supportSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(5)//一日の最大応援回数
					);
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
			
			val supportSche = SupportSchedule.create(require, Arrays.asList(
						Helper.createSupportScheduleDetail(SupportType.TIMEZONE, Optional.of(time1))
					,	Helper.createSupportScheduleDetail(SupportType.TIMEZONE, Optional.of(time2))
						));
			//act
			Optional<SupportType> result = supportSche.getSupportType();
			
			//assert
			assertThat(result.get()).isEqualTo(SupportType.TIMEZONE);
			
		}
		
	}
	
	public static class SupportSchedule_getSupportTimeSpanList{
		
		@Injectable
		private SupportSchedule.Require require;
		
		private static SupportOperationSetting supportSetting;
		
		@Before
		public void initData() {
			supportSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(5)//一日の最大応援回数
					);
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
		}
		/**
		 * Target	: getSupportTimeSpanList
		 * input	: 終日
		 * Output	: empty
		 */
		@Test
		public void testGetSupportTimeSpanList_case_allDay(@Injectable TargetOrgIdenInfor supportDestination) {
			
			val supportSche = SupportSchedule.create(require, Arrays.asList(
					new SupportScheduleDetail (supportDestination, SupportType.ALLDAY, Optional.empty())));
			
			//act
			val result = supportSche.getSupportTimeSpanList();
			
			//assert
			assertThat(result).isEmpty();
			
		}
		
		/**
		 * Target	: getSupportTimeSpanList
		 * input	: 時間帯応援
		 */
		@Test
		public void testGetSupportTimeSpanList_case_timezone() {
			
			val supportSche = SupportSchedule.create(require, Arrays.asList(
						Helper.createSupportScheDetailTimeZone(
								new TimeSpanForCalc( TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(10, 0)))
					,	Helper.createSupportScheDetailTimeZone(
							new TimeSpanForCalc( TimeWithDayAttr.hourMinute(11, 0), TimeWithDayAttr.hourMinute(12, 0)))
						));
			//Act
			val result = supportSche.getSupportTimeSpanList();
			
			//Assert
			assertThat(result)
			.extracting(
					d -> d.getStart()
				,	d -> d.getEnd())
			.containsExactly(
					tuple(TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(10, 0))
				,	tuple(TimeWithDayAttr.hourMinute(11, 0), TimeWithDayAttr.hourMinute(12, 0))
					);
		}
		
	}
	
	public static class SupportSchedule_remove{
		
		@Injectable
		private SupportSchedule.Require require;
		
		private static SupportOperationSetting supportSetting;
		
		@Before
		public void initData() {
			supportSetting = new SupportOperationSetting(
					true, true //dummy
				,	new MaximumNumberOfSupport(5)//一日の最大応援回数
					);
			
			new Expectations() {{
				
				require.getSupportOperationSetting();
				result = supportSetting;
				
			}};
		}
		
		@Test
		public void testRemove_case_timezone(@Injectable TargetOrgIdenInfor supportDestination) {
			
			// 削除対象
			val removeTarget = Helper.createSupportTicket(supportDestination, SupportType.TIMEZONE
					,	Optional.of(new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 0), 
							TimeWithDayAttr.hourMinute(10, 0))));
			
			val details = new ArrayList<>(Arrays.asList(
					// 変更前の対象と同じ
					new SupportScheduleDetail (supportDestination, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(9, 0), 
												TimeWithDayAttr.hourMinute(10, 0))))
					// 変更前の対象 が違う
				,	new SupportScheduleDetail (supportDestination, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(8, 0), 
												TimeWithDayAttr.hourMinute(9, 0))))
					// 変更前の対象 が違う
				,	new SupportScheduleDetail (supportDestination, SupportType.TIMEZONE
						,	Optional.of(new TimeSpanForCalc(
												TimeWithDayAttr.hourMinute(10, 0), 
												TimeWithDayAttr.hourMinute(11, 0))))
					));
			val supportSche = SupportSchedule.create(require, details);
			
			//act
			val result = supportSche.remove(removeTarget);
			
			//assert
			assertThat(result.getDetails())
			.extracting(
					d -> d.getSupportDestination()
				,	d -> d.getTimeSpan().get().getStart()
				,	d -> d.getTimeSpan().get().getEnd())
			.containsExactly(
					tuple(supportDestination, TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(9, 0))
				,	tuple(supportDestination, TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0))
					);
		}
		
		@Test
		public void testRemove_case_allDay(@Injectable TargetOrgIdenInfor supportDestination) {
			
			// 削除対象
			val removeTarget = Helper.createSupportTicket(supportDestination, SupportType.ALLDAY, Optional.empty());
			
			val supportSche = SupportSchedule.create(require, Arrays.asList(
					new SupportScheduleDetail (supportDestination, SupportType.ALLDAY, Optional.empty())));
			//act
			val result = supportSche.remove(removeTarget);
			
			//assert
			assertThat(result.getDetails()).isEmpty();
			
		}
		
	}
	
	
	
	public static class Helper{
		
		static TargetOrgIdenInfor supportDestination;
		
		/**
		 * 応援予定詳細を作る
		 * @param supportType 応援形式
		 * @param timeSpan 時間帯
		 * @return
		 */
		public static SupportScheduleDetail createSupportScheduleDetail(	
				SupportType supportType
			,	Optional<TimeSpanForCalc> timespan) {
			
			return new SupportScheduleDetail(supportDestination, supportType, timespan);
		}
		/**
		 * 時間帯で応援予定詳細を作る
		 * @param supportType 応援形式
		 * @param timeSpan 時間帯
		 * @return
		 */
		public static SupportScheduleDetail createSupportScheDetailTimeZone( TimeSpanForCalc timespan) {
			return new SupportScheduleDetail(supportDestination, SupportType.TIMEZONE, Optional.of(timespan));
		}
		
		/**
		 * 応援チケットを作る
		 * @param recipient 応援先
		 * @param supportType 応援形式
		 * @param timespan 時間帯
		 * @return
		 */
		public static SupportTicket createSupportTicket(
				TargetOrgIdenInfor recipient
			,	SupportType supportType
			,	Optional<TimeSpanForCalc> timespan) {
			
			return new SupportTicket(new EmployeeId("sid"), recipient, supportType, GeneralDate.today(), timespan);
			
		}
		
	}
	
}
