package nts.uk.ctx.at.function.dom.processexecution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The class Update processing re-execution condition.<br>
 * Domain 更新処理再実行条件
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReExecutionCondition extends DomainObject {

	/**
	 * The Recreate person change work type.<br>
	 * 勤務種別変更者を再作成
	 */
	private NotUseAtr recreatePersonChangeWkt;

	/**
	 * The Recreate transfer.<br>
	 * 異動者を再作成する
	 */
	private NotUseAtr recreateTransfer;

	/**
	 * The Recreate leave.<br>
	 * 休職者・休業者を再作成
	 */
	private NotUseAtr recreateLeave;

	/**
	 * Instantiates a new <code>ReExecutionCondition</code>
	 *
	 * @param recreatePersonChangeWkt the recreate person change work type
	 * @param recreateTransfer        the recreate transfer
	 * @param recreateLeave           the recreate leave
	 */
	public ReExecutionCondition(int recreatePersonChangeWkt, int recreateTransfer, int recreateLeave) {
		this.recreatePersonChangeWkt = EnumAdaptor.valueOf(recreatePersonChangeWkt, NotUseAtr.class);
		this.recreateTransfer = EnumAdaptor.valueOf(recreateTransfer, NotUseAtr.class);
		this.recreateLeave = EnumAdaptor.valueOf(recreateLeave, NotUseAtr.class);
	}

}
