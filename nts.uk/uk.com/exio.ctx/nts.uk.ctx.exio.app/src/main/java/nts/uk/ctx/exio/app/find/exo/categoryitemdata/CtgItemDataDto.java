package nts.uk.ctx.exio.app.find.exo.categoryitemdata;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CtgItemDataDto {

	/**
	 * 項目NO
	 */
	private Integer itemNo;

	/**
	 * 項目名
	 */
	private String itemName;

	/**
	 * 予約語区分
	 */
	private Integer keywordAtr;

}
