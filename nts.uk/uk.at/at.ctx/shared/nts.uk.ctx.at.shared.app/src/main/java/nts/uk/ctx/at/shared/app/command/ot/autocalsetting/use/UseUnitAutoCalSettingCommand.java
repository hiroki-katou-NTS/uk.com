/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.use;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSettingGetMemento;

/**
 * Checks if is use jobwkp set.
 *
 * @return true, if is use jobwkp set
 */
@Getter

/**
 * Sets the use jobwkp set.
 *
 * @param useJobwkpSet
 *            the new use jobwkp set
 */
@Setter
public class UseUnitAutoCalSettingCommand {

	/** The use job set. */
	// 職位の自動計算設定をする
	private boolean useJobSet;

	/** The use wkp set. */
	// 職場の自動計算設定をする
	private boolean useWkpSet;

	/** The use jobwkp set. */
	// 職場・職位の自動計算設定を行う
	private boolean useJobwkpSet;

	/**
	 * Instantiates a new use unit auto cal setting command.
	 *
	 * @param useJobSet
	 *            the use job set
	 * @param useWkpSet
	 *            the use wkp set
	 * @param useJobwkpSet
	 *            the use jobwkp set
	 */
	public UseUnitAutoCalSettingCommand(boolean useJobSet, boolean useWkpSet,
			boolean useJobwkpSet) {
		super();
		this.useJobSet = useJobSet;
		this.useWkpSet = useWkpSet;
		this.useJobwkpSet = useJobwkpSet;
	}

	/**
	 * Instantiates a new use unit auto cal setting command.
	 */
	public UseUnitAutoCalSettingCommand() {
		super();
	}

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the use unit auto cal setting
	 */
	public UseUnitAutoCalSetting toDomain(String companyId) {
		return new UseUnitAutoCalSetting(new DtoGetMemento(companyId, this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements UseUnitAutoCalSettingGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private UseUnitAutoCalSettingCommand command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param command
		 *            the command
		 */
		public DtoGetMemento(String companyId, UseUnitAutoCalSettingCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.use.
		 * UseUnitAutoCalSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * UseUnitAutoCalSettingGetMemento#getUseJobSet()
		 */
		@Override
		public ApplyAtr getUseJobSet() {
			if (this.command.isUseJobSet()) {
				return ApplyAtr.USE;
			}
			return ApplyAtr.NOT_USE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * UseUnitAutoCalSettingGetMemento#getUseWkpSet()
		 */
		@Override
		public ApplyAtr getUseWkpSet() {
			if (this.command.isUseWkpSet()) {
				return ApplyAtr.USE;
			}
			return ApplyAtr.NOT_USE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * UseUnitAutoCalSettingGetMemento#getUseJobwkpSet()
		 */
		@Override
		public ApplyAtr getUseJobwkpSet() {
			if (this.command.isUseJobwkpSet()) {
				return ApplyAtr.USE;
			}
			return ApplyAtr.NOT_USE;
		}
	}
}
