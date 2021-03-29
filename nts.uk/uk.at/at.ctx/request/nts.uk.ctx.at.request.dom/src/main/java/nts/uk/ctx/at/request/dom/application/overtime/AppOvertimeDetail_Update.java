package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;

/**
 * 時間外時間の詳細
 */
@AllArgsConstructor
@Getter
@Setter
public class AppOvertimeDetail_Update extends DomainObject {


	/**
	 * 年月
	 */
	@Setter
	private YearMonth yearMonth;

	/**
	 * 36協定時間
	 */
	private Time36Agree time36Agree;
	
	/**
	 * 36協定上限時間
	 */
	private Time36AgreeUpperLimit time36AgreeUpperLimit;

	public AppOvertimeDetail_Update() {
		super();
		this.yearMonth = new YearMonth(0);
		this.time36Agree = new Time36Agree();
		this.time36AgreeUpperLimit = new Time36AgreeUpperLimit();
	}

}
