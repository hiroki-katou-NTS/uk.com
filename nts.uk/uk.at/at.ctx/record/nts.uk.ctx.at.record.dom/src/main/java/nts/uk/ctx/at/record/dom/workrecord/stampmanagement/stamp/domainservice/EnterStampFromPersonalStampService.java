package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;

/**
 * @author ThanhNX
 *
 *         個人打刻から打刻を入力する (old : 個人打刻入力から打刻を作成する)		
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.個人打刻から打刻を入力する
 */
public class EnterStampFromPersonalStampService {

	// [1] 作成する
	/**
	 * @param require 	@Require
	 * @param employeeId 社員ID	
	 * @param stmapDateTime 打刻日時		
	 * @param pageNo ページNO	
	 * @param buttonPosNo ボタン位置NO
	 * @param relieve 打刻する方法	
	 * @param refActualResults 実績への反映内容	
	 * @param positionInfo 打刻位置情報	
	 * @param empInfoTerCode 就業情報端末コード
	 */
	public static StampDataReflectResult create(Require require, String employeeId, GeneralDateTime stmapDateTime, int pageNo, int buttonPosNo,
			Relieve relieve, RefectActualResult refActualResults, Optional<GeoCoordinate> positionInfo,
			Optional<EmpInfoTerminalCode> empInfoTerCode) {

		// $個人利用の打刻設定 = require.個人利用の打刻設定を取得する()
		Optional<StampSettingPerson> stampSettingPerOpt = require.getStampSet();
		if (!stampSettingPerOpt.isPresent()) {
			throw new BusinessException("Msg_1632");
		}

		// $ボタン詳細設定 = $個人利用の打刻設定.ボタン詳細設定を取得する(ページNO, ボタン位置NO)
		Optional<ButtonSettings> buttonSet = stampSettingPerOpt.get().getButtonSet(pageNo, buttonPosNo);
		if (!buttonSet.isPresent()) {
			throw new BusinessException("Msg_1632");
		}
		// return 社員の打刻データを作成する.作成する(require, 社員ID, 打刻日時, $ボタン詳細設定.ボタン種類, 実績への反映内容, 打刻位置情報, 就業情報端末コード)
		return CreateStampDataForEmployeesService.create(require, employeeId, stmapDateTime, relieve, buttonSet.get().getButtonType(), Optional.of(refActualResults), positionInfo, empInfoTerCode);
		
	}

	public static interface Require extends CreateStampDataForEmployeesService.Require {
		
		 //[R-1] 個人利用の打刻設定を取得する -StampSetPerRepository
		 Optional<StampSettingPerson> getStampSet ();
	}
}
