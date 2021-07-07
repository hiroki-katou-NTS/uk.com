package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * DS : 打刻場所を求める
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の前準備.スマホ打刻の打刻設定.打刻場所を求める
 * @author ThanhPV
 *
 */
public class FindWorkPlaceFromStampedPositionService {
	
	public static Optional<WorkLocation> find(Require require, Optional<GeoCoordinate> positionInfor) {
		//$打刻設定 = require.打刻設定を取得する()
		Optional<SettingsSmartphoneStamp> settingsSmartphoneStamp = require.getSettingsSmartphoneStamp();
		
		if(!positionInfor.isPresent()) {
			if(settingsSmartphoneStamp.isPresent() && settingsSmartphoneStamp.get().getAreaLimitAtr().equals(NotUseAtr.USE)) {
				throw new BusinessException("Msg_2096");
			}
			return Optional.empty();
		}
		//$場所一覧 = require.勤務場所を取得する()
		List<WorkLocation> workLocationList = require.findAll();
		//$勤務場所 = $場所一覧：																					
				//sort $.コード ASC																			
				//filter $.携帯打刻で打刻してもいい位置か判断する(打刻位置) == true									
				//first		
		Optional<WorkLocation> workLocation = workLocationList.stream().sorted(Comparator.comparing(WorkLocation::getWorkLocationCD)).filter(c->c.canStamptedByMobile(positionInfor.get())).findFirst();
		
		//if $打刻設定.isPresent AND $打刻設定.打刻エリア制限する == する AND $勤務場所.isEmpty						
			//BusinessException: Msg_2095
		if(settingsSmartphoneStamp.isPresent() && settingsSmartphoneStamp.get().getAreaLimitAtr().equals(NotUseAtr.USE) && !workLocation.isPresent()) {
			throw new BusinessException("Msg_2095");
		}
		
		return workLocation;
	}
	
	
	public static interface Require {
		
		//[R-1] 勤務場所を取得する
		//勤務場所Repository.契約コード条件として勤務場所を取得する(ログインしている契約コード)
		List<WorkLocation> findAll();
		// [R-2] 打刻設定を取得する
		Optional<SettingsSmartphoneStamp> getSettingsSmartphoneStamp();
		
	}
}
