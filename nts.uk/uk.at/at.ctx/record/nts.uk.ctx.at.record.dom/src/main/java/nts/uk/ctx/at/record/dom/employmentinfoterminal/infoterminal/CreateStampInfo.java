package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

/**
 * @author ThanhNX
 *
 *         打刻情報の作成
 */
@Value
public class CreateStampInfo implements DomainValue {

	/**
	 * 外出打刻の変換
	 */
	private final OutPlaceConvert outPlaceConvert;

	/**
	 * 打刻区分を変換
	 */
	private final ConvertEmbossCategory convertEmbCate;

	/**
	 * 勤務場所コード
	 */
	private final Optional<WorkLocationCD> workLocationCd;
}
