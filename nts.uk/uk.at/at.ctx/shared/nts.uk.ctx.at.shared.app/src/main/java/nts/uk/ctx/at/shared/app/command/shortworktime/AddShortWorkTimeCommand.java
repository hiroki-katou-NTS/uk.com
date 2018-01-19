/**
 * 
 */
package nts.uk.ctx.at.shared.app.command.shortworktime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;

@Setter
public class AddShortWorkTimeCommand implements SWorkTimeHistItemGetMemento{
	
	@PeregEmployeeId
	private String employeeId;
	/**
	 * 短時間開始日
	 */
	@PeregItem("IS00102")
	@Getter
	private GeneralDate startDate;
	
	private String historyId;
	/**
	 * 短時間終了日
	 */
	@PeregItem("IS00103")
	@Getter
	private GeneralDate endDate;

	/**
	 * 短時間勤務区分
	 */
	@PeregItem("IS00104")
	private BigDecimal childCareAtr;

	/**
	 * 育児開始1
	 */
	@PeregItem("IS00106")
	public BigDecimal startTime1;

	/**
	 * 育児終了1
	 */
	@PeregItem("IS00107")
	public BigDecimal endTime1;

	/**
	 * 育児開始2
	 */
	@PeregItem("IS00109")
	public BigDecimal startTime2;

	/**
	 * 育児終了2
	 */
	@PeregItem("IS00110")
	public BigDecimal endTime2;

	@Override
	public String getHistoryId() {
		return this.historyId;
	}

	@Override
	public ChildCareAtr getChildCareAtr() {
		return EnumAdaptor.valueOf(this.childCareAtr.intValue(), ChildCareAtr.class);
	}

	@Override
	public List<SChildCareFrame> getLstTimeSlot() {
		return ShortWorkTimeUtils.getLstTimeSlot(startTime1, endTime1, startTime2, endTime2);
	}

	@Override
	public String getEmployeeId() {
		return this.employeeId;
	}

	/**
	 * IS00111 11 備考
	 */

	/**
	 * IS00114 14 "家族の同一の要介護状態について介護短時間勤務をしたことがあるか
	 */

	/**
	 * IS00115 15 "対象の家族についてのこれまでの介護休業および介護短時間勤務の日数
	 */

	/**
	 * IS00116 16 "子の区分
	 */

	/**
	 * IS00117 17 "縁組成立の年月日
	 */
}
