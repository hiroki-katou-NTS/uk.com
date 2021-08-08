/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getaggregatedInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftDto;
import nts.uk.screen.at.app.ksu001.processcommon.WorkScheduleWorkInforDto;
import nts.uk.screen.at.app.ksu001.start.AggregatePersonalMapDto;
import nts.uk.screen.at.app.ksu001.start.AggregateWorkplaceMapDto;
import nts.uk.screen.at.app.ksu001.start.ExternalBudgetMapDtoList;

/**
 * @author laitv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggregatedInformationRs {

	public List<ExternalBudgetMapDtoList> externalBudget = Collections.emptyList(); // Map<年月日, Map<外部予算実績項目, 外部予算実績値>>
	public AggregatePersonalMapDto aggreratePersonal; // 個人計集計結果 ←集計内容によって情報が異なる
	public AggregateWorkplaceMapDto aggrerateWorkplace; // ・職場計集計結果
	
	public List<WorkScheduleWorkInforDto> listWorkScheduleWorkInfor= Collections.emptyList();
	public List<ScheduleOfShiftDto> listWorkScheduleShift= Collections.emptyList();
	public Map<ShiftMaster,Optional<WorkStyle>> mapShiftMasterWithWorkStyle = new HashMap<ShiftMaster, Optional<WorkStyle>>();

}
