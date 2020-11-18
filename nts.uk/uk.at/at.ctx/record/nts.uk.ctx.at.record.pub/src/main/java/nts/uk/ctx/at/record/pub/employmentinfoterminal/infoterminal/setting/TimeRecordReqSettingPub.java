package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting;

import java.util.Optional;

import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.setting.dto.TimeRecordReqSettingExport;

/**
 * @author ThanhNX
 *
 *         就業情報端末のリクエスト一覧Pub
 */
public interface TimeRecordReqSettingPub {

	/**
	 * 就業情報端末のリクエスト一覧を取得する
	 * 
	 * @param empInfoTerCode
	 * @return
	 */
	public Optional<TimeRecordReqSettingExport> getTimeRecordReqSetting(Integer empInfoTerCode,
			String contractCode);

}
