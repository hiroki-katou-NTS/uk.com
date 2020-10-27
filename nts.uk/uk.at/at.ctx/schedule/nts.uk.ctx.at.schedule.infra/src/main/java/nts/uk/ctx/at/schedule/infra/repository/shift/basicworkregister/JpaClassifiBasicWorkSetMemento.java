/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCls;

/**
 * The Class JpaClassifiBasicWorkSetMemento.
 */
public class JpaClassifiBasicWorkSetMemento implements ClassifiBasicWorkSetMemento {

	/** The type value. */
	private List<KscmtBasicWorkCls> typeValue;

	/**
	 * Instantiates a new jpa classifi basic work set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaClassifiBasicWorkSetMemento(List<KscmtBasicWorkCls> typeValue) {
		super();
		this.typeValue = typeValue;
		if (this.typeValue == null) {
			this.typeValue = new ArrayList<>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.stream().forEach(item -> {
			item.getKscmtBasicWorkClsPK().setCid(companyId);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkSetMemento#setClassificationCode(nts.uk.ctx.at.schedule.
	 * dom.shift.basicworkregister.ClassificationCode)
	 */
	@Override
	public void setClassificationCode(ClassificationCode classificationCode) {
		this.typeValue.stream().forEach(item -> {
			item.getKscmtBasicWorkClsPK().setClassifyCode(classificationCode.v());
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkSetMemento#setBasicWorkSetting(java.util.List)
	 */
	@Override
	public void setBasicWorkSetting(List<BasicWorkSetting> basicWorkSetting) {
		basicWorkSetting.stream().forEach(item -> {
			KscmtBasicWorkCls entity = new KscmtBasicWorkCls();
			entity.getKscmtBasicWorkClsPK().setWorkdayDivision(item.getWorkdayDivision().value);
			entity.setWorktypeCode(item.getWorktypeCode().v());
			entity.setWorkingCode(item.getWorkingCode().v());
			this.typeValue.add(entity);
		});
	}

}
