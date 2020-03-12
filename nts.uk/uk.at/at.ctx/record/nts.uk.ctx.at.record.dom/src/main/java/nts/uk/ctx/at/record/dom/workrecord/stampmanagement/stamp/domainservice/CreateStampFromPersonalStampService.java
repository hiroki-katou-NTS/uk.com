package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonSettings;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;

/**
 * @author ThanhNX
 *
 *         個人打刻入から打刻を作成する
 */
@Stateless
public class CreateStampFromPersonalStampService {

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
	public void create(Require require, String employeeId, GeneralDateTime stmapDateTime, int pageNo, int buttonPosNo,
			Relieve relieve, RefectActualResult refActualResults, Optional<Integer> positionInfo,
			Optional<EmpInfoTerminalCode> empInfoTerCode) {

		// $個人利用の打刻設定 = require.個人利用の打刻設定を取得する()
		Optional<StampSettingPerson> stampSettingPerOpt = require.getStampSet();

		// $ボタン詳細設定 = $個人利用の打刻設定.ボタン詳細設定を取得する(ページNO, ボタン位置NO)
		Optional<ButtonSettings> buttonSet = stampSettingPerOpt.get().getButtonSet(pageNo, buttonPosNo);
		if (!buttonSet.isPresent())
			throw new BusinessException("Msg_1632");
		//TODO: return 社員の打刻データを作成する.作成する(require, 社員ID, 打刻日時, $ボタン詳細設定.ボタン種類, 実績への反映内容, 打刻位置情報, 就業情報端末コード)
		
	}

	public static interface Require {
		
		 //[R-1] 個人利用の打刻設定を取得する -StampSetPerRepository
		 Optional<StampSettingPerson> getStampSet ();
	}
}
