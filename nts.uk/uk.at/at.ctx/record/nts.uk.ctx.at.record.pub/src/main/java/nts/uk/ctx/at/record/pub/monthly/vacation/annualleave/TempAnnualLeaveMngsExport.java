package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 暫定年休管理データ
 */
@Getter
@AllArgsConstructor
public class TempAnnualLeaveMngsExport {

	/** 残数管理データID */
	private String remainManaID;
	/** 社員ID */
	private String sID;
	/** 対象日 */
	private GeneralDate ymd;
	/** 作成元区分 */
	private CreateAtr creatorAtr;
	/** 残数種類 */
	private RemainType remainType;
	/** 残数分類 */
	private RemainAtr remainAtr;
	/** 勤務種類 */
	private WorkTypeCode workTypeCode;
	/** 年休使用数 */
	private LeaveUsedNumber usedNumber;
	/** 時間休暇種類 */
	private  Optional<AppTimeType> appTimeType;

}
