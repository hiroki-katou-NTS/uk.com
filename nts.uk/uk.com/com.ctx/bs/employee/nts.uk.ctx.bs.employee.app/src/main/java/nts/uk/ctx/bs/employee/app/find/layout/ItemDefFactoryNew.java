package nts.uk.ctx.bs.employee.app.find.layout;

import java.util.ArrayList;
import java.util.List;

import find.layout.classification.ActionRole;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.info.Person;

/**
 * Item definition factory: (thay đổi so với cái hiện tại để tùy chỉnh theo chức năng)
 * @author xuan vinh
 *
 */

public class ItemDefFactoryNew {
	public static LayoutPersonInfoClsDto matchInformation(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, Person person) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			case "IS00001":
				// 個人名グループ．個人名
				data = person.getPersonNameGroup().getPersonName().v();
				break;
			case "IS00002":
				// 個人名グループ．個人名カナ
				data = person.getPersonNameGroup().getPersonNameKana().v();
				break;
			case "IS00003":
				// 個人名グループ．個人名ローマ字．氏名
				data = person.getPersonNameGroup().getPersonRomanji().getFullName().v();
				break;
			case "IS00004":
				// 個人名グループ．個人名ローマ字．氏名カナ
				data = person.getPersonNameGroup().getPersonRomanji().getFullNameKana().v();
				break;
			case "IS00005":
				// 個人名グループ．ビジネスネーム
				data = person.getPersonNameGroup().getBusinessName().v();
				break;
			case "IS00006":
				// 個人名グループ．ビジネスネーム．英語
				data = person.getPersonNameGroup().getBusinessEnglishName().v();
				break;
			case "IS00007":
				// 個人名グループ．ビジネスネーム．その他
				data = person.getPersonNameGroup().getBusinessOtherName().v();
				break;
			case "IS00008":
				// 個人名グループ．個人旧氏名．氏名
				data = person.getPersonNameGroup().getOldName().getFullName().v();
				break;
			case "IS00009":
				// 個人名グループ．個人旧氏名．氏名カナ
				data = person.getPersonNameGroup().getOldName().getFullNameKana().v();
				break;
			case "IS00010":
				// 個人名グループ．個人届出名称．氏名
				data = person.getPersonNameGroup().getTodokedeFullName().getFullName().v();
				break;
			case "IS00011":
				// 個人名グループ．個人届出名称．氏名カナ
				data = person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v();
				break;
			case "IS00012":
				// 個人名グループ．個人届出名称．氏名
				data = person.getPersonNameGroup().getTodokedeFullName().getFullName().v();
				break;
			case "IS00013":
				// 個人名グループ．個人届出名称．氏名カナ
				data = person.getPersonNameGroup().getTodokedeFullName().getFullNameKana().v();
				break;
			case "IS00014":
				// 性別
				data = person.getGender().value;
				break;
			case "IS00015":
				// 個人携帯
				data = person.getPersonMobile().v();
				break;
			case "IS00016":
				// 個人メールアドレス
				data = person.getMailAddress().v();
				break;
			case "IS00017":
				// 趣味
				data = person.getHobBy().v();
				break;
			case "IS00018":
				// 嗜好
				data = person.getTaste().v();
				break;
			case "IS00019":
				// 国籍
				data = person.getCountryId().v();
				break;
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
	
	public static LayoutPersonInfoClsDto matchInfoCurrentAddres(String categoryCode, List<PerInfoItemDefDto> listItemDef, ActionRole actionRole, CurrentAddress currentAddress) {
		LayoutPersonInfoClsDto layoutPerInfoClsDto = new LayoutPersonInfoClsDto();
		layoutPerInfoClsDto.setListItemDf(listItemDef);
		layoutPerInfoClsDto.setItems(new ArrayList<>());
		//LayoutPersonInfoClsDto
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Object data = null;
			switch (itemDef.getItemCode()) {
			
			}
			if (data != null) {
				LayoutPersonInfoValueDto obj = LayoutPersonInfoValueDto.initData(categoryCode, itemDef, data);
				obj.setActionRole(actionRole);
				layoutPerInfoClsDto.getItems().add(obj);
			}				
		}
		return layoutPerInfoClsDto;
	}
}
