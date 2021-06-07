package nts.uk.ctx.at.shared.dom.holidaymanagement.interim;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

/**
 * 
 * @author sonnlb
 * 
 *         暫定公休管理データ
 */
@Getter
@Setter
public class InterimHolidayMng extends InterimRemain {

	// 公休使用日数
	private double days;

	public InterimHolidayMng(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr,
			RemainType remainType, double days) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.days = days;
	}
}
