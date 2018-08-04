package nts.uk.ctx.sys.log.app.command.pereg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.CategoryCorrectionTarget;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCategoryCorrectionLogParameter.PersonCorrectionItemInfo;
import nts.uk.ctx.sys.log.app.command.pereg.PersonCorrectionLogParameter.PersonCorrectionTarget;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessor;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.LogBasicInformationWriter;
import nts.uk.shr.com.security.audittrail.correction.processor.pereg.PeregCorrectionLogProcessorContext;
import nts.uk.shr.com.security.audittrail.correction.processor.pereg.PeregCorrectionLogWriter;

@Stateless
public class PeregCorrectionLogProcessor extends CorrectionLogProcessor<PeregCorrectionLogProcessorContext> {

	@Inject
	protected LogBasicInformationWriter basicInfoRepository;
	
	@Inject
	protected PeregCorrectionLogWriter correctionLogRepository;
	
	
	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.PEREG_REGISTER;
	}

	@Override
	protected void buildLogContents(PeregCorrectionLogProcessorContext context) {
		// xử lý PeregCorrectionLogParameter để chuyển thành domain
		// PersonInfoCorrectionLog ở đây
		
		 PersonCorrectionLogParameter personLog = context.getParameter(String.valueOf(KeySetCorrectionLog.PERSON_CORRECTION_LOG.value));
		 PersonCategoryCorrectionLogParameter categoryLog = context.getParameter(String.valueOf(KeySetCorrectionLog.CATEGORY_CORRECTION_LOG.value));
		 
		 context.addCorrection(toDomain(context, personLog, categoryLog));
		 
	}
	
	private PersonInfoCorrectionLog toDomain (PeregCorrectionLogProcessorContext context, PersonCorrectionLogParameter personLog, 
			PersonCategoryCorrectionLogParameter categoryLog) {
		
		List<CategoryCorrectionLog> ctgLog = new ArrayList<>();
		
		for(CategoryCorrectionTarget category : categoryLog.getTargets()) {
			
			List<ItemInfo> lstItemInfo = new ArrayList<>();
			
			for(PersonCorrectionItemInfo itemInfo : category.getItemInfos()) {
				lstItemInfo.add(ItemInfo.create(
						itemInfo.getItemId(), 
						itemInfo.getItemName(),
						DataValueAttribute.of(itemInfo.getValueType()),
						itemInfo.getValueBefore(),
						itemInfo.getValueAfter()));}
			
			ctgLog.add(new CategoryCorrectionLog(
					category.getCategoryName(), 
					category.getInfoOperateAttr(),
					category.getTargetKey(),
					lstItemInfo,
					category.getReviseInfo()));
		}
		
		PersonCorrectionTarget target = personLog.getTargets().get(0);
		PersonInfoCorrectionLog domain = target.toPersonInfoCorrection(context.getOperationId(), target.remark , ctgLog);
				
		return domain;
	}

	@Override
	public void processLoggingForBus(LogBasicInformation basicInfo, Object parameter) {
		
		@SuppressWarnings("unchecked")
		HashMap<String, Serializable> parameters = (HashMap<String, Serializable>) parameter;
		
		val context = PeregCorrectionLogProcessorContext.newContext(basicInfo.getOperationId(), parameters);
		this.buildLogContents(context);

		this.basicInfoRepository.save(basicInfo);
		this.correctionLogRepository.save(context.getCorrections());
	}
}
