package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.WorkRecordExtraConPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.WorkRecordExtraConPubExport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErrorAlarmConditionPubExport;

@Stateless
public class WorkRecordExtraConPubImpl implements WorkRecordExtraConPub {

	@Inject
	private WorkRecordExtraConRepository repo;
	
	@Inject
    private ErrorAlarmConditionRepository errorAlarmConditionRepository;
	
	@Override
	public List<WorkRecordExtraConPubExport> getAllWorkRecordExtraConByListID(List<String> listErrorAlarmID) {
		List<WorkRecordExtraConPubExport> data = repo.getAllWorkRecordExtraConByListID(listErrorAlarmID)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		// get List ErrorAlarmCondition
		List<ErrorAlarmCondition> listErrorAlarmCondition = errorAlarmConditionRepository.findConditionByListErrorAlamCheckId(listErrorAlarmID);
		data = data.stream().map(item -> setErrorAlarmConditionPubExport(item, listErrorAlarmCondition)).collect(Collectors.toList());
		return data;
	}

	@Override
	public WorkRecordExtraConPubExport getWorkRecordExtraConById(String errorAlarmCheckID) {
		Optional<WorkRecordExtraConPubExport> data = repo.getWorkRecordExtraConById(errorAlarmCheckID).map(c->convertToExport(c));
		if(data.isPresent())
			return data.get();
		return null;
	}
	
	public WorkRecordExtraConPubExport convertToExport(WorkRecordExtractingCondition domain) {
		return new WorkRecordExtraConPubExport(
					domain.getErrorAlarmCheckID(),
					domain.getCheckItem().value,
					domain.getDisplayMessages().isMessageBold(),
					domain.getDisplayMessages().getMessageColor().v(),
					domain.getSortOrderBy(),
					domain.isUseAtr(),
					domain.getNameWKRecord().v()
				);
	}

	private WorkRecordExtraConPubExport setErrorAlarmConditionPubExport(WorkRecordExtraConPubExport workRecordExtraConPubExport,
			List<ErrorAlarmCondition> listErrorAlarmCondition) {
		workRecordExtraConPubExport.setErrorAlarmCondition(getErrorAlarmCondition(
				workRecordExtraConPubExport.getCheckItem(), listErrorAlarmCondition));
		return workRecordExtraConPubExport;
	}
	private ErrorAlarmConditionPubExport getErrorAlarmCondition(int eralCheckId, List<ErrorAlarmCondition> listErrorAlarmCondition) {
		Optional<ErrorAlarmCondition> optErAlCondition = 
				listErrorAlarmCondition.stream().filter(item -> item.getErrorAlarmCheckID().equals(eralCheckId)).findFirst();
        if (!optErAlCondition.isPresent()) {
        	return null;
        }
      //ドメインモデル「勤怠項目に対する条件」を取得する - Acquire domain model "Condition for attendance item"
        ErrorAlarmCondition errorAlarmCondition = optErAlCondition.get();
       
    	ErrorAlarmConditionPubExport errorAlarmConditionPubExport = new ErrorAlarmConditionPubExport();
    	errorAlarmConditionPubExport.convertFromDomain(errorAlarmCondition);
        return errorAlarmConditionPubExport;
	}

	@Override
	public void addWorkRecordExtraConPub(WorkRecordExtraConPubExport workRecordExtraConPubExport) {
		String errorAlarmCheckId = IdentifierUtil.randomUniqueId();
		
		ErrorAlarmConditionPubExport errorAlarmConditionPubExport = workRecordExtraConPubExport.getErrorAlarmCondition();
		
		// for ErrorAlarmCondition
		if (workRecordExtraConPubExport.getCheckItem() >= TypeCheckWorkRecord.CONTINUOUS_TIME.value) {
			validRangeOfErAlCondition(errorAlarmConditionPubExport);
		}
		ErrorAlarmCondition errorAlarmCondition = errorAlarmConditionPubExport.toConditionDomain();
		errorAlarmCondition.setCheckId(errorAlarmCheckId);
		errorAlarmCondition.validate();
		workRecordExtraConPubExport.setErrorAlarmCheckID(errorAlarmCheckId);
		this.errorAlarmConditionRepository.addErrorAlarmCondition(errorAlarmCondition);
	}

	@Override
	public void updateWorkRecordExtraConPub(WorkRecordExtraConPubExport workRecordExtraConPubExport) {
		ErrorAlarmConditionPubExport errorAlarmConditionPubExport = workRecordExtraConPubExport.getErrorAlarmCondition();
		
		// for ErrorAlarmCondition
		validRangeOfErAlCondition(errorAlarmConditionPubExport);
		ErrorAlarmCondition errorAlarmCondition = errorAlarmConditionPubExport.toConditionDomain();
		errorAlarmCondition.validate();
		this.errorAlarmConditionRepository.updateErrorAlarmCondition(errorAlarmCondition);
	}

	@Override
	public void deleteWorkRecordExtraConPub(List<String> lstErrorAlarmCheckID) {
		// for ErrorAlarmCondition
		if (lstErrorAlarmCheckID != null && !lstErrorAlarmCheckID.isEmpty()) {
			this.errorAlarmConditionRepository.removeErrorAlarmCondition(lstErrorAlarmCheckID);
		}
	}
	
	private void validRangeOfErAlCondition(ErrorAlarmConditionPubExport errorAlarmConditionPubExport) {
		
	}

}
