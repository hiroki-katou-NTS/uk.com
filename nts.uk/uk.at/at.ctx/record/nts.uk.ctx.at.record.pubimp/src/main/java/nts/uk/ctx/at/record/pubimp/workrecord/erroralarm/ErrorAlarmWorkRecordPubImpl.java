package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErrorAlarmWorkRecordPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.ErrorAlarmWorkRecordPubExport;
import nts.uk.shr.com.i18n.TextResource;
@Stateless
public class ErrorAlarmWorkRecordPubImpl implements ErrorAlarmWorkRecordPub {
	
	@Inject
	private ErrorAlarmWorkRecordRepository repo;
	
	@Inject
	private WorkRecordExtraConRepository workRecordExtraConRepo;

	@Inject 
	private ErrorAlarmConditionRepository errorAlarmConditionRepo;
	
	@Override
	public ErrorAlarmWorkRecordPubExport findByErrorAlamCheckId(String eralCheckId) {
		Optional<ErrorAlarmWorkRecordPubExport> data = repo.findByErrorAlamCheckId(eralCheckId).map(c->convertToExport(c));
		if(data.isPresent())
			return data.get();
		return null;
	}

	@Override
	public List<ErrorAlarmWorkRecordPubExport> findByListErrorAlamCheckId(List<String> listEralCheckId) {
		List<ErrorAlarmWorkRecordPubExport> data = repo.findByListErrorAlamCheckId(listEralCheckId)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	private ErrorAlarmWorkRecordPubExport convertToExport(ErrorAlarmWorkRecord domain) {
		return new ErrorAlarmWorkRecordPubExport(
				domain.getCompanyId(),
				domain.getCode().v(),
				domain.getName().v(),
				domain.getFixedAtr()?1:0,
				domain.getUseAtr()?1:0,
				domain.getTypeAtr().value,
				domain.getMessage().getBoldAtr()?1:0,
				domain.getMessage().getMessageColor().v(),
				domain.getCancelableAtr()?1:0,
				domain.getErrorDisplayItem()==null ? 0 : domain.getErrorDisplayItem(),
				domain.getCancelRoleId(),
				domain.getErrorAlarmCheckID(),
				null
				);
	}
	
//	private ErrorAlarmWorkRecord convertToDomain(ErrorAlarmWorkRecordPubExport dto) {
//		return new ErrorAlarmWorkRecord(
//				dto.getCompanyId(),
//				new ErrorAlarmWorkRecordCode(dto.getCode()),
//				new ErrorAlarmWorkRecordName(dto.getName()),
//				dto.getFixedAtr()==1?true:false,
//				dto.getUseAtr()==1?true:false,
//				EnumAdaptor.valueOf(dto.getTypeAtr(),ErrorAlarmClassification.class),
//				new ErrorAlarmMessage(
//						dto.getBoldAtr()==1?true:false,
//						new ColorCode(dto.getMessageColor())
//						),
//				dto.getCancelableAtr()==1?true:false,
//				BigDecimal.valueOf(dto.getErrorDisplayItem()).movePointLeft(2),
//				dto.getCancelRoleId(),
//				null,
//				dto.getErrorAlarmCheckID()
//				);
//	}

	@Override
	public void addErrorAlarmWorkRecordPub(ErrorAlarmWorkRecordPubExport errorAlarmWorkRecordPubExport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateErrorAlarmWorkRecordPub(ErrorAlarmWorkRecordPubExport errorAlarmWorkRecordPubExport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteErrorAlarmWorkRecordPub(List<String> listEralCheckId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ErrorAlarmWorkRecordPubExport> getAllErrorAlarmWorkRecord(String companyID) {
		List<ErrorAlarmWorkRecordPubExport> data = repo.getListErrorAlarmWorkRecord(companyID)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		List<String> listErrorAlarmID = data.stream().map(c -> c.getErrorAlarmCheckID()).collect(Collectors.toList());
		List<ErrorAlarmCondition> listErrorAlarmCon =  this.errorAlarmConditionRepo.findMessageConByListErAlCheckId(listErrorAlarmID);
		
		for(ErrorAlarmWorkRecordPubExport errorAlarmWorkRecordPubExport :data) {
			boolean exist = false;
			for(ErrorAlarmCondition errorAlarmCondition:listErrorAlarmCon) {
				if(errorAlarmWorkRecordPubExport.getErrorAlarmCheckID().equals(errorAlarmCondition.getErrorAlarmCheckID())) {
					errorAlarmWorkRecordPubExport.setDisplayMessage(errorAlarmCondition.getDisplayMessage().v());
					exist = true;
					break;
				}
			}
			if (!exist) {
				errorAlarmWorkRecordPubExport.setName(TextResource.localize("KAL008_1"));
			}
			
		}
		return data;
	}

	@Override
	public List<ErrorAlarmWorkRecordPubExport> getListErAlByListCode(String companyId, List<String> listCode) {
		List<ErrorAlarmWorkRecordPubExport> data =  this.repo.getListErAlByListCode(companyId, listCode)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}

}
