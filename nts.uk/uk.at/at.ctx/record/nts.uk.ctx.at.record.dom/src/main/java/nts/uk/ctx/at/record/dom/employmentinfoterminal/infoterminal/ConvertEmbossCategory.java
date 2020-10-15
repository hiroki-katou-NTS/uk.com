package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author ThanhNX
 *
 *         打刻区分を変換
 */
@Value
public class ConvertEmbossCategory implements DomainValue {

	/**
	 * 出退勤を入退門に変換
	 */
	private final NotUseAtr entranceExit;

	/**
	 * 外出を応援に変換
	 */
	private final NotUseAtr outSupport;
}
