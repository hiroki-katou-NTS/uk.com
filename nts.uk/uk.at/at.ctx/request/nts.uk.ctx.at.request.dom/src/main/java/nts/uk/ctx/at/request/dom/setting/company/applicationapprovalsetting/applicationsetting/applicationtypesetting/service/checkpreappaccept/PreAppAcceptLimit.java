package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.checkpreappaccept;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.アルゴリズム.事前申請がいつから受付可能か確認する.事前申請の受付制限
 * @author Doan Duy Hung
 *
 */
@Getter
public class PreAppAcceptLimit {
	
	/**
	 * 受付制限利用する
	 */
	private boolean useReceptionRestriction;
	
	/**
	 * 受付可能年月日
	 */
	@Setter
	private Optional<GeneralDate> opAcceptableDate;
	
	/**
	 * 受付可能時刻
	 */
	@Setter
	private Optional<AttendanceClock> opAvailableTime;
	
	public PreAppAcceptLimit(boolean useReceptionRestriction) {
		this.useReceptionRestriction = useReceptionRestriction;
		this.opAcceptableDate = Optional.empty();
		this.opAvailableTime = Optional.empty();
	}
	
}
