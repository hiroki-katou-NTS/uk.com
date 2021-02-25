/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnitGetMemento;

/**
 * The Class CompensatoryDigestiveTimeUnitDto.
 */
@Getter
@Setter
@AllArgsConstructor
public class CompensatoryDigestiveTimeUnitDto {

	/** The is manage by time. */
	private Integer isManageByTime;

	/** The digestive unit. */
	private Integer digestiveUnit;

	public CompensatoryDigestiveTimeUnit toDomainNew() {
		return new CompensatoryDigestiveTimeUnit(
				ManageDistinct.valueOf(this.isManageByTime),
				TimeDigestiveUnit.valueOf(this.digestiveUnit));
	}
	
	public static CompensatoryDigestiveTimeUnitDto toDto(CompensatoryDigestiveTimeUnit domain) {
		return new CompensatoryDigestiveTimeUnitDto(domain.getIsManageByTime().value, domain.getDigestiveUnit().value);
	}
	/**
	 * To domain.
	 *
	 * @return the compensatory digestive time unit
	 */
	public CompensatoryDigestiveTimeUnit toDomain() {
		return new CompensatoryDigestiveTimeUnit(new CompensatoryDigestiveTimeUnitGetMementoImpl(this));
	}

	/**
	 * The Class CompensatoryDigestiveTimeUnitGetMementoImpl.
	 */
	public class CompensatoryDigestiveTimeUnitGetMementoImpl implements CompensatoryDigestiveTimeUnitGetMemento {

		/** The compensatory digestive time unit dto. */
		private CompensatoryDigestiveTimeUnitDto compensatoryDigestiveTimeUnitDto;

		/**
		 * Instantiates a new compensatory digestive time unit get memento impl.
		 *
		 * @param compensatoryDigestiveTimeUnitDto the compensatory digestive time unit dto
		 */
		public CompensatoryDigestiveTimeUnitGetMementoImpl(CompensatoryDigestiveTimeUnitDto compensatoryDigestiveTimeUnitDto) {
			this.compensatoryDigestiveTimeUnitDto = compensatoryDigestiveTimeUnitDto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryDigestiveTimeUnitGetMemento#getIsManageByTime()
		 */
		@Override
		public ManageDistinct getIsManageByTime() {
			return ManageDistinct.valueOf(this.compensatoryDigestiveTimeUnitDto.isManageByTime);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave2.CompensatoryDigestiveTimeUnitGetMemento#getDigestiveUnit()
		 */
		@Override
		public TimeDigestiveUnit getDigestiveUnit() {
			return TimeDigestiveUnit.valueOf(this.compensatoryDigestiveTimeUnitDto.digestiveUnit);
		}

	}

}
