package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessorContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogProcessor;

@Stateless
public class BasicScheduleCorrectionProcessor extends DataCorrectionLogProcessor {

	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.SCHEDULE;
	}

	@Override
	protected void buildLogContents(CorrectionLogProcessorContext context) {
		BasicScheduleCorrectionParameter parameter = context.getParameter();
		
		for (val target : parameter.getTargets()) {
			
			for (val correctedItem : target.getCorrectedItems()) {
				
				val correction = this.newCorrection(
						target.getEmployeeId(),
						correctedItem.getAttr(),
						correctedItem.toItemInfo(),
						correctedItem.getRemark(),
						target.getDate(),
						correctedItem.getItemNo());
				
				context.addCorrection(correction);
			}
		}
		
	}
	
	private BasicScheduleCorrection newCorrection(
			String employeeId,
			CorrectionAttr correctionAttr,
			ItemInfo correctedItem,
			String remark,
			GeneralDate date,
			int showOrder) {
		
		val targetUser = this.userInfoAdaptor.findByEmployeeId(employeeId);
		
		return new BasicScheduleCorrection(correctionAttr,correctedItem,remark,date,showOrder,targetUser);
	}

}
