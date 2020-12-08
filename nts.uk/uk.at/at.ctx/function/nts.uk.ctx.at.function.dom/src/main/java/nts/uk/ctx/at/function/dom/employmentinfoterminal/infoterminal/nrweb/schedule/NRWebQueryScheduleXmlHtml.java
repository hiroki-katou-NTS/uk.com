package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.schedule.NRWebScheduleRecordData;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRType;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

/**
 * @author thanh_nx
 *
 *
 *         NRWeb照会勤務予定XMLとHTMLに変換
 */
public class NRWebQueryScheduleXmlHtml {

	// 情報処理
	public static Response process(Require require, NRWebQuerySidDateParameter param) {

		List<NRWebScheduleRecordData> dto = GetNRWebQuerySchedule.process(require, param);

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
	private static String createHtml(List<NRWebScheduleRecordData> dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("<HTML>");
		builder.append("<HEAD><TITLE>");
		builder.append(NRWebQueryMenuName.SCHEDULE.value);
		builder.append("</TITLE></HEAD>");
		builder.append("<BODY><DL>");
		dto.stream().forEach(data -> {
			builder.append(String.format("<DT>日付</DT><DD>%s</DD>", data.getDate().toString()));
			data.getValue().forEach(value -> {
				builder.append(String.format("<DT>%s </DT><DD>%s</DD>", value.getName(), value.getValue()));
			});
		});
		builder.append("</DL></BODY></HTML>");
		return builder.toString();
	}

	// [pvt-2] XMLを作る
	private static String createXml(List<NRWebScheduleRecordData> dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("<kindata type='1'><item type='head'>");
		if (!dto.isEmpty()) {
			AtomicInteger index = new AtomicInteger(1);
			dto.get(0).getValue().forEach(data -> {
				builder.append(String.format("<subitem index='%s' value='%s' align='2'/>",
						String.valueOf(index.getAndIncrement()), data.getName()));
			});
			builder.append("</item>");
			dto.stream().forEach(data -> {
				builder.append(String.format("<item type='data' date='%s' color='%s' align='2'>",
						data.getDate().toString(), createColor(data.getDate())));
				index.set(1);
				data.getValue().forEach(value -> {
					builder.append(String.format("<subitem index='%s' value='%s' align='3' color='%s'/> ",
							String.valueOf(index.getAndIncrement()), value.getValue(), createState(value.getState())));
				});
				builder.append("</item>");
			});

		}

		builder.append(
				"<item type='guide'><subitem index='1' value='手入力・修正' color='134,194,255' /><subitem index='2' value='申請' color='0,255,0' /></item></kindata>");
		return builder.toString();

	}

	// [pvt-3] 曜日の色を変更する
	public static String createColor(GeneralDate date) {
		if (date.dayOfWeek() == 6)
			return "51,102,255";
		else if (date.dayOfWeek() == 7)
			return "255,100,180";
		else
			return "";
	}

	// [pvt-4] 編集状態の色を変更する
	public static String createState(EditStateSetting state) {
		switch (state) {
		case HAND_CORRECTION_MYSELF:
			return "128,255,255";
		case HAND_CORRECTION_OTHER:
			return "134,194,255";
		case REFLECT_APPLICATION:
			return "'0,255,0";
		default:
			return "";
		}

	}

	public static interface Require extends GetNRWebQuerySchedule.Require {

	}
}
