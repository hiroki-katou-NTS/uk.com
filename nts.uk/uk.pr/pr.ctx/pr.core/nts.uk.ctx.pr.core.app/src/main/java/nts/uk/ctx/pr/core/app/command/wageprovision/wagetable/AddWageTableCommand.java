package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.shr.com.context.AppContexts;

/**
 * 賃金テーブル
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddWageTableCommand {

	/**
	 * 賃金テーブルコード
	 */
	private String wageTableCode;

	/**
	 * 賃金テーブル名
	 */
	private String wageTableName;

	/**
	 * 要素設定
	 */
	private int elementSetting;

	/**
	 * 備考情報
	 */
	private String remarkInformation;

	/**
	 * 要素情報
	 */
	private ElementInformationCommand elementInformation;

	private YearMonthHistoryItemCommand history;

	public WageTable toWageTableDomain() {
		return new WageTable(AppContexts.user().companyId(), wageTableCode, wageTableName, elementSetting,
				remarkInformation, elementInformation.fromCommandToDomain());
	}

	public WageTableHistory toWageTableHistoryDomain() {
		history.setHistoryID(IdentifierUtil.randomUniqueId());
		return new WageTableHistory(AppContexts.user().companyId(), wageTableCode,
				Arrays.asList(history.fromCommandToDomain()));
	}

}
