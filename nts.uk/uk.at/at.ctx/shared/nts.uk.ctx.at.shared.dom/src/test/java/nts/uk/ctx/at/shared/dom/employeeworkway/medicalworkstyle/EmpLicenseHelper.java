package nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class EmpLicenseHelper {
	 public static List<EmpMedicalWorkFormHisItem> getLstEmpMedical() {
		 
		 EmpMedicalWorkFormHisItem result = new EmpMedicalWorkFormHisItem(
					"empID1", 
					"historyID1",
					new NurseClassifiCode("1"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			
			EmpMedicalWorkFormHisItem results = new EmpMedicalWorkFormHisItem(
					"003", 
					"historyID2",
					new NurseClassifiCode("2"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			
			EmpMedicalWorkFormHisItem result2 = new EmpMedicalWorkFormHisItem(
					"002", 
					"historyID4",
					new NurseClassifiCode("3"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			
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
					"historyID1", 
					new NurseClassifiCode("1"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			
			EmpMedicalWorkFormHisItem results = new EmpMedicalWorkFormHisItem(
					"009", 
					"historyID2",
					new NurseClassifiCode("2"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			
			EmpMedicalWorkFormHisItem result2 = new EmpMedicalWorkFormHisItem(
					"008", 
					"historyID4",
					new NurseClassifiCode("3"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			
			List<EmpMedicalWorkFormHisItem> listEmpMedicalWorkFormHisItem = new ArrayList<>();
			listEmpMedicalWorkFormHisItem.add(result);
			listEmpMedicalWorkFormHisItem.add(results);
			listEmpMedicalWorkFormHisItem.add(result2);
			
			return listEmpMedicalWorkFormHisItem;
	 }
	 
	
	 
public static List<EmpMedicalWorkFormHisItem> getnurseClass_null() {
		 
		 EmpMedicalWorkFormHisItem result = new EmpMedicalWorkFormHisItem(
					"003", 
					"historyID1",
					new NurseClassifiCode("5"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			
			EmpMedicalWorkFormHisItem results = new EmpMedicalWorkFormHisItem(
					"002", 
					"historyID2",
					new NurseClassifiCode("7"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			
			
			EmpMedicalWorkFormHisItem resultss = new EmpMedicalWorkFormHisItem(
					"008", 
					"historyID2", 
					new NurseClassifiCode("2"),
					NotUseAtr.USE,
					MedicalCareWorkStyle.FULLTIME,
					NotUseAtr.USE);
			

			
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
