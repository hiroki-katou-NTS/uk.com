package nts.uk.ctx.at.request.dom.application.common.adapter.record.infoterminal;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.TimeRecordReqSetting;

/**
 * @author ThanhNX
 *
 *         就業情報端末のリクエスト一覧Adapter
 */
public interface TimeRecordReqSettingAdapter {

	/**
	 * 就業情報端末のリクエスト一覧を取得する
	 * 
	 * @param empInfoTerCode
	 * @return
	 */
	public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(Integer empInfoTerCode,
			String contractCode);

}
