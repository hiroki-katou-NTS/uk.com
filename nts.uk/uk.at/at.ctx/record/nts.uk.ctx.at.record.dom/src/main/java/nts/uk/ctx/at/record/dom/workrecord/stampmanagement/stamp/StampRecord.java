package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;

/**
 * @author ThanhNX
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻記録
 *         打刻記録
 */
@AllArgsConstructor
@Getter
public class StampRecord implements DomainAggregate {
																																									
	/**
	 * 	契約コード
	 */
	private final ContractCode contractCode;
	/**
	 * 打刻カード番号
	 */
	private final StampNumber stampNumber;

	/**
	 * 打刻日時
	 */
	private final GeneralDateTime stampDateTime;

	/**
	 * 打刻区分
	 */
	private final boolean stampArt;

	/**
	 * 予約区分
	 */
	private final ReservationArt revervationAtr;

	/**
	 * 就業情報端末コード
	 */
	private final Optional<EmpInfoTerminalCode> empInfoTerCode;

	/**
	 * [C-0] 打刻記録(契約コード, 打刻カード番号, 打刻日時, 打刻区分, 予約区分, 就業情報端末コード)
	*/
}
