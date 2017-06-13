/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.closure.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureMonth;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId;
import nts.uk.ctx.at.record.dom.workrecord.closure.UseClassification;

/**
 * The Class ClosureFindDto.
 */
@Getter
@Setter
public class ClosureFindDto implements ClosureSetMemento{
	
	/** The closure id. */
	private int closureId;
	
	/** The use classification. */
	private int useClassification;
	
	/** The month. */
	private int month;
	
	
	/** The closure histories. */
	private List<ClosureHistoryMDto> closureHistories;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#
	 * setCompanyId(nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Nothing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#
	 * setClosureId(java.lang.Integer)
	 */
	@Override
	public void setClosureId(Integer closureId) {
		this.closureId = closureId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#
	 * setUseClassification(nts.uk.ctx.at.record.dom.workrecord.closure.
	 * UseClassification)
	 */
	@Override
	public void setUseClassification(UseClassification useClassification) {
		this.useClassification = useClassification.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#setMonth(
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureMonth)
	 */
	@Override
	public void setMonth(ClosureMonth month) {
		this.month = month.getProcessingDate().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#
	 * setClosureHistories(java.util.List)
	 */
	@Override
	public void setClosureHistories(List<ClosureHistory> closureHistories) {
		this.closureHistories = closureHistories.stream().map(history->{
			ClosureHistoryMDto dto = new ClosureHistoryMDto();
			history.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	

}
