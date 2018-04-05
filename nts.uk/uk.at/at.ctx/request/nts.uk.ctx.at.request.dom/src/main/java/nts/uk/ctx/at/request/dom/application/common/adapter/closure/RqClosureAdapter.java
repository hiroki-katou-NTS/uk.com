package nts.uk.ctx.at.request.dom.application.common.adapter.closure;

import java.util.Optional;

public interface RqClosureAdapter {

	// 処理年月と締め期間を取得する
	Optional<PresentClosingPeriodImport> getClosureById(String cId, int closureId);
}
