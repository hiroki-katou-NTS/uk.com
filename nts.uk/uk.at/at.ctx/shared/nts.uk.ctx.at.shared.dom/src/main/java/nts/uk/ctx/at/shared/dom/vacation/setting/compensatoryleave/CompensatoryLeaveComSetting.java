/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * 代休管理設定
 * The Class CompensatoryLeaveComSetting.
 */
// 
@Getter
public class CompensatoryLeaveComSetting extends AggregateRoot {

	// 会社ID
	/** The company id. */
	private String companyId;

	// 管理区分
	/** The is managed. */
	private ManageDistinct isManaged;

	// 取得と使用方法
	/** The normal vacation setting. */
	private CompensatoryAcquisitionUse compensatoryAcquisitionUse;

	// 時間代休の消化単位
	/** The compensatory digestive time unit. */
	private CompensatoryDigestiveTimeUnit compensatoryDigestiveTimeUnit;

	// 発生設定
	/** The occurrence vacation setting. */
	private List<CompensatoryOccurrenceSetting> compensatoryOccurrenceSetting;
    // 代休発生設定 ---
	private SubstituteHolidaySetting substituteHolidaySetting;
	// 紐付け管理区分
	private ManageDistinct linkingManagementATR;
	
	
	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	public boolean isManaged() {
		return this.isManaged.equals(ManageDistinct.YES);
	}

	/**
	 * Instantiates a new compensatory leave com setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public CompensatoryLeaveComSetting(CompensatoryLeaveComGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.isManaged = memento.getIsManaged();
		this.compensatoryAcquisitionUse = memento.getCompensatoryAcquisitionUse();
		this.compensatoryDigestiveTimeUnit = memento.getCompensatoryDigestiveTimeUnit();
		this.compensatoryOccurrenceSetting = memento.getCompensatoryOccurrenceSetting();
		this.substituteHolidaySetting = memento.getSubstituteHolidaySetting();
		this.linkingManagementATR = memento.getLinkingManagementATR();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(CompensatoryLeaveComSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setIsManaged(this.isManaged);
		memento.setCompensatoryAcquisitionUse(this.compensatoryAcquisitionUse);
		memento.setCompensatoryDigestiveTimeUnit(this.compensatoryDigestiveTimeUnit);
		memento.setCompensatoryOccurrenceSetting(this.compensatoryOccurrenceSetting);
		memento.setSubstituteHolidaySetting(this.substituteHolidaySetting);
		memento.setLinkingManagementATR(this.linkingManagementATR);
	}
}
