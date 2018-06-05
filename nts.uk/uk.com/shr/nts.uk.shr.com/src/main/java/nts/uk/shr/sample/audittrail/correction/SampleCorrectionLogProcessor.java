package nts.uk.shr.sample.audittrail.correction;

import nts.uk.shr.com.security.audittrail.correction.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionLoggingContext;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.CorrectionLogProcessor;

@Stateless
public class SampleCorrectionLogProcessor extends CorrectionLogProcessor {

	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.SAMPLE;
	}

	@Override
	protected void buildLogContents(DataCorrectionLoggingContext context) {
		
		SampleCorrectionLogParameter parameter = context.getParameter();
		
		for (val target : parameter.getTargets()) {
			
			for (val correctedItem : target.getCorrectedItems()) {
				
				val correction = this.newCorrection(
						target.getEmployeeId(),
						target.getDate(),
						CorrectionAttr.EDIT,
						correctedItem.toItemInfo(),
						correctedItem.getItemNo());
				
				context.addCorrection(correction);
			}
		}
	}

	private SampleCorrection newCorrection(
			String employeeId,
			GeneralDate targetDate,
			CorrectionAttr correctionAttr,
			ItemInfo correctedItem,
			int showOrder) {
		
		val targetUser = this.userInfoAdaptor.findByEmployeeId(employeeId);
		
		return new SampleCorrection(targetUser, targetDate, correctionAttr, correctedItem, showOrder);
	}
}
