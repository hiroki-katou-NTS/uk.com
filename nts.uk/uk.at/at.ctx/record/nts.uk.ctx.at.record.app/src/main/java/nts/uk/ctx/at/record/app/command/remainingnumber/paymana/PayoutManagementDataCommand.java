package nts.uk.ctx.at.record.app.command.remainingnumber.paymana;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.HolidayAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDataRemainUnit;

@Getter
public class PayoutManagementDataCommand {

	// 振出データID
	private String payoutId;

	private String cID;

	// 社員ID
	private String sID;

    private String employeeId;
	// 日付不明
	private boolean unknownDate;
	
	// 年月日
	private GeneralDate dayoffDate;

	// 使用期限日
	private GeneralDate expiredDate;

	// 法定内外区分
	private int lawAtr;

	// 発生日数
	private Double occurredDays;

	// 未使用日数
	private Double unUsedDays;

	// 振休消化区分
	private int stateAtr;
	
	private boolean checkBox;

}
