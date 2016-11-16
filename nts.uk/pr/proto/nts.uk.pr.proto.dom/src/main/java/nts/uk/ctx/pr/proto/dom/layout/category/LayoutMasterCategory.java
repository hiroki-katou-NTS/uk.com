package nts.uk.ctx.pr.proto.dom.layout.category;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.layout.LayoutAtr;
import nts.uk.ctx.pr.proto.dom.layout.LayoutCode;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutName;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;

/**
 * LayoutMasterCategory valueObject
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

	/** 終了年月 */
	@Getter
	private YearMonth endYM;

	/** レイアウト区分 */
	@Getter
	private LayoutAtr layoutAtr;

	/** 明細書名 */
	@Getter
	private CategoryPosition ctgPos;
	
	@Getter
	private CategoryAtr ctAtr;
	
	@Getter
	private List<LayoutMasterLine> layoutMasterLines;

	public LayoutMasterCategory(CompanyCode companyCode, YearMonth startYM, LayoutCode stmtCode, YearMonth endYM,
			LayoutAtr layoutAtr, CategoryPosition ctgPos ,CategoryAtr atr) {
		super();
		this.companyCode = companyCode;
		this.startYM = startYM;
		this.stmtCode = stmtCode;
		this.endYM = endYM;
		this.layoutAtr = layoutAtr;
		this.ctgPos = ctgPos;
		this.ctAtr = ctAtr;

	}

	/**
	 * create From Java Type
	 * 
	 * @return LayoutMasterCategory
	 */

	public static LayoutMasterCategory createFromJavaType(String companyCode, int startYM,
			String stmtCode, int endYM, int layoutAtr ,int ctgPos ,int ctgAtr) {
		
		return new LayoutMasterCategory(new CompanyCode(companyCode), new YearMonth(startYM), new LayoutCode(stmtCode),
				new YearMonth(endYM), EnumAdaptor.valueOf(layoutAtr, LayoutAtr.class), new CategoryPosition(ctgPos),EnumAdaptor.valueOf(ctgAtr, CategoryAtr.class));

	}
}
