package nts.uk.ctx.pr.core.dom.layout;

import java.util.List;

import nts.arc.time.YearMonth;

public interface LayoutHistRepository {

	/**
	 * QSTMT_STMT_LAYOUT_HIST - SEL-1
	 * @return
	 */
	List<LayoutHist> getBy_SEL_1(String ccd, YearMonth baseYM);
}
