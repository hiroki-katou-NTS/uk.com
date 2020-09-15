package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.adapter.person.EmpBasicInfoImport;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementExcessInfoImport;

/**
 * 	従業員用の時間外時間表示
 *
 */
@Data
@Builder
public class EmployeesOvertimeDisplayDto {
	/** ログイン者の締めID */
	private int closureID;
	
	/** 対象社員の個人情報 */
	private EmpBasicInfoImport empInfo;
	
	/** 対象社員の各月の時間外時間 */
	private List<YearMonthOvertimeHourse> overtimeHourses;
	
	/** 対象社員の年間超過回数 */
	private AgreementExcessInfoImport agreeInfo;
	
	/** 当月の締め情報 */
	private PresentClosingPeriodImport closingPeriod;
	
	/** 当月含む年 */
	private int year;
	
	/** 翌月含む年 */
	private int year2;
	
	/** 表示する年 */
	private int displayYear;
	
}
