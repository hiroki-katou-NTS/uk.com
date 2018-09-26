package nts.uk.screen.at.app.monthlyperformance.audittrail;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.BusinessTypeSortedMonRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.OrderReferWorkType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessorContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogProcessor;

@Stateless
public class MonthlyCorrectionLogProcessor extends DataCorrectionLogProcessor {

	@Inject
	private BusinessTypeSortedMonRepository busMonSortedItemRepo;
	
	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.MONTHLY;
	}

	@Override
	protected void buildLogContents(CorrectionLogProcessorContext context) {
		MonthlyCorrectionLogParameter parameter = context.getParameter();

		val itemOders = busMonSortedItemRepo.getOrderReferWorkType(AppContexts.user().companyId());
		
		for (val target : parameter.getTargets()) {

			for (val correctedItem : target.getCorrectedItems()) {
				
				int showOrder = 0;
				if (itemOders.isPresent()) {
					Optional<OrderReferWorkType> order = itemOders.get().getListOrderReferWorkType().stream()
							.filter(o -> o.getAttendanceItemID() == correctedItem.getItemNo()).findFirst();
					if (order.isPresent())
						showOrder = order.get().getOrder();
				}

				val correction = this.newCorrection(target.getEmployeeId(), target.getDate(), CorrectionAttr.of(correctedItem.getAttr().value),
						correctedItem.toItemInfo(), showOrder);

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
