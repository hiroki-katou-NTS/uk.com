package nts.uk.ctx.pereg.app.find.common;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.dom.common.WorkTimeSettingRepo;
import nts.uk.ctx.pereg.dom.common.WorkTypeRepo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InitDefaultValue {
	
	@Inject
	private WorkTimeSettingRepo wtsRepo;
	
	@Inject 
	private WorkTypeRepo wtRepo;
	
	public void setDefaultValueRadio(List<LayoutPersonInfoClsDto> classItemList) {
		List<String> firstReqLstItems = new ArrayList<String>();
		firstReqLstItems.add("IS00130");
		firstReqLstItems.add("IS00128");
		firstReqLstItems.add("IS00139");
		firstReqLstItems.add("IS00157");
		firstReqLstItems.add("IS00166");
		firstReqLstItems.add("IS00175");
		firstReqLstItems.add("IS00148");
		firstReqLstItems.add("IS00193");
		firstReqLstItems.add("IS00202");
		firstReqLstItems.add("IS00211");
		firstReqLstItems.add("IS00220");
		firstReqLstItems.add("IS00229");
		firstReqLstItems.add("IS00238");
		firstReqLstItems.add("IS00184");
		List<String> secReqLstItems = new ArrayList<String>();
		secReqLstItems.add("IS00131");
		secReqLstItems.add("IS00140");
		secReqLstItems.add("IS00158");
		secReqLstItems.add("IS00167");
		secReqLstItems.add("IS00176");
		secReqLstItems.add("IS00149");
		secReqLstItems.add("IS00194");
		secReqLstItems.add("IS00203");
		secReqLstItems.add("IS00212");
		secReqLstItems.add("IS00221");
		secReqLstItems.add("IS00230");
		secReqLstItems.add("IS00239");
		secReqLstItems.add("IS00185");
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				if (valueItem.getValue() == null) {
					switch (valueItem.getItemCode()) {
					case "IS00248":
					case "IS00247":
					case "IS00258":
						valueItem.setValue("0");
						break;
					case "IS00121":
						valueItem.setValue("1");
						break;
					default:
						break;
					}

				}else {
					String companyId = AppContexts.user().companyId();
					if(firstReqLstItems.contains(valueItem.getItemCode())) {
						valueItem.setTextValue(getFirstValueText(valueItem.getValue().toString()));
					} else if(secReqLstItems.contains(valueItem.getItemCode())) {
						valueItem.setTextValue(getSecValueText(valueItem.getValue().toString(), companyId));
					}
				}
			}
		}
	}
	//request list request 251
	private String getFirstValueText(String itemValue) {
		return wtRepo.acquireWorkTypeName(itemValue);
	}
	//request list request 252
	private String getSecValueText(String itemValue, String companyId) {
		return wtsRepo.getWorkTimeSettingName(companyId, itemValue);
	}
	
	
}
