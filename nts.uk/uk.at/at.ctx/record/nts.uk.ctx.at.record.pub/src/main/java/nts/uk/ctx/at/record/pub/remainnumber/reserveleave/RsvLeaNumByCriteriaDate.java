package nts.uk.ctx.at.record.pub.remainnumber.reserveleave;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * 基準日時点積立年休残数
 * @author shuichi_ishida
 */
@Getter
@AllArgsConstructor
public class RsvLeaNumByCriteriaDate {

	/** 積立年休情報 */
	private ReserveLeaveInfo reserveLeaveInfo;
	/** 積立年休付与残数データ */
	private List<RsvLeaGrantRemainingExport> grantRemainingList;
	/** 暫定積立年休管理データ */
	private List<TmpReserveLeaveMngExport> tmpManageList;
	/** 積立付与年月日 */
	private Optional<GeneralDate> grantDate;
	/** 積立年休残日数 */
	private ReserveLeaveRemainingDayNumber remainingDays;
}
