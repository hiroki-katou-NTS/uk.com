package nts.uk.ctx.pr.proto.app.layout.category.find;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;

@Value
public class LayoutMasterCategoryDto {
	/**会社コード 	 */
	private String companyCd;
	/**明細書コード*/
	private String layoutCd;
	/**開始年月*/
	private int startYm;
	/**カテゴリ区分	 */
	private int categoryAtr;
	/**終了年月	 */
	private int endYM;
	/**カテゴリ表示位置 */
	private int ctgPos;
	
	public static LayoutMasterCategoryDto fromDomain(LayoutMasterCategory domain){
		return new LayoutMasterCategoryDto(
				domain.getCompanyCode().v(), 
				domain.getStmtCode().v(), 
				domain.getStartYM().v(),
				domain.getCtAtr().value,
				domain.getEndYM().v(),
				domain.getCtgPos().v());
	}
}
