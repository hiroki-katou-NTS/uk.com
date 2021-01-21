package nts.uk.ctx.at.function.app.nrl.data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.response.MeanCarryable;
import nts.uk.ctx.at.function.app.nrl.xml.Element;

/**
 * Item sequence.
 * 
 * @author manhnd
 *
 * @param <T>
 */
public abstract class ItemSequence<T extends MeanCarryable> implements Sequential<T> {

	public static final List<String> NOACCEPT_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.PAYLOAD, Element.BCC);
	public static final List<String> ACCEPT_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);

	public static final List<String> TEST_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);

	public static final List<String> POLLING_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);
	public static final List<String> POLLING_RES_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.STATUS, Element.REQUEST1, Element.REQUEST2, Element.REQUEST3, Element.REQUEST4, Element.REQUEST5,
			Element.REQUEST6, Element.REQUEST7, Element.REQUEST8, Element.REQUEST9, Element.REQUEST10,
			Element.REQUEST11, Element.REQUEST12, Element.REQUEST13, Element.REQUEST14, Element.REQUEST15,
			Element.REQUEST16, Element.REQUEST17, Element.BCC);

	public static final List<String> SESSION_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);

	public static final List<String> ALL_IOTIME_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.PADDING1,
			Element.LENGTH, Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR,
			Element.PADDING2, Element.PAYLOAD, Element.BCC);

	public static final List<String> ALL_PETITIONS_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.PADDING1,
			Element.LENGTH, Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR,
			Element.PADDING2, Element.PAYLOAD, Element.BCC);

	public static final List<String> PERSONAL_INFO_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);
	public static final List<String> PERSONAL_INFO_RES_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.NUMBER, Element.PAYLOAD, Element.BCC);

	public static final List<String> ALL_RSV_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.PADDING1,
			Element.LENGTH, Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR,
			Element.PADDING2, Element.PAYLOAD, Element.BCC);

	public static final List<String> WORKTYPE_INFO_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);
	public static final List<String> WORKTYPE_INFO_RES_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.NUMBER, Element.PAYLOAD, Element.BCC);

	public static final List<String> WORKTIME_INFO_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);
	public static final List<String> WORKTIME_INFO_RES_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.NUMBER, Element.PAYLOAD, Element.BCC);

	public static final List<String> OVERTIME_INFO_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);
	public static final List<String> OVERTIME_INFO_RES_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.PAYLOAD, Element.BCC);

	public static final List<String> RESERV_INFO_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);
	public static final List<String> RESERV_INFO_RES_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.PAYLOAD, Element.BCC);

	public static final List<String> TIMESET_INFO_REQ_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.BCC);
	public static final List<String> TIMESET_INFO_RES_ORDER = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.YEAR, Element.MONTH, Element.DAY, Element.HOUR, Element.MINITE, Element.SECOND, Element.WEEK,
			Element.BCC);

	public static final List<String> TR_REMOTE = Arrays.asList(Element.SOH, Element.HDR, Element.LENGTH,
			Element.VERSION, Element.FLAG, Element.FRAGMENT_NUMBER, Element.NRL_NO, Element.MAC_ADDR, Element.PADDING,
			Element.PAYLOAD, Element.BCC);
	
	/**
	 * From map.
	 * 
	 * @param data    data
	 * @param command command
	 * @return
	 */
	public abstract T fromMap(Map<String, String> data, Command command);

	/**
	 * Enumerate command.
	 * 
	 * @param command command
	 * @param request request
	 * @return
	 */
	public static Optional<List<String>> enumerate(Command command, boolean request) {
		List<String> orders;
		switch (command) {
		case ACCEPT:
			orders = ACCEPT_ORDER;
			break;
		case NOACCEPT:
			orders = NOACCEPT_ORDER;
			break;
		case TEST:
			orders = TEST_ORDER;
			break;
		case ALL_IO_TIME:
			orders = request ? ALL_IOTIME_REQ_ORDER : ACCEPT_ORDER;
			break;
		case ALL_PETITIONS:
			orders = request ? ALL_PETITIONS_REQ_ORDER : ACCEPT_ORDER;
			break;
		case ALL_RESERVATION:
			orders = request ? ALL_RSV_REQ_ORDER : ACCEPT_ORDER;
			break;
		case POLLING:
			orders = request ? POLLING_REQ_ORDER : POLLING_RES_ORDER;
			break;
		case SESSION:
			orders = request ? SESSION_REQ_ORDER : ACCEPT_ORDER;
			break;
		case PERSONAL_INFO:
			orders = request ? PERSONAL_INFO_REQ_ORDER : PERSONAL_INFO_RES_ORDER;
			break;

		case WORKTYPE_INFO:
			orders = request ? WORKTYPE_INFO_REQ_ORDER : WORKTYPE_INFO_RES_ORDER;
			break;

		case WORKTIME_INFO:
			orders = request ? WORKTIME_INFO_REQ_ORDER : PERSONAL_INFO_RES_ORDER;
			break;

		case RESERVATION_INFO:
			orders = request ? RESERV_INFO_REQ_ORDER : RESERV_INFO_RES_ORDER;
			break;

		case OVERTIME_INFO:
			orders = request ? OVERTIME_INFO_REQ_ORDER : OVERTIME_INFO_RES_ORDER;
			break;

		case TIMESET_INFO:
			orders = request ? PERSONAL_INFO_REQ_ORDER : TIMESET_INFO_RES_ORDER;
			break;
			
		case TR_REMOTE:
			orders = TR_REMOTE;
			
		default:
			return Optional.empty();
		}
		return Optional.of(orders);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.app.nrl.data.Sequential#plot(java.util.List,
	 * nts.uk.ctx.at.function.app.nrl.Command)
	 */
	public T plot(List<MapItem> items, Command command) {
		Map<String, String> itemMap = items.stream().collect(Collectors.toMap(i -> i.getKey(), i -> i.getValue()));
		return fromMap(itemMap, command);
	}

	@AllArgsConstructor
	@NoArgsConstructor
	public static class MapItem {
		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
