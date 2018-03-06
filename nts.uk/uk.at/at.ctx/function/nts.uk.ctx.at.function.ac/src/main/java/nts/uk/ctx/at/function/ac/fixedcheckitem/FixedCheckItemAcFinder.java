package nts.uk.ctx.at.function.ac.fixedcheckitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.fixedcheckitem.FixedCheckItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.fixedcheckitem.ValueExtractAlarmWRAdapterDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
import nts.uk.ctx.at.record.pub.fixedcheckitem.FixedCheckItemPub;
import nts.uk.ctx.at.record.pub.fixedcheckitem.ValueExtractAlarmWRPubExport;
@Stateless
public class FixedCheckItemAcFinder implements FixedCheckItemAdapter {
	
	@Inject
	private FixedCheckItemPub fixedCheckItemPub;

	@Override
	public boolean checkWorkTypeNotRegister(String employeeID, GeneralDate date, String workTypeCD) {
		return fixedCheckItemPub.checkWorkTypeNotRegister(employeeID, date, workTypeCD);
	}

	@Override
	public boolean checkWorkTimeNotRegister(String employeeID, GeneralDate date, String workTimeCD) {
		return fixedCheckItemPub.checkWorkTimeNotRegister(employeeID, date, workTimeCD);
	}

	@Override
	public List<ValueExtractAlarmWRAdapterDto> checkPrincipalUnconfirm(String workplaceID, String employeeID,
			GeneralDate startDate, GeneralDate endDate) {
		return fixedCheckItemPub.checkPrincipalUnconfirm(workplaceID, employeeID, startDate, endDate)
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
	}

	@Override
	public List<ValueExtractAlarmWRAdapterDto> checkAdminUnverified(String workplaceID, String employeeID,
			GeneralDate startDate, GeneralDate endDate) {
		return fixedCheckItemPub.checkAdminUnverified(workplaceID, employeeID, startDate, endDate)
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
	}

	@Override
	public List<ValueExtractAlarmWRAdapterDto> checkingData(String employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		return fixedCheckItemPub.checkingData(employeeID, startDate, endDate)
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
	}
	
	private ValueExtractAlarmWRAdapterDto convertToImport(ValueExtractAlarmWRPubExport export) {
		return new ValueExtractAlarmWRAdapterDto(
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
