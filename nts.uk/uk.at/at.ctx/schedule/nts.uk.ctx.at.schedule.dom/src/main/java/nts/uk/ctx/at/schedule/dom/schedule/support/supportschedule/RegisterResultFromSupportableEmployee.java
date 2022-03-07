package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee.SupportableEmployee;

/**
 * 応援可能な社員からの登録結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.応援.応援予定.応援可能な社員からの登録結果
 * @author dan_pv
 */
@Value
public class RegisterResultFromSupportableEmployee {
	
	/**
	 * エラーがあるか
	 */
	private final boolean isError;
	
	/**
	 * エラー情報
	 */
	private final Optional<ErrorInformation> errorInfo;
	
	/**
	 * atomTaskリスト
	 */
	private final List<AtomTask> atomTaskList;
	
	/**
	 * エラーありで作る
	 * @param supportableEmployee 応援可能な社員
	 * @param errorMessage エラーメッセージ
	 * @return
	 */
	public static RegisterResultFromSupportableEmployee createWithError(
			SupportableEmployee supportableEmployee, String errorMessage) {
		
		return new RegisterResultFromSupportableEmployee(
				true, 
				Optional.of( new ErrorInformation(supportableEmployee, errorMessage) ), 
				Collections.emptyList());
	}
	
	/**
	 * エラーなしで作る
	 * @param atomTaskList atomTaskリスト
	 * @return
	 */
	public static RegisterResultFromSupportableEmployee createWithoutError(
			List<AtomTask> atomTaskList ) {
		
		return new RegisterResultFromSupportableEmployee(false, Optional.empty(), atomTaskList);
	}
	
	/**
	 * emptyで作る
	 * @return
	 */
	public static RegisterResultFromSupportableEmployee createEmpty () {
		
		return new RegisterResultFromSupportableEmployee(false, Optional.empty(), Collections.emptyList() );
	}
	
	@Value
	public static class ErrorInformation {
		
		/**
		 * 応援可能な社員
		 */
		private final SupportableEmployee supportableEmployee;
		
		/**
		 * エラーメッセージ
		 */
		private final String errorMessage;
		
	} 

}
