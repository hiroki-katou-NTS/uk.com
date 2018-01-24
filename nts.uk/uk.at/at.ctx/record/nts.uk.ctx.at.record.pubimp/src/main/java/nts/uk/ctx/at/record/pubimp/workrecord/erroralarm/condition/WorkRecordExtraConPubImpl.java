package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.DisplayMessages;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.NameWKRecord;
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
					domain.getNameWKRecord().v(),
					null
				);
	}
	
	private WorkRecordExtractingCondition convertToDomainWR(WorkRecordExtraConPubExport dto) {
		return new  WorkRecordExtractingCondition(
				dto.getErrorAlarmCheckID(),
				EnumAdaptor.valueOf(dto.getCheckItem(), TypeCheckWorkRecord.class),
				new DisplayMessages(
						dto.isMessageBold(),
						new ColorCode(dto.getMessageColor())),
				dto.getSortOrderBy(),
				dto.isUseAtr(),
				new NameWKRecord(dto.getNameWKRecord())
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
		this.repo.addWorkRecordExtraCon(
				convertToDomainWR(workRecordExtraConPubExport)
				);
	}

	@Override
	public void updateWorkRecordExtraConPub(WorkRecordExtraConPubExport workRecordExtraConPubExport) {
		ErrorAlarmConditionPubExport errorAlarmConditionPubExport = workRecordExtraConPubExport.getErrorAlarmCondition();
		
		// for ErrorAlarmCondition
		validRangeOfErAlCondition(errorAlarmConditionPubExport);
		ErrorAlarmCondition errorAlarmCondition = errorAlarmConditionPubExport.toConditionDomain();
		errorAlarmCondition.validate();
		this.errorAlarmConditionRepository.updateErrorAlarmCondition(errorAlarmCondition);
		this.repo.updateWorkRecordExtraCon(convertToDomainWR(workRecordExtraConPubExport));
	}

	@Override
	public void deleteWorkRecordExtraConPub(List<String> lstErrorAlarmCheckID) {
		// for ErrorAlarmCondition
		if (lstErrorAlarmCheckID != null && !lstErrorAlarmCheckID.isEmpty()) {
			this.errorAlarmConditionRepository.removeErrorAlarmCondition(lstErrorAlarmCheckID);
			for(String eralId : lstErrorAlarmCheckID) {
				this.repo.deleteWorkRecordExtraCon(eralId);
			}
		}
	}
	
	private void validRangeOfErAlCondition(ErrorAlarmConditionPubExport errorAlarmConditionPubExport) {
		
	}

	@Override
	public void checkUpdateListErAl(List<String> listErrorAlarmCheckID,List<WorkRecordExtraConPubExport> listErroAlarm) {
		//get list gốc theo list Error AlarmCheckID
		List<WorkRecordExtraConPubExport> data = new ArrayList<>();
		for(String erroAlarmCheckID : listErrorAlarmCheckID) {
			WorkRecordExtraConPubExport workRecordExtraConPubExport = getWorkRecordExtraConById(erroAlarmCheckID);
			if(workRecordExtraConPubExport != null) {
				data.add(workRecordExtraConPubExport);
			}
		}
		List<String> listErrorAlarmID = new ArrayList<>();
		//ktra xem có xóa phần tử nào k?
		//lặp list gốc
		for(WorkRecordExtraConPubExport oldWorkRecordExtraCon :data) {
			boolean checkExist = false;
			//lặp list truyền vào
			for(WorkRecordExtraConPubExport newWorkRecordExtraCon  : listErroAlarm ) {
				//nếu tồn tại thì gán checkExist
				if(oldWorkRecordExtraCon.getErrorAlarmCheckID().equals(newWorkRecordExtraCon.getErrorAlarmCheckID())) {
					checkExist = true;
					break;
				}
			}//end lặp list truyền vào 
			//nếu checkExist bằng false tức là đã bị xóa, gọi hàm xóa
			if(!checkExist) {
				listErrorAlarmID.add(oldWorkRecordExtraCon.getErrorAlarmCheckID());
			}
		}//end lặp list gốc
		//nếu list id có dữ liệu, tức có phần tử bị xóa đy, gọi hàm xóa
		if(!listErrorAlarmID.isEmpty()) {
			deleteWorkRecordExtraConPub(listErrorAlarmID);
		}
		
		
		//ktra xem có add và update phần tử nào k?
		//lặp list truyền vào
		for(WorkRecordExtraConPubExport newWorkRecordExtraCon :listErroAlarm) {
			//check tồn tại
			boolean checkExist1 = false;
			//lặp list gốc
			for(WorkRecordExtraConPubExport oldWorkRecordExtraCon : data) {
				if(newWorkRecordExtraCon.getErrorAlarmCheckID().equals(oldWorkRecordExtraCon.getErrorAlarmCheckID())) {
					if(!newWorkRecordExtraCon.equals(oldWorkRecordExtraCon)) {
						updateWorkRecordExtraConPub(newWorkRecordExtraCon);
						checkExist1 = true;
						break;
					}
				}
			}//end lặp list gốc
			//nếu checkExist1 = false, tức là phần tử thêm mới, gọi hàm thêm mới
			if(!checkExist1) {
				addWorkRecordExtraConPub(newWorkRecordExtraCon);
			}
		}//end lặp list truyền vào
		
	}

	@Override
	public List<String> addNewListErAl(List<WorkRecordExtraConPubExport> listErroAlarm) {
		List<String> listErrorAlarmCheckID = new ArrayList<>();
		for(WorkRecordExtraConPubExport workRecordExtraCon :listErroAlarm) {
			addWorkRecordExtraConPub(workRecordExtraCon);
			listErrorAlarmCheckID.add(workRecordExtraCon.getErrorAlarmCheckID());
		}
		return listErrorAlarmCheckID;
	}

}
