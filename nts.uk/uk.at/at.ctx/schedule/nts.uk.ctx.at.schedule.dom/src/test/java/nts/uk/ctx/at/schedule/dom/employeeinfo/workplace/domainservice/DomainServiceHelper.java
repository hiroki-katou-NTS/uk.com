package nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.AffWorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroup;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroupCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroupName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceGroupType;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceReplaceResult;
import nts.uk.ctx.at.schedule.dom.employeeinfo.workplace.WorkplaceReplacement;

public class DomainServiceHelper {
	public static class Helper {
		// String wKPGRPID = "000000000000000000000000000000000011";
		// String cID = AppContexts.user().companyId();
		// String wKPGRPCode = "0000000001";
		// String wKPGRPName = "00000000000000000011";
		// String wKPID = "000000000000000000000000000000000011";
		int wKPGRPType = 1;
		public static WorkplaceGroup DUMMY = new WorkplaceGroup(
				"000000000000000000000000000000000011", 
				"00000000000001", 
				new WorkplaceGroupCode("0000000001"), 
				new WorkplaceGroupName("00000000000000000011"), 
				EnumAdaptor.valueOf(1, WorkplaceGroupType.class));
		
		AffWorkplaceGroup affWorkplaceGroup = new AffWorkplaceGroup(
				"000000000000000000000000000000000011",
				"000000000000000000000000000000000011");
		
	}
	
	public static List<AffWorkplaceGroup> getHelper() {
		String wKPID = "000000000000000000000000000000000011";
		String wKPGRPID = "00000000000001";
		List<AffWorkplaceGroup> workplaceGroup = new ArrayList<AffWorkplaceGroup>();
		AffWorkplaceGroup affWorkplaceGroup = new AffWorkplaceGroup(wKPGRPID, wKPID);
		AffWorkplaceGroup affWorkplaceGroup1 = new AffWorkplaceGroup("00000000000002", "000000000000000000000000000000000011");
		AffWorkplaceGroup affWorkplaceGroup2 = new AffWorkplaceGroup("00000000000003", "000000000000000000000000000000000012");
		AffWorkplaceGroup affWorkplaceGroup3 = new AffWorkplaceGroup("00000000000004", "000000000000000000000000000000000013");
		workplaceGroup.add(affWorkplaceGroup);
		workplaceGroup.add(affWorkplaceGroup1);
		workplaceGroup.add(affWorkplaceGroup2);
		workplaceGroup.add(affWorkplaceGroup3);
		
		return workplaceGroup;
	}
	
	public static List<WorkplaceInfoImport> getLstWpII(){
		String wKPID = "000000000000000000000000000000000011";
		List<WorkplaceInfoImport> lstInfoImports = new ArrayList<>();
		WorkplaceInfoImport import1 = new WorkplaceInfoImport(wKPID, "HierarchyCode", "WorkplaceCode", "WorkplaceName", "DisplayName", "GenericName", "ExternalCode");
		WorkplaceInfoImport import2 = new WorkplaceInfoImport(wKPID, "HierarchyCode1", "WorkplaceCode", "WorkplaceName", "DisplayName", "GenericName", "ExternalCode");
		WorkplaceInfoImport import3 = new WorkplaceInfoImport(wKPID, "HierarchyCode1", "WorkplaceCode", "WorkplaceName", "DisplayName", "GenericName", "ExternalCode");
		WorkplaceInfoImport import4 = new WorkplaceInfoImport(wKPID, "HierarchyCode1", "WorkplaceCode", "WorkplaceName", "DisplayName", "GenericName", "ExternalCode");
		lstInfoImports.add(import1);
		lstInfoImports.add(import2);
		lstInfoImports.add(import3);
		lstInfoImports.add(import4);
		
		return lstInfoImports;
	}
	
	public static List<WorkplaceInfoImport> getLstWpIISecond(){
		String wKPID = "000000000000000000000000000000000012";
		List<WorkplaceInfoImport> lstInfoImports = new ArrayList<>();
		WorkplaceInfoImport import1 = new WorkplaceInfoImport(wKPID, "HierarchyCode", "WorkplaceCode", "WorkplaceName", "DisplayName", "GenericName", "ExternalCode");
		WorkplaceInfoImport import2 = new WorkplaceInfoImport(wKPID, "HierarchyCode1", "WorkplaceCode", "WorkplaceName", "DisplayName", "GenericName", "ExternalCode");
		WorkplaceInfoImport import3 = new WorkplaceInfoImport(wKPID, "HierarchyCode1", "WorkplaceCode", "WorkplaceName", "DisplayName", "GenericName", "ExternalCode");
		WorkplaceInfoImport import4 = new WorkplaceInfoImport(wKPID, "HierarchyCode1", "WorkplaceCode", "WorkplaceName", "DisplayName", "GenericName", "ExternalCode");
		lstInfoImports.add(import1);
		lstInfoImports.add(import2);
		lstInfoImports.add(import3);
		lstInfoImports.add(import4);
		
		return lstInfoImports;
	}
	
	public static WorkplaceReplaceResult getWorkplaceReplaceResultDefault(int i) {
		return new WorkplaceReplaceResult(EnumAdaptor.valueOf(i, WorkplaceReplacement.class)
				, Optional.of(AtomTask.of(() -> {
		})));
	}
	
}
