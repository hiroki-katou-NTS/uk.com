package nts.uk.ctx.pr.core.dom.layout;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;

public class LayoutHist {

	/** code */
	@Getter
	private CompanyCode companyCode;
	
	/** 明細書コード */
	@Getter
	private LayoutCode stmtCode;
	
	@Getter
	private String histId;
	
	/** 開始年月 */
	@Getter
	@Setter
	private YearMonth startYm;
	
	/** 終了年月 */
	@Getter
	@Setter
	private YearMonth endYm;
	
	/** レイアウト区分 */
	@Getter
	private LayoutAtr layoutAtr;
	
}
