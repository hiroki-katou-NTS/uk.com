package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveUsedNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.TempAnnualLeaveUsedNumberExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

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
	private CreateAtrExport creatorAtr;
	/** 残数種類 */
	private RemainType remainType;
	/** 勤務種類 */
	private String workTypeCode;
	/** 年休使用数 */
	private TempAnnualLeaveUsedNumberExport usedNumber;
	/** 時間休暇種類 */
	private Optional<AppTimeTypeExport> appTimeType;

}
