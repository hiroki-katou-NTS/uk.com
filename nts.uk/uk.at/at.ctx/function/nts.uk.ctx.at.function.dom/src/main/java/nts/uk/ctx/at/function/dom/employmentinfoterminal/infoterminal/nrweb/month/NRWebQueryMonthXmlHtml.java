package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month;

import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.month.NRWebMonthData;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRType;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;

/**
 * @author thanh_nx
 *
 *         NRWeb照会月別実績XMLとHTMLに変換
 */
public class NRWebQueryMonthXmlHtml {

	// 情報処理
	public static Response process(Require require, NRWebQuerySidDateParameter param) {

		Optional<NRWebMonthData> dto = GetNRWebQueryMonth.process(require, param);

		if (param.getNrWebQuery().getType().isPresent()
				&& param.getNrWebQuery().getType().get().equals(NRType.XML.value)) {
			// XMLを作る(＄NRWeb照会メニュー一覧)
			return Response.ok(createXml(dto), MediaType.APPLICATION_XML).build();
		} else {
			// HTMLを作る(＄NRWeb照会メニュー一覧)
			return Response.ok(createHtml(dto), MediaType.TEXT_HTML).build();
		}

	}

	// [pvt-1] HTMLを作る
	private static String createHtml(Optional<NRWebMonthData> dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("<HTML>");
		builder.append("<HEAD><TITLE>");
		builder.append(NRWebQueryMenuName.MONTHLY.value);
		builder.append("</TITLE></HEAD>");
		builder.append("<BODY><DL>");
		dto.ifPresent(x -> {
			builder.append(String.format("<DT>%4d年%02d月度</DT>", x.getYm().year(), x.getYm().month()));
			x.getValue().forEach(item -> {
				builder.append(String.format("<DT>%s </DT><DT>%s<DT>", item.getName(), item.getValue()));
			});
		});
		builder.append("</DL></BODY></HTML>");
		return builder.toString();
	}

	// [pvt-2] XMLを作る
	private static String createXml(Optional<NRWebMonthData> dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("<kindata type='4'><item type='head'>");
		dto.ifPresent(x -> {
			builder.append(
					String.format("<subitem index = '2' title=日付 value = '%s' type='date' align='2' valign='3'/>",
							String.format("'%4d/%02d'", x.getYm().year(), x.getYm().month())));
			x.getValue().forEach(item -> {
				builder.append(String.format("<subitem index = '2' title='%s' value = '%s' align='2' valign='3'/>  ",
						item.getName(), item.getValue()));
			});
		});
		builder.append("</item></kindata>");
		return builder.toString();

	}

	public static interface Require extends GetNRWebQueryMonth.Require {

	}
}
