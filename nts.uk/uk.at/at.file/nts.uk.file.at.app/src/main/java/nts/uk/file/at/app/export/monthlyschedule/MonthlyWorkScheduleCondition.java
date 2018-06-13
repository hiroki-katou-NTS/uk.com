/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.app.export.monthlyschedule;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.file.at.app.export.dailyschedule.FormOutputType;
import nts.uk.file.at.app.export.dailyschedule.PageBreakIndicator;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleSettingTotalOutput;

/**
 * The Class MonthlyWorkScheduleCondition.
 */
@Data
public class MonthlyWorkScheduleCondition {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The user id. */
	// ユーザID
	private String userId;

	/** The output type. */
	// 帳票出力種類
	private FormOutputType outputType;

	/** The page break indicator. */
	// 改ページ区分
	private PageBreakIndicator pageBreakIndicator;

	/** The total output setting. */
	// 月別勤務表用明細・合計出力設定
	private WorkScheduleSettingTotalOutput totalOutputSetting;

}
