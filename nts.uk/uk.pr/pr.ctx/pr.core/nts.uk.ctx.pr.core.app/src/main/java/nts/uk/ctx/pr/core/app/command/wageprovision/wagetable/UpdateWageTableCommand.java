package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.Arrays;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.shr.com.context.AppContexts;

/**
 * 賃金テーブル
 */
@NoArgsConstructor
@Data
public class UpdateWageTableCommand {

	/**
	 * 賃金テーブルコード
	 */
	private String wageTableCode;

	/**
	 * 賃金テーブル名
	 */
	private String wageTableName;

	/**
	 * 備考情報
	 */
	private String remarkInformation;

	private YearMonthHistoryItemCommand history;
	
	private ElementRangeSettingCommand elementRange;
	
	private WageTableContentCommand wageTableContent;

	public WageTable toWageTableDomain() {
		return new WageTable(AppContexts.user().companyId(), wageTableCode, wageTableName, 0,
				remarkInformation, null);
	}

	public WageTableHistory toWageTableHistoryDomain() {
		return new WageTableHistory(AppContexts.user().companyId(), wageTableCode,
				Arrays.asList(history.fromCommandToDomain()));
	}
	
}
