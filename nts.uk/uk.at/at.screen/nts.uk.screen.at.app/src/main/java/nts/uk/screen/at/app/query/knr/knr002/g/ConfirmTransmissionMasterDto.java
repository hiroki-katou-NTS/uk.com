package nts.uk.screen.at.app.query.knr.knr002.g;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author xuannt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfirmTransmissionMasterDto {

	//	端末No
	private String empInfoTerCode;
	//	社員ID送信
	private boolean sendEmployeeId;
	//	社員ID
	private List<String> employeeIds;
	//	弁当メニュー枠番送信
	private boolean sendBentoMenu;
	//	弁当メニュー枠番
	private List<Integer> bentoMenuFrameNumbers;
	//	勤務種類コード送信
	private boolean sendWorkType;
	//	勤務種類コード
	private List<String> workTypeCodes;
	//	 就業時間帯コード送信
	private boolean sendWorkTime;
	//	就業時間帯コード
	private List<String> workTimeCodes;
	//	 残業・休日出勤送信
	private boolean overTimeHoliday;
	//	申請理由送信
	private boolean applicationReason;
	//	全ての打刻データ
	private boolean stampReceive;
	//	全ての予約データ
	private boolean reservationReceive;
	//	 全ての申請データ
	private boolean applicationReceive;
	//	時刻セット
	private boolean timeSetting;
	//	 リモート設定
	private boolean remoteSetting;
	//	再起動を行う
	private boolean reboot;
}
