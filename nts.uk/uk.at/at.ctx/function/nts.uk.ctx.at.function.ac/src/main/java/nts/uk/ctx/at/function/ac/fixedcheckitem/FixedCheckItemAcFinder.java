package nts.uk.ctx.at.function.ac.fixedcheckitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.fixedcheckitem.FixedCheckItemAdapter;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.record.pub.fixedcheckitem.FixedCheckItemPub;
import nts.uk.ctx.at.record.pub.fixedcheckitem.ValueExtractAlarmWRPubExport;
@Stateless
public class FixedCheckItemAcFinder implements FixedCheckItemAdapter {
	
	@Inject
	private FixedCheckItemPub fixedCheckItemPub;

	@Override
	public Optional<ValueExtractAlarm>  checkWorkTypeNotRegister(String workplaceID,String employeeID, GeneralDate date, String workTypeCD) {
		return Optional.ofNullable(convertToExport(fixedCheckItemPub.checkWorkTypeNotRegister(workplaceID,employeeID, date, workTypeCD)));
	}

	@Override
	public Optional<ValueExtractAlarm> checkWorkTimeNotRegister(String workplaceID,String employeeID, GeneralDate date, String workTimeCD) {
		return Optional.ofNullable(convertToExport(fixedCheckItemPub.checkWorkTimeNotRegister(workplaceID,employeeID, date, workTimeCD)));
	}

	@Override
	public List<ValueExtractAlarm> checkPrincipalUnconfirm(String workplaceID, String employeeID,
			GeneralDate startDate, GeneralDate endDate) {
		return fixedCheckItemPub.checkPrincipalUnconfirm(workplaceID, employeeID, startDate, endDate)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
	}

	@Override
	public List<ValueExtractAlarm> checkAdminUnverified(String workplaceID, String employeeID,
			GeneralDate startDate, GeneralDate endDate) {
		return fixedCheckItemPub.checkAdminUnverified(workplaceID, employeeID, startDate, endDate)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
	}

	@Override
	public List<ValueExtractAlarm> checkingData(List<ValueExtractAlarm> listValue,String workplaceID,String employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		List<ValueExtractAlarmWRPubExport> listExport = listValue.stream().map(c->convertToImport(c)).collect(Collectors.toList());
		return fixedCheckItemPub.checkingData(listExport,workplaceID,employeeID, startDate, endDate)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
	}
	
	private ValueExtractAlarm convertToExport(ValueExtractAlarmWRPubExport export) {
		return new ValueExtractAlarm(
				export.getWorkplaceID(),
				export.getEmployeeID(),
				export.getAlarmValueDate(),
				export.getClassification(),
				export.getAlarmItem(),
				export.getAlarmValueMessage(),
				export.getComment()
				);
	}
	
	private ValueExtractAlarmWRPubExport convertToImport(ValueExtractAlarm importDto) {
		return new ValueExtractAlarmWRPubExport(
				importDto.getWorkplaceID(),
				importDto.getEmployeeID(),
				importDto.getAlarmValueDate(),
				importDto.getClassification(),
				importDto.getAlarmItem(),
				importDto.getAlarmValueMessage(),
				importDto.getComment()
				);
	}

}
