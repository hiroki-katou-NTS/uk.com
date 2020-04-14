package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.application.CheckErrorType;
import nts.uk.ctx.at.record.dom.stamp.application.PromptingMessage;

/**
 * 日別勤怠エラー情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻後の日別勤怠エラー情報を確認する.日別勤怠エラー情報
 * @author tutk
 *
 */

public class DailyAttdErrorInfo {
	/**
	 * 	エラー種類
	 */
	@Getter
	private final CheckErrorType checkErrorType;
	/**
	 * 促すメッセージ
	 */
	@Getter
	private final PromptingMessage promptingMessage;
	/**
	 * 	最後エラー発生日
	 */
	@Getter
	private final GeneralDate lastDateError;
	/**
	 * 	促す申請一覧
	 */
	@Getter
	private List<Integer> listRequired = new ArrayList<>();

	public DailyAttdErrorInfo(CheckErrorType checkErrorType, PromptingMessage promptingMessage,
			GeneralDate lastDateError, List<Integer> listRequired) {
		this.checkErrorType = checkErrorType;
		this.promptingMessage = promptingMessage;
		this.lastDateError = lastDateError;
		this.listRequired = listRequired;
	}
	
	
}
