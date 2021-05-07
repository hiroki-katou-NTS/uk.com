package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiName;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;

public class EmpLicenseHelper {
	 public static List<EmpMedicalWorkStyleHistoryItem> getLstEmpMedical() {
		 
		 EmpMedicalWorkStyleHistoryItem result = new EmpMedicalWorkStyleHistoryItem(
					"empID1", 
					"historyID1",
					new NurseClassifiCode("1"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			
			EmpMedicalWorkStyleHistoryItem results = new EmpMedicalWorkStyleHistoryItem(
					"003", 
					"historyID2",
					new NurseClassifiCode("2"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			
			EmpMedicalWorkStyleHistoryItem result2 = new EmpMedicalWorkStyleHistoryItem(
					"002", 
					"historyID4",
					new NurseClassifiCode("3"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			
			List<EmpMedicalWorkStyleHistoryItem> listEmpMedicalWorkFormHisItem = new ArrayList<>();
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
	 
public static List<EmpMedicalWorkStyleHistoryItem> getLstEmpMedical_null() {
		 
		 EmpMedicalWorkStyleHistoryItem result = new EmpMedicalWorkStyleHistoryItem(
					"empID1", 
					"historyID1", 
					new NurseClassifiCode("1"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			
			EmpMedicalWorkStyleHistoryItem results = new EmpMedicalWorkStyleHistoryItem(
					"009", 
					"historyID2",
					new NurseClassifiCode("2"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			
			EmpMedicalWorkStyleHistoryItem result2 = new EmpMedicalWorkStyleHistoryItem(
					"008", 
					"historyID4",
					new NurseClassifiCode("3"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			
			List<EmpMedicalWorkStyleHistoryItem> listEmpMedicalWorkFormHisItem = new ArrayList<>();
			listEmpMedicalWorkFormHisItem.add(result);
			listEmpMedicalWorkFormHisItem.add(results);
			listEmpMedicalWorkFormHisItem.add(result2);
			
			return listEmpMedicalWorkFormHisItem;
	 }
	 
	
	 
public static List<EmpMedicalWorkStyleHistoryItem> getnurseClass_null() {
		 
		 EmpMedicalWorkStyleHistoryItem result = new EmpMedicalWorkStyleHistoryItem(
					"003", 
					"historyID1",
					new NurseClassifiCode("5"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			
			EmpMedicalWorkStyleHistoryItem results = new EmpMedicalWorkStyleHistoryItem(
					"002", 
					"historyID2",
					new NurseClassifiCode("7"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			
			
			EmpMedicalWorkStyleHistoryItem resultss = new EmpMedicalWorkStyleHistoryItem(
					"008", 
					"historyID2", 
					new NurseClassifiCode("2"),
					true,
					MedicalCareWorkStyle.FULLTIME,
					true);
			

			
			List<EmpMedicalWorkStyleHistoryItem> listEmpMedicalWorkFormHisItem = new ArrayList<>();
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
