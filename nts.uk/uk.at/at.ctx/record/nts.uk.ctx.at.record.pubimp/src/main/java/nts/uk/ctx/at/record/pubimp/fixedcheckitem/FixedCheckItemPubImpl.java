package nts.uk.ctx.at.record.pubimp.fixedcheckitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkadminunverified.CheckAdminUnverifiedService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkingData.CheckingDataService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.CheckPrincipalUnconfirmService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktimenotregister.WorkTimeNotRegisterService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktypenotregister.WorkTypeNotRegisterService;
import nts.uk.ctx.at.record.pub.fixedcheckitem.FixedCheckItemPub;
import nts.uk.ctx.at.record.pub.fixedcheckitem.ValueExtractAlarmWRPubExport;

@Stateless
public class FixedCheckItemPubImpl implements FixedCheckItemPub {

	@Inject
	private WorkTypeNotRegisterService workTypeNotRegisterService;
	
	@Inject
	private WorkTimeNotRegisterService workTimeNotRegisterService;
	
	@Inject
	private CheckPrincipalUnconfirmService checkPrincipalUnconfirmService;
	
	@Inject
	private CheckAdminUnverifiedService checkAdminUnverifiedService;
	
	@Inject
	private CheckingDataService checkingDataService;
	
	
	@Override
	public ValueExtractAlarmWRPubExport checkWorkTypeNotRegister(String workplaceID,String employeeID, GeneralDate date, String workTypeCD) {
		Optional<ValueExtractAlarmWRPubExport> data = Optional.of(convertToExport(workTypeNotRegisterService.checkWorkTypeNotRegister(workplaceID,employeeID, date, workTypeCD)));
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}

	@Override
	public ValueExtractAlarmWRPubExport checkWorkTimeNotRegister(String workplaceID,String employeeID, GeneralDate date, String workTimeCD) {
		Optional<ValueExtractAlarmWRPubExport> data = Optional.of(convertToExport(workTimeNotRegisterService.checkWorkTimeNotRegister(workplaceID,employeeID, date, workTimeCD)));
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}

	@Override
	public List<ValueExtractAlarmWRPubExport> checkPrincipalUnconfirm(String workplaceID, String employeeID,
			GeneralDate startDate, GeneralDate endDate) {
		return checkPrincipalUnconfirmService.checkPrincipalUnconfirm(workplaceID, employeeID, startDate, endDate)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
	}

	@Override
	public List<ValueExtractAlarmWRPubExport> checkAdminUnverified(String workplaceID, String employeeID,
			GeneralDate startDate, GeneralDate endDate) {
		return checkAdminUnverifiedService.checkAdminUnverified(workplaceID, employeeID, startDate, endDate)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
	}

	@Override
	public List<ValueExtractAlarmWRPubExport> checkingData(List<ValueExtractAlarmWRPubExport> listValue,String workplaceID,String employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		List<ValueExtractAlarmWR> listValueExtractAlarmWR = listValue.stream().map(c->convertToDto(c)).collect(Collectors.toList());
		return checkingDataService.checkingData(listValueExtractAlarmWR,workplaceID,employeeID, startDate, endDate)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
	}
	
	private ValueExtractAlarmWRPubExport convertToExport(ValueExtractAlarmWR valueExtractAlarmWR) {
		return new ValueExtractAlarmWRPubExport(
				valueExtractAlarmWR.getWorkplaceID(),
				valueExtractAlarmWR.getEmployeeID(),
				valueExtractAlarmWR.getAlarmValueDate(),
				valueExtractAlarmWR.getClassification(),
				valueExtractAlarmWR.getAlarmItem(),
				valueExtractAlarmWR.getAlarmValueMessage(),
				valueExtractAlarmWR.getComment()
				);
	}
	
	private ValueExtractAlarmWR convertToDto(ValueExtractAlarmWRPubExport export) {
		return new ValueExtractAlarmWR(
				export.getWorkplaceID(),
				export.getEmployeeID(),
				export.getAlarmValueDate(),
				export.getClassification(),
				export.getAlarmItem(),
				export.getAlarmValueMessage(),
				export.getComment()
				);
	}
	
	
	

}
