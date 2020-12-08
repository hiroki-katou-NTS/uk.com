package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.menu;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRType;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.menu.dto.NRWebQueryMenuClosureDate;

/**
 * @author thanh_nx
 *
 *         NRWeb照会メニュー一覧XMLとHTMLに変換
 */
public class NRWebQueryMenuXmlHtml {

	private NRWebQueryMenuXmlHtml() {
	};

	// 情報処理
	public static Response process(Require require, NRWebQuerySidDateParameter param) {

		NRWebQueryMenuClosureDate dto = GetNRWebQueryMenu.process(require, param);

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
	private static String createHtml(NRWebQueryMenuClosureDate dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("<HTML>");
		builder.append("<HEAD><TITLE>");
		builder.append(NRWebQueryMenuName.MENU.value);
		builder.append("</TITLE></HEAD>");
		builder.append("<BODY><DL>");
		dto.getMenuDetails().forEach(menu -> {
			builder.append("<DT>");
			builder.append(menu.getMenuName());
			builder.append("</DT>");
			builder.append("<DD>");
			builder.append(menu.getUrl());
			builder.append("</DD>");
		});
		builder.append("</DL></BODY></HTML>");
		return builder.toString();
	}

	// [pvt-2] XMLを作る
	private static String createXml(NRWebQueryMenuClosureDate dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("<kindata type='0'><item>");
		dto.getMenuDetails().forEach(menu -> {
			builder.append(String.format("<subitem title='%s' func='%s' value='%s' args='%s' />", menu.getMenuName(),
					menu.getMenuNo().map(x -> String.valueOf(x)).orElse(""), menu.getUrl(), menu.getArgument()));
		});
		builder.append("</item>");
		builder.append(String.format("<item type='shime'><subitem value='%s'/></item></kindata>",
				dto.getClosureYm().map(x -> String.valueOf(x)).orElse(null)));

		return builder.toString();

	}

	public static interface Require extends GetNRWebQueryMenu.Require {

	}
}
