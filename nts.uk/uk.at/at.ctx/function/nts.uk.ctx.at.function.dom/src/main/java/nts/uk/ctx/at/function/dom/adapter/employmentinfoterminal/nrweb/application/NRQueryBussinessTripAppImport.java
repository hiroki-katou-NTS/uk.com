package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;

/**
 * @author thanh_nx
 *
 *         出張申請
 */
@Getter
public class NRQueryBussinessTripAppImport extends NRQueryAppImport {

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 勤務種類名
	private Optional<String> workTypeName;

	// 期間
	private List<TripAppTimeZoneImport> period;

	public NRQueryBussinessTripAppImport(NRQueryAppImport app, Optional<String> workTimeName, Optional<String> workTypeName,
			List<TripAppTimeZoneImport> period) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
		this.period = period;
	}

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='%s' align='1' valign='1'/>", this.getAppName()));
		builder.append("\n");
		builder.append(
				String.format("<subitem title='承認状況' value='%s 'align='1' valign='1' />", this.getApprovalStatus()));
		builder.append("\n");
		workTypeName.ifPresent(data -> {
			builder.append(String.format("<subitem title='勤務種類' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});
		workTimeName.ifPresent(data -> {
			builder.append(String.format("<subitem title='就業時間帯' value='%s' align='1' valign='1' />", data));
			builder.append("\n");
		});

		for (int index = 0; index < period.size(); index++) {
			if (index == 0) {
				builder.append(String.format(
						"<subitem title='出勤' value='%s' align='1' valign='1' />\n<subitem title='退勤' value='%s'  align='1' valign='1' />",
						period.get(index).getStartTime().map(x -> DataValueAttribute.CLOCK.format(x.v())).orElse(""),
						period.get(index).getEndTime().map(x -> DataValueAttribute.CLOCK.format(x.v())).orElse("")));
				builder.append("\n");
			}
			if (index == 1) {
				builder.append(String.format(
						"<subitem title='出勤2' value='%s' align='1' valign='1' />\n<subitem title='退勤2' value='%s'  align='1' valign='1' />",
						period.get(index).getStartTime().map(x -> DataValueAttribute.CLOCK.format(x.v())).orElse(""),
						period.get(index).getEndTime().map(x -> DataValueAttribute.CLOCK.format(x.v())).orElse("")));
				builder.append("\n");
			}
			
		}
		return builder.toString();

	}

	@Override
	public String createHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>申請名</DT>\n<DD>%s</DD>", this.getAppName()));
		builder.append(String.format("<DT>承認状況</DT>\n<DD>%s</DD>", this.getApprovalStatus()));
		builder.append("\n");
		workTypeName.ifPresent(data -> {
			builder.append(String.format("<DT>勤務種類</DT>\n<DD>%s</DD>", data));
			builder.append("\n");
		});
		workTimeName.ifPresent(data -> {
			builder.append(String.format("<DT>就業時間帯</DT>\n<DD>%s</DD>", data));
			builder.append("\n");
		});
		for (int index = 0; index < period.size(); index++) {
			if (index == 0) {
				builder.append(String.format("<DT>出勤</DT>\n<DD>%s</DD>\n<DT>退勤</DT>\n<DD>%s</DD>",
						period.get(index).getStartTime().map(x -> x.getRawTimeWithFormat()).orElse(""),
						period.get(index).getEndTime().map(x -> x.getRawTimeWithFormat()).orElse("")));
			builder.append("\n");
			}
			if (index == 1)
			{
				builder.append(String.format("<DT>出勤2</DT>\n<DD>%s</DD>\n<DT>退勤2</DT>\n<DD>%s</DD>",
						period.get(index).getStartTime().map(x -> x.getRawTimeWithFormat()).orElse(""),
						period.get(index).getEndTime().map(x -> x.getRawTimeWithFormat()).orElse("")));
				builder.append("\n");
			}
		}
		return builder.toString();
	}
}
