package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;

import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceFormatDto;

public interface DisplayFormatSelectionRepository {
	
	// 対応するドメインモデル「会社の日別実績の修正のフォーマット」をすべて取得する
	List<DailyPerformanceFormatDto> getDailyPerformanceFormatList(String companyId);
}
