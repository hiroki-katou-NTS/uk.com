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
		builder.append("\n");
		builder.append("<HEAD><TITLE>");
		builder.append(String.format("%s", menuName.value));
		builder.append("</TITLE></HEAD>");
		builder.append("\n");
		builder.append("<BODY>\n<DL>");
		builder.append("\n");
		builder.append("<DT>エラー</DT>\n<DD></DD>");
		builder.append("\n");

		switch (errorType) {
		case NO1:
			builder.append("<DT>引数に誤りがあります。</DT>\n<DD></DD>");
			builder.append("\n");
			break;

		case NO2:
			builder.append("<DT>カードNoの桁数に誤りがあります。</DT>\n<DD></DD>");
			builder.append("\n");
			break;
		case NO3:
			builder.append("<DT>該当者が存在しません。/DT>\n<DD></DD>");
			builder.append("\n");
			break;
		case NO4:
			builder.append(String.format("<DT>%sが存在しません。</DT>\n<DD></DD>", cno.get()));
			builder.append("\n");
			break;
		case NO5:
			builder.append("<DT>照会準備が整っていません</DT>\n<DD></DD>");
			builder.append("\n");
			break;
		}

		builder.append("<DT>管理者に連絡してください</DT>\n<DD></DD>");
		builder.append("\n");
		builder.append("</DL>\n</BODY>\n</HTML>");
		return builder.toString();
	}

}
