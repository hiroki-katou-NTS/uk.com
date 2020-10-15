package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author ThanhNX
 *
 *         残業・休日出勤送信Export
 */
@Value
public class SendOvertimeNameExport {

	/**
	 * 残業
	 */
	private List<SendOvertimeDetailExport> overtimes;

	/**
	 * 休日出勤
	 */
	private List<SendOvertimeDetailExport> vacations;

	public SendOvertimeNameExport(List<SendOvertimeDetailExport> overtimes, List<SendOvertimeDetailExport> vacations) {
		this.overtimes = overtimes;
		this.vacations = vacations;
	}

	/**
	 * 時間外
	 */
	@Value
	@AllArgsConstructor
	public static class SendOvertimeDetailExport {

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
