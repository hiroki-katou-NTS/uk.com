/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail.memento;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryCategorySetting;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingCode;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingGetMemento;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingName;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormDetail;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstFormHead;

/**
 * The Class JpaSalaryOutputSettingGetMemento.
 */
public class JpaSalaryOutputSettingGetMemento implements SalaryOutputSettingGetMemento {

	/** The entity. */
	private QlsptPaylstFormHead entity;

	/** The is load header data only. */
	private boolean isLoadHeaderDataOnly;

	/**
	 * Instantiates a new jpa salary output setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaSalaryOutputSettingGetMemento(QlsptPaylstFormHead entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingGetMemento#getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.entity.getQlsptPaylstFormHeadPK().getCcd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingGetMemento#getCode()
	 */
	@Override
	public SalaryOutputSettingCode getCode() {
		return new SalaryOutputSettingCode(this.entity.getQlsptPaylstFormHeadPK().getFormCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingGetMemento#getName()
	 */
	@Override
	public SalaryOutputSettingName getName() {
		return new SalaryOutputSettingName(this.entity.getFormName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingGetMemento#getCategorySettings()
	 */
	@Override
	public List<SalaryCategorySetting> getCategorySettings() {
		if (this.isLoadHeaderDataOnly) {
			return null;
		}
		// Group detail list by category.
		Map<SalaryCategory, List<QlsptPaylstFormDetail>> categoryMap = this.entity.getQlsptPaylstFormDetailList()
				.stream().collect(Collectors.groupingBy(item -> {
					return SalaryCategory.valueOf(item.getQlsptPaylstFormDetailPK().getCtgAtr());
				}));
		List<SalaryCategorySetting> categorySettings = new ArrayList<>();

		for (SalaryCategory category : categoryMap.keySet()) {
			List<QlsptPaylstFormDetail> categoryList = categoryMap.get(category);
			categorySettings
					.add(new SalaryCategorySetting(new JpaSalaryCategorySettingGetMemento(categoryList, category)));
		}

		return categorySettings;
	}

}
