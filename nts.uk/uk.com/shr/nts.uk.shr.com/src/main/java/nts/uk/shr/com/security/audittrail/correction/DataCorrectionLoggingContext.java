package nts.uk.shr.com.security.audittrail.correction;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrection;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;

public class DataCorrectionLoggingContext {

	@Getter
	private final String operationId;
	
	private final Object parameter;
	
	@Getter
	private final List<DataCorrectionLog> corrections;

	private DataCorrectionLoggingContext(String operationId, Object parameter) {
		this.operationId = operationId;
		this.parameter = parameter;
		this.corrections = new ArrayList<>();
	}
	
	public static DataCorrectionLoggingContext newContext(String operationId, Object parameter) {
		return new DataCorrectionLoggingContext(operationId, parameter);
	}
	
	@SuppressWarnings("unchecked")
	public <P> P getParameter() {
		return (P) this.parameter;
	}
	
	public void addCorrection(DataCorrection correction) {
		this.addCorrection(DataCorrectionLog.of(this.operationId, correction));
	}
	
	public void addCorrection(
			UserInfo targetUser,
			TargetDataType targetDataType,
			TargetDataKey targetDataKey,
			CorrectionAttr correctionAttr,
			ItemInfo correctedItem,
			int showOrder) {
		
		this.addCorrection(new DataCorrectionLog(
				this.operationId, targetUser, targetDataType, targetDataKey, correctionAttr, correctedItem, showOrder));
	}
	
	public void addCorrection(
			UserInfo targetUser,
			TargetDataType targetDataType,
			TargetDataKey targetDataKey,
			CorrectionAttr correctionAttr,
			ItemInfo correctedItem,
			int showOrder,
			String remark) {
		
		this.addCorrection(new DataCorrectionLog(
				this.operationId, targetUser, targetDataType, targetDataKey, correctionAttr, correctedItem, showOrder, remark));
	}
	
	private void addCorrection(DataCorrectionLog correction) {
		this.corrections.add(correction);
	}
}
