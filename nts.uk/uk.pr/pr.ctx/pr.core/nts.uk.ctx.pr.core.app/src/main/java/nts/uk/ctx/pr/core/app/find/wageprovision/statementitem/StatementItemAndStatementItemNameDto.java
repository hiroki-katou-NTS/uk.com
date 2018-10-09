package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import java.util.Optional;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItem;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemName;

@Getter
@Value
public class StatementItemAndStatementItemNameDto {
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

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 略名
	 */
	private String shortName;

	/**
	 * その他言語名称
	 */
	private String otherLanguageName;

	/**
	 * 英語名称
	 */
	private String englishName;

	public static StatementItemAndStatementItemNameDto fromDomain(StatementItem domain1, Optional<StatementItemName> domain2) {
		return new StatementItemAndStatementItemNameDto(domain1.getCategoryAtr().value, domain1.getItemNameCd().v(),
				domain1.getSalaryItemId(), domain1.getDefaultAtr().value, domain1.getValueAtr().value,
				domain1.getDeprecatedAtr().value, 
				domain1.getSocialInsuaEditableAtr().map(i -> i.value).orElse(null),
				domain1.getIntergrateCd().map(i -> i.v()).orElse(null), 
				domain2.map(i -> i.getName().v()).orElse(null),
				domain2.map(i -> i.getShortName().v()).orElse(null), 
				domain2.map(i -> i.getOtherLanguageName().map(j -> j.v()).orElse(null)).orElse(null),
				domain2.map(i -> i.getEnglishName().map(j -> j.v()).orElse(null)).orElse(null));
	}
}
