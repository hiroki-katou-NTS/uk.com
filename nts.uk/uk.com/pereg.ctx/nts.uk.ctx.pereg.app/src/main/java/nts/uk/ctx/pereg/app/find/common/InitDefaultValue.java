package nts.uk.ctx.pereg.app.find.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.PaymentMethod;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.dom.common.WorkTimeSettingRepo;
import nts.uk.ctx.pereg.dom.common.WorkTypeRepo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class InitDefaultValue {

	@Inject
	private WorkTimeSettingRepo wtsRepo;

	@Inject
	private WorkTypeRepo wtRepo;
	
	@Inject
	I18NResourcesForUK ukResouce;
	
	private static final String SPACE_JP = " ";

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
		if (classItemList == null) {

			return;
		}
		List<LayoutPersonInfoClsDto> cls = classItemList.stream().filter(x -> x.getItems() != null)
				.collect(Collectors.toList());
		for (LayoutPersonInfoClsDto classItem : cls) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				if (valueItem.getValue() == null) {
					switch (valueItem.getItemCode()) {
					case "IS00248":
					case "IS00247":
					case "IS00258":
					case "IS00296":
					case "IS00303":
					case "IS00310":
					case "IS00317":
					case "IS00324":
					case "IS00331":
					case "IS00338":
					case "IS00345":
					case "IS00352":
					case "IS00359":
					case "IS00560":
					case "IS00567":
					case "IS00574":
					case "IS00581":
					case "IS00588":
					case "IS00595":
					case "IS00602":
					case "IS00609":
					case "IS00616":
					case "IS00623":
					case "IS00370":
					case "IS00375":
					case "IS00380":
						valueItem.setValue(String.valueOf(UseAtr.NotUse.value));
						break;
					case "IS00121":
						valueItem.setValue(String.valueOf(UseAtr.Use.value));
						break;
					case "IS00372":
						valueItem.setValue(String.valueOf(PaymentMethod.VACATION_OCCURRED.value));
					default:
						break;
					}

				} else {
					String companyId = AppContexts.user().companyId();
					if (firstReqLstItems.contains(valueItem.getItemCode())) {
						valueItem.setTextValue(getFirstValueText(valueItem.getValue().toString()));
					} else if (secReqLstItems.contains(valueItem.getItemCode())) {
						valueItem.setTextValue(getSecValueText(valueItem.getValue().toString(), companyId));
					}
				}
			}
		}
	}

	// request list request 251
	private String getFirstValueText(String itemValue) {
		String resultText = wtRepo.acquireWorkTypeName(itemValue);
		if(resultText == null) {
			resultText = ukResouce.getRawContent("CPS001_107").get();
		}
		return resultText;
	}

	// request list request 252
	private String getSecValueText(String itemValue, String companyId) {
		String resultText = wtsRepo.getWorkTimeSettingName(companyId, itemValue);
		if(resultText == null) {
			resultText = ukResouce.getRawContent("CPS001_107").get();
		}
		return resultText;
	}

}
