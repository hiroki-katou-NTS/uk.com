package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * DS : 打刻場所を求める
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の前準備.スマホ打刻の打刻設定.打刻場所を求める
 * @author ThanhPV
 *
 */
public class FindWorkPlaceFromStampedPositionService {
	
	public static Optional<WorkLocation> find(Require require, Optional<GeoCoordinate> positionInfor, String cid) {
		//$打刻設定 = require.打刻設定を取得する()
		Optional<SettingsSmartphoneStamp> settingsSmartphoneStamp = require.getSettingsSmartphoneStamp(cid);
		
		if(!positionInfor.isPresent()) {
			if(settingsSmartphoneStamp.isPresent() && settingsSmartphoneStamp.get().getAreaLimitAtr().equals(NotUseAtr.USE)) {
				throw new BusinessException("Msg_2096");
			}
			return Optional.empty();
		}
		//$場所一覧 = require.勤務場所を取得する()
		// chờ team a lương làm xong agg 勤務場所 rồi làm tiếp.
		return Optional.empty();
	}
	
	
	public static interface Require {
		
		//[R-1] 勤務場所を取得する
		// chờ team a lương làm xong agg 勤務場所 rồi làm tiếp.
		//Optional<WorkLocation> findByCode (String companyID, String conString);
		
		// [R-2] 打刻設定を取得する
		Optional<SettingsSmartphoneStamp> getSettingsSmartphoneStamp(String cid);
		
	}
}
