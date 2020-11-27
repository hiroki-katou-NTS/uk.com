package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.management.RuntimeErrorException;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.承認すべき申請の対象期間を取得する.承認すべき申請の対象期間を取得する
 * @author tutt
 *
 */
@Stateless
public class PresentClosingPeriodFinder {
	
	/**
	 * 承認すべき申請の対象期間を取得する
	 * 
	 * @return
	 */
	public GetYearProcessAndPeriodDto getPeriod(List<ClosureIdPresentClosingPeriod> closingPeriods) {

		List<GeneralDate> startDates = closingPeriods.stream()
				.map(c -> c.getCurrentClosingPeriod().getClosureStartDate()).collect(Collectors.toList());

		// 一番小さい締め開始日
		Optional<GeneralDate> startDate = startDates.stream().min(Comparator.comparing(GeneralDate::date));

		if (!startDate.isPresent()) {
			throw new RuntimeErrorException(new Error(), "Can't get Start Date");
		} else {
			// 終了日＝開始日 + ２年 - １日
			GeneralDate endDate = startDate.get().addYears(2).addDays(-1);

			YearMonth processingYm = closingPeriods.stream()
					.filter(c -> c.getCurrentClosingPeriod().getClosureStartDate() == startDate.get())
					.map(m -> m.getCurrentClosingPeriod().getProcessingYm()).collect(Collectors.toList()).get(0);

			return new GetYearProcessAndPeriodDto(processingYm, startDate.get(), endDate);
		}
	}
}
