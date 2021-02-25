package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;

/**
 * @author anhdt
 * シフトマスタと出勤休日区分の組み合わせを取得する
 */
public class GetCombineShiftMasterandWorkHolidayClassificationService {
	
	public static Map<ShiftMaster, Optional<WorkStyle>> get(Required required, String companyId, List<String> shiftMasterCodes) {
		return null;
	}
	
	public static interface Required {
		/**
		 * ShiftMasterRepository
		 * [R-1] コードでシフトマスタ取得 
		 * @param companyId
		 * @param listShiftMaterCode
		 * @return
		 */
		List<ShiftMasterDto> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode);
		
		/**
		 * ShiftMasterRepository
		 * [R-2] 勤務情報でマスタリスト取得
		 * @param cid
		 * @param workInfos
		 * @return
		 */
		List<ShiftMaster> getbyCidAndWorkInfo(String cid, List<WorkInformation> workInfos);
	}
}
