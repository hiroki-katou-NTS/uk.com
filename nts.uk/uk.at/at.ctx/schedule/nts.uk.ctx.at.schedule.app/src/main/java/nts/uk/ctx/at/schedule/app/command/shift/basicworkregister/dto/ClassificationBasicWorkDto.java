/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationBasicWorkDto {

	/** The company id. */
	private String companyId;
	
	/** The classification code. */
	private String classificationCode;
	
	/** The basic work setting. */
	private List<BasicWorkSettingDto> basicWorkSetting;

	/**
	 * To domain.
	 *
	 * @return the classification basic work
	 */
	public ClassificationBasicWork toDomain() {
		return new ClassificationBasicWork(new GetMementoImpl(this));
	}

	/**
	 * The Class GetMementoImpl.
	 */
	private class GetMementoImpl implements ClassifiBasicWorkGetMemento {

		/** The dto. */
		private ClassificationBasicWorkDto dto;

		/**
		 * Instantiates a new gets the memento impl.
		 *
		 * @param dto the dto
		 */
		public GetMementoImpl(ClassificationBasicWorkDto dto) {
			super();
			this.dto = dto;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return dto.getCompanyId();
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkGetMemento#getClassificationCode()
		 */
		@Override
		public ClassificationCode getClassificationCode() {
			return new ClassificationCode(dto.getClassificationCode());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkGetMemento#getBasicWorkSetting()
		 */
		@Override
		public List<BasicWorkSetting> getBasicWorkSetting() {
			return dto.getBasicWorkSetting().stream().map(item -> item.toDomain())
					.collect(Collectors.toList());
		}

	}
}
