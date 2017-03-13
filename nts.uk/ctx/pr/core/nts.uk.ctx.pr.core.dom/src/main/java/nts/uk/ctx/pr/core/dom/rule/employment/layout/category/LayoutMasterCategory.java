package nts.uk.ctx.pr.core.dom.rule.employment.layout.category;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.LayoutCode;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.line.LayoutMasterLine;

/**
 * LayoutMasterCategory AggregateRoot
 * 
 * 
 *
 */
public class LayoutMasterCategory extends AggregateRoot {
	/** code */
	@Getter
	private CompanyCode companyCode;

	/** 開始年月 */
	@Getter
	private YearMonth startYM;

	/** 明細書コード */
	@Getter
	private LayoutCode stmtCode;
	
	@Getter
	private CategoryAtr ctAtr;

	@Getter
	private String historyId;
	
	/** 終了年月 */
	@Getter
	private YearMonth endYm;

	/** 明細書名 */
	@Getter
	private CategoryPosition ctgPos;
	
	@Getter
	private List<LayoutMasterLine> layoutMasterLines;

	public LayoutMasterCategory(CompanyCode companyCode, YearMonth startYM, LayoutCode stmtCode,CategoryAtr categoryAtr, YearMonth endYm,
			 CategoryPosition ctgPos, String historyId) {
		super();
		this.companyCode = companyCode;
		this.startYM = startYM;
		this.stmtCode = stmtCode;
		this.ctAtr = categoryAtr;
		this.endYm = endYm;
		this.ctgPos = ctgPos;
		this.historyId = historyId;
	}

	/**
	 * create From Java Type
	 * 
	 * @return LayoutMasterCategory
	 */
	public static LayoutMasterCategory createFromJavaType(String companyCode, int startYM,
			String stmtCode,int ctgAtr, int endYm,int ctgPos, String historyId) {
		
		return new LayoutMasterCategory(new CompanyCode(companyCode), new YearMonth(startYM), 
				new LayoutCode(stmtCode),EnumAdaptor.valueOf(ctgAtr, CategoryAtr.class),
				new YearMonth(endYm), new CategoryPosition(ctgPos), historyId);
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param startYm
	 * @param stmtCode
	 * @param categoryAtr
	 * @param endYm
	 * @param ctgPos
	 * @return
	 */
	public static LayoutMasterCategory createFromDomain(CompanyCode companyCode,
			YearMonth startYm, LayoutCode stmtCode, CategoryAtr categoryAtr,
			YearMonth endYm, CategoryPosition ctgPos, String historyId){
		
		return new LayoutMasterCategory(companyCode, startYm, stmtCode, categoryAtr, endYm, ctgPos, historyId);
	}
}
