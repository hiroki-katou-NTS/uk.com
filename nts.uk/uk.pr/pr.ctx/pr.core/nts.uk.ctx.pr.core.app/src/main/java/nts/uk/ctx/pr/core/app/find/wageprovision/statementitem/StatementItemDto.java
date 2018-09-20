package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItem;

@Value
public class StatementItemDto {

	/**
	 * カテゴリ区分
	 */
	private int categoryAtr;

	/**
	 * 項目名コード
	 */
	private String itemNameCd;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 既定区分
	 */
	private int defaultAtr;

	/**
	 * 値の属性
	 */
	private int valueAtr;

	/**
	 * 廃止区分
	 */
	private int deprecatedAtr;

	/**
	 * 社会保険対象変更区分
	 */
	private Integer socialInsuaEditableAtr;

	/**
	 * 統合コード
	 */
	private String intergrateCd;

	public static StatementItemDto fromDomain(StatementItem domain) {
		return new StatementItemDto(domain.getCategoryAtr().value, domain.getItemNameCd().v(),
				domain.getSalaryItemId(), domain.getDefaultAtr().value, domain.getValueAtr().value,
				domain.getDeprecatedAtr().value, domain.getSocialInsuaEditableAtr().map(i -> i.value).orElse(null),
				domain.getIntergrateCd().map(i -> i.v()).orElse(null));
	}
}
