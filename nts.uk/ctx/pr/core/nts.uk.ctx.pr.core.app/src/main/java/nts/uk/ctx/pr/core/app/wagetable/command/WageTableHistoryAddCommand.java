/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableDemensionDetail;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryGetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableItem;

/**
 * The Class WageTableHistoryAddCommand.
 */
@Setter
@Getter
public class WageTableHistoryAddCommand extends WageTableHistoryBaseCommand {

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the wage table history
	 */
	public WageTableHistory toDomain(CompanyCode companyCode) {
		WageTableHistoryAddCommand command = this;

		// Transfer data
		WageTableHistory wageTableHistory = new WageTableHistory(
				new WageTableHistoryAddCommandMemento(companyCode, command));

		return wageTableHistory;

	}

	/**
	 * The Class WageTableHistoryAddCommandMemento.
	 */
	private class WageTableHistoryAddCommandMemento implements WageTableHistoryGetMemento {

		/** The type value. */
		protected WageTableHistoryAddCommand command;

		/** The company code. */
		protected CompanyCode companyCode;

		/**
		 * Instantiates a new jpa accident insurance rate get memento.
		 *
		 * @param typeValue
		 *            the type value
		 */
		public WageTableHistoryAddCommandMemento(CompanyCode companyCode,
				WageTableHistoryAddCommand command) {
			this.companyCode = companyCode;
			this.command = command;
		}

		@Override
		public List<WageTableItem> getValueItems() {
			return command.getValueItems().stream().map(item -> new WageTableItem(item))
					.collect(Collectors.toList());
		}

		@Override
		public String getHistoryId() {
			return IdentifierUtil.randomUniqueId();
		}

		@Override
		public List<WageTableDemensionDetail> getDemensionDetail() {
			return command.getDemensionDetails().stream()
					.map(item -> new WageTableDemensionDetail(item)).collect(Collectors.toList());
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
	}
}
