package nts.uk.ctx.pereg.app.find.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.PaymentMethod;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveAppSetting;
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
	
	private static List<String> categorySetDefault = Arrays.asList("CS00020", 
			"CS00025", "CS00026", "CS00027","CS00028", "CS00029", "CS00030", "CS00031", "CS00032", "CS00033", "CS00034", 
			"CS00035", "CS00036","CS00037", "CS00038", 
			"CS00039", "CS00040", "CS00041", "CS00042", "CS00043", "CS00044", "CS00045","CS00046", "CS00047", "CS00048", 
			"CS00049", "CS00050", "CS00051", "CS00052", "CS00053", "CS00054", "CS00055", "CS00056", "CS00057", "CS00058", 
			"CS00059", "CS00060", "CS00061", "CS00062", "CS00063", "CS00064", "CS00065", "CS00066", "CS00067", "CS00068");
	
	public void setDefaultValue(List<LayoutPersonInfoClsDto> classItemList) {
		List<String> firstReqLstItems = Arrays.asList("IS00130", "IS00128", "IS00139", "IS00157", "IS00166", "IS00175",
				"IS00148", "IS00193", "IS00202", "IS00211", "IS00220", "IS00229", "IS00238", "IS00184");
		List<String> secReqLstItems = Arrays.asList("IS00131", "IS00140", "IS00158", "IS00167", "IS00176", "IS00149",
				"IS00194", "IS00203", "IS00212", "IS00221", "IS00230", "IS00239", "IS00185");
		if (classItemList == null) {
			return;
		}
		
		// set default radio value
		List<LayoutPersonInfoClsDto> cls = classItemList.stream()
				.filter(classItem -> categorySetDefault.contains(classItem.getPersonInfoCategoryCD()))
				.filter(x -> x.getItems() != null)
				.collect(Collectors.toList());
		
		for (LayoutPersonInfoClsDto classItem : cls) {
			for (Object item : classItem.getItems()) {
				LayoutPersonInfoValueDto valueItem = (LayoutPersonInfoValueDto) item;
				if (valueItem.getValue() == null) {
					switch (valueItem.getItemCode()) {
					//労働条件　の　各項目
					case "IS00248":
					case "IS00247":
					case "IS00258":
					//特別休暇１～２０情報　の　使用区分
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
					//その他休暇情報　の　60H超休管理
					case "IS00370":
					//子の看護・介護休暇情報　の　子の看護休暇管理
					case "IS00375":
					//子の看護・介護休暇情報　の　子の介護休暇管理
					case "IS00380":
						valueItem.setValue(String.valueOf(UseAtr.NotUse.value));
						break;
					// 特別休暇１～２０情報　の　付与設定
					case "IS00297":
					case "IS00304":
					case "IS00311":
					case "IS00318":
					case "IS00325":
					case "IS00332":
					case "IS00339":
					case "IS00346":
					case "IS00353":
					case "IS00360":
					case "IS00561":
					case "IS00568":
					case "IS00575":
					case "IS00582":
					case "IS00589":
					case "IS00596":
					case "IS00603":
					case "IS00610":
					case "IS00617":
					case "IS00624":
						valueItem.setValue(String.valueOf(SpecialLeaveAppSetting.PRESCRIBED.value));
						break;
					//労働条件のスケジュール管理
					case "IS00121":
						valueItem.setValue(String.valueOf(UseAtr.Use.value));
						break;
					//その他休暇情報　の　精算方法
					case "IS00372":
						valueItem.setValue(String.valueOf(PaymentMethod.VACATION_OCCURRED.value));
						break;
					//年休付与残数　の　年休期限切れ状態
					case "IS00387":
					//積立年休付与残数　の　積立年休期限切れ状態
					case "IS00400":
					// 特別休暇１～２０付与残数　の　付与設定
					case "IS00411":
					case "IS00426":
					case "IS00441":
					case "IS00456":
					case "IS00471":
					case "IS00486":
					case "IS00501":
					case "IS00516":
					case "IS00531":
					case "IS00546":
					case "IS00631":
					case "IS00646":
					case "IS00661":
					case "IS00676":
					case "IS00691":
					case "IS00706":
					case "IS00721":
					case "IS00736":
					case "IS00751":
					case "IS00766":
						valueItem.setValue(String.valueOf(LeaveExpirationStatus.AVAILABLE.value));
						break;
					//子の看護・介護休暇情報
					case "IS00376":
					case "IS00381":
						valueItem.setValue(String.valueOf(UpperLimitSetting.FAMILY_INFO.value));
						break;
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
