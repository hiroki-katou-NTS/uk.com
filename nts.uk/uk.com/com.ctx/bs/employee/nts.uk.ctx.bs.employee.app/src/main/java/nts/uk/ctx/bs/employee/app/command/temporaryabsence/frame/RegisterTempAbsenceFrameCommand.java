/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.temporaryabsence.frame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.NotUseAtr;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrame;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrameGetMemento;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrameName;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrameNo;

/**
 * The Class RegisterTempAbsenceFrameCommand.
 */

@Getter
@Setter
public class RegisterTempAbsenceFrameCommand {
	
	/** The dto. */
	private List<Dto> dto;
	
	/**
	 * To domain.
	 *
	 * @return the list
	 */
	public List<TempAbsenceFrame> toDomain() {
		List<TempAbsenceFrame> lstFrame = new ArrayList<>();
		for (Dto dto: dto) {
			lstFrame.add(new TempAbsenceFrame(new TempAbsenceFrameGetMementoImpl(dto)));
		}
		if (!lstFrame.isEmpty() && lstFrame != null) {
			return lstFrame;
		}
		return null;
	}

	/**
	 * The Class TempAbsenceFrameGetMementoImpl.
	 */
	public class TempAbsenceFrameGetMementoImpl implements TempAbsenceFrameGetMemento {

		/** The temp absence frame command. */
		private RegisterTempAbsenceFrameCommand.Dto tempAbsenceFrameCommand;
		
		/**
		 * Instantiates a new temp absence frame get memento impl.
		 *
		 * @param tempAbsenceFrameCommand the temp absence frame command
		 */
		public TempAbsenceFrameGetMementoImpl(RegisterTempAbsenceFrameCommand.Dto tempAbsenceFrameCommand) {
			super();
			this.tempAbsenceFrameCommand = tempAbsenceFrameCommand;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrameGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.tempAbsenceFrameCommand.getCompanyId();
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrameGetMemento#getTempAbsenceFrameNo()
		 */
		@Override
		public TempAbsenceFrameNo getTempAbsenceFrameNo() {
			 return new TempAbsenceFrameNo(BigDecimal.valueOf(this.tempAbsenceFrameCommand.getTempAbsenceFrNo()));
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrameGetMemento#getUseClassification()
		 */
		@Override
		public NotUseAtr getUseClassification() {
			return NotUseAtr.valueOf((int) this.tempAbsenceFrameCommand.getUseClassification()); 
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.bs.employee.dom.temporaryabsence.frame.TempAbsenceFrameGetMemento#getTempAbsenceFrameName()
		 */
		@Override
		public TempAbsenceFrameName getTempAbsenceFrameName() {
			return new TempAbsenceFrameName(this.tempAbsenceFrameCommand.getTempAbsenceFrName());
		}
		
	}
	
	@Getter
	@Setter
	public static class Dto {
		
		/** The company id. */
		// 会社ID
		private String companyId;
		
		/** The temp absence fr no. */
		//休職休業枠NO
		private short tempAbsenceFrNo;
		
		/** The use classification. */
		//使用区分
		private short useClassification;
		
		/** The temp absence fr name. */
		//休職休業枠名称
		private String tempAbsenceFrName;

	}
}
