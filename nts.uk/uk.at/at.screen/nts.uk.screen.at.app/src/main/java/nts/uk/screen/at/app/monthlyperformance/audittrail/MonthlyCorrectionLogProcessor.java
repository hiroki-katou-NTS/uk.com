package nts.uk.screen.at.app.monthlyperformance.audittrail;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessorContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogProcessor;

@Stateless
public class MonthlyCorrectionLogProcessor extends DataCorrectionLogProcessor {

	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.MONTHLY;
	}

	@Override
	protected void buildLogContents(CorrectionLogProcessorContext context) {
		MonthlyCorrectionLogParameter parameter = context.getParameter();

		for (val target : parameter.getTargets()) {

			for (val correctedItem : target.getCorrectedItems()) {

				val correction = this.newCorrection(target.getEmployeeId(), target.getDate(), CorrectionAttr.of(correctedItem.getAttr().value),
						correctedItem.toItemInfo(), correctedItem.getItemNo());

				context.addCorrection(correction);
			}
		}
		
	}

	private MonthlyCorrection newCorrection(String employeeId, GeneralDate targetDate, CorrectionAttr correctionAttr,
			ItemInfo correctedItem, int showOrder) {

		val targetUser = this.userInfoAdaptor.findByEmployeeId(employeeId);

		return new MonthlyCorrection(targetUser, targetDate, correctionAttr, correctedItem, showOrder);
	}
}
