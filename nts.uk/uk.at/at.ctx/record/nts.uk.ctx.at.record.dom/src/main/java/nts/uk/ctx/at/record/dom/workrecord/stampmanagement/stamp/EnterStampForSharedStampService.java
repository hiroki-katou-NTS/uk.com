package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectProcessService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;

/**
 * DS: 共有打刻から打刻を入力する
 * path: 
 * @author chungnt
 *
 */
public class EnterStampForSharedStampService {

	/**
	 * [1] Create
	 * 
	 * @param require
	 * @param ContractCode
	 *            契約コード
	 * @param employeeID
	 *            社員ID
	 * @param stampCardNo
	 *            打刻カード番号
	 * @param stampMethod
	 *            打刻する方法
	 * @param stampDatetime
	 *            打刻日時
	 * @param stampButton
	 *            打刻ボタン
	 * @param 実績への反映内容
	 * 
	 * @return stampInputResutl 打刻入力結果
	 * 
	 */
	public static StampDataReflectResult create(Require require ,String conteactCode, String employeeID, StampNumber StampNumber,
			Relieve relieve,GeneralDateTime stmapDateTime, String stampButton , Optional<RefectActualResult> refActualResults) {
		
		//	$共有打刻の打刻設定 = require.共有打刻の打刻設定を取得する()				
		// doi

		//$ボタン詳細設定 = $共有打刻の打刻設定.ボタン詳細設定を取得する(打刻ボタン)																

		
		// return 社員の打刻データを作成する#作成する(require, 契約コード, 社員ID, 打刻カード番号, 打刻日時, 打刻する方法, $ボタン詳細設定.ボタン種類, 実績への反映内容, empty)
		return null;
			
	}
	
	public static interface Require extends StampDataReflectProcessService.Require {
		/**
		 * [R-1] 共有打刻の打刻設定を取得する
		 * 
		 * 	共有打刻の打刻設定Repository.取得する()										
		 */
		
	}

}
