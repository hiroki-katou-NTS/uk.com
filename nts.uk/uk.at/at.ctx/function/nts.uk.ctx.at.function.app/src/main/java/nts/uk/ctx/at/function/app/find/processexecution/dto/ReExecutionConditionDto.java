package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;

/**
 * The class Re-execution condition dto.<br>
 * Dto 更新処理再実行条件
 */
@Data
@AllArgsConstructor
public class ReExecutionConditionDto {

	/**
	 * 勤務種別変更者を再作成
	 */
	private int recreatePerson;

	/**
	 * 異動者を再作成する
	 */
	private int recreateTransfer;

	/**
	 * 休職者・休業者を再作成
	 */
	private int recreateLeave;

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the re-execution condition dto
	 */
	public static ReExecutionConditionDto createFromDomain(ReExecutionCondition domain) {
		if (domain == null) {
			return null;
		}
		return new ReExecutionConditionDto(domain.getRecreatePersonChangeWkt().value,
										   domain.getRecreateTransfer().value,
										   domain.getRecreateLeave().value);
	}

}
