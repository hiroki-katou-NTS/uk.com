package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;

/**
 * @author ThanhNX
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻記録
 *         打刻記録
 */
@AllArgsConstructor
public class StampRecord implements DomainAggregate {
	/**
	 * 	契約コード
	 */
	@Getter
	private final ContractCode contractCode;
	/**
	 * 打刻カード番号
	 */
	@Getter
	private final StampNumber stampNumber;

	/**
	 * 打刻日時
	 */
	@Getter
	private final GeneralDateTime stampDateTime;
	
	/**
	 * 	表示する打刻区分
	 */
	@Getter
	private final StampTypeDisplay stampTypeDisplay;

	/**
	 * 就業情報端末コード
	 */
	@Getter
	private final Optional<EmpInfoTerminalCode> empInfoTerCode;

	/**
	 * [C-0] 打刻記録(契約コード, 打刻カード番号, 	日時, 表示する打刻区分, Optional<就業情報端末コード>)
	 * @param contractCode
	 * @param stampNumber
	 * @param stampDateTime
	 * @param stampTypeDisplay
	 * @param empInfoTerCode
	 */


	public String retriveKey() {
		return this.getStampNumber()+ this.getStampDateTime().toString();
	}
}
