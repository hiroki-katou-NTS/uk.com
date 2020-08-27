package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.勤務変更申請.勤務変更申請
 * @author Doan Duy Hung
 *
 */
@Getter
@Setter

public class AppWorkChange extends Application {

	/**
	 * 直行区分
	 */
	private NotUseAtr straightGo;
	
	/**
	 * 直帰区分
	 */
	private NotUseAtr straightBack;
	
	/**
	 * 勤務種類コード
	 */
	private Optional<WorkTypeCode> opWorkTypeCD;
	
	/**
	 * 就業時間帯コード
	 */
	private Optional<WorkTimeCode> opWorkTimeCD;
	
	/**
	 * 勤務時間帯
	 */
	private List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst;
	
	public AppWorkChange(NotUseAtr straightGo, NotUseAtr straightBack,
		Optional<WorkTypeCode> opWorkTypeCD, Optional<WorkTimeCode> opWorkTimeCD,
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst, Application application) {
		super(application);
		this.straightGo = straightGo;
		this.straightBack = straightBack;
		this.opWorkTypeCD = opWorkTypeCD;
		this.opWorkTimeCD = opWorkTimeCD;
		this.timeZoneWithWorkNoLst = timeZoneWithWorkNoLst;
	}
	
	
	public AppWorkChange(Application application) {
		super(application);
	}
	
}
