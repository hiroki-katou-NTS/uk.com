package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         任意項目申請
 */
@Getter
public class NRQueryAnyItemAppImport extends NRQueryAppImport {

	// 任意項目申請詳細
	private List<NRQueryAnyItemAppDetailImport> anyItemDetailLst;

	public NRQueryAnyItemAppImport(NRQueryAppImport app, List<NRQueryAnyItemAppDetailImport> anyItemDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.anyItemDetailLst = anyItemDetailLst;
	}

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='％s' align='1' valign='1'/>", this.getAppName()));
		builder.append(
				String.format("<subitem title='承認状況' value='%s' align='1' valign='1' />", this.getApprovalStatus()));
		this.anyItemDetailLst.forEach(data -> {
			builder.append(String.format("<subitem title='%s' value='%s'  align='1' valign='1' />",
					data.getAnyItemName(), data.getValue()));
		});
		return builder.toString();
	}

	@Override
	public String createHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>申請名</DT><DD>%s</DD>", this.getAppName()));
		builder.append(String.format("<DT>承認状況</DT><DD>%s</DD>", this.getApprovalStatus()));
		this.anyItemDetailLst.forEach(data -> {
			builder.append(String.format("<DT>%s</DT><DD>%s</DD>", data.getAnyItemName(), data.getValue()));
		});
		return builder.toString();
	}

}
