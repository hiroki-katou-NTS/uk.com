package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         イムレコードのﾘｸｴｽﾄ設定Repository
 */
public interface TimeRecordReqSettingRepository {

	/**
	 * イムレコードのﾘｸｴｽﾄ設定を取得する
	 * 
	 * @param empInfoTerCode
	 * @return
	 */
	public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode);

}
