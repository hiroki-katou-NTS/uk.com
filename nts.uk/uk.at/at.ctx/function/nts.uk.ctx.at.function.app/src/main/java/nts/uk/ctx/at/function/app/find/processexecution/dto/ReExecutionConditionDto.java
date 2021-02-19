package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.processexecution.ReExecutionCondition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Re-execution condition dto.<br>
 * Dto 更新処理再実行条件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReExecutionConditionDto {

	/**
	 * The Recreate person change work type.<br>
	 * 勤務種別変更者を再作成
	 */
	private int recreatePerson;

	/**
	 * The Recreate transfer.<br>
	 * 異動者を再作成する
	 */
	private int recreateTransfer;

	/**
	 * The Recreate leave.<br>
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
			return new ReExecutionConditionDto();
		}
		return new ReExecutionConditionDto(domain.getRecreatePersonChangeWkt().value,
										   domain.getRecreateTransfer().value,
										   domain.getRecreateLeave().value);
	}

	public ReExecutionCondition toDomain() {
		return ReExecutionCondition.builder()
				.recreateLeave(EnumAdaptor.valueOf(this.recreateLeave, NotUseAtr.class))
				.recreatePersonChangeWkt(EnumAdaptor.valueOf(this.recreatePerson, NotUseAtr.class))
				.recreateTransfer(EnumAdaptor.valueOf(this.recreateTransfer, NotUseAtr.class))
				.build();
	}
	
}
