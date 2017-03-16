/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail.memento;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryCategorySettingGetMemento;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputItem;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormDetail;

/**
 * The Class JpaSalaryCategorySettingGetMemento.
 */
public class JpaSalaryCategorySettingGetMemento implements SalaryCategorySettingGetMemento {

	/** The details. */
	private List<QlsptPaylstFormDetail> details;

	/** The category. */
	private SalaryCategory category;

	/**
	 * Instantiates a new jpa salary category setting get memento.
	 *
	 * @param details the details
	 * @param category the category
	 */
	public JpaSalaryCategorySettingGetMemento(List<QlsptPaylstFormDetail> details, SalaryCategory category) {
		super();
		this.details = details;
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.
	 * SalaryCategorySettingGetMemento#getSalaryCategory()
	 */
	@Override
	public SalaryCategory getSalaryCategory() {
		return this.category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.
	 * SalaryCategorySettingGetMemento#getSalaryOutputItems()
	 */
	@Override
	public List<SalaryOutputItem> getSalaryOutputItems() {
		return this.details.stream().map(detail -> {
			return new SalaryOutputItem(new JpaSalaryOutputItemGetMemento(detail));
		}).collect(Collectors.toList());
	}

}
