package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

public class EmpLicenseHelper {
	 public static List<EmpMedicalWorkFormHisItem> getLstEmpMedical() {
		 
		 EmpMedicalWorkFormHisItem result = new EmpMedicalWorkFormHisItem(
					"empID1", 
					"historyID1", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("1"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			
			EmpMedicalWorkFormHisItem results = new EmpMedicalWorkFormHisItem(
					"003", 
					"historyID2", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("2"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			
			EmpMedicalWorkFormHisItem result2 = new EmpMedicalWorkFormHisItem(
					"002", 
					"historyID4", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("3"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			
			List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = new ArrayList<>();
			listEmpMedicalWorkFormHisItem.add(result);
			listEmpMedicalWorkFormHisItem.add(results);
			listEmpMedicalWorkFormHisItem.add(result2);
			
			return listEmpMedicalWorkFormHisItem;
	 }
	 
	 public static List<NurseClassification> getLstNurseClass (){
		 NurseClassification classification = new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("1"), new NurseClassifiName("NAME"), LicenseClassification.valueOf(LicenseClassification.NURSE.value), true);
		 
		 NurseClassification classification2 = new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("2"), new NurseClassifiName("NAME1"), LicenseClassification.valueOf(LicenseClassification.NURSE_ASSIST.value), true);
		
		 NurseClassification classification3 = new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("3"), new NurseClassifiName("NAME2"), LicenseClassification.valueOf(LicenseClassification.NURSE_ASSOCIATE.value), true);
		 
		 List<NurseClassification> list = new ArrayList<NurseClassification>();
		 list.add(classification);
		 list.add(classification2);
		 list.add(classification3);
		 return list;
	 }
	 
	 public static List<NurseClassification> getLstNurseClass_null (){
		 NurseClassification classification = new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("1"), new NurseClassifiName("NAME"), LicenseClassification.valueOf(LicenseClassification.NURSE.value), true);
		 
		 NurseClassification classification2 = new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("2"), new NurseClassifiName("NAME1"), LicenseClassification.valueOf(LicenseClassification.NURSE_ASSIST.value), true);
		
		 NurseClassification classification3 = new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("3"), new NurseClassifiName("NAME2"), LicenseClassification.valueOf(LicenseClassification.NURSE_ASSOCIATE.value), true);
		 
		 List<NurseClassification> list = new ArrayList<NurseClassification>();
		 list.add(classification);
		 list.add(classification2);
		 list.add(classification3);
		 return list;
	 }
	 
public static List<EmpMedicalWorkFormHisItem> getLstEmpMedical_null() {
		 
		 EmpMedicalWorkFormHisItem result = new EmpMedicalWorkFormHisItem(
					"empID1", 
					"historyID1", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("1"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			
			EmpMedicalWorkFormHisItem results = new EmpMedicalWorkFormHisItem(
					"009", 
					"historyID2", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("2"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			
			EmpMedicalWorkFormHisItem result2 = new EmpMedicalWorkFormHisItem(
					"008", 
					"historyID4", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("3"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			
			List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = new ArrayList<>();
			listEmpMedicalWorkFormHisItem.add(result);
			listEmpMedicalWorkFormHisItem.add(results);
			listEmpMedicalWorkFormHisItem.add(result2);
			
			return listEmpMedicalWorkFormHisItem;
	 }
	 
	
	 
public static List<EmpMedicalWorkFormHisItem> getnurseClass_null() {
		 
		 EmpMedicalWorkFormHisItem result = new EmpMedicalWorkFormHisItem(
					"003", 
					"historyID1", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("5"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			
			EmpMedicalWorkFormHisItem results = new EmpMedicalWorkFormHisItem(
					"002", 
					"historyID2", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("7"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			
			EmpMedicalWorkFormHisItem resultss = new EmpMedicalWorkFormHisItem(
					"008", 
					"historyID2", true,
					Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
							new NurseClassifiCode("2"), true)),
					Optional.ofNullable(new NursingWorkFormInfor(
							MedicalCareWorkStyle.FULLTIME,
							true,
							new FulltimeRemarks("FulltimeRemarks"),
							new NightShiftRemarks("NightShiftRemarks"))));
			

			
			List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = new ArrayList<>();
			listEmpMedicalWorkFormHisItem.add(result);
			listEmpMedicalWorkFormHisItem.add(results);
			listEmpMedicalWorkFormHisItem.add(resultss);
			
			return listEmpMedicalWorkFormHisItem;
	 }
	 
	 public static List<NurseClassification> getnurseClassification_null (){
		 NurseClassification classification2 = new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("3"), new NurseClassifiName("NAME1"), LicenseClassification.valueOf(LicenseClassification.NURSE_ASSIST.value), true);
		 NurseClassification classification = new NurseClassification(new CompanyId("CID"), 
				 new NurseClassifiCode("2"), new NurseClassifiName("NAME1"), LicenseClassification.valueOf(LicenseClassification.NURSE_ASSIST.value), true);
		
		
		 
		 List<NurseClassification> list = new ArrayList<NurseClassification>();
		 list.add(classification2);
		 list.add(classification);
		 return list;
	 }
}
