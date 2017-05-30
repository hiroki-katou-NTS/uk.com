/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.NormalVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.OccurrenceVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command.dto.NormalVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command.dto.OccurrenceVacationSettingDto;

@Getter
@Setter
public class SaveCompensatoryLeaveCommand {

	/** The company id. */
	private String companyId;

	/** The is managed. */
	private ManageDistinct isManaged;

	/** The normal vacation setting. */
	private NormalVacationSettingDto normalVacationSetting;

	/** The occurrence vacation setting. */
	private OccurrenceVacationSettingDto occurrenceVacationSetting;

	public CompensatoryLeaveComSetting toDomain(String companyId) {
		return new CompensatoryLeaveComSetting(new CompensatoryLeaveComGetMementoImpl(companyId, this));
	}

	public class CompensatoryLeaveComGetMementoImpl implements CompensatoryLeaveComGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private SaveCompensatoryLeaveCommand command;

		/**
		 * @param companyId
		 * @param command
		 */
		public CompensatoryLeaveComGetMementoImpl(String companyId, SaveCompensatoryLeaveCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		@Override
		public ManageDistinct getIsManaged() {
			return ManageDistinct.valueOf(this.command.isManaged.value);
		}

		@Override
		public NormalVacationSetting getNormalVacationSetting() {
			return this.command.normalVacationSetting.toDomain();
		}

		@Override
		public OccurrenceVacationSetting getOccurrenceVacationSetting() {
			return this.command.occurrenceVacationSetting.toDomain();
		}
	}
}
