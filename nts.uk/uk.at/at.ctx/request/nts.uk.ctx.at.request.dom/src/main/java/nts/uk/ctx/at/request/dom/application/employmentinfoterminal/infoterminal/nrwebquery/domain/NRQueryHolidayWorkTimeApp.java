package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         休日出勤申請
 */
@Getter
public class NRQueryHolidayWorkTimeApp extends NRQueryApp {

	// 直行区分
	private NotUseAtr goOutAtr;

	// 直帰区分
	private NotUseAtr comeBackAtr;

	// 就業時間帯名
	private String workTimeName;

	// 勤務種類名
	private String workTypeName;

	// 外深夜時間
	private Optional<String> midnightIimeOutside;

	// 休出枠
	private List<NRQueryHolidayQuota> holidayQuotaLst;

	// 乖離理由
	private List<String> reasonDissocsLst;

	public NRQueryHolidayWorkTimeApp(NRQueryApp app, NotUseAtr goOutAtr, NotUseAtr comeBackAtr, String workTimeName,
			String workTypeName, Optional<String> midnightIimeOutside, List<NRQueryHolidayQuota> holidayQuotaLst,
			List<String> reasonDissocsLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.goOutAtr = goOutAtr;
		this.comeBackAtr = comeBackAtr;
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
		this.midnightIimeOutside = midnightIimeOutside;
		this.holidayQuotaLst = holidayQuotaLst;
		this.reasonDissocsLst = reasonDissocsLst;
	}
}
