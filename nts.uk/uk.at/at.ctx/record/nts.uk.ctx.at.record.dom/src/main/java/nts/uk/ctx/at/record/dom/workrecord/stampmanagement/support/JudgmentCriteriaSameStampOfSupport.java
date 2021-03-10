/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * AR: 応援の同一打刻の判断基準
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.応援
 * @author laitv
 *
 */
@Getter
public class JudgmentCriteriaSameStampOfSupport extends AggregateRoot {

	// 会社ID
	private final CompanyId cid;

	// 同一打刻とみなす範囲
	private final RangeRegardedSupportStamp sameStampRanceInMinutes;

	// 最大応援回数
	private final MaximumNumberOfSupport supportMaxFrame;
	
	// [C-1] 応援の同一打刻の判断基準を作成する	
	public JudgmentCriteriaSameStampOfSupport(CompanyId cid,
			RangeRegardedSupportStamp sameStampRanceInMinutes,MaximumNumberOfSupport supportMaxFrame) {
		super();
		this.cid = cid;
		this.sameStampRanceInMinutes = sameStampRanceInMinutes;
		this.supportMaxFrame = supportMaxFrame;
	}
	
	public JudgmentCriteriaSameStampOfSupport(String cid,
			int sameStampRanceInMinutes,int supportMaxFrame) {
		super();
		this.cid = new CompanyId(cid);
		this.sameStampRanceInMinutes = EnumAdaptor.valueOf(sameStampRanceInMinutes, RangeRegardedSupportStamp.class);
		this.supportMaxFrame    = EnumAdaptor.valueOf(supportMaxFrame, MaximumNumberOfSupport.class);
	}
	
	public static JudgmentCriteriaSameStampOfSupport create(String cid,
			int sameStampRanceInMinutes, int supportMaxFrame) {
		
		return new JudgmentCriteriaSameStampOfSupport(cid, sameStampRanceInMinutes, supportMaxFrame);
	}
	
	/**
	 * [1] 同一と認識すべきの打刻か
	 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.応援.応援の同一打刻の判断基準.同一と認識すべきの打刻か
	 * 「パラメータ」 
	 * ・基準打刻：時刻（日区分付き） standardStamp
	 * ・対象打刻：時刻（日区分付き） targetStamp
	 * 「Output」 ・Boolean
	 */
	public boolean checkStampRecognizedAsSame(TimeWithDayAttr standardStamp, TimeWithDayAttr targetStamp) {
		int standardStampMinutes = standardStamp.minute();
		int targetStampMinutes   = targetStamp.minute();
		if (Math.abs(standardStampMinutes - targetStampMinutes) <= this.sameStampRanceInMinutes.v())
			return true;
		return false;
	}
}
