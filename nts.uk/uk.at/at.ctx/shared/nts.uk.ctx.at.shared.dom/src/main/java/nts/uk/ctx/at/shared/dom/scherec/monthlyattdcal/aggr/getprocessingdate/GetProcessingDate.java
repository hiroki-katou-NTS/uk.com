package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.getprocessingdate;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;

/**
 * 処理日を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).月の勤怠計算.月別集計処理.月別実績集計処理.月次集計Mgrクラス.アルゴリズム.処理日を取得する
 * 
 * @author tutk
 *
 */
@Stateless
public class GetProcessingDate {
	@Inject
	private ShareEmploymentAdapter employmentAdapter;

	public Optional<GeneralDate> getProcessingDate(String employeeId, GeneralDate date) {
		// Imported（就業）「所属雇用履歴」を取得する (Lấy dữ liệu)
		List<EmploymentHistShareImport> listEmploymentHis = this.employmentAdapter.findByEmployeeIdOrderByStartDate(employeeId);
		if (listEmploymentHis.isEmpty()) {
			return Optional.empty();
		}
		// 含まれる雇用履歴を取得する
		Optional<EmploymentHistShareImport> employmentHistoryImported = listEmploymentHis.stream()
				.filter(c -> date.afterOrEquals(c.getPeriod().start()) && date.beforeOrEquals(c.getPeriod().end()))
				.findFirst();
		if (employmentHistoryImported.isPresent()) {
			return Optional.of(date);
		}
		// 最も手前の雇用履歴を取得する
		employmentHistoryImported = listEmploymentHis.stream().filter(c -> date.beforeOrEquals(c.getPeriod().start()))
				.sorted((x, y) -> x.getPeriod().start().compareTo(y.getPeriod().start())).findFirst(); //ASC
		if (employmentHistoryImported.isPresent()) {
			return Optional.of(employmentHistoryImported.get().getPeriod().start());
		}
		// 最も過去の雇用履歴を取得する
		employmentHistoryImported = listEmploymentHis.stream().filter(c -> date.afterOrEquals(c.getPeriod().end()))
				.sorted((x, y) -> y.getPeriod().end().compareTo(x.getPeriod().end())).findFirst(); //DESC
		if (employmentHistoryImported.isPresent()) {
			return Optional.of(employmentHistoryImported.get().getPeriod().start());
		}
		return Optional.empty();
	}

}
