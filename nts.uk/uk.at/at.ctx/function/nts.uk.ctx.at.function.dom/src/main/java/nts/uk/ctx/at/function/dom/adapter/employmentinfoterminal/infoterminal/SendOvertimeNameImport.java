package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author ThanhNX
 *
 *         残業・休日出勤送信Import
 */
@Value
public class SendOvertimeNameImport {

	/**
	 * 残業
	 */
	private List<SendOvertimeDetailImport> overtimes;

	/**
	 * 休日出勤
	 */
	private List<SendOvertimeDetailImport> vacations;

	public SendOvertimeNameImport(List<SendOvertimeDetailImport> overtimes, List<SendOvertimeDetailImport> vacations) {
		this.overtimes = overtimes;
		this.vacations = vacations;
	}

	/**
	 * 時間外
	 */
	@Value
	@AllArgsConstructor
	public static class SendOvertimeDetailImport {

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
