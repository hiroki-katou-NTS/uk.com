package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.errorpage;

import java.util.Optional;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryError;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;

/**
 * @author thanh_nx
 *
 *         NRWeb照会 HTMLエラーページを作る
 */
public class NRWebErrorPage {

	// [S-1] HTMLエラーページを作る
	public static String createHtmlErrorPage(NRWebQueryMenuName menuName, NRWebQueryError errorType,
			Optional<String> cno) {

		StringBuilder builder = new StringBuilder();
		builder.append("<HTML>");
		builder.append("<HEAD><TITLE>");
		builder.append(String.format("%s", menuName.value));
		builder.append("</TITLE></HEAD>");
		builder.append("<BODY><DL>");
		builder.append("<DT>エラー</DT><DD></DD>");

		switch (errorType) {
		case NO1:
			builder.append("<DT>引数に誤りがあります。</DT><DD></DD>");

		case NO2:
			builder.append("<DT>カードNoの桁数に誤りがあります。</DT><DD></DD>");

		case NO3:
			builder.append("<DT>該当者が存在しません。/DT><DD></DD>");

		case NO4:
			builder.append(String.format("<DT>%sが存在しません。</DT><DD></DD>", cno.get()));

		case NO5:
			builder.append("<DT>照会準備が整っていません</DT><DD></DD>");
		}

		builder.append("<DT>管理者に連絡してください</DT><DD></DD>");
		builder.append("</DL></BODY></HTML>");
		return builder.toString();
	}

}
