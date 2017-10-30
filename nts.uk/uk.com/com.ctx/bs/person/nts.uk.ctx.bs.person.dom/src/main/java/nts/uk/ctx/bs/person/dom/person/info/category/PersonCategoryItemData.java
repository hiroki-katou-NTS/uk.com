package nts.uk.ctx.bs.person.dom.person.info.category;

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
	public static Map<String, Map<String, String>> CategoryMap = new HashMap<String, Map<String, String>>();

	public PersonCategoryItemData() {
		// CS00001 - 個人基本情報 CO00001 CS00001
		CategoryMap.put("CO00001", createItemDataOfPersonCategory());

		// CS00002 - 社員基本情報
		CategoryMap.put("CO00002", createItemDataOfEmployeeCategory());

		// CS00003 - 現住所
		CategoryMap.put("CO00003", createItemDataOfAddressCategory());

	}

	/**
	 * Data của category CS00001 - 個人基本情報
	 * 
	 * @return
	 */
	public Map<String, String> createItemDataOfPersonCategory() {
		Map<String, String> itemData = new HashMap<>();
		//IO00001, IO00001
		itemData.put("IO00001", "PersonName");
		itemData.put("IO00002", "PersonNameKana");
		itemData.put("IO00003", "FullName");
		itemData.put("IO00004", "FullNameKana");
		itemData.put("IO00005", "BusinessName");
		itemData.put("IO00006", "BusinessEnglIOhName");
		itemData.put("IO00007", "BusinessOtherName");
		itemData.put("IO00008", "FullName");
		itemData.put("IO00009", "FullNameKana");
		itemData.put("IO00010", "FullName");
		itemData.put("IO00011", "FullNameKana");
		itemData.put("IO00012", "FullName");
		itemData.put("IO00013", "FullNameKana");
		itemData.put("IO00014", "GenderPerson");
		itemData.put("IO00015", "PersonMobile");
		itemData.put("IO00016", "PersonMailAddress");
		itemData.put("IO00017", "Hobby");
		itemData.put("IO00018", "Taste");
		itemData.put("IO00019", "Nationality");
		return itemData;
	}

	/**
	 * Data của category CS00002 - 社員基本情報
	 * 
	 * @return
	 */
	public Map<String, String> createItemDataOfEmployeeCategory() {
		Map<String, String> itemData = new HashMap<>();
		itemData.put("IO00020", "EmployeeCode");
		itemData.put("IO00021", "GeneralDate");
		itemData.put("IO00022", "GeneralDate");
		// IO00023 sao lai khong co???
		itemData.put("IO00024", "EmployeeMail");
		itemData.put("IO00025", "EmployeeMail");
		itemData.put("IO00026", "CompanyMobile");
		itemData.put("IO00027", "HiringType");
		itemData.put("IO00028", "GeneralDate");
		return itemData;
	}

	/**
	 * Data của category CS00003 - 現住所
	 * 
	 * @return
	 */
	public Map<String, String> createItemDataOfAddressCategory() {
		Map<String, String> itemData = new HashMap<>();
		itemData.put("IO00029", "GeneralDate");
		itemData.put("IO00030", "PostalCode");
		itemData.put("IO00031", "Perfectures");
		itemData.put("IO00032", "CountryId");
		// IO00033 address 1 ? validate kieu gi
		itemData.put("IO00033", "PersonAddress1");
		itemData.put("IO00034", "PersonAddressKana1");
		itemData.put("IO00035", "PersonAddress2");
		itemData.put("IO00036", "PersonAddressKana2");
		itemData.put("IO00037", "PhoneNumber");
		itemData.put("IO00038", "HomeSituationType");
		itemData.put("IO00039", "HouseRent");
		return itemData;
	}
}
