package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.i18n.TextResource;

/**
 * @author thanh_nx
 *
 *         振休振出申請
 */
@Getter
public class NRQueryFurikyuFurishutsuAppImport extends NRQueryAppImport {

	// 勤務種類名
	private String workTypeName;

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 振休申請
	private boolean furikyu;

	public NRQueryFurikyuFurishutsuAppImport(NRQueryAppImport app, String workTypeName, Optional<String> workTimeName,
			boolean furikyu) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.workTypeName = workTypeName;
		this.workTimeName = workTimeName;
		this.furikyu = furikyu;
	}

	@Override
	public String createXml() {
		String Com_SubstituteHoliday = TextResource.localize("Com_SubstituteHoliday");
		String Com_SubstituteWork = TextResource.localize("Com_SubstituteWork");
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='%s' align='1' valign='1'/>", this.getAppName()));
		builder.append("\n");
		builder.append(
				String.format("<subitem title='承認状況' value='%s' align='1' valign='1' />", this.getApprovalStatus()));
		builder.append("\n");
		if (furikyu) {
			builder.append(String.format("<subitem title='%s/%s' value='%s'  align='1' valign='1' />",
					Com_SubstituteHoliday, Com_SubstituteWork, Com_SubstituteHoliday));
		builder.append("\n");
		}
		else {
			builder.append(String.format("<subitem title='%s/%s' value='%s'  align='1' valign='1' />",
					Com_SubstituteHoliday, Com_SubstituteWork, Com_SubstituteWork));
			builder.append("\n");
		}
		builder.append(String.format("<subitem title='勤務種類' value='%s'  align='1' valign='1' />", workTypeName));
		builder.append("\n");
		workTimeName.ifPresent(data -> {
			builder.append(String.format("<subitem title='就業時間帯' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});

		return builder.toString();
	}

	@Override
	public String createHtml() {
		String Com_SubstituteHoliday = TextResource.localize("Com_SubstituteHoliday");
		String Com_SubstituteWork = TextResource.localize("Com_SubstituteWork");
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>申請名</DT>\n<DD>%s</DD>", this.getAppName()));
		builder.append("\n");
		builder.append(String.format("<DT>承認状況</DT>\n<DD>%s</DD>", this.getApprovalStatus()));
		builder.append("\n");
		if (furikyu) {
			builder.append(String.format("<DT>%s/%s</DT>\n<DD>%s </DD>", Com_SubstituteHoliday, Com_SubstituteWork,
					Com_SubstituteHoliday));
		builder.append("\n");
		}
		else {
			builder.append(String.format("<DT>%s/%s</DT>\n<DD>%s </DD>", Com_SubstituteHoliday, Com_SubstituteWork,
					Com_SubstituteWork));
			builder.append("\n");
		}
		builder.append(String.format("<DT>勤務種類</DT>\n<DD>%s </DD>", workTypeName));
		builder.append("\n");
		workTimeName.ifPresent(data -> {
			builder.append(String.format("<DT>勤務種類</DT>\n<DD>%s </DD>", data));
			builder.append("\n");
		});
		return builder.toString();
	}

}
