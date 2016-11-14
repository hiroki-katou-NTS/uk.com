package nts.uk.ctx.pr.proto.dom.layout;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;

public class LayoutMaster extends AggregateRoot {

	/** code */
	@Getter
	private CompanyCode code;
	
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
	private LayoutName stmtName;
	
	/** 明細書のレイアウトのカテゴリ情報 */
	@Getter
	private List<LayoutMasterCategory> layoutMasterCategories;

	public LayoutMaster(CompanyCode code, YearMonth startYM, LayoutCode stmtCode, YearMonth endYM, LayoutAtr layoutAtr,
			LayoutName stmtName, List<LayoutMasterCategory> layoutMasterCategories) {
		super();
		this.code = code;
		this.startYM = startYM;
		this.stmtCode = stmtCode;
		this.endYM = endYM;
		this.layoutAtr = layoutAtr;
		this.stmtName = stmtName;
		this.layoutMasterCategories = layoutMasterCategories;
	}

	/**
	 * create From Java Type
	 * @return LayoutMaster
	 */
	public static LayoutMaster createFromJavaType(String code, int startYM, String stmtCode, int endYM, int layoutAtr,
			String stmtName, List<LayoutMasterCategory> layoutMasterCategories){
		//Date startYearMonth = new
		return null;// new LayoutMaster(code, startYM, stmtCode, endYM, layoutAtr, stmtName, layoutMasterCategories);
	}
	
	
}
