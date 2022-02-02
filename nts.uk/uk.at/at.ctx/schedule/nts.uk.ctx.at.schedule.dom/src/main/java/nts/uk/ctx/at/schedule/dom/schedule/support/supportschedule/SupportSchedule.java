package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Value;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;

/**
 * 応援予定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.応援.応援予定.応援予定
 * @author dan_pv
 */
@Value
public class SupportSchedule implements DomainValue{
	
	/**
	 * 詳細リスト
	 */
	private final List<SupportScheduleDetail> details;
	
	/**
	 * 応援なしで作る
	 * @return
	 */
	public static SupportSchedule createWithEmptyList() {
		return new SupportSchedule( Collections.emptyList() );
	}
	
	/**
	 * 作る
	 * @param require
	 * @param details 詳細リスト
	 * @return
	 */
	public static SupportSchedule create(Require require, List<SupportScheduleDetail> details) {
		
		if ( details.isEmpty() ) {
			return SupportSchedule.createWithEmptyList();
		}
		
		boolean haveFullDaySupport = details.stream()
				.anyMatch( detail -> detail.getSupportType() == SupportType.ALLDAY );
		if ( haveFullDaySupport ) {
			
			// inv-1
			if ( details.size() > 1) {
				throw new BusinessException("Msg_2277");
			}
			
			return new SupportSchedule(details);
		}
		
		Integer maxSupportTimes = require.getSupportOperationSetting().getMaxNumberOfSupportOfDay().v();
		if ( details.size() > maxSupportTimes ) {
			throw new BusinessException("Msg_2315");
		}
		
		TimeSpanSupportScheduleDetailList detailList = TimeSpanSupportScheduleDetailList.create(details);
		
		// inv-2
		if ( detailList.checkDuplicate() ) {
			throw new BusinessException("Msg_2278");
		}
		
		// inv-3
		detailList.sort();
		
		return new SupportSchedule(detailList.getDetails());
	}
	
	/**
	 * 応援チケットリストから作る
	 * @param require
	 * @param supportTicketList 応援チケットリスト
	 * @return
	 */
	public static SupportSchedule createFromSupportTicketList(Require require, List<SupportTicket> supportTicketList) {
		
		List<SupportScheduleDetail> details = supportTicketList.stream()
				.map(ticket -> SupportScheduleDetail.createBySupportTicket(ticket))
				.collect(Collectors.toList());
		
		return SupportSchedule.create(require, details);
	}
	
	/**
	 * 追加する
	 * @param require
	 * @param ticket 応援チケット
	 * @return
	 */
	public SupportSchedule add(Require require, SupportTicket ticket) {
		
		val addTarget = SupportScheduleDetail.createBySupportTicket(ticket);
		
		val registeredList = this.details.stream().collect(Collectors.toList());
		registeredList.add(addTarget);
		
		return SupportSchedule.create(require, registeredList);
	}
	
	/**
	 * 変更する
	 * @param require
	 * @param beforeUpdate 変更前
	 * @param afterUpdate 変更後
	 * @return
	 */
	public SupportSchedule update(Require require, SupportTicket beforeUpdate, SupportTicket afterUpdate) {
		
		val beforeUpdateTarget = SupportScheduleDetail.createBySupportTicket(beforeUpdate);
		val afterUpdateTarget = SupportScheduleDetail.createBySupportTicket(afterUpdate);
		
		val registeredList = this.details.stream()
				.filter(e -> !e.equals(beforeUpdateTarget))
				.collect(Collectors.toList());
		registeredList.add(afterUpdateTarget);
		
		return SupportSchedule.create(require, registeredList);
	}
	
	/**
	 * 削除する
	 * @param require
	 * @param ticket 応援チケット
	 * @return
	 */
	public SupportSchedule remove(SupportTicket ticket) {
		
		val removeTarget = SupportScheduleDetail.createBySupportTicket(ticket);
		
		val newDetailList = this.details.stream()
				.filter(e -> !e.equals(removeTarget))
				.collect(Collectors.toList());
		
		return new SupportSchedule(newDetailList);
	}
	
	/**
	 * 応援時間帯リストを取得する
	 * @return
	 */
	public List<TimeSpanForCalc> getSupportTimeSpanList() {
		
		return this.details.stream().filter( e -> e.getSupportType() == SupportType.TIMEZONE )
				.map(e -> e.getTimeSpan().get())
				.collect(Collectors.toList());
	}
	
	/**
	 * 応援形式を返す
	 * @return
	 */
	public Optional<SupportType> getSupportType() {
		
		if ( this.details.isEmpty() ) {
			return Optional.empty(); 
		}
		
		return Optional.of(this.details.get(0).getSupportType());
	}
	
	private static class TimeSpanSupportScheduleDetailList {
		
		@Getter
		private List<SupportScheduleDetail> details;
		
		/**
		 * private constructor
		 * @param details
		 */
		private TimeSpanSupportScheduleDetailList(List<SupportScheduleDetail> details) {
			this.details = details;
		}
		
		public static TimeSpanSupportScheduleDetailList create(List<SupportScheduleDetail> details) {
			
			boolean isAllTimeSpanSupport = details.stream()
					.allMatch( detail -> detail.getSupportType() == SupportType.TIMEZONE);
			
			if ( !isAllTimeSpanSupport ) {
				throw new BusinessException("詳細リストが全部時間帯応援じゃないです");
			}
			
			return new TimeSpanSupportScheduleDetailList(details);
		}
		
		/**
		 * 重複チェックする
		 * true: if any time spans in @details is duplicate 対象の詳細リストの中の要素が重複
		 * false: all time spans in @details are not duplicate 対象の詳細リストの中の要素が全て非重複  
		 */
		public boolean checkDuplicate() {
			
			List<TimeSpanForCalc> timeSpans = this.details.stream()
					.map(d -> d.getTimeSpan().get())
					.collect(Collectors.toList());
			
			AtomicInteger index = new AtomicInteger(0);
			
			return timeSpans.stream().anyMatch( base -> {
				return timeSpans.subList(index.incrementAndGet(), timeSpans.size()).stream()
						.anyMatch( target -> base.checkDuplication( target ).isDuplicated() );
			});
		}
		
		/**
		 * sort @details by start-time
		 */
		public void sort() {
			Collections.sort(this.details);
		}
		
	}
	
	public static interface Require {
		
		/**
		 * 応援の運用設定を取得する
		 * @return
		 */
		SupportOperationSetting getSupportOperationSetting();
	}

}
