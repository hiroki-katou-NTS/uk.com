package nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
}
