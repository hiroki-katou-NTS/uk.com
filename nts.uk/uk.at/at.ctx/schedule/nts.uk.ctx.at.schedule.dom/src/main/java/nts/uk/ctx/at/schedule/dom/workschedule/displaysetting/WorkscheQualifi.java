package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.List;

import lombok.Getter;
import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;
/**
 * 	勤務予定の資格設定	
 * @author HieuLT
 *
 */
@Value

public class WorkscheQualifi implements DomainValue {

	@Getter
	/**	資格表示記号 --- 資格表示記号**/
	private final PersonSymbolQualify qualificationMark;
	
	@Getter
	/** 使用資格リスト --- List<資格コード> **/
	private final List<QualificationCD> listQualificationCD;
	
	/**
	 * [C-1] 勤務予定の資格設定
	 * @param qualificationMark
	 * @param listQualificationCD
	 * @return
	 */
	public static  WorkscheQualifi workScheduleQualification(PersonSymbolQualify qualificationMark, List<QualificationCD> listQualificationCD) {
		//	inv-1	1 <= 使用資格リスト.size <=5		
		if(listQualificationCD.size() > 5 ||listQualificationCD.size() == 0 ){
			throw new BusinessException("Msg_1786");
		}
		return new WorkscheQualifi(qualificationMark, listQualificationCD) ;
	}
	
	
	
}

