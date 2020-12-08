package nts.uk.ctx.at.record.pub.actualsituation.confirmstatusmonthly;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;

public interface ConfirmStatusMonthlyPub {
	// RQ586 : 月の実績の確認状況を取得する
	public Optional<StatusConfirmMonthEx> getConfirmStatusMonthly(String companyId, List<String> listEmployeeId, YearMonth yearmonthInput, Integer clsId, boolean clearState);
}
