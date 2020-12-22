package nts.uk.ctx.at.shared.app.command.ot.zerotime;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.HdFromHd;

/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class HdFromHdHolidayCommand {
	/** 会社ID */
	private String companyId;

	/** 変更前の休出枠NO */
	private int holidayWorkFrameNo;

	/** 変更後の法定内休出NO */
	private int calcOverDayEnd;

	/** 変更後の法定外休出NO */
	private int statutoryHd;

	/** 変更後の祝日休出NO */
	private int excessHd;

	public HdFromHd toDomain(String companyId) {
		return HdFromHd.createFromJavaType(companyId, this.holidayWorkFrameNo, this.calcOverDayEnd, this.statutoryHd,
				this.excessHd);
	}
}
