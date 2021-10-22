package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;

/**
 * @author thanh_nx
 *
 *         年月日での変更要求一覧
 */
@AllArgsConstructor
@Getter
public class ChangeRequestByDateList {

	// 明細
	private final List<ChangeRequestByDate> changeRequestList;

	// [1] 年月日での変更要求で上書きする
	public VacationDetails overwriteChangeReqByDate(VacationDetails before) {
		val after = new VacationDetails(before.getLstAcctAbsenDetail().stream()
				.filter(x -> x.getDateOccur().getDayoffDate().isPresent() && this.changeRequestList.stream()
						.noneMatch(y -> y.getDate().equals(x.getDateOccur().getDayoffDate().get())))
				.collect(Collectors.toList()));
		after.addDetail(this.changeRequestList.stream().flatMap(x -> x.getVacDetail().getLstAcctAbsenDetail().stream())
				.collect(Collectors.toList()));
		return after;
	}
}
