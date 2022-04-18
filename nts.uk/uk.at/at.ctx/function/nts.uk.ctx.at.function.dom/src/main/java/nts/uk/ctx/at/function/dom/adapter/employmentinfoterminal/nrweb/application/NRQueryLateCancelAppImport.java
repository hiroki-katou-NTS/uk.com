package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         NRQueryLateCancelApp
 */
@Getter
public class NRQueryLateCancelAppImport extends NRQueryAppImport {

	// 申請内容
	private List<String> lateCancelAppDetailLst;

	public NRQueryLateCancelAppImport(NRQueryAppImport app, List<String> lateCancelAppDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.lateCancelAppDetailLst = lateCancelAppDetailLst;
	}

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='%s' align='1' valign='1'/>", this.getAppName()));
		builder.append("\n");
		builder.append(
				String.format("<subitem title='承認状況' value='%s' align='1' valign='1' />", this.getApprovalStatus()));
		builder.append("\n");
		this.lateCancelAppDetailLst.forEach(data -> {
			builder.append(String.format("<subitem title='取り消し区分' value='%s' align='1' valign='1' />", data));
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
		this.lateCancelAppDetailLst.forEach(data -> {
			builder.append(String.format("<DT>取り消し区分</DT>\n<DD>%s</DD>>", data));
			builder.append("\n");
		});
		return builder.toString();
	}

}
