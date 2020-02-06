package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * パラメータ：期間中の積立年休残数を取得する
 * @author shuichi_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class GetRsvLeaRemNumWithinPeriodParam {

	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 集計期間 */
	private DatePeriod aggrPeriod;
	/** モード */
	private InterimRemainMngMode mode;
	/** 基準日 */
	private GeneralDate criteriaDate;
	/** 翌月管理データ取得フラグ */
	private boolean isGetNextMonthData;
	/** 年休情報消滅時リスト */
	private List<AnnualLeaveInfo> lapsedAnnualLeaveInfos;
	/** 上書きフラグ */
	private Optional<Boolean> isOverWrite;
	/** 上書き用の暫定管理データ */
	private Optional<List<TmpReserveLeaveMngWork>> forOverWriteList;
	/** 不足分付与残数データ出力区分 */
	private Optional<Boolean> isOutputForShortage;
	/** 集計開始日を締め開始日として扱う　（締め開始日を確認しない） */
	private Optional<Boolean> isNoCheckStartDate;
	/** 前回の積立年休の集計結果 */
	private Optional<AggrResultOfReserveLeave> prevReserveLeave;
}
