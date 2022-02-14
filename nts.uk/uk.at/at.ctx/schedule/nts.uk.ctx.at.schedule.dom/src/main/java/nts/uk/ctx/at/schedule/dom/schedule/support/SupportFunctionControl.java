package nts.uk.ctx.at.schedule.dom.schedule.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 応援予定の機能制御
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.応援.応援予定の機能制御
 * @author kumiko_otake
 */
@Getter
@AllArgsConstructor
public class SupportFunctionControl implements DomainAggregate {

	/** Serializable */
	@SuppressWarnings("unused") private static final long serialVersionUID = 1L;

	/** 応援予定を利用するか **/
	private final boolean isUse;
	/** 時間帯応援を利用するか **/
	private final boolean isUseSupportInTimezone;

}
