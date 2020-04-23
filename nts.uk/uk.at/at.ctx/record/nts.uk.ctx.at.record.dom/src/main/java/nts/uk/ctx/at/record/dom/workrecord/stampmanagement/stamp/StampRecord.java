package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.management.ReservationArt;

/**
 * @author ThanhNX
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻記録
 *         打刻記録
 */
public class StampRecord implements DomainAggregate {

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
	 * 打刻区分
	 */
	@Getter
	private final boolean stampArt;

	/**
	 * 予約区分
	 */
	@Getter
	private final ReservationArt revervationAtr;

	/**
	 * 就業情報端末コード
	 */
	@Getter
	private final Optional<EmpInfoTerminalCode> empInfoTerCode;

	public StampRecord(StampNumber stampNumber, GeneralDateTime stampDateTime, boolean stampArt,
			ReservationArt revervationAtr, Optional<EmpInfoTerminalCode> empInfoTerCode) {
		super();
		this.stampNumber = stampNumber;
		this.stampDateTime = stampDateTime;
		this.stampArt = stampArt;
		this.revervationAtr = revervationAtr;
		this.empInfoTerCode = empInfoTerCode;
	}

	public String retriveKey() {
		return this.getStampNumber()+ this.getStampDateTime().toString();
	}
}
