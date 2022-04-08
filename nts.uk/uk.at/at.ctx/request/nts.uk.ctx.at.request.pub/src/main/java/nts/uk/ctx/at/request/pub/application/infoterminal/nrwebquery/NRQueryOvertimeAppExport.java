package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         残業申請のデーた
 */
@Getter
public class NRQueryOvertimeAppExport extends NRQueryAppExport {

	// 外深夜時間
	private Optional<String> midnightIimeOutside;

	// フレックス超過時間
	private Optional<String> flexOvertime;

	// 勤務種類名
	private Optional<String> overTimeWoktypeName;

	// 就業時間帯名
	private Optional<String> overTimeZoneName;

	// 任意項目
	private List<NRQueryOvertimeAppAnyItemExport> anyItemLst;

	// 乖離理由
	private List<String> reasonDissocsLst;

	// 残業枠
	private List<NRQueryOvertimeQuotaExport> overtimeQuotalst;

	public NRQueryOvertimeAppExport(NRQueryAppExport app, Optional<String> midnightIimeOutside, Optional<String> flexOvertime,
			Optional<String> overTimeWoktypeName, Optional<String> overTimeZoneName,
			List<NRQueryOvertimeAppAnyItemExport> anyItemLst, List<String> reasonDissocsLst,
			List<NRQueryOvertimeQuotaExport> overtimeQuotalst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.midnightIimeOutside = midnightIimeOutside;
		this.flexOvertime = flexOvertime;
		this.overTimeWoktypeName = overTimeWoktypeName;
		this.overTimeZoneName = overTimeZoneName;
		this.anyItemLst = anyItemLst;
		this.reasonDissocsLst = reasonDissocsLst;
		this.overtimeQuotalst = overtimeQuotalst;
	}
}
