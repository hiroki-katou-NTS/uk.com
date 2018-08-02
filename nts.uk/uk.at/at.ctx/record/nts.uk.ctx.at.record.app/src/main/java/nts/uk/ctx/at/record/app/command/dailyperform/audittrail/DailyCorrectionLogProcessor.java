package nts.uk.ctx.at.record.app.command.dailyperform.audittrail;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessorContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogProcessor;

@Stateless
public class DailyCorrectionLogProcessor extends DataCorrectionLogProcessor {

	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.DAILY;
	}


	protected void buildLogContents(CorrectionLogProcessorContext context) {
		DailyCorrectionLogParameter parameter = context.getParameter();

		for (val target : parameter.getTargets()) {

			for (val correctedItem : target.getCorrectedItems()) {

				val correction = this.newCorrection(target.getEmployeeId(), target.getDate(), CorrectionAttr.EDIT,
						correctedItem.toItemInfo(), correctedItem.getItemNo());

				context.addCorrection(correction);
			}
		}
		
	}

	private DailyCorrection newCorrection(String employeeId, GeneralDate targetDate, CorrectionAttr correctionAttr,
			ItemInfo correctedItem, int showOrder) {

		val targetUser = this.userInfoAdaptor.findByEmployeeId(employeeId);

		return new DailyCorrection(targetUser, targetDate, correctionAttr, correctedItem, showOrder);
	}

	@Override
	protected void buildLogContents(Object context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processLoggingForBus(LogBasicInformation basicInfo, Object parameter) {
		// TODO Auto-generated method stub
		
	}

}
