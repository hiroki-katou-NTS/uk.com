package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         打刻申請
 */
@Getter
public class NRQueryStampAppImport extends NRQueryAppImport {

	// 打刻申請詳細
	private List<NRQueryStampAppDetailImport> appDetailLst;

	public NRQueryStampAppImport(NRQueryAppImport app, List<NRQueryStampAppDetailImport> appDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.appDetailLst = appDetailLst;
	}

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='%s' align='1' valign='1'/>", this.getAppName()));
		builder.append("\n");
		builder.append(
				String.format("<subitem title='承認状況' value='%s' align='1' valign='1' />", this.getApprovalStatus()));
		builder.append("\n");
		this.appDetailLst.forEach(data -> {
			builder.append(String.format("<subitem title='%s' value='%s' align='1' valign='1' />", data.getStampTypeName(),
					data.getContents()));
			builder.append("\n");
		});
		return builder.toString();
	}

	@Override
	public String createHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>申請名</DT>\n<DD>%s</DD>", this.getAppName()));
		builder.append(String.format("<DT>承認状況</DT>\n<DD>%s</DD>", this.getApprovalStatus()));
		builder.append("\n");
		this.appDetailLst.forEach(data -> {
			builder.append(String.format("<DT>%s</DT>\n<DD>%s </DD>", data.getStampTypeName(), data.getContents()));
			builder.append("\n");
		});
		return builder.toString();

	}

}
