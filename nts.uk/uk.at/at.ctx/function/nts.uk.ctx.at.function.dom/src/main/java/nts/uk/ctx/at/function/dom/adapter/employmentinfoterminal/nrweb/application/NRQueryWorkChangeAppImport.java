package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeZone;

/**
 * @author thanh_nx
 *
 *         勤務変更申請
 */
@Getter
public class NRQueryWorkChangeAppImport extends NRQueryAppImport {

	// 直行区分
	private NotUseAtr goOutAtr;

	// 直帰区分
	private NotUseAtr comeBackAtr;

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 勤務種類名
	private Optional<String> workTypeName;

	// 時刻
	private List<TimeZone> hourMinuteLst;

	public NRQueryWorkChangeAppImport(NRQueryAppImport app, NotUseAtr goOutAtr, NotUseAtr comeBackAtr,
			Optional<String> workTimeName, Optional<String> workTypeName, List<TimeZone> hourMinuteLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.goOutAtr = goOutAtr;
		this.comeBackAtr = comeBackAtr;
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
		this.hourMinuteLst = hourMinuteLst;
	}

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='%s' align='1' valign='1'/>", this.getAppName()));
		builder.append(
				String.format("<subitem title='承認状況' value='%s' align='1' valign='1' />", this.getApprovalStatus()));

		workTypeName.ifPresent(
				data -> builder.append(String.format("<subitem title='勤務種類' value='%s'  align='1' valign='1' />", data)));
		workTimeName.ifPresent(data -> builder
				.append(String.format("<subitem title='就業時間帯' value='%s'  align='1' valign='1' />", data)));
		if (goOutAtr == NotUseAtr.USE)
			builder.append("<subitem title='直行直帰' value=直行  align='1' valign='1' />");
		if (comeBackAtr == NotUseAtr.USE)
			builder.append("<subitem title='直行直帰' value=直帰  align='1' valign='1' />");

		for (int index = 0; index < hourMinuteLst.size(); index++) {
			if (index == 0)
				builder.append(String.format(
						"<subitem title='出勤' value=%s align='1' valign='1' /><subitem title='退勤' value=%s  align='1' valign='1' />",
						hourMinuteLst.get(index).getStartTime().getRawTimeWithFormat(),
						hourMinuteLst.get(index).getEndTime().getRawTimeWithFormat()));
			if (index == 1)
				builder.append(String.format(
						"<subitem title='出勤2' value=%s align='1' valign='1' /><subitem title='退勤2' value=%s  align='1' valign='1' />",
						hourMinuteLst.get(index).getStartTime().getRawTimeWithFormat(),
						hourMinuteLst.get(index).getEndTime().getRawTimeWithFormat()));
		}
		return builder.toString();
	}

	@Override
	public String createHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>申請名</DT><DD>%s</DD>", this.getAppName()));
		builder.append(String.format("<DT>承認状況</DT><DD>%s</DD>", this.getApprovalStatus()));
		workTypeName.ifPresent(data -> builder.append(String.format("<DT>勤務種類</DT><DD>%s</DD>", data)));
		workTimeName.ifPresent(data -> builder.append(String.format("<DT>就業時間帯</DT><DD>%s</DD>", data)));
		if (goOutAtr == NotUseAtr.USE)
			builder.append("<DT>直行直帰</DT><DD>直行</DD");
		if (comeBackAtr == NotUseAtr.USE)
			builder.append("<DT>直行直帰</DT><DD>直帰</DD>");
		for (int index = 0; index < hourMinuteLst.size(); index++) {
			if (index == 0)
				builder.append(String.format("<DT>出勤</DT><DD>%s</DD><DT>退勤</DT><DD>%s</DD>",
						hourMinuteLst.get(index).getStartTime().getRawTimeWithFormat(),
						hourMinuteLst.get(index).getEndTime().getRawTimeWithFormat()));
			if (index == 1)
				builder.append(String.format("<DT>出勤2</DT><DD>%s</DD><DT>退勤2</DT><DD>%s</DD>",
						hourMinuteLst.get(index).getStartTime().getRawTimeWithFormat(),
						hourMinuteLst.get(index).getEndTime().getRawTimeWithFormat()));
		}
		return builder.toString();
	}

}
