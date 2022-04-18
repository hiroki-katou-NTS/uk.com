package nts.uk.ctx.at.function.app.nrl;

import java.util.Arrays;
import java.util.Optional;

/**
 * Command.
 *  コマンドの種類
 * @author manhnd
 */
public enum Command {
	// ACCEPT(11, 01)
	ACCEPT("ACK", "11", "01"),
	NOACCEPT("NAK", "12", "02"),
	// タイムレコードに設定(13, 03)
	POLLING("Polling", "13", "03"),
	// フラグ削除(17, 01)
	SESSION("Session", "17", "01"),
	//
	TEST("Test", "14", "04"),
	// 打刻(15, 01)
	ALL_IO_TIME("IOTime", "15", "01"),
	// 申請(19, 01)
	ALL_PETITIONS("AllPetitions", "19", "01"),
	// 予約(18, 01)
	ALL_RESERVATION("AllReservation", "18", "01"),
	// 個人情報(1B, 07)
	PERSONAL_INFO("PersonalInfo", "1B", "07"),
	// 勤務種類(1C, 08)
	WORKTYPE_INFO("WorkTypeInfo", "1C", "08"),
	// 就業時間帯(1D, 09)
	WORKTIME_INFO("WorkTimeInfo", "1D", "09"),
	// 残業・休日出勤(1E, 0A)
	OVERTIME_INFO("OverTimeInfo", "1E", "10"),
	// 予約メニュー(1F, 0B)
	RESERVATION_INFO("ReservationInfo", "1F", "11"),
	// 申請理由(20, 0C)
	APPLICATION_INFO("ApplicationInfo", "20", "12"),
	// 時刻合わせ(16, 05)
	TIMESET_INFO("TimeSetInfo", "16", "05"),
	// NRリモート設定(21, 0D)
	TR_REMOTE("TimeRecordRemote", "21", "13"),
	// UKモードへの切替日時(22, 0E)
	UK_SWITCH_MODE("DateTimeSwitchUKMode", "22", "14"),
	// レコーダの設定送信
	TR_REMOTE_SEND_SETTING("ReceiveNRLRemoteDataSetting", "23", "01"),
	// メッセージ
	MESSAGE("GetNotificationMessageQuery", "1A", "06"),
	
	// 再起動の応答(24, 15)
	REBOOT("RebootRequest", "24", "15");
	

	/**
	 * Name
	 */
	public String Name;
	
	
	/**
	 *	Request 
	 */
	public String Request;
	
	
	/**
	 * Response
	 */
	public String Response;
	
	private Command(String name, String request, String response) {
		this.Name = name;
		this.Request = request;
		this.Response = response;
	}
	
	/**
	 * Find request name.
	 * @param request request
	 * @return command
	 */
	public static Optional<Command> findName(String request) {
		return Arrays.asList(values()).stream().filter(c -> request.toUpperCase().equals(c.Request)).findFirst();
	}
	
	public boolean canCreateAndDestroy() {
		switch (this) {
		// 打刻(15, 01)
		case ALL_IO_TIME:
			// 申請(19, 01)
		case ALL_PETITIONS:
			// 予約(18, 01)
		case ALL_RESERVATION:
			// 個人情報(1B, 07)
		case PERSONAL_INFO:
			// 勤務種類(1C, 08)
		case WORKTYPE_INFO:
			// 就業時間帯(1D, 09)
		case WORKTIME_INFO:
			// 残業・休日出勤(1E, 0A)
		case OVERTIME_INFO:
			// 予約メニュー(1F, 0B)
		case RESERVATION_INFO:
			// 申請理由(20, 0C)
		case APPLICATION_INFO:
			// 時刻合わせ(16, 05)
		case TIMESET_INFO:
			// NRリモート設定(21, 0D)
		case TR_REMOTE:
			// UKモードへの切替日時(22, 0E)
		case UK_SWITCH_MODE:
			// レコーダの設定送信
		case TR_REMOTE_SEND_SETTING:
			// メッセージ
		case MESSAGE:
		case REBOOT:
			return true;
		default:
			return false;
		}
	}

	public boolean onlyCreate() {
		switch (this) {
		case SESSION:
			return true;
		default:
			return false;
		}
	}
	
	public boolean simple() {
		switch (this) {
		case TR_REMOTE_SEND_SETTING:
		case MESSAGE:
			return true;
		default:
			return false;
		}
	}
}
