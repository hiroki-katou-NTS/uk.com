/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLCategorySetting;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingCode;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingGetMemento;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingName;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormDetail;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerFormHead;

/**
 * The Class JpaWLOutputSettingGetMemento.
 */
@AllArgsConstructor
public class JpaWLOutputSettingGetMemento implements WLOutputSettingGetMemento {
	
	/** The entity. */
	private QlsptLedgerFormHead entity;
	
	/** The is load header data only. */
	private boolean isLoadHeaderDataOnly;
	
	/**
	 * Instantiates a new jpa WL output setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWLOutputSettingGetMemento(QlsptLedgerFormHead entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingGetMemento
	 * #getCode()
	 */
	@Override
	public WLOutputSettingCode getCode() {
		return new WLOutputSettingCode(
				this.entity.getQlsptLedgerFormHeadPK().getFormCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingGetMemento
	 * #getName()
	 */
	@Override
	public WLOutputSettingName getName() {
		return new WLOutputSettingName(this.entity.getFormName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingGetMemento
	 * #getOnceSheetPerPerson()
	 */
	@Override
	public Boolean getOnceSheetPerPerson() {
		// 1 => true.
		// 0 => false.
		return this.entity.getPrint1pageByPersonSet() == 1;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingGetMemento
	 * #getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.entity.getQlsptLedgerFormHeadPK().getCcd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingGetMemento
	 * #getCategorySettings()
	 */
	@Override
	public List<WLCategorySetting> getCategorySettings() {
		if (this.isLoadHeaderDataOnly) {
			return null;
		}
		// Group detail list by category.
		Map<WLCategory, List<QlsptLedgerFormDetail>> categoryMap = this.entity
				.getQlsptLedgerFormDetailList().stream()
				.collect(Collectors.groupingBy(item -> {
					return WLCategory.valueOf(item.getQlsptLedgerFormDetailPK().getCtgAtr());
				}));
		List<WLCategorySetting> categorySettings = new ArrayList<>();
		for (WLCategory category : categoryMap.keySet()) {
			List<QlsptLedgerFormDetail> categoryList = categoryMap.get(category);
			
			// Group category Setting by payment type.
			Map<PaymentType, List<QlsptLedgerFormDetail>> paymentTypeMap = categoryList
					.stream().collect(Collectors.groupingBy(
							item -> PaymentType.valueOf(item.getQlsptLedgerFormDetailPK().getPayBonusAtr())));
			for (PaymentType paymentType : paymentTypeMap.keySet()) {
				List<QlsptLedgerFormDetail> paymentTypeList = paymentTypeMap.get(paymentType);
				categorySettings.add(new WLCategorySetting(
						new JpaWLCategorySettingGetMemento(paymentTypeList, category, paymentType)));
			}
		}
				
		// Convert to domain.
		
		return categorySettings;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingGetMemento
	 * #getVersion()
	 */
	@Override
	public long getVersion() {
		return this.entity.getExclusVer();
	}

}
