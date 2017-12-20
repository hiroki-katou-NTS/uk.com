/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;

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
	
	/** The closure selected. */
	private ClosureHistoryMasterDto closureSelected;
	
	/** The closure histories. */
	private List<ClosureHistoryMasterDto> closureHistories;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Nothing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setClosureId(java.lang.Integer)
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.closureId = closureId.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setUseClassification(nts.uk.ctx.at.shared.dom.workrule.closure.
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
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#setMonth(
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureMonth)
	 */
	@Override
	public void setClosureMonth(CurrentMonth month) {
		this.month = month.getProcessingYm().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setClosureHistories(java.util.List)
	 */
	@Override
	public void setClosureHistories(List<ClosureHistory> closureHistories) {
		
		// check empty list closure history
		if (CollectionUtil.isEmpty(closureHistories)) {
			this.closureHistories = new ArrayList<>();
			return;
		}
		
		this.closureHistories = closureHistories.stream().map(history->{
			ClosureHistoryMasterDto dto = new ClosureHistoryMasterDto();
			history.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

}
