package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * @author ThanhNX
 *
 *         就業情報端末のリクエスト一覧Repository
 */
public interface TimeRecordReqSettingRepository {

	/**
	 * 就業情報端末のリクエスト一覧を取得する
	 * 
	 * @param empInfoTerCode
	 * @return
	 */
	public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode);

	/**
	 * 就業情報端末のリクエスト一覧を更新する
	 * 
	 * @param empInfoTerCode
	 * @param contractCode
	 */
	public void updateSetting(TimeRecordReqSetting setting);

}
