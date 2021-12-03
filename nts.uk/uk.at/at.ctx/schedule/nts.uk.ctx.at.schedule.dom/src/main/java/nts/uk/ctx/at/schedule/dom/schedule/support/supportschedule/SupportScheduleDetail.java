package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 応援予定詳細
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.応援.応援予定.応援予定詳細
 * @author dan_pv
 */
@Value
public class SupportScheduleDetail implements DomainValue, Comparable<SupportScheduleDetail> {
	
	/**
	 * 応援先
	 */
	private final TargetOrgIdenInfor supportDestination;
	
	/**
	 * 応援形式
	 */
	private final FakeSupportType supportType; // TODO: change variable type
	
	/**
	 * 時間帯
	 */
	private final Optional<TimeSpanForCalc> timeSpan;
	
	/**
	 * 応援チケットから作る
	 * @param supportTicket 応援チケット
	 * @return
	 */
	public static final SupportScheduleDetail createBySupportTicket(FakeSupportTicket supportTicket) {
		// TODO: complete this code
		return new SupportScheduleDetail(
				TargetOrgIdenInfor.creatIdentifiWorkplace("id"), 
				FakeSupportType.FULL_DAY_SUPPORT, 
				Optional.empty());
	}
	
	/**
	 * 指定時間帯に収まるか
	 * @param specifiedTimeSpans
	 * @return
	 */
	public boolean doesItFitInTheSpecifiedTimeSpan(List<TimeSpanForCalc> specifiedTimeSpans) {
		
		if ( this.supportType == FakeSupportType.FULL_DAY_SUPPORT ) {
			return true;
		}
		
		return specifiedTimeSpans.stream().anyMatch( specifiedTimeSpan -> {
			val duplicationCheckResult = specifiedTimeSpan.checkDuplication(this.timeSpan.get());
			return duplicationCheckResult == TimeSpanDuplication.SAME_SPAN || 
					duplicationCheckResult == TimeSpanDuplication.CONTAINED;
		});
		
	}
	
	/**
	 * 同一か
	 * @param target 比較対象
	 * @return
	 */
	public boolean isSame(SupportScheduleDetail target) {
		return this.supportDestination == target.getSupportDestination()
				&& this.supportType == target.getSupportType()
				&& this.timeSpan == target.getTimeSpan();
	}

	@Override
	public int compareTo(SupportScheduleDetail o) {
		
		if ( !this.timeSpan.isPresent() || !o.getTimeSpan().isPresent() ) {
			return 0;
		} 
		
		return this.timeSpan.get().getStart().compareTo( o.getTimeSpan().get().getStart() );
	}

}
