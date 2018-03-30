package nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;

/**
 * 介護休暇基本情報
 * @author xuan vinh
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NursingCareLeaveRemainingInfo extends AggregateRoot{
	
	//社員ID
	private String sId;
	
	private LeaveType leaveType;
	
	//使用区分
	private boolean useClassification;
	
	//上限設定
	private UpperLimitSetting upperlimitSetting;	
	
	//本年度上限日数
	private Optional<Integer> maxDayForThisFiscalYear;
	
	//次年度上限日数
	private Optional<Integer> maxDayForNextFiscalYear;
	
	public static NursingCareLeaveRemainingInfo createChildCareLeaveInfo(String sId, int useClassification, int upperlimitSetting, 
			Optional<Integer> maxDayForThisFiscalYear, Optional<Integer> maxDayForNextFiscalYear){
		return new NursingCareLeaveRemainingInfo(sId, EnumAdaptor.valueOf(2, LeaveType.class), 
				useClassification == 1, EnumAdaptor.valueOf(upperlimitSetting, UpperLimitSetting.class), 
				maxDayForThisFiscalYear, maxDayForNextFiscalYear);
	}
	
	public static NursingCareLeaveRemainingInfo createCareLeaveInfo(String sId, int useClassification, int upperlimitSetting, 
			Optional<Integer> maxDayForThisFiscalYear, Optional<Integer> maxDayForNextFiscalYear){
		return new NursingCareLeaveRemainingInfo(sId, EnumAdaptor.valueOf(1, LeaveType.class), 
				useClassification == 1, EnumAdaptor.valueOf(upperlimitSetting, UpperLimitSetting.class), 
				maxDayForThisFiscalYear, maxDayForNextFiscalYear);
	}
}
