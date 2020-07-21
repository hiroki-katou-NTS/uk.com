package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/**
 * シフトマスタと出勤休日区分の組み合わせを取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.シフトマスタ
 * @author HieuLt
 *
 */
public class GetCombinationrAndWorkHolidayAtrService {
	
	/**
	 * [1] コードで取得する
	 * @param require
	 * @param companyID
	 * @param lstShiftMasterCd
	 * @return
	 */
	public static Map<ShiftMaster,Optional<WorkStyle>> getCode(Require require , String companyID , List<String> lstShiftMasterCd){
		//	$シフトマスタリスト = require.コードでシフトマスタ取得(会社ID, シフトマスタコードリスト)	
		List<ShiftMaster> lstShiftMater = require.getByListEmp(companyID, lstShiftMasterCd);
		// retrurn	[prv-1] 出勤休日区分をセット($シフトマスタリスト)
		return setWorkHolidayClassification(require, lstShiftMater);		
	}
	//
	public static Map<ShiftMaster,Optional<WorkStyle>> getbyWorkInfo(Require require , String companyId , List<WorkInformation> lstWorkInformation){
		List<ShiftMaster> listShiftMaster = require.getByListWorkInfo(companyId,lstWorkInformation);
		return setWorkHolidayClassification(require, listShiftMaster);
	}
	//[prv-1] 出勤休日区分をセット
	private static Map<ShiftMaster,Optional<WorkStyle>> setWorkHolidayClassification(Require require , List<ShiftMaster> listShiftMaster){
		Map<ShiftMaster,Optional<WorkStyle>> map = new HashMap<>();
		//listShiftMaster.stream().map(mapper->mapper.)
		//------------------------QA http://192.168.50.4:3000/issues/110620
		
		return null;
		
	} 
	
	public static interface Require{
		
		/**
		 * 
		 * [R-1] コードでシフトマスタ取得
		 * シフトマスタRepository.*get(会社ID, List<シフトマスタコード>)	
		 * @param lstShiftMasterCd
		 * @return
		 */
		List<ShiftMaster> getByListEmp(String companyID, List<String> lstShiftMasterCd);
		
		/**
		 * [R-2] 勤務情報でマスタリスト取得
		 * 	シフトマスタRepository.*get(会社ID, List<勤務情報>)			
		 * @param lstWorkInformation
		 * @return
		 */
		List<ShiftMaster> getByListWorkInfo(String companyId ,List<WorkInformation> lstWorkInformation);
		

	}

}
