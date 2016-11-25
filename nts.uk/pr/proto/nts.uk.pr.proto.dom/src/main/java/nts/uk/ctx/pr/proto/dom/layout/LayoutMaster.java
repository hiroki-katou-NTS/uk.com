package nts.uk.ctx.pr.proto.dom.layout;

import java.util.List;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;

public class LayoutMaster extends AggregateRoot {

	/** code */
	@Getter
	private CompanyCode companyCode;
	
	/** 開始年月 */
	@Getter
	@Setter
	private YearMonth startYM;
	
	/** 明細書コード */
	@Getter
	private LayoutCode stmtCode;
	
	/** 終了年月 */
	@Getter
	@Setter
	private YearMonth endYm;
	
	/** レイアウト区分 */
	@Getter
	private LayoutAtr layoutAtr;
	
	/** 明細書名 */
	@Getter
	private LayoutName stmtName;
	
	/** 明細書のレイアウトのカテゴリ情報 */
	@Getter
	private List<LayoutMasterCategory> layoutMasterCategories;

	public LayoutMaster(CompanyCode companyCode, YearMonth startYM, LayoutCode stmtCode, YearMonth endYm, LayoutAtr layoutAtr,
			LayoutName stmtName) {
		super();
		this.companyCode = companyCode;
		this.startYM = startYM;
		this.stmtCode = stmtCode;
		this.endYm = endYm;
		this.layoutAtr = layoutAtr;
		this.stmtName = stmtName;
	}

	/**
	 * create From Java Type
	 * @return LayoutMaster
	 */
	public static LayoutMaster createFromJavaType(String companyCode, int startYM, String stmtCode, int endYm, int layoutAtr,
			String stmtName){
		if (stmtName.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		
		return new LayoutMaster(
				new CompanyCode(companyCode), 
				new YearMonth(startYM), 
				new LayoutCode(stmtCode), 
				new YearMonth(endYm),
				EnumAdaptor.valueOf(layoutAtr, LayoutAtr.class), 
				new LayoutName(stmtName));
	}
	
	public void adjustForNextHistory(LayoutMaster nextHistory) {
		this.endYm = nextHistory.getStartYM().previousMonth();
	}
}
