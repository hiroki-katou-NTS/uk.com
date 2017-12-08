package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceFormatDto;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DisplayFormatSelectionProcessor {

	@Inject
	private DisplayFormatSelectionRepository repository;

	/**
	 * 対応するドメインモデル「会社の日別実績の修正のフォーマット」をすべて取得する
	 * @return 「会社の日別実績の修正のフォーマット」一覧
	 */
	public List<DailyPerformanceFormatDto> getDailyPerformanceFormatList() {
		return repository.getDailyPerformanceFormatList(AppContexts.user().companyId());
	}
}
