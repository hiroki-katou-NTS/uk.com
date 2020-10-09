package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 * 
 *         残業・休日出勤送信
 */
@Value
public class SendOvertimeName implements DomainValue {

	/**
	 * 残業
	 */
	private List<SendOvertimeDetail> overtimes;

	/**
	 * 休日出勤
	 */
	private List<SendOvertimeDetail> vacations;

	public SendOvertimeName(List<SendOvertimeDetail> overtimes, List<SendOvertimeDetail> vacations) {
		super();
		this.overtimes = overtimes;
		this.vacations = vacations;
	}

	/**
	 * 時間外
	 */
	@Value
	@AllArgsConstructor
	public static class SendOvertimeDetail {

		/**
		 * 時間外No
		 */
		private final String sendOvertimeNo;

		/**
		 * 時間外名
		 */
		private final String sendOvertimeName;
	}
}
