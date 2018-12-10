package nts.uk.ctx.pereg.dom.person.info.category;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Class này lưu trữ cấu trúc dữ liệu của PersonCategoryInfo và
 * PersonInfoItemDefinition Với dữ liệu được mapping theo file excel
 * [項目定義-説明書.xlsx] ở
 * \\192.168.50.4\share\500_新構想開発\03_概要設計\01_概要設計書\04_共通\CPS_個人情報\個人情報定義
 * 
 * @author lanlt
 *
 */
@Setter
@Getter
public class PersonCategoryItemData {
	// cấu trúc map như sau:
	// Map<CategoryCode, Map<ItemCode, PrimitiveValue Class Name>>
	public static Map<String, Map<String, ReferenceStateData>> CategoryMap = new HashMap<String, Map<String, ReferenceStateData>>();
	
	public static Map<Integer, Map<Integer, String>> selectionLst = new HashMap<Integer, Map<Integer, String>>();
	
	
	public PersonCategoryItemData() {
		// CS00001 - 個人基本情報 CO00001 CS00001
		CategoryMap.put("CS00001", createItemDataOfPersonCategory());

		// CS00002 - 社員基本情報
		CategoryMap.put("CS00002", createItemDataOfEmployeeCategory());

		// CS00003 - 現住所
		CategoryMap.put("CS00003", createItemDataOfAddressCategory());
		

	}

	/**
	 * Data của category CS00001 - 個人基本情報
	 * 
	 * @return
	 */
	public Map<String, ReferenceStateData> createItemDataOfPersonCategory() {
		
		Map<String, ReferenceStateData> itemData = new HashMap<String, ReferenceStateData>();
		//IO00001, IO00001
		ReferenceStateData referenceData = new ReferenceStateData();
		
		referenceData.setConstraint("EmployeeCode");
		itemData.put("IS00001", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("ExternalCode");
		itemData.put("IS00002", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullName");
		itemData.put("IS00003", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullNameKana");
		itemData.put("IS00004", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullName");
		itemData.put("IS00005", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullNameKana");
		itemData.put("IS00006", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("BusinessOtherName");
		itemData.put("IS00007", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullName");
		itemData.put("IS00008", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullNameKana");
		itemData.put("IS00009", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullName");
		itemData.put("IS00010", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullNameKana");
		itemData.put("IS00011", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullName");
		itemData.put("IS00012", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("FullNameKana");
		itemData.put("IS00013", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("GenderPerson");
		
		itemData.put("IS00014", referenceData);
		Map<Integer, String>  selSub = new HashMap<>();
		selSub.put(3, "女");
		selSub.put(3, "男");
//		referenceData.selectionLst.put(3, selSub);
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("PersonMobile");
		itemData.put("IS00015", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("PersonMailAddress");
		itemData.put("IS00016", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("Hobby");
		itemData.put("IS00017", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("Taste");
		itemData.put("IS00018", referenceData);
		
		referenceData = new ReferenceStateData();
		referenceData.setConstraint("Nationality");
		itemData.put("IS00019", referenceData);
		return itemData;
	}

	/**
	 * Data của category CS00002 - 社員基本情報
	 * 
	 * @return
	 */
	public Map<String, ReferenceStateData> createItemDataOfEmployeeCategory() {
		Map<String, ReferenceStateData> itemData = new HashMap<>();
		ReferenceStateData reference = new ReferenceStateData();
		
		reference = new ReferenceStateData();
		reference.setConstraint("EmployeeCode");
		itemData.put("IS00020", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("GeneralDate");
		itemData.put("IS00021", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("GeneralDate");
		itemData.put("IS00022", reference);
		// IO00023 sao lai khong co???
		
		reference = new ReferenceStateData();
		reference.setConstraint("EmployeeMail");
		itemData.put("IS00024", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("EmployeeMail");
		itemData.put("IS00025", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("CompanyMobile");
		itemData.put("IS00026", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("HiringType");
		itemData.put("IS00027", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("GeneralDate");
		itemData.put("IS00028", reference);
		return itemData;
	}

	/**
	 * Data của category CS00003 - 現住所
	 * 
	 * @return
	 */
	public Map<String, ReferenceStateData> createItemDataOfAddressCategory() {
		Map<String, ReferenceStateData> itemData = new HashMap<>();
		ReferenceStateData reference = new ReferenceStateData();
		
		reference = new ReferenceStateData();
		reference.setConstraint("GeneralDate");
		itemData.put("IS00029", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("PostalCode");
		itemData.put("IS00030", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("Perfectures");
		itemData.put("IS00031", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("CountryId");
		itemData.put("IS00032", reference);
		// IO00033 address 1 ? validate kieu gi
		
		reference = new ReferenceStateData();
		reference.setConstraint("PersonAddress1");
		itemData.put("IS00033", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("PersonAddressKana1");
		itemData.put("IS00034", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("PersonAddress2");
		itemData.put("IS00035", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("PersonAddressKana2");
		itemData.put("IS00036", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("PhoneNumber");
		itemData.put("IS00037", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("HomeSituationType");
		itemData.put("IS00038", reference);
		
		reference = new ReferenceStateData();
		reference.setConstraint("HouseRent");
		itemData.put("IS00039", reference);
		return itemData;
	}
	
	public Map<Integer, String> createMaster(){
		Map<Integer, String> selectionData = new HashMap<>();
		return selectionData;
	}
}
