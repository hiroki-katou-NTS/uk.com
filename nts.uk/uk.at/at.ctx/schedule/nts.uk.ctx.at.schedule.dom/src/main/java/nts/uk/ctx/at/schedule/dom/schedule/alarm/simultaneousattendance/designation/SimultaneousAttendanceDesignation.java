package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.designation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * AR: 同時出勤指定
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class SimultaneousAttendanceDesignation implements DomainAggregate{
	//社員ID
	private final String sid;
	
	//同時に出勤すべき社員の候補
	private List<String> empMustWorkTogetherLst;
	
	/**
	 * 
	 * [C-1] 作成する
	 * @param sid
	 * @param empMustWorkTogetherLst
	 * @return
	 */
	public  static SimultaneousAttendanceDesignation create(String sid, List<String> empMustWorkTogetherLst) {
		
		if(empMustWorkTogetherLst.isEmpty() || empMustWorkTogetherLst.size() > 10) {
			throw new BusinessException("Msg_1881");
		}
		
		if(empMustWorkTogetherLst.contains(sid)) {
			throw new BusinessException("Msg_1882");
		}
		
		return new SimultaneousAttendanceDesignation(sid, empMustWorkTogetherLst);
		
	}
	
	

}
