package nts.uk.ctx.pr.proto.app.find.layout.line;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.pr.proto.app.find.layout.detail.LayoutMasterDetailDto;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;

@Value
public class LayoutMasterLineDto {
	/**会社コード	 */
	private String companyCode;
	/**開始年月	 */
	private int startYM;
	/**明細書コード	 */
	private String stmtCode;
	/**終了年月	 */
	private int endYM;
	/**自動採番の行番号	 */
	private String autoLineId;
	/**表示区分	 */
	private int lineDispayAtr;
	/**画面の表示位置	 */
	private int linePosition;
	/**カテゴリ区分	 */
	private int categoryAtr;
	private List<LayoutMasterDetailDto> details;
	
	public static LayoutMasterLineDto fromDomain(LayoutMasterLine domain){
		return new LayoutMasterLineDto(domain.getCompanyCode().v(), 
				domain.getStartYM().v(),
				domain.getStmtCode().v(),
				domain.getEndYM().v(),
				domain.getAutoLineId().v(), 
				domain.getLineDispayAttribute().value,
				domain.getLinePosition().v(),
				domain.getCategoryAtr().value,
				new ArrayList<>());
		
	}
}
