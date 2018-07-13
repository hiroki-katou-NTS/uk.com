package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoverySelection;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoverySelectionRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.OperableSystemImport;
import nts.uk.ctx.sys.assist.dom.datarestoration.SystemTypeDataAdapter;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DataRecoverySelectionFinder {
	@Inject
	private DataRecoverySelectionRepository finder;
	@Inject
	private SystemTypeDataAdapter systemTypeAdapter;

	public List<DataRecoverySelectionDto> getTargetById(GeneralDateTime startDate, GeneralDateTime endDate) {
		String companyId = AppContexts.user().companyId();
		List<Integer> systemType = this.getSystemType();
		List<DataRecoverySelection> optDataRecoverySelection = finder.getDataRecoverySelection(companyId, systemType,
				startDate, endDate);
		if (!optDataRecoverySelection.isEmpty()) {
			//return DataRecoverySelectionDto.fromDomain(optDataRecoverySelection);
			
		}
		List<DataRecoverySelectionDto> listDataRecovery =  optDataRecoverySelection.stream().map(c -> DataRecoverySelectionDto.fromDomain(c)).collect(Collectors.toList());
		listDataRecovery.sort(new Comparator<DataRecoverySelectionDto>() {
		    @Override
		    public int compare(DataRecoverySelectionDto m1, DataRecoverySelectionDto m2) {
		        return m1.getSaveStartDatetime().compareTo(m2.getSaveStartDatetime());
		     }
		});
		return listDataRecovery;
	}

	private List<Integer> getSystemType() {
		List<Integer> systemType = new ArrayList<>();
		OperableSystemImport operableSystemImport = systemTypeAdapter.getOperableSystem();
		if (operableSystemImport.isOfficeHelper()) {
			systemType.add(SystemType.OFFICE_HELPER.value);
		}
		if (operableSystemImport.isAttendance()) {
			systemType.add(SystemType.ATTENDANCE_SYSTEM.value);
		}
		if (operableSystemImport.isHumanResource()) {
			systemType.add(SystemType.PERSON_SYSTEM.value);
		}
		if (operableSystemImport.isSalary()) {
			systemType.add(SystemType.PAYROLL_SYSTEM.value);
		}
		return systemType;
	}
}
