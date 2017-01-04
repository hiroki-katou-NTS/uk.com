package nts.uk.ctx.pr.core.dom.layout.line;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.layout.LayoutCode;
import nts.uk.ctx.pr.core.dom.layout.detail.LayoutMasterDetail;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;

public class LayoutMasterLine extends AggregateRoot {

	@Getter
	private CompanyCode companyCode;

	@Getter
	private YearMonth startYM;

	@Getter
	private LayoutCode stmtCode;
	@Getter
	private String historyId;

	@Getter
	private YearMonth endYM;

	@Getter
	private AutoLineId autoLineId;

	@Getter
	private LineDispAtr lineDispayAttribute;

	@Getter
	private LinePosition linePosition;
	
	@Getter
	private CategoryAtr categoryAtr;

	@Getter
	private List<LayoutMasterDetail> layoutMasterDetails;

	public LayoutMasterLine(CompanyCode companyCode, YearMonth startYM, LayoutCode stmtCode, YearMonth endYM,
			AutoLineId autoLineId, CategoryAtr categoryAtr, LineDispAtr lineDispayAttribute, LinePosition linePosition,
			String historyId) {
		super();
		this.companyCode = companyCode;
		this.startYM = startYM;
		this.stmtCode = stmtCode;
		this.endYM = endYM;
		this.autoLineId = autoLineId;
		this.categoryAtr = categoryAtr;
		this.lineDispayAttribute = lineDispayAttribute;
		this.linePosition = linePosition;
		this.historyId = historyId;
	}

	public static LayoutMasterLine createFromJavaType(String companyCode, int startYM, String stmtCode, int endYM,
			String autoLineId, int lineDispayAttribute, int linePosition, int categoryAtr, String historyId) {

		return new LayoutMasterLine(
				new CompanyCode(companyCode), 
				new YearMonth(startYM), 
				new LayoutCode(stmtCode),
				new YearMonth(endYM), 
				new AutoLineId(autoLineId),
				EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class),
				EnumAdaptor.valueOf(lineDispayAttribute, LineDispAtr.class), 
				new LinePosition(linePosition),
				historyId
				);

	}

	public static LayoutMasterLine createFromDomain(
			CompanyCode companyCode, 
			YearMonth startYM, 
			LayoutCode stmtCode,
			YearMonth endYM, 
			AutoLineId autoLineId,
			CategoryAtr categoryAtr,
			LineDispAtr lineDispayAttribute, 
			LinePosition linePosition,
			String historyId){
		
		return new LayoutMasterLine(companyCode, startYM, stmtCode, endYM, autoLineId, 
				categoryAtr, lineDispayAttribute, linePosition,
				historyId);
	}
	
}
