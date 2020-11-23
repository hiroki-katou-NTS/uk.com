package nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterimRemainDto {
	/**
	 * 残数管理データID
	 */
	private String remainManaID;
	/**
	 * 社員ID
	 */
	private String sID;
	/**
	 * 対象日
	 */
	private String ymd;
	/**
	 * 作成元区分
	 */
	private Integer creatorAtr;
	/**
	 * 残数種類
	 */
	private Integer remainType;
	/**
	 * 残数分類
	 */
	private Integer remainAtr;
}
