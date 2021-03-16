package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;


import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.年休管理.暫定年休管理データ.暫定年休管理データ
 * @author do_dt
 *
 */
@Getter
@Setter
public class TmpAnnualHolidayMng extends InterimRemain {
	
	/**
	 * 時間休暇種類
	 */
	private DigestionHourlyTimeType timeBreakType;
	/**
	 * 勤務種類
	 */
	private WorkTypeCode workTypeCode;
	/**
	 * 年休使用数
	 */
	private AnnualLeaveUsedNumber useNumber;
	
	public TmpAnnualHolidayMng(String annualId, String sid, GeneralDate ymd, CreateAtr creatorAtr) {
		
		this(annualId, sid, ymd, creatorAtr, new DigestionHourlyTimeType(), 
				new WorkTypeCode(""), new AnnualLeaveUsedNumber());
	}

	public TmpAnnualHolidayMng(String annualId, String sid, GeneralDate ymd, 
			CreateAtr creatorAtr, DigestionHourlyTimeType timeBreakType,
			WorkTypeCode workTypeCode, AnnualLeaveUsedNumber useNumber) {
		
		super(annualId, sid, ymd, creatorAtr, RemainType.ANNUAL);
		this.timeBreakType = timeBreakType;
		this.workTypeCode = workTypeCode;
		this.useNumber = useNumber;
	}
}
