package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.vacation.holidayover60h.HolidayOver60h;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;

/**
 * 60H超休情報残数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class HolidayOver60hRemainingNumber implements Cloneable {

		/**
		 * 60H超休（マイナスあり）
		 */
		private HolidayOver60h holidayOver60hWithMinus;

		/**
		 * 60H超休（マイナスなし）
		 */
		private HolidayOver60h holidayOver60hNoMinus;

		/**
		 * 繰越時間
		*/
		private AnnualLeaveRemainingTime carryForwardTimes;

		/**
		 * 未消化数
		*/
		private AnnualLeaveRemainingTime holidayOver60hUndigestNumber;

		/**
		 * コンストラクタ
		 */
		public HolidayOver60hRemainingNumber(){
			this.holidayOver60hNoMinus = new HolidayOver60h();
			this.holidayOver60hWithMinus = new HolidayOver60h();
			this.carryForwardTimes = new AnnualLeaveRemainingTime(0);
			this.holidayOver60hUndigestNumber = new AnnualLeaveRemainingTime(0);
		}
	}