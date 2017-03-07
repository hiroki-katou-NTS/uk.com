/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDemensionDetail;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;

/**
 * The Class WageTableHistoryUpdateCommand.
 */
@Setter
@Getter
public class WageTableHistoryUpdateCommand extends WageTableHistoryBaseCommand {

	/** The history id. */
	private String historyId;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the unit price history
	 */
	public WageTableHistory toDomain(CompanyCode companyCode) {
		WageTableHistoryUpdateCommand command = this;

		// Transfer data
		WageTableHistory wageTableHistory = new WageTableHistory(new WageTableHistoryGetMemento() {

			@Override
			public List<WageTableItem> getValueItems() {
				return command.getValueItems().stream().map(item -> new WageTableItem(item))
						.collect(Collectors.toList());
			}

			@Override
			public String getHistoryId() {
				return command.getHistoryId();
			}

			@Override
			public List<WageTableDemensionDetail> getDemensionDetail() {
				return command.getDemensionDetails().stream()
						.map(item -> new WageTableDemensionDetail(item))
						.collect(Collectors.toList());
			}

			@Override
			public CompanyCode getCompanyCode() {
				return companyCode;
			}

			@Override
			public WageTableCode getCode() {
				return new WageTableCode(command.getCode());
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.range(command.getStartMonth(), command.getEndMonth(),
						PrimitiveUtil.DEFAULT_YM_SEPARATOR_CHAR);
			}
		});

		return wageTableHistory;

	}
}
