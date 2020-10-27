/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtBasicWorkCls;

/**
 * The Class JpaClassifiBasicWorkGetMemento.
 */
public class JpaClassifiBasicWorkGetMemento implements ClassifiBasicWorkGetMemento {

	/** The type value. */
	private List<KscmtBasicWorkCls> typeValue;

	/**
	 * Instantiates a new jpa classifi basic work get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaClassifiBasicWorkGetMemento(List<KscmtBasicWorkCls> typeValue) {
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
	 * ClassifiBasicWorkGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return this.typeValue.get(0).getKscmtBasicWorkClsPK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkGetMemento#getClassificationCode()
	 */
	@Override
	public ClassificationCode getClassificationCode() {
		return new ClassificationCode(this.typeValue.get(0).getKscmtBasicWorkClsPK().getClassifyCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * ClassifiBasicWorkGetMemento#getBasicWorkSetting()
	 */
	@Override
	public List<BasicWorkSetting> getBasicWorkSetting() {
		return this.typeValue.stream().map(entity -> {
			return new BasicWorkSetting(new JpaBWSettingClassifyGetMemento(entity));
		}).collect(Collectors.toList());
	}

}
