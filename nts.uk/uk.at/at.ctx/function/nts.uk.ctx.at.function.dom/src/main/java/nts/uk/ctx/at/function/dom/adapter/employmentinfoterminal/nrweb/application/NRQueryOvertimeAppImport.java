package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         残業申請のデーた
 */
@Getter
public class NRQueryOvertimeAppImport extends NRQueryAppImport {

	// 外深夜時間
	private Optional<String> midnightIimeOutside;

	// フレックス超過時間
	private Optional<String> flexOvertime;

	// 勤務種類名
	private Optional<String> overTimeWoktypeName;

	// 就業時間帯名
	private Optional<String> overTimeZoneName;

	// 任意項目
	private List<NRQueryOvertimeAppAnyItemImport> anyItemLst;

	// 乖離理由
	private List<String> reasonDissocsLst;

	// 残業枠
	private List<NRQueryOvertimeQuotaImport> overtimeQuotalst;

	public NRQueryOvertimeAppImport(NRQueryAppImport app, Optional<String> midnightIimeOutside, Optional<String> flexOvertime,
			Optional<String> overTimeWoktypeName, Optional<String> overTimeZoneName,
			List<NRQueryOvertimeAppAnyItemImport> anyItemLst, List<String> reasonDissocsLst,
			List<NRQueryOvertimeQuotaImport> overtimeQuotalst) {
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

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='%s' align='1' valign='1'/>", this.getAppName()));
		builder.append("\n");
		builder.append(
				String.format("<subitem title='承認状況' value='%s' align='1' valign='1' />", this.getApprovalStatus()));
		builder.append("\n");
		this.overtimeQuotalst.forEach(data -> {
			builder.append(String.format("<subitem title='%s' value='%s' align='1' valign='1' />",
					data.getOvertimeQuotaName(), data.getOvertimeHours()));
			builder.append("\n");
		});

		this.midnightIimeOutside.ifPresent(data -> {
			builder.append(String.format("<subitem title='外深夜時間' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});

		this.flexOvertime.ifPresent(data -> {
			builder.append(String.format("<subitem title='フレックス超過時間' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});

		this.overTimeWoktypeName.ifPresent(data -> {
			builder.append(String.format("<subitem title='勤務種類' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});
		this.overTimeZoneName.ifPresent(data -> {
			builder.append(String.format("<subitem title='就業時間帯' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});
		this.anyItemLst.forEach(data -> {
			builder.append(String.format("<subitem title='%s' value='%s'  align='1' valign='1' />", data.getName(),
					data.getValue()));
			builder.append("\n");
		});
		this.reasonDissocsLst.forEach(data -> {
			builder.append(String.format("<subitem title='乖離理由' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});
		return builder.toString();

	}

	@Override
	public String createHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>申請名</DT>\n<DD>%s</DD>", this.getAppName()));
		builder.append("\n");
		builder.append(String.format("<DT>承認状況</DT>\n<DD>%s</DD>", this.getApprovalStatus()));
		builder.append("\n");
		this.overtimeQuotalst.forEach(data -> {
			builder.append(
					String.format("<DT>%s</DT>\n<DD>%s</DD>", data.getOvertimeQuotaName(), data.getOvertimeHours()));
			builder.append("\n");
		});
		this.midnightIimeOutside.ifPresent(data -> {
			builder.append(String.format("<DT>外深夜時間</DT>\n<DD>%s</DD>", data));
			builder.append("\n");
		});
		this.flexOvertime.ifPresent(data -> {
			builder.append(String.format("<DT>フレックス超過時間</DT>\n<DD>%s</DD>", data));
		});
		this.overTimeWoktypeName.ifPresent(data -> {
			builder.append(String.format("<DT>勤務種類</DT>\n<DD>%s</DD>", data));
			builder.append("\n");
		});
		this.overTimeZoneName.ifPresent(data -> {
			builder.append(String.format("<DT>就業時間帯</DT>\n<DD>%s</DD>", data));
			builder.append("\n");
		});
		this.anyItemLst.forEach(data -> {
			builder.append(String.format("<DT>%s</DT>\n<DD>%s</DD>", data.getName(), data.getValue()));
			builder.append("\n");
		});
		this.reasonDissocsLst.forEach(data -> {
			builder.append(String.format("<DT>乖離理由</DT>\n<DD>%s</DD>", data));
			builder.append("\n");
		});
		return builder.toString();

	}
}
