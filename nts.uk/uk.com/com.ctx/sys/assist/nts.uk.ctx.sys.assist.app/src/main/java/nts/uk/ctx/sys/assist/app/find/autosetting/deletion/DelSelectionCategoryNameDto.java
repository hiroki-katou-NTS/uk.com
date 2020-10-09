package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import lombok.Data;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.TextResourceHolderDto;

/**
 * 選択カテゴリ名称保存 DTO
 */
@Data
public class DelSelectionCategoryNameDto {
	/**
	 * カテゴリ名称
	 */
	private String categoryName;

	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * 削除禁止期間
	 */
	private String timeStop;

	/**
	 * 削除方法指定可能
	 */
	private int specifiedMethod;

	/**
	 * 削除時保存範囲
	 */
	private int storeRange;

	/**
	 * 削除期間区分
	 */
	private int periodDivision;

	/**
	 * 別会社区分
	 */
	private int separateCompClassification;

	/**
	 * システム種類
	 */
	private int systemType;

	private TextResourceHolderDto holder;

	public String convertTimeStop(String timeStopDel) {
		switch (Integer.parseInt(timeStopDel)) {
		case 6:
			return "６ヶ月";
		case 300:
			return "３年";
		case 1210:
			return "１２年１０カ月";
		default:
			return null;
		}
	}
}
