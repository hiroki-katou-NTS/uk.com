package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.auth.dom.adapter.person.EmployeeBasicInforAuthImport;
import nts.uk.ctx.at.record.app.find.monthly.agreement.export.AgreementExcessInfoDto;

/**
 * 	従業員用の時間外時間表示
 */
@Data
@Builder
public class EmployeesOvertimeDisplayDto {
	/** ログイン者の締めID */
	private int closureID;
	
	/** 対象社員の個人情報 */
	private EmployeeBasicInforAuthImport empInfo;
	
	/** 対象社員の各月の時間外時間 */
	private List<YearMonthOvertime> ymOvertimes;
	
	/** 対象社員の年間超過回数 */
	private AgreementExcessInfoDto agreeInfo;
	
	/** 当月の締め情報 */
	private PresentClosingPeriodDto closingPeriod;
	
	/** 当月含む年 */
	private int yearIncludeThisMonth;
	
	/** 翌月含む年 */
	private int yearIncludeNextMonth;
	
	/** 表示する年 */
	private int displayYear;
	
}
