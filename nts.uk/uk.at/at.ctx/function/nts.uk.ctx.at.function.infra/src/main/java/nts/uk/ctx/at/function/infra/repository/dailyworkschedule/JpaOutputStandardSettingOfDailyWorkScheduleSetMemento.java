/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;

/**
 * The Class JpaOutputStandardSettingOfDailyWorkScheduleSetMemento.
 *
 * @author LienPTK
 */
@Data
public class JpaOutputStandardSettingOfDailyWorkScheduleSetMemento implements OutputStandardSettingOfDailyWorkSchedule.MementoSetter {

	/** The kfnmt rpt wk dai out item. */
	private List<KfnmtRptWkDaiOutItem> kfnmtRptWkDaiOutItem;
	
	/** The company id. */
	private String companyId;
	
	/** The selection. */
	private int selection;

	/**
	 * Sets the output items.
	 *
	 * @param outputItem the new output items
	 */
	@Override
	public void setOutputItems(List<OutputItemDailyWorkSchedule> outputItem) {
		this.kfnmtRptWkDaiOutItem = outputItem.stream().map(t -> {
			KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem();
			t.saveToMemento(entity);
			return entity;
		}).collect(Collectors.toList());
	}


}
