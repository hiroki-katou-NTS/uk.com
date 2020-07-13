package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
/**
 * VO_勤務情報
 * @author HieuLt
 *
 */
@Value
public class WorkInformation implements DomainValue {
	
	/**勤務種類コード**/
	private final WorkTypeCode workTypeCd;
	/**就業時間帯コード **/
	private final WorkTimeCode workTimeCd;
	
	/**
	 * [1] 正常な状態か  (trạng thái bình thường?)
	 * @param require
	 * @return
	 */
	public boolean isNormal(Require require){
		return false;
		
	}
	
	private static interface Require{
		
		/**
		 * [R-1] 勤務種類を取得する(Get worktype)
		 * アルゴリズム.勤務種類を取得する(会社ID, 勤務種類コード)	
		 * @param companyId
		 * @param workTypeCd
		 * @return
		 */
		Optional<WorkType> findByPK(String companyId, String workTypeCd);
		
		/**
		 *[R-2] 就業時間帯を取得する(Get worktime)	
		 * アルゴリズム.会社を指定し就業時間帯を取得する(会社ID, 就業時間帯コード)					
		 * @param companyId
		 * @param workTimeCode
		 * @return
		 */
		Optional<WorkTimeSetting> findByCode(String companyId, String workTimeCode);
		
		
		//----------------------------- Chờ QA http://192.168.50.4:3000/issues/110619
		/**
		 * [R-4] 1日半日出勤・1日休日系の判定(phán định là đi làm 1 ngày nửa ngày/ nghỉ 1 ngày)
		 * 	アルゴリズム.1日半日出勤・1日休日系の判定(勤務種類コード)	
		 * @param workTypeCode
		 * @return
		 */
		WorkStyle find(String workTypeCode);
		
		 
				
	}
}
