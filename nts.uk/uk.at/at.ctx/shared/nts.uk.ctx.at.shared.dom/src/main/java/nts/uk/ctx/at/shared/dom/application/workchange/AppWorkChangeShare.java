package nts.uk.ctx.at.shared.dom.application.workchange;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務変更申請
 * 
 * @author thanh_nx
 *
 */
@Getter
@Setter

public class AppWorkChangeShare extends ApplicationShare {

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

	public AppWorkChangeShare(NotUseAtr straightGo, NotUseAtr straightBack, Optional<WorkTypeCode> opWorkTypeCD,
			Optional<WorkTimeCode> opWorkTimeCD, List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst,
			ApplicationShare application) {
		super(application);
		this.straightGo = straightGo;
		this.straightBack = straightBack;
		this.opWorkTypeCD = opWorkTypeCD;
		this.opWorkTimeCD = opWorkTimeCD;
		this.timeZoneWithWorkNoLst = timeZoneWithWorkNoLst;
	}

	public AppWorkChangeShare(ApplicationShare application) {
		super(application);
	}

}
