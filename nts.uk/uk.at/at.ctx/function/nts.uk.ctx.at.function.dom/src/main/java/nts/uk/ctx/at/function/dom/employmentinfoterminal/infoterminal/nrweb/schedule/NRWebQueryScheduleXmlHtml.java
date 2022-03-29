package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
	public static String process(Require require, NRWebQuerySidDateParameter param) {

		List<NRWebScheduleRecordData> dto = GetNRWebQuerySchedule.process(require, param);

		if (param.getNrWebQuery().getType().isPresent()
				&& param.getNrWebQuery().getType().get().equals(NRType.XML.value)) {
			// XMLを作る(＄NRWeb照会メニュー一覧)
			return createXml(dto);
		} else {
			// HTMLを作る(＄NRWeb照会メニュー一覧)
			return createHtml(dto);
		}
	}

	// [pvt-1] HTMLを作る
	private static String createHtml(List<NRWebScheduleRecordData> dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("<HTML>");
		builder.append("\n");
		builder.append("<HEAD><TITLE>");
		builder.append(NRWebQueryMenuName.SCHEDULE.value);
		builder.append("</TITLE></HEAD>");
		builder.append("\n");
		builder.append("<BODY>\n<DL>");
		dto.stream().forEach(data -> {
			builder.append(String.format("<DT>日付</DT>\n<DD>%s</DD>", data.getDate().toString()));
			builder.append("\n");
			data.getValue().forEach(value -> {
				builder.append(String.format("<DT>%s </DT>\n<DD>%s</DD>", value.getName(), value.getValue()));
				builder.append("\n");
			});
		});
		builder.append("</DL>\n</BODY>\n</HTML>");
		return builder.toString();
	}

	// [pvt-2] XMLを作る
	private static String createXml(List<NRWebScheduleRecordData> dto) {
		StringBuilder builder = new StringBuilder();
		builder.append("<kindata type='1'>\n<item type='head'>");
		builder.append("\n");
		if (!dto.isEmpty()) {
			AtomicInteger index = new AtomicInteger(1);
			dto.get(0).getValue().forEach(data -> {
				builder.append(String.format("<subitem index='%s' value='%s' align='2'/>",
						String.valueOf(index.getAndIncrement()), data.getName()));
				builder.append("\n");
			});
			builder.append("</item>");
			builder.append("\n");
			dto.stream().forEach(data -> {
				builder.append(String.format("<item type='data' date='%s' color='%s' align='2'>",
						String.format("%04d%02d%02d", data.getDate().year(), data.getDate().month(), data.getDate().day()), createColor(data.getDate())));
				builder.append("\n");
				index.set(1);
				data.getValue().forEach(value -> {
					builder.append(String.format("<subitem index='%s' value='%s' align='3' color='%s'/> ",
							String.valueOf(index.getAndIncrement()), value.getValue(), createState(value.getState())));
					builder.append("\n");
				});
				builder.append("</item>");
				builder.append("\n");
			});

		}

		builder.append(
				"<item type='guide'>\n<subitem index='1' value='手入力・修正' color='134,194,255' />\n<subitem index='2' value='申請' color='0,255,0' />\n</item>\n</kindata>");
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
			return "0,255,0";
		default:
			return "";
		}

	}

	public static interface Require extends GetNRWebQuerySchedule.Require {

	}
}
