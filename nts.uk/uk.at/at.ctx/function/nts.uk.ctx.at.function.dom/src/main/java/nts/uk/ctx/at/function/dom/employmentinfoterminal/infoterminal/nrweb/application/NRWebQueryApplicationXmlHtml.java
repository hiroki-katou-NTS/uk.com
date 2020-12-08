package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.application;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryAppImport;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRType;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;

/**
 * @author thanh_nx
 * 
 *         NRWeb照会申請XMLとHTMLに変換
 */
public class NRWebQueryApplicationXmlHtml {

	// 情報処理
	public static Response process(Require require, NRWebQuerySidDateParameter param) {

		List<? extends NRQueryAppImport> lstApp = GetNRWebQueryApplication.process(require, param);
		LinkedHashMap<GeneralDate, List<NRQueryAppImport>> dtoMap = lstApp.stream()
				.sorted((x, y) -> x.getAppDate().compareTo(y.getAppDate()))
				.collect(Collectors.groupingBy(x -> x.getAppDate(), LinkedHashMap::new, Collectors.toList()));

		if (param.getNrWebQuery().getType().isPresent()
				&& param.getNrWebQuery().getType().get().equals(NRType.XML.value)) {
			// XMLを作る(＄NRWeb照会メニュー一覧)
			return Response.ok(createXml(dtoMap), MediaType.APPLICATION_XML).build();
		} else {
			// HTMLを作る(＄NRWeb照会メニュー一覧)
			return Response.ok(createHtml(dtoMap), MediaType.TEXT_HTML).build();
		}
	}

	// [pvt-1] HTMLを作る
	private static String createHtml(LinkedHashMap<GeneralDate, List<NRQueryAppImport>> lstApp) {
		StringBuilder builder = new StringBuilder();
		builder.append("<HTML>");
		builder.append("<HEAD><TITLE>");
		builder.append(NRWebQueryMenuName.APPLICATION.value);
		builder.append("</TITLE></HEAD>");
		builder.append("<BODY><DL><DT>日付</DT>");
		Comparator<NRQueryAppImport> compareByName = Comparator.comparing(NRQueryAppImport::getAppType)
				.thenComparing(NRQueryAppImport::getBeforeAfterType).thenComparing(NRQueryAppImport::getInputDate);
		lstApp.forEach((key, value) -> {
			builder.append(String.format("<DD>%s</DD>", key.toString()));
			value.stream().sorted(compareByName).forEach(dataDetail -> {
				builder.append(dataDetail.createHtml());
			});
		});
		builder.append("</DL></BODY></HTML>");
		return builder.toString();
	}

	// [pvt-2] XMLを作る
	private static String createXml(LinkedHashMap<GeneralDate, List<NRQueryAppImport>> lstApp) {
		StringBuilder builder = new StringBuilder();
		builder.append("<kindata type='3'><item type='data'>");
		Comparator<NRQueryAppImport> compareByName = Comparator.comparing(NRQueryAppImport::getAppType)
				.thenComparing(NRQueryAppImport::getBeforeAfterType).thenComparing(NRQueryAppImport::getInputDate);
		lstApp.forEach((key, value) -> {
			builder.append(
					String.format("<subitem title='日付' value='%s' type='date' align='1' valign='1' />", key.toString()));
			value.stream().sorted(compareByName).forEach(dataDetail -> {
				builder.append(dataDetail.createXml());
			});
		});
		builder.append("</item></kindata>");
		return builder.toString();
	}

	public static interface Require extends GetNRWebQueryApplication.Require {

	}
}
