package nts.uk.ctx.at.function.app.command.statement;

import lombok.Data;
import nts.uk.ctx.at.function.dom.statement.StampOutputSettingCode;
import nts.uk.ctx.at.function.dom.statement.StampOutputSettingName;
import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * Instantiates a new adds the stamping output item set command.
 */
@Data
public class AddStampingOutputItemSetCommand implements StampingOutputItemSetGetMemento{
	    
    	/** The stamp output set code. */
		private String stampOutputSetCode;
		
		/** The stamp output set name. */
		private String stampOutputSetName;
		
		/** The output emboss method. */
		private boolean outputEmbossMethod;
		
		/** The output work hours. */
		private boolean outputWorkHours; 
		
		/** The output set location. */
		private boolean outputSetLocation;
		
		/** The output pos infor. */
		private boolean outputPosInfor;
		
		/** The output OT. */
		private boolean outputOT;
		
		/** The output night time. */
		private boolean outputNightTime;
		
		/** The output support card. */
		private boolean outputSupportCard;

		
		@Override
		public CompanyId getCompanyID() {
			return new CompanyId(AppContexts.user().companyId());
		}

		
		@Override
		public StampOutputSettingCode getStampOutputSetCode() {
			return new StampOutputSettingCode(this.stampOutputSetCode);
		}

		
		@Override
		public StampOutputSettingName getStampOutputSetName() {
			return new StampOutputSettingName(this.stampOutputSetName);
		}

		
		@Override
		public boolean getOutputEmbossMethod() {
			return this.outputEmbossMethod;
		}

		
		@Override
		public boolean getOutputWorkHours() {
			return this.outputWorkHours;
		}

		
		@Override
		public boolean getOutputSetLocation() {
			return this.outputSetLocation;
		}

		
		@Override
		public boolean getOutputPosInfor() {
			return this.outputPosInfor;
		}

		
		@Override
		public boolean getOutputOT() {
			return this.outputOT;
		}

		
		@Override
		public boolean getOutputNightTime() {
			return this.outputNightTime;
		}

		
		@Override
		public boolean getOutputSupportCard() {
			return this.outputSupportCard;
		}
	
}
