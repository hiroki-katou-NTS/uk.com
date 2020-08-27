/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatdPK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;

/**
 * The Class JpaOutputItemDailyWorkScheduleSetMemento.
 * 
 * @author HoangDD change by LienPTK update specs ver34
 */
public class JpaOutputItemDailyWorkScheduleSetMemento
		implements FreeSettingOfOutputItemForDailyWorkSchedule.MementoSetter,
		OutputStandardSettingOfDailyWorkSchedule.MementoSetter {
	
	private List<KfnmtRptWkDaiOutnote> kfnmtRptWkDaiOutnotes;
	private List<KfnmtRptWkDaiOutatd> kfnmtRptWkDaiOutatds;
	private KfnmtRptWkDaiOutItem kfnmtRptWkDaiOutItem;

	@Override
	public void setCid(String companyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEmployeeId(String employeeId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelectionSetting(int itemSelection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOutputItemDailyWorkSchedule(List<OutputItemDailyWorkSchedule> outputItem) {
		// TODO Auto-generated method stub
	}

}
