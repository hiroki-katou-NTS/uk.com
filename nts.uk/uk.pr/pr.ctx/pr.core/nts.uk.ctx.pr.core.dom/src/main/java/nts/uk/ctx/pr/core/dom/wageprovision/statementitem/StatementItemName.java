package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 明細書項目名称
 */
@Getter
public class StatementItemName extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * カテゴリ区分
	 */
	private CategoryAtr categoryAtr;

	/**
	 * 項目名コード
	 */
	private ItemNameCode itemNameCd;

	/**
	 * 名称
	 */
	private ItemName name;

	/**
	 * 略名
	 */
	private ItemShortName shortName;

	/**
	 * その他言語名称
	 */
	private Optional<OtherLanguageName> otherLanguageName;

	/**
	 * 英語名称
	 */
	private Optional<EnglishName> englishName;

	public StatementItemName(String cid, int categoryAtr, String itemNameCd, String name, String shortName,
			String otherLanguageName, String englishName) {
		this.cid = cid;
		this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
		this.itemNameCd = new ItemNameCode(itemNameCd);
		this.name = new ItemName(name);
		this.shortName = new ItemShortName(shortName);
		this.otherLanguageName = otherLanguageName == null ? Optional.empty()
				: Optional.of(new OtherLanguageName(otherLanguageName));
		this.englishName = englishName == null ? Optional.empty() : Optional.of(new EnglishName(englishName));
	}
}
