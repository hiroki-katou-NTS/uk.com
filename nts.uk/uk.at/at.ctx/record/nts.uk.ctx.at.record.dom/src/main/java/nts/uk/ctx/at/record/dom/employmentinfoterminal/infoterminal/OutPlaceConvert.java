package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author ThanhNX
 *
 *         外出打刻の変換
 */
@Value
public class OutPlaceConvert implements DomainValue {

	/**
	 * 置換する
	 */
	private final NotUseAtr replace;

	/**
	 * 外出理由
	 */
	private final Optional<GoingOutReason> goOutReason;
}
