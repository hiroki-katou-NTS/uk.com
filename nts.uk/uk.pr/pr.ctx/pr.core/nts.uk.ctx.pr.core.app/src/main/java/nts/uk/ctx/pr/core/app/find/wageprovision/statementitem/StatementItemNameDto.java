package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemName;

@Value
public class StatementItemNameDto {
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

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

	public static StatementItemNameDto fromDomain(StatementItemName domain) {
		return new StatementItemNameDto(domain.getCid(), domain.getSalaryItemId(), domain.getName().v(),
				domain.getShortName().v(), domain.getOtherLanguageName().map(i -> i.v()).orElse(null),
				domain.getEnglishName().map(i -> i.v()).orElse(null));
	}
}
