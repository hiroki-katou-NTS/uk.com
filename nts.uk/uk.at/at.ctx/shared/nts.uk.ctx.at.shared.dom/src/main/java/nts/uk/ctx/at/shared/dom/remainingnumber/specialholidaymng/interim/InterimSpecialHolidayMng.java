package nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;

/**
 * 特別休暇暫定データ
 *
 * 暫定特別休暇データ -  cả 2 tên này đều là đúng cơ mà lộn chữ
 *
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InterimSpecialHolidayMng extends InterimRemain {

	/**	特別休暇コード */
	private int specialHolidayCode;

	/**	管理単位区分 */
	private ManagermentAtr mngAtr;

	/**時間特休使用 */
	private Optional<UseTime> useTimes;

	/**	特休使用 */
	private Optional<UseDay> useDays;
	/** 時間休暇種類*/
	private Optional<DigestionHourlyTimeType>  appTimeType;

	public InterimSpecialHolidayMng(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr,
			RemainType remainType, int specialHolidayCode, ManagermentAtr mngAtr,
			Optional<UseTime> useTimes, Optional<UseDay> useDays ,Optional<DigestionHourlyTimeType> appTimeType) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.specialHolidayCode = specialHolidayCode;
		this.mngAtr = mngAtr;
		this.useTimes = useTimes;
		this.useDays = useDays;
		this.appTimeType = appTimeType;
	}

	public void setParentValue(InterimRemain interimRemain){
		this.setRemainManaID(interimRemain.getRemainManaID());
		this.setSID(interimRemain.getSID());
		this.setYmd(interimRemain.getYmd());
		this.setCreatorAtr(interimRemain.getCreatorAtr());
		this.setRemainType(interimRemain.getRemainType());
	}

	public void set(InterimSpecialHolidayMng domain) {
		this.specialHolidayId=domain.specialHolidayId;
		this.specialHolidayCode=domain.specialHolidayCode;
		this.mngAtr=domain.mngAtr;
		this.useDays=domain.useDays;
		this.useTimes=domain.useTimes;
	}



}
