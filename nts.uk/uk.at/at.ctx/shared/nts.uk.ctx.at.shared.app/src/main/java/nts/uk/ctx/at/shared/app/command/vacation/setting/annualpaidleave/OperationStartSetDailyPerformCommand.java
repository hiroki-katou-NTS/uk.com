/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.annualpaidleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformGetMemento;

/**
 * The Class OperationStartSetDailyPerformCommand.
 */
@Getter
@Setter
public class OperationStartSetDailyPerformCommand implements OperationStartSetDailyPerformGetMemento{
	
	private static final String YYYYMMDD = "yyyy/MM/dd";
	
	// 会社ID
	private String companyId;
	
	/** The operate start date daily perform. */
	// 日別実績の運用開始日
	private	String operateStartDateDailyPerform;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.companyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformGetMemento#getOperateStartDateDailyPerform()
	 */
	@Override
	public Optional<GeneralDate> getOperateStartDateDailyPerform() {
		if (this.operateStartDateDailyPerform.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(GeneralDate.fromString(this.operateStartDateDailyPerform, YYYYMMDD));
	}
}

