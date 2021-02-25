package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;

/**
 * 個人条件の表示制御 
 * 
 */
@NoArgsConstructor
@Getter
public class DisplayControlPersonalCondDto   {


	/** 会社ID **/
	public  String companyID;
	/** List<条件表示制御> --- 条件表示制御リスト **/
	public List<PersonInforDisplayControlDto> listConditionDisplayControl;

	/**	資格表示記号 --- 資格表示記号**/
	public String qualificationMark;
	
	/** 使用資格リスト --- List<資格コード> **/
	public List<String> listQualificationCD;

	public DisplayControlPersonalCondDto(DisplayControlPersonalCondition domain) {
		this.companyID = domain.getCompanyID();
		
		this.listConditionDisplayControl = domain.getListConditionDisplayControl().stream().map(item -> {
			return new PersonInforDisplayControlDto(item.getConditionATR().value, item.getDisplayCategory().value);
		}).collect(Collectors.toList());
		
		this.qualificationMark = qualificationMark;
		this.listQualificationCD = listQualificationCD;
	}
	
	

	
}
