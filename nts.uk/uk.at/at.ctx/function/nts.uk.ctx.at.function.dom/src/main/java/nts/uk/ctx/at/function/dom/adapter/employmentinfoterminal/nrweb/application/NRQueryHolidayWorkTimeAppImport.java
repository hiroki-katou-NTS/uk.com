package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         休日出勤申請
 */
@Getter
public class NRQueryHolidayWorkTimeAppImport extends NRQueryAppImport {

	// 直行区分
	private NotUseAtr goOutAtr;

	// 直帰区分
	private NotUseAtr comeBackAtr;

	// 就業時間帯名
	private String workTimeName;

	// 勤務種類名
	private String workTypeName;

	// 外深夜時間
	private Optional<String> midnightIimeOutside;

	// 休出枠
	private List<NRQueryHolidayQuotaImport> holidayQuotaLst;

	// 乖離理由
	private List<String> reasonDissocsLst;

	public NRQueryHolidayWorkTimeAppImport(NRQueryAppImport app, NotUseAtr goOutAtr, NotUseAtr comeBackAtr, String workTimeName,
			String workTypeName, Optional<String> midnightIimeOutside, List<NRQueryHolidayQuotaImport> holidayQuotaLst,
			List<String> reasonDissocsLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.goOutAtr = goOutAtr;
		this.comeBackAtr = comeBackAtr;
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
		this.midnightIimeOutside = midnightIimeOutside;
		this.holidayQuotaLst = holidayQuotaLst;
		this.reasonDissocsLst = reasonDissocsLst;
	}

	@Override
	public String createXml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<subitem title='申請名' value='%s' align='1' valign='1'/>", this.getAppName()));
		builder.append("\n");
		builder.append(
				String.format("<subitem title='承認状況' value='%s' align='1' valign='1' />", this.getApprovalStatus()));
		builder.append("\n");
		builder.append(String.format("<subitem title='勤務種類' value='%s'  align='1' valign='1' />", workTypeName));
		builder.append("\n");
		builder.append(String.format("<subitem title='就業時間帯' value='%s'  align='1' valign='1' />", workTimeName));
		builder.append("\n");
		this.holidayQuotaLst.forEach(data -> {
			builder.append(String.format("<subitem title='%s' value='%s' align='1' valign='1' />",
					data.getHolidayQuotaName(), data.getHolidayQuotaTime()));
			builder.append("\n");
		});

		this.midnightIimeOutside.ifPresent(data -> {
			builder.append(String.format("<subitem title='外深夜時間' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});

		this.reasonDissocsLst.forEach(data -> {
			builder.append(String.format("<subitem title='乖離理由' value='%s'  align='1' valign='1' />", data));
			builder.append("\n");
		});

		if (goOutAtr == NotUseAtr.USE) {
			builder.append("<subitem title='直行直帰' value='直行'  align='1' valign='1' />");
			builder.append("\n");
		}
		if (comeBackAtr == NotUseAtr.USE) {
			builder.append("<subitem title='直行直帰' value='直帰'  align='1' valign='1' />");
			builder.append("\n");
		}
		return builder.toString();
	}

	@Override
	public String createHtml() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("<DT>申請名</DT>\n<DD>%s</DD>", this.getAppName()));
		builder.append(String.format("<DT>承認状況</DT>\n<DD>%s</DD>", this.getApprovalStatus()));
		builder.append("\n");
		this.holidayQuotaLst.forEach(data -> {
			builder.append(
					String.format("<DT>%s</DT>\n<DD>%s</DD>", data.getHolidayQuotaName(), data.getHolidayQuotaTime()));
			builder.append("\n");
		});
		this.midnightIimeOutside.ifPresent(data -> {
			builder.append(String.format("<DT>外深夜時間</DT>\n<DD>%s</DD>", data));
			builder.append("\n");
		});
		this.reasonDissocsLst.forEach(data -> {
			builder.append(String.format("<DT>乖離理由</DT>\n<DD>%s</DD>", data));
			builder.append("\n");
		});
		if (goOutAtr == NotUseAtr.USE)
			builder.append("<DT>直行直帰</DT>\n<DD>直行</DD");
		if (comeBackAtr == NotUseAtr.USE)
			builder.append("<DT>直行直帰</DT>\n<DD>直帰</DD>");
		builder.append("\n");
		return builder.toString();
	}

}
