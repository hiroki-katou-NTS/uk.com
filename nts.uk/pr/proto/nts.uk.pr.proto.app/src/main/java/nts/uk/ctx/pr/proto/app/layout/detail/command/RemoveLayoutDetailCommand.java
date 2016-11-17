package nts.uk.ctx.pr.proto.app.layout.detail.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveLayoutDetailCommand {
	/**会社ＣＤ	 */
	private String companyCode;
	/**明細書コード	 */
	private String layoutCode;
	/**明細書コード	 */
	private int startYm;
	/**カテゴリ区分	 */
	private int categoryAtr;
	/**項目CD	 */
	private String itemCode;
}
