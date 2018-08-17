package nts.uk.ctx.at.record.pub.dailyprocess.scheduletime;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.ManagePerCompanySet;


/**
 * Publisher
 * @author keisuke_hoshina
 *
 */
public interface ScheduleTimePub {
	//RequestList No.91
	ScheduleTimePubExport calculationScheduleTime(ScheduleTimePubImport impTime);
	
	//RequestList No.91 
	//For multi people
	List<ScheduleTimePubExport> calclationScheduleTimeForMultiPeople(List<ScheduleTimePubImport> impList);
	
	//RequestList No91
	//For able Common Company Setting
	List<ScheduleTimePubExport> calclationScheduleTimePassCompanyCommonSetting(List<ScheduleTimePubImport> impList,Optional<ManagePerCompanySet> companySetting);
}
