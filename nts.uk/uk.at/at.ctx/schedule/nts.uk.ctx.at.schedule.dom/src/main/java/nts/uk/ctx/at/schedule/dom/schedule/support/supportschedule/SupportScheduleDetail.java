package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportTicket;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 応援予定詳細
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.応援.応援予定.応援予定詳細
 * @author dan_pv
 */
@Value
@EqualsAndHashCode
public class SupportScheduleDetail implements DomainValue, Comparable<SupportScheduleDetail> {
	
	/**
	 * 応援先
	 */
	private final TargetOrgIdenInfor supportDestination;
	
	/**
	 * 応援形式
	 */
	private final SupportType supportType;
	
	/**
	 * 時間帯
	 */
	private final Optional<TimeSpanForCalc> timeSpan;
	
	/**
	 * 応援チケットから作る
	 * @param supportTicket 応援チケット
	 * @return
	 */
	public static SupportScheduleDetail createBySupportTicket(SupportTicket supportTicket) {
		return new SupportScheduleDetail(
				supportTicket.getRecipient(), 
				supportTicket.getSupportType(), 
				supportTicket.getTimespan());
	}
	
	/**
	 * 指定時間帯に収まるか
	 * @param specifiedTimeSpans 指定時間帯リスト
	 * @return
	 */
	public boolean doesItFitInTheSpecifiedTimeSpan(List<TimeSpanForCalc> specifiedTimeSpans) {
		
		if ( this.supportType == SupportType.ALLDAY ) {
			return true;
		}
		
		return specifiedTimeSpans.stream().anyMatch( specifiedTimeSpan -> {
			val duplicationCheckResult = this.timeSpan.get().checkDuplication(specifiedTimeSpan);
			return duplicationCheckResult == TimeSpanDuplication.SAME_SPAN || 
					duplicationCheckResult == TimeSpanDuplication.CONTAINED;
		});
		
	}

	@Override
	public int compareTo(SupportScheduleDetail o) {
		
		if ( !this.timeSpan.isPresent() || !o.getTimeSpan().isPresent() ) {
			return 0;
		} 
		
		return this.timeSpan.get().getStart().compareTo( o.getTimeSpan().get().getStart() );
	}

}
