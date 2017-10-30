/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.workfixed;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.ConfirmClsStatus;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento;

/**
 * The Class WorkFixedSaveCommand.
 */
@Getter
@Setter
public class WorkFixedSaveCommand {

	/** The closure id. */
	private Integer closureId;
	
	/** The wkp id. */
	private String wkpId;
	
	/** The confirm cls status. */
	private Integer confirmClsStatus;
	
	/** The process date. */
	private Integer processDate;
    
	/**
	 * To domain.
	 *
	 * @param personId the person id
	 * @param fixedDate the fixed date
	 * @return the work fixed
	 */
	public WorkFixed toDomain(String personId, GeneralDate fixedDate) {
        return new WorkFixed(new WorkFixedGetMementoImpl(personId, fixedDate, this));
    }
	
	/**
	 * The Class WorkFixedGetMementoImpl.
	 */
	public class WorkFixedGetMementoImpl implements WorkFixedGetMemento {
        
		/** The person id. */
        private String personId;
        
        /** The fixed date. */
        private GeneralDate fixedDate;

        /** The closure id. */
    	private Integer closureId;
    	
    	/** The wkp id. */
    	private String wkpId;
    	
    	/** The confirm cls status. */
    	private Integer confirmClsStatus;
    	
    	/** The process date. */
    	private Integer processDate;
		
    	/**
	     * Instantiates a new work fixed get memento impl.
	     *
	     * @param personId the person id
	     * @param fixedDate the fixed date
	     * @param saveCommand the save command
	     */
	    public WorkFixedGetMementoImpl(String personId, GeneralDate fixedDate, WorkFixedSaveCommand saveCommand) {
            this.personId = personId;
            this.fixedDate = fixedDate;
            this.closureId = saveCommand.closureId;
            this.wkpId = saveCommand.wkpId;
            this.confirmClsStatus = saveCommand.confirmClsStatus;
            this.processDate = saveCommand.processDate;
        }

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getClosureId()
		 */
		@Override
		public Integer getClosureId() {
			return this.closureId;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getConfirmPId()
		 */
		@Override
		public String getConfirmPId() {
			return this.personId;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getWorkPlaceId()
		 */
		@Override
		public String getWorkPlaceId() {
			return this.wkpId;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getConfirmClsStatus()
		 */
		@Override
		public ConfirmClsStatus getConfirmClsStatus() {
			return ConfirmClsStatus.valueOf(confirmClsStatus);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getFixedDate()
		 */
		@Override
		public GeneralDate getFixedDate() {
			return this.fixedDate;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedGetMemento#getProcessDate()
		 */
		@Override
		public Integer getProcessDate() {
			return this.processDate;
		}
	}
}
