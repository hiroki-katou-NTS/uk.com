package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;

/**
 * @author thanh_nx
 *
 *         期間での変更要求
 */
@AllArgsConstructor
@Getter
public class ChangeRequestInPeriod {

	// 追加確定データ
	private final VacationDetails addConfirmData;

	// 上書き確定データ
	private final VacationDetails overwriteConfirmData;

	// 上書き暫定データ
	private final VacationDetails overwriteTemporaryData;

	// 上書き期間
	private final Optional<DatePeriod> overwritePeriod;

	// [1] 変更する
	public VacationDetails change(VacationDetails confirmData, VacationDetails temporaryData) {

		// ＄暫定
		val temporary = overwriteData(temporaryData, overwriteTemporaryData, overwritePeriod);

		// ＄確定
		val confirm = overwriteData(confirmData, overwriteConfirmData, overwritePeriod);

		// ＄確定
		addData(confirm, addConfirmData);

		return determineDigestOccrList(confirm, temporary);

	}

	// [1] 上書き
	private VacationDetails overwriteData(VacationDetails overwriteDataSource, VacationDetails overwriteData,
			Optional<DatePeriod> period) {
		if(!period.isPresent()) {
			return overwriteDataSource;
		}
		val result = new VacationDetails(
				overwriteDataSource.getLstAcctAbsenDetail().stream()
						.filter(x -> !x.getDateOccur().getDayoffDate().isPresent()  || (x.getDateOccur().getDayoffDate().isPresent()
								&& !period.get().contains(x.getDateOccur().getDayoffDate().get())))
						.collect(Collectors.toList()));
		result.getLstAcctAbsenDetail().addAll(overwriteData.getLstAcctAbsenDetail());
		return result;
	}

	// [2] 追加
	private void addData(VacationDetails dataSource, VacationDetails data) {
		dataSource.getLstAcctAbsenDetail().addAll(data.getLstAcctAbsenDetail());
	}

	// [3] 暫定と確定を一覧にする
	private VacationDetails determineDigestOccrList(VacationDetails confirmData, VacationDetails temporaryData) {
		VacationDetails result = new VacationDetails(new ArrayList<>());
		result.addDetail(confirmData.getLstAcctAbsenDetail());
		result.addDetail(temporaryData.getLstAcctAbsenDetail());
		return result;
	}

}
