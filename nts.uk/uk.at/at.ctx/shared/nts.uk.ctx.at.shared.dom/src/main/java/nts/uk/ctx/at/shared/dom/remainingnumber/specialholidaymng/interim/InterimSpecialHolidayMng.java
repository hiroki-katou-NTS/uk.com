package nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;

/**
 * 特別休暇暫定データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InterimSpecialHolidayMng extends InterimRemain{

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
	public InterimSpecialHolidayMng() {
		useTimes = Optional.empty();
		useDays = Optional.empty();
	}

	/**
	 * 親クラス（RemainAtr）の値をセット
	 * @param interimRemain
	 */
	public void setParentValue(InterimRemain interimRemain){
		this.specialHolidayId="";
		this.setRemainManaID(interimRemain.getRemainManaID());
		this.setSID(interimRemain.getSID());
		this.setYmd(interimRemain.getYmd());
		this.setCreatorAtr(interimRemain.getCreatorAtr());
		this.setRemainType(interimRemain.getRemainType());
//		this.setRemainAtr(interimRemain.getRemainAtr());
//		this.useTimes = Optional.empty();
//		this.useDays = Optional.empty();
	}
}
