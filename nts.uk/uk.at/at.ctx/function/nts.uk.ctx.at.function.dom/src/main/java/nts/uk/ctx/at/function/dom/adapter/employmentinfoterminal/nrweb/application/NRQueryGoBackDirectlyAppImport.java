package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         直行直帰申請
 */
@Getter
public class NRQueryGoBackDirectlyAppImport extends NRQueryAppImport {

	// 直行区分
	private NotUseAtr goOutAtr;

	// 直帰区分
	private NotUseAtr comeBackAtr;

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 勤務種類名
	private Optional<String> workTypeName;

	public NRQueryGoBackDirectlyAppImport(NRQueryAppImport app, NotUseAtr goOutAtr, NotUseAtr comeBackAtr,
			Optional<String> workTimeName, Optional<String> workTypeName) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.goOutAtr = goOutAtr;
		this.comeBackAtr = comeBackAtr;
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
	}

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='％s' align='1' valign='1'/>", this.getAppName()));
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
			builder.append("<DT>直行直帰</DT><DD>直行</DD>");
		if (comeBackAtr == NotUseAtr.USE)
			builder.append("<DT>直行直帰</DT><DD>直帰</DD>");
		return builder.toString();
	}

}
