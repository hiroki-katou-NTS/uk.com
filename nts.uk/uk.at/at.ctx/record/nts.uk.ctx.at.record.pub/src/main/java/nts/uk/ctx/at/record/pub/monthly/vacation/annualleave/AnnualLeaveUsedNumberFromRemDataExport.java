package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveConditionInfo;

/**
 * 年休付与残数データ
 */
@Getter
@AllArgsConstructor
public class AnnualLeaveUsedNumberFromRemDataExport {

	/** 年休付与条件情報 */
	private Optional<AnnualLeaveConditionInfo> annualLeaveConditionInfo;

}
