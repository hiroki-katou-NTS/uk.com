package nts.uk.ctx.at.schedule.dom.importschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 1セル分の内容
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.取込.1セル分の内容
 * @author kumiko_otake
 */
@Value
public class CapturedRawDataOfCell {

	/** 社員コード **/
	private final String employeeCode;
	/** 年月日 **/
	private final GeneralDate ymd;
	/** 取り込みコード **/
	private final String importCode;

}
