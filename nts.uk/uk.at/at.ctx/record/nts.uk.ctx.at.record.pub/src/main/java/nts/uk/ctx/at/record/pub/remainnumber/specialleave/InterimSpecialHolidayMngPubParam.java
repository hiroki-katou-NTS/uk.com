package nts.uk.ctx.at.record.pub.remainnumber.specialleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ManagermentAtr;

/**
 * 特別休暇暫定データ パラメータ
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class InterimSpecialHolidayMngPubParam {

	/** 特別休暇暫定ID */
	private String specialHolidayId;

	/**	特別休暇コード */
	private int specialHolidayCode;

	/**	予定実績区分 */
	private ManagermentAtr mngAtr;

	/**時間特休使用 */
	private Optional<UseTime> useTimes;

	/**	特休使用 */
	private Optional<UseDay> useDays;

	/**
	 * コンストラクタ
	 */
	public InterimSpecialHolidayMngPubParam() {
		useTimes = Optional.empty();
		useDays = Optional.empty();
	}


}
