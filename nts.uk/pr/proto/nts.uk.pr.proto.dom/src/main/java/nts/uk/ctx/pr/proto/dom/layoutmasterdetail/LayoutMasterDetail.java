package nts.uk.ctx.pr.proto.dom.layoutmasterdetail;

import java.sql.Date;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.layoutmaster.LayoutCode;
/**
 * 
 * 明 細 書 マ ス タ 明 細
 *
 */
public class LayoutMasterDetail extends AggregateRoot{
	/**会社ＣＤ */
	@Getter
	private CompanyCode companyCode;
	/**カテゴリ区分 */
	@Getter
	private CategoryAtr categoryAttribute;
	
//	private RangeChecker alarm;
	/**計算方法 */
	@Getter
	private CalculationMethod calculationMethod;
	/**項目位置 */
	@Getter
	private ColumnPosition columnPosition;
	
//	private Distribute distribute;
	
//	private RangeChecker error;
	/**会社ＣＤ */
	@Getter
	private ItemCode itemCode;
	/**明細書コード*/
	@Getter
	private LayoutCode layoutCode;
	/**開始年月*/
	@Getter
	private Date startDate;
	/**合計対象区分 */
	@Getter
	private SumScopeAtr sumScopeAtr;
	
	public LayoutMasterDetail(CompanyCode companyCode, CategoryAtr categoryAttribute,
			CalculationMethod calculationMethod, ColumnPosition columnPosition,
			ItemCode itemCode, LayoutCode layoutCode, Date startDate, SumScopeAtr sumScopeAtr) {
		super();
		this.companyCode = companyCode;
		this.categoryAttribute = categoryAttribute;
		this.calculationMethod = calculationMethod;
		this.columnPosition = columnPosition;
		this.itemCode = itemCode;
		this.layoutCode = layoutCode;
		this.startDate = startDate;
		this.sumScopeAtr = sumScopeAtr;
	}
	
	
}
