package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         休暇申請
 */
@Getter
public class NRQueryVacationAppImport extends NRQueryAppImport {

	// 休暇区分
	private String vacationType;

	// 勤務種類名
	private String workTypeName;

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 時間消化休暇名
	private List<NRQueryTimeDigesLeavNameImport> timeDigestLeavNameLst;

	public NRQueryVacationAppImport(NRQueryAppImport app, String vacationType, String workTypeName, Optional<String> workTimeName,
			List<NRQueryTimeDigesLeavNameImport> timeDigestLeavNameLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.vacationType = vacationType;
		this.workTypeName = workTypeName;
		this.workTimeName = workTimeName;
		this.timeDigestLeavNameLst = timeDigestLeavNameLst;
	}

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value=％s align='1' valign='1'/>", this.getAppName()));
		builder.append(
				String.format("<subitem title='承認状況' value=%s align='1' valign='1' />", this.getApprovalStatus()));
		builder.append(String.format("<subitem title='休暇区分' value=%s  align='1' valign='1' />", this.vacationType));
		builder.append(String.format("<subitem title='勤務種類' value=%s  align='1'  valign='1' />", this.workTypeName));
		this.workTimeName.ifPresent(data -> builder
				.append(String.format("<subitem title='就業時間帯' value=%s  align='1' valign='1' />", data)));
		this.timeDigestLeavNameLst
				.forEach(data -> builder.append(String.format("<subitem title='%s' value=%s  align='1' valign='1' />",
						data.getTimeDigestName(), data.getTimeDigestHours())));

		return builder.toString();

	}

	@Override
	public String createHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>申請名</DT><DD>%s</DD>", this.getAppName()));
		builder.append(String.format("<DT>承認状況</DT><DD>%s</DD>", this.getApprovalStatus()));
		builder.append(String.format("<DT>休暇区分</DT><DD>%s</DD>", this.vacationType));
		builder.append(String.format("<DT>勤務種類</DT><DD>%s</DD>", this.workTypeName));
		this.workTimeName.ifPresent(data -> builder.append(String.format("<DT>就業時間帯</DT><DD>%s</DD>", data)));
		this.timeDigestLeavNameLst.forEach(data -> builder
				.append(String.format("<DT>%s</DT><DD>%s</DD>", data.getTimeDigestName(), data.getTimeDigestHours())));
		return builder.toString();

	}

}
