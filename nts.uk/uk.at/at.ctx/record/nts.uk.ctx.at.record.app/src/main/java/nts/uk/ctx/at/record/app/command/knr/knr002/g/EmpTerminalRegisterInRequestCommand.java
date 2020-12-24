package nts.uk.ctx.at.record.app.command.knr.knr002.g;

import lombok.Value;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;

/**
 * @author xuannt
 *
 */
@Value
public class EmpTerminalRegisterInRequestCommand {
	//	就業情報端末コード
	private EmpInfoTerminalCode terminalCode;
	//	社員ＩＤ送信
	private boolean sendEmployeeId;
	//	勤務種類コード送信
	private boolean sendWorkType;
	//	就業時間帯コード送信
	private boolean sendWorkTime;
	//	残業・休日出勤送信
	private boolean overTimeHoliday;
	//	申請理由送信
	private boolean applicationReason;
	//	弁当メニュー枠番送信
	private boolean sendBentoMenu;
	//	時刻セット
	private boolean timeSetting;
	//	再起動を行う
	private boolean reboot;
	//	全ての打刻データ
	private boolean stampReceive;
	//	全ての申請データ
	private boolean applicationReceive;
	//	全ての予約データ
	private boolean reservationReceive;
}
