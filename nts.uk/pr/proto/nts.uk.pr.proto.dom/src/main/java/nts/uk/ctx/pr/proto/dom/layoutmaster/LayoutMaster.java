package nts.uk.ctx.pr.proto.dom.layoutmaster;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.enums.LayoutAtr;

public class LayoutMaster extends AggregateRoot {

	/** code */
	@Getter
	private CompanyCode code;
	
	/** 開始年月 */
	@Getter
	private Date startYM;
	
	/** 明細書コード */
	@Getter
	private LayoutCode stmtCode;
	
	/** 終了年月 */
	@Getter
	private Date endYM;
	
	/** レイアウト区分 */
	@Getter
	private LayoutAtr layoutAtr;
	
	/** 明細書名 */
	@Getter
	private LayoutName stmtName;
	
	/** 明細書のレイアウトのカテゴリ情報 */
	@Getter
	private List<LayoutMasterCategory> layoutMasterCategories;

	public LayoutMaster(CompanyCode code, Date startYM, LayoutCode stmtCode, Date endYM, LayoutAtr layoutAtr,
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

	
	
}
