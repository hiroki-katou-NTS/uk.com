package nts.uk.ctx.pereg.app.command.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.UseAtr;
import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.PaymentMethod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.HourlyPaymentAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.item.InitValue;
import nts.uk.ctx.pereg.dom.person.info.item.ItemBasicInfo;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.ItemValueType;

@Stateless
public class FacadeUtils {

	@Inject
	private AffCompanyHistRepository affCompanyHistRepository;

	@Inject
	private PerInfoItemDefRepositoty perInfoItemRepo;

	@Inject
	private ComboBoxRetrieveFactory combox;

	@Inject
	private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	private static final List<String> historyCategoryCodeList = Arrays.asList("CS00003", "CS00004", "CS00014",
			"CS00016", "CS00017", "CS00018", "CS00019", "CS00020", "CS00021", "CS00070", "CS00082", "CS00075", "CS00092");

	private static final Map<String, String> startDateItemCodes;
	static {
		Map<String, String> aMap = new HashMap<>();
		// 所属会社履歴
		aMap.put("CS00003", "IS00020");
		// 分類１
		aMap.put("CS00004", "IS00026");
		// 雇用
		aMap.put("CS00014", "IS00066");
		// 職位本務
		aMap.put("CS00016", "IS00077");
		// 職場
		aMap.put("CS00017", "IS00082");
		// 休職休業
		aMap.put("CS00018", "IS00087");
		// 短時間勤務
		aMap.put("CS00019", "IS00102");
		// 労働条件
		aMap.put("CS00020", "IS00119");
		// 勤務種別
		aMap.put("CS00021", "IS00255");
		// 労働条件２
		aMap.put("CS00070", "IS00781");
		//社員健康保険資格情報
		aMap.put("CS00082", "IS00841");

		aMap.put("CS00075","IS00788");

		aMap.put("CS00092", "IS01016");

		startDateItemCodes = Collections.unmodifiableMap(aMap);
	}

	private static final Map<String, String> endDateItemCodes;
	static {
		Map<String, String> aMap = new HashMap<>();
		// 所属会社履歴
		aMap.put("CS00003", "IS00021");
		// 分類１
		aMap.put("CS00004", "IS00027");
		// 雇用
		aMap.put("CS00014", "IS00067");
		// 職位本務
		aMap.put("CS00016", "IS00078");
		// 職場
		aMap.put("CS00017", "IS00083");
		// 休職休業
		aMap.put("CS00018", "IS00088");
		// 短時間勤務
		aMap.put("CS00019", "IS00103");
		// 労働条件
		aMap.put("CS00020", "IS00120");
		// 勤務種別
		aMap.put("CS00021", "IS00256");
		// 労働条件２
		aMap.put("CS00070", "IS00782");
		//社員健康保険資格情報
		aMap.put("CS00082", "IS00842");

		aMap.put("CS00075","IS00789");

		aMap.put("CS00092", "IS01017");

		endDateItemCodes = Collections.unmodifiableMap(aMap);
	}



	private static final String FUNCTION_NAME = "getListDefault";

	// CS00020
	public List<ItemValue> getListDefaultCS00020(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		List<PersonInfoItemDefinition> itemdfLst = this.perInfoItemRepo.getItemsByCtgCdItemCdsCid("CS00020",
				Arrays.asList("IS00253", "IS00248", "IS00247", "IS00258", "IS00259", "IS00121"),
				AppContexts.user().companyId(), AppContexts.user().contractCode());

		String[][] cs00020Item = new String[6][4];
		if (!CollectionUtil.isEmpty(itemdfLst)) {
			for (PersonInfoItemDefinition c : itemdfLst) {
				if (c.getItemCode().v().equals("IS00253")) {
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : "0";
					cs00020Item[0][0] = c.getItemCode().v();
					cs00020Item[0][1] = numberType;
					cs00020Item[0][2] = value;
					cs00020Item[0][3] = ItemValue.formatMinutesToTime(Integer.valueOf(value).intValue());
					break;
				}

				if (c.getItemCode().v().equals("IS00248")) {
					String notUse = String.valueOf(NotUseAtr.NOT_USE.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : notUse;
					cs00020Item[1][0] = c.getItemCode().v();
					cs00020Item[1][1] = numberType;
					cs00020Item[1][2] = value;
					cs00020Item[1][3] = value.equals("0") == true ? "しない" : "する";
					break;
				}

				if (c.getItemCode().v().equals("IS00247")) {
					String notUse = String.valueOf(NotUseAtr.NOT_USE.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : notUse;
					cs00020Item[2][0] = c.getItemCode().v();
					cs00020Item[2][1] = numberType;
					cs00020Item[2][2] = value;
					cs00020Item[2][3] = value.equals("0") == true ? "しない" : "する";
					break;
				}

				if (c.getItemCode().v().equals("IS00258")) {
					String notUse = String.valueOf(NotUseAtr.NOT_USE.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : notUse;
					cs00020Item[3][0] = c.getItemCode().v();
					cs00020Item[3][1] = numberType;
					cs00020Item[3][2] = value;
					cs00020Item[3][3] = value.equals("0") == true ? "しない" : "する";
					break;
				}

				if (c.getItemCode().v().equals("IS00259")) {
					String OOUTSIDE_TIME_PAY = String.valueOf(HourlyPaymentAtr.OOUTSIDE_TIME_PAY.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v()
							: OOUTSIDE_TIME_PAY;
					cs00020Item[4][0] = c.getItemCode().v();
					cs00020Item[4][1] = numberType;
					cs00020Item[4][2] = value;
					cs00020Item[4][3] = value.equals("0") == true ? "時給者" : "時給者以外";
					break;
				}

				if (c.getItemCode().v().equals("IS00121")) {
					String USE = String.valueOf(ManageAtr.USE.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : USE;
					cs00020Item[5][0] = c.getItemCode().v();
					cs00020Item[5][1] = numberType;
					cs00020Item[5][2] = value;
					cs00020Item[5][3] = value.equals("0") == true ? "管理しない" : "管理する";
					break;
				}
			}

			return FacadeUtils.createListItems(cs00020Item, listItemIfo);
		} else {

			String[][] cs00020ItemInter = { { "IS00253", numberType, "0", ItemValue.formatMinutesToTime(0) },
					{ "IS00248", numberType, String.valueOf(NotUseAtr.NOT_USE.value), "しない" },
					{ "IS00247", numberType, String.valueOf(NotUseAtr.NOT_USE.value), "しない" },
					{ "IS00258", numberType, String.valueOf(NotUseAtr.NOT_USE.value), "しない" },
					{ "IS00259", numberType, String.valueOf(HourlyPaymentAtr.OOUTSIDE_TIME_PAY.value), "時給者以外" },
					{ "IS00121", numberType, String.valueOf(ManageAtr.USE.value), "管理する" } };
			return FacadeUtils.createListItems(cs00020ItemInter, listItemIfo);
		}
	}

	// CS00025
	public List<ItemValue> getListDefaultCS00025(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00025",
				"IS00296", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00025Item = {
					{ "IS00296", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v(): notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00025Item, listItemIfo);
		} else {
			String[][] cs00025Item = { { "IS00296", numberType,	notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00025Item, listItemIfo);
		}
	}

	// CS00025
	public List<ItemValue> getListDefaultEnum(List<ItemBasicInfo> listItemIfo) {
		List<String> itemEnum = Arrays.asList("IS00297","IS00304","IS00311","IS00318", "IS00325","IS00332","IS00339","IS00346","IS00353","IS00360","IS00561","IS00568","IS00575",
				"IS00582", "IS00589","IS00596","IS00603","IS00610","IS00617","IS00624");
		List<ComboBoxObject> comboxs = this.combox.getComboBox(ReferenceTypes.ENUM, "E00012",
				GeneralDate.today(), AppContexts.user().employeeId(), null, true,
				PersonEmployeeType.EMPLOYEE, true, "CS00025", GeneralDate.today(),  false);
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		List<ItemValue>  result = new ArrayList<>();
		if (!CollectionUtil.isEmpty(comboxs)) {
			listItemIfo.stream().forEach(c ->{
				if(itemEnum.contains(c.getItemCode())) {
					String[][] cs00025Item = {
							{ c.getItemCode(), numberType, comboxs.get(0).getOptionValue(), comboxs.get(0).getOptionValue() } };
					result.addAll(FacadeUtils.createListItems(cs00025Item, listItemIfo));
				}
			});
		}
		return result;
	}

	// CS00026
	public List<ItemValue> getListDefaultCS00026(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00026",
				"IS00303", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00026Item = {
					{ "IS00303", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v() : notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00026Item, listItemIfo);
		} else {
			String[][] cs00026Item = { { "IS00303", numberType, notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00026Item, listItemIfo);
		}
	}

	// CS00027
	public List<ItemValue> getListDefaultCS00027(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00027",
				"IS00310", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00027Item = {
					{ "IS00310", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v() : notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00027Item, listItemIfo);
		} else {
			String[][] cs00027Item = { { "IS00310", numberType, notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00027Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00028(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00028",
				"IS00317", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00028Item = {
					{ "IS00317", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v(): notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00028Item, listItemIfo);
		} else {
			String[][] cs00028Item = { { "IS00317", numberType, notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00028Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00029(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00029",
				"IS00324", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00029Item = {
					{ "IS00324", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v()
									: notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00029Item, listItemIfo);
		} else {
			String[][] cs00029Item = { { "IS00324", numberType, notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00029Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00030(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00030",
				"IS00331", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00030Item = {
					{ "IS00331", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v()
									: notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00030Item, listItemIfo);
		} else {
			String[][] cs00030Item = { { "IS00331", numberType, notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00030Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00031(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00031",
				"IS00338", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00031Item = {
					{ "IS00338", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v()
									: notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00031Item, listItemIfo);
		} else {
			String[][] cs00031Item = { { "IS00338", numberType, notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00031Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00032(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00032",
				"IS00345", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00032Item = {
					{ "IS00345", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v()
									: notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00032Item, listItemIfo);
		} else {
			String[][] cs00032Item = { { "IS00345", numberType,
				notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00032Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00033(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00033",
				"IS00352", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00033Item = {
					{ "IS00352", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v()
									: notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00033Item, listItemIfo);
		} else {
			String[][] cs00033Item = { { "IS00352", numberType,
				notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00033Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00034(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		String notUse = String.valueOf(UseAtr.NOT_USE.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00034",
				"IS00359", AppContexts.user().companyId(), AppContexts.user().contractCode());
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String[][] cs00034Item = {
					{ "IS00359", numberType,
							initValueOpt.isPresent() == true ? initValueOpt.get().v()
									: notUse,
							initValueOpt.isPresent() == true
									? (initValueOpt.get().v().equals(notUse) == true
											? TextResource.localize("CPS001_100")
											: TextResource.localize("CPS001_99"))
									: TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00034Item, listItemIfo);
		} else {
			String[][] cs00034Item = { { "IS00359", numberType,
				notUse, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00034Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00035(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		List<PersonInfoItemDefinition> itemdfLst = this.perInfoItemRepo.getItemsByCtgCdItemCdsCid("CS00035",
				Arrays.asList("IS00369", "IS00370", "IS00371", "IS00372"), AppContexts.user().companyId(),
				AppContexts.user().contractCode());

		if (!CollectionUtil.isEmpty(itemdfLst)) {
			String[][] cs00035Item = new String[4][4];
			for (PersonInfoItemDefinition c : itemdfLst) {
				if (c.getItemCode().v().equals("IS00369")) {
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : "0";
					cs00035Item[0][0] = c.getItemCode().v();
					cs00035Item[0][1] = numberType;
					cs00035Item[0][2] = value;
					cs00035Item[0][3] = value;
					break;
				}

				if (c.getItemCode().v().equals("IS00370")) {
					String notUse = String.valueOf(NotUseAtr.NOT_USE.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : notUse;
					cs00035Item[1][0] = c.getItemCode().v();
					cs00035Item[1][1] = numberType;
					cs00035Item[1][2] = value;
					cs00035Item[1][3] = value.equals("0") == true ? "しない" : "する";
					break;
				}

				if (c.getItemCode().v().equals("IS00371")) {
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : "0";
					cs00035Item[2][0] = c.getItemCode().v();
					cs00035Item[2][1] = numberType;
					cs00035Item[2][2] = value;
					cs00035Item[2][3] = value;
					break;
				}

				if (c.getItemCode().v().equals("IS00372")) {
					String VACATION_OCCURRED = String.valueOf(PaymentMethod.VACATION_OCCURRED.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v()
							: VACATION_OCCURRED;
					cs00035Item[3][0] = c.getItemCode().v();
					cs00035Item[3][1] = numberType;
					cs00035Item[3][2] = value;
					cs00035Item[3][3] = value.equals("0") == true ? "休暇発生" : "金額精算";
					break;
				}

			}

			return FacadeUtils.createListItems(cs00035Item, listItemIfo);

		} else {
			String[][] cs00035Item = { { "IS00369", numberType, "0", "0" },
					{ "IS00370", numberType, String.valueOf(NotUseAtr.NOT_USE.value), "しない" },
					{ "IS00371", numberType, "0", "0" },
					{ "IS00372", numberType, String.valueOf(PaymentMethod.VACATION_OCCURRED.value), "休暇発生" } };
			return FacadeUtils.createListItems(cs00035Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00036(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		List<PersonInfoItemDefinition> itemdfLst = this.perInfoItemRepo.getItemsByCtgCdItemCdsCid("CS00036",
				Arrays.asList("IS00375", "IS00376", "IS00380", "IS00381"), AppContexts.user().companyId(),
				AppContexts.user().contractCode());

		if (!CollectionUtil.isEmpty(itemdfLst)) {
			String[][] cs00036Item = new String[4][4];
			for (PersonInfoItemDefinition c : itemdfLst) {
				if (c.getItemCode().v().equals("IS00375")) {
					String notUse = String.valueOf(NotUseAtr.NOT_USE.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : notUse;
					cs00036Item[0][0] = c.getItemCode().v();
					cs00036Item[0][1] = numberType;
					cs00036Item[0][2] = value;
					cs00036Item[0][3] = value.equals("0") == true ? "しない" : "する";
					break;
				}

				if (c.getItemCode().v().equals("IS00376")) {
					String PER_INFO_EVERY_YEAR = String.valueOf(UpperLimitSetting.PER_INFO_EVERY_YEAR.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : PER_INFO_EVERY_YEAR;
					cs00036Item[1][0] = c.getItemCode().v();
					cs00036Item[1][1] = numberType;
					cs00036Item[1][2] = value;
					if(value.equals("1")) {
						cs00036Item[1][3] = "家族情報を参照";
					}else if(value.equals("2")) {
						cs00036Item[1][3] = "個人情報を参照（毎年利用）";
					}else {
						cs00036Item[1][3] = "個人情報を参照（本年度のみ利用）";
					}
					break;
				}

				if (c.getItemCode().v().equals("IS00380")) {
					String notUse = String.valueOf(NotUseAtr.NOT_USE.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : notUse;
					cs00036Item[2][0] = c.getItemCode().v();
					cs00036Item[2][1] = numberType;
					cs00036Item[2][2] = value;
					cs00036Item[2][3] = value.equals("0") == true ? "しない" : "する";
					break;
				}

				if (c.getItemCode().v().equals("IS00381")) {
					String PER_INFO_EVERY_YEAR = String.valueOf(UpperLimitSetting.PER_INFO_EVERY_YEAR.value);
					String value = c.getInitValue().isPresent() == true ? c.getInitValue().get().v() : PER_INFO_EVERY_YEAR;
					cs00036Item[1][0] = c.getItemCode().v();
					cs00036Item[1][1] = numberType;
					cs00036Item[1][2] = value;
					if(value.equals("1")) {
						cs00036Item[1][3] = "家族情報を参照";
					}else if(value.equals("2")) {
						cs00036Item[1][3] = "個人情報を参照（毎年利用）";
					}else {
						cs00036Item[1][3] = "個人情報を参照（本年度のみ利用）";
					}
					break;
				}
			}

			return FacadeUtils.createListItems(cs00036Item, listItemIfo);

		} else {
			String[][] cs00036Item = { { "IS00375", numberType, String.valueOf(NotUseAtr.NOT_USE.value), "しない" },
					{ "IS00376", numberType, String.valueOf(UpperLimitSetting.PER_INFO_EVERY_YEAR.value), "家族情報を参照" },
					{ "IS00380", numberType, String.valueOf(NotUseAtr.NOT_USE.value), "しない" },
					{ "IS00381", numberType, String.valueOf(UpperLimitSetting.PER_INFO_EVERY_YEAR.value), "家族情報を参照" }};
			return FacadeUtils.createListItems(cs00036Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00037(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00037",
				"IS00387", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00037Item = {
					{ "IS00387", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00037Item, listItemIfo);
		} else {
			String[][] cs00037Item = { { "IS00387", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00037Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00038(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00038",
				"IS00400", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00037Item = {
					{ "IS00400", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00037Item, listItemIfo);
		} else {
			String[][] cs00038Item = { { "IS00400", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00038Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00039(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00039",
				"IS00411", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00039Item = {
					{ "IS00411", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00039Item, listItemIfo);
		} else {
			String[][] cs00039Item = { { "IS00411", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00039Item, listItemIfo);
		}

	}

	public List<ItemValue> getListDefaultCS00040(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00040",
				"IS00426", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00040Item = {
					{ "IS00426", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00040Item, listItemIfo);
		} else {
			String[][] cs00040Item = { { "IS00426", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00040Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00041(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00041",
				"IS00441", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00041Item = {
					{ "IS00441", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00041Item, listItemIfo);
		} else {
			String[][] cs00041Item = { { "IS00441", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00041Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00042(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00042",
				"IS00456", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00042Item = {
					{ "IS00456", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00042Item, listItemIfo);
		} else {
			String[][] cs00042Item = { { "IS00456", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00042Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00043(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00043",
				"IS00471", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00043Item = {
					{ "IS00471", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00043Item, listItemIfo);
		} else {
			String[][] cs00043Item = { { "IS00471", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00043Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00044(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00044",
				"IS00486", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00044Item = {
					{ "IS00486", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00044Item, listItemIfo);
		} else {
			String[][] cs00044Item = { { "IS00486", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00044Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00045(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00045",
				"IS00501", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00045Item = {
					{ "IS00501", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00045Item, listItemIfo);
		} else {
			String[][] cs00045Item = { { "IS00501", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00045Item, listItemIfo);
		}
	}


	public List<ItemValue> getListDefaultCS00046(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00046",
				"IS00516", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00046Item = {
					{ "IS00516", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00046Item, listItemIfo);
		} else {
			String[][] cs00046Item = { { "IS00516", numberType, AVAILABLE,"使用可能" }};
			return FacadeUtils.createListItems(cs00046Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00047(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00047",
				"IS00531", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00047Item = {
					{ "IS00531", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00047Item, listItemIfo);
		} else {
			String[][] cs00047Item = { { "IS00531", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00047Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00048(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00048",
				"IS00546", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String AVAILABLE = String.valueOf(LeaveExpirationStatus.AVAILABLE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): AVAILABLE;
			String[][] cs00048Item = {
					{ "IS00546", numberType, value, value.equals("1") == true? "使用可能": " 期限切れ"}};
			return FacadeUtils.createListItems(cs00048Item, listItemIfo);
		} else {
			String[][] cs00048Item = { { "IS00546", numberType, AVAILABLE, "使用可能" }};
			return FacadeUtils.createListItems(cs00048Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00049(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00049",
				"IS00560", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {

			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00049Item = {
					{ "IS00560", numberType, value, value.equals("0") == true? "しない": "する" }};
			return FacadeUtils.createListItems(cs00049Item, listItemIfo);
		} else {
			String[][] cs00049Item = { { "IS00560", numberType, NOT_USE, "しない" } };
			return FacadeUtils.createListItems(cs00049Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00050(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00050",
				"IS00567", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00050Item = {
					{ "IS00567", numberType, value, value.equals("0") == true? "しない": "する" } };
			return FacadeUtils.createListItems(cs00050Item, listItemIfo);
		} else {
			String[][] cs00050Item = { { "IS00567", numberType, NOT_USE, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00050Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00051(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00051",
				"IS00574", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00051Item = {
					{ "IS00574",  numberType, value, value.equals("0") == true? "しない": "する" } };
			return FacadeUtils.createListItems(cs00051Item, listItemIfo);
		} else {
			String[][] cs00051Item = { { "IS00574", numberType, NOT_USE, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00051Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00052(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00052",
				"IS00581", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00052Item = {
					{ "IS00581", numberType, value, value.equals("0") == true? "しない": "する"} };
			return FacadeUtils.createListItems(cs00052Item, listItemIfo);
		} else {
			String[][] cs00052Item = { { "IS00581", numberType, NOT_USE, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00052Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00053(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00053",
				"IS00588", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00053Item = {
					{ "IS00588", numberType, value, value.equals("0") == true? "しない": "する"} };
			return FacadeUtils.createListItems(cs00053Item, listItemIfo);
		} else {
			String[][] cs00053Item = { { "IS00588", numberType, NOT_USE, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00053Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00054(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00054",
				"IS00595", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00054Item = {
					{ "IS00595", numberType, value, value.equals("0") == true? "しない": "する"} };
			return FacadeUtils.createListItems(cs00054Item, listItemIfo);
		} else {
			String[][] cs00054Item = { { "IS00595", numberType, NOT_USE, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00054Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00055(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00055",
				"IS00602", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00055Item = {
					{ "IS00602", numberType, value, value.equals("0") == true? "しない": "する"} };
			return FacadeUtils.createListItems(cs00055Item, listItemIfo);
		} else {
			String[][] cs00055Item = { { "IS00602", numberType, NOT_USE, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00055Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00056(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00056",
				"IS00609", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00056Item = {
					{ "IS00609", numberType, value, value.equals("0") == true? "しない": "する"} };
			return FacadeUtils.createListItems(cs00056Item, listItemIfo);
		} else {
			String[][] cs00056Item = { { "IS00609", numberType,
					String.valueOf(UseAtr.NOT_USE.value), TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00056Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00057(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00057",
				"IS00616", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00057Item = {
					{ "IS00616",  numberType, value, value.equals("0") == true? "しない": "する"} };
			return FacadeUtils.createListItems(cs00057Item, listItemIfo);
		} else {
			String[][] cs00057Item = { { "IS00616", numberType, NOT_USE, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00057Item, listItemIfo);
		}
	}

	public List<ItemValue> getListDefaultCS00058(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		Optional<PersonInfoItemDefinition> itemdfOpt = this.perInfoItemRepo.getPerInfoItemDefByCtgCdItemCdCid("CS00058",
				"IS00623", AppContexts.user().companyId(), AppContexts.user().contractCode());
		String NOT_USE = String.valueOf(UseAtr.NOT_USE.value);
		if (itemdfOpt.isPresent()) {
			Optional<InitValue> initValueOpt = itemdfOpt.get().getInitValue();
			String value = initValueOpt.isPresent() == true? initValueOpt.get().v(): NOT_USE;
			String[][] cs00058Item = {
					{ "IS00623", numberType, value, value.equals("0") == true? "しない": "する"} };
			return FacadeUtils.createListItems(cs00058Item, listItemIfo);
		} else {
			String[][] cs00058Item = { { "IS00623", numberType, NOT_USE, TextResource.localize("CPS001_100") } };
			return FacadeUtils.createListItems(cs00058Item, listItemIfo);
		}
	}

	/**
	 * Create item
	 * @param itemCode
	 * @param type
	 * @param itemValue
	 * @return
	 */
	public static ItemValue createItem(String itemCode, int type, String itemValue,String contentValue, List<ItemBasicInfo> listItemIfo ){

		Optional<ItemBasicInfo> itemInfo = listItemIfo.stream().filter(i -> itemCode.equals(i.getItemCode()))
				.findFirst();

		return new ItemValue(itemInfo.map(i -> i.getItemId()).orElse(null), itemCode,
				itemInfo.map(i -> i.getItemName()).orElse(null), itemValue, contentValue, null, null, type, type);
	}

	/**
	 * Create list items
	 * @param listItem
	 * @return
	 */
	public static List<ItemValue> createListItems(String[][] listItem, List<ItemBasicInfo> listItemIfo){
		List<ItemValue> listItemResult = new ArrayList<>();
		for (int i = 0; i < listItem.length;i++){
			ItemValue item= FacadeUtils.createItem(listItem[i][0], Integer.parseInt(listItem[i][1]), listItem[i][2],listItem[i][3], listItemIfo);
			listItemResult.add(item);
		}
		return listItemResult;
	}
	public List<ItemValue> getListDefaultCS00092(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.NUMERIC.value);
		List<String> itemEnum = Arrays.asList("IS01019");
		List<ComboBoxObject> comboxs = this.combox.getComboBox(ReferenceTypes.ENUM, "E00042",
				GeneralDate.today(), AppContexts.user().employeeId(), null, true,
				PersonEmployeeType.EMPLOYEE, true, "CS00092", GeneralDate.today(),  false);
		List<ItemValue>  result = new ArrayList<>();
		if (!CollectionUtil.isEmpty(comboxs)) {
			listItemIfo.stream().forEach(c ->{
				if(itemEnum.contains(c.getItemCode())) {
					String[][] cs00092Item = {
							{ c.getItemCode(), numberType, comboxs.get(0).getOptionValue(), comboxs.get(0).getOptionValue() } };
					result.addAll(FacadeUtils.createListItems(cs00092Item, listItemIfo));
				}
			});
		}
		return result;
	}

	public List<ItemValue> getListDefaultCS00075(List<ItemBasicInfo> listItemIfo) {
		String numberType = String.valueOf(ItemValueType.STRING.value);
		List<String> itemEnum = Arrays.asList("IS00790");
		List<ComboBoxObject> comboxs =socialInsuranceOfficeRepository.findByCid(AppContexts.user().companyId())
				.stream()
				.map(x -> new ComboBoxObject(x.getCode().v(), x.getCode().v() + "　" + x.getName())).collect(Collectors.toList());
		List<ItemValue> result = new ArrayList<>();
		if (!CollectionUtil.isEmpty(comboxs)) {
			listItemIfo.stream().forEach(c ->{
				if(itemEnum.contains(c.getItemCode())) {
					String[][] cs00075Item = {
							{ c.getItemCode(), numberType, comboxs.get(0).getOptionValue(), comboxs.get(0).getOptionText() } };
					result.addAll(FacadeUtils.createListItems(cs00075Item, listItemIfo));
				}
			});
		}
		return result;
	}
	/**
	 * dùng cho cps001. cps002
	 * Get list Default item exclude item in screen
	 * @param listCategoryCode
	 * @param listItemCodeInScreen
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public List<ItemValue> getListDefaultItem(String categoryCode,
			List<String> listItemCodeInScreen, String sid, List<ItemBasicInfo> listItemIfo) {

		List<ItemValue> listItemResult = new ArrayList<>();
		try {
			Method method = FacadeUtils.class.getMethod(FUNCTION_NAME + categoryCode, List.class);
			@SuppressWarnings("unchecked")
			List<ItemValue> value = (List<ItemValue>) method.invoke(this,listItemIfo);
			listItemResult.addAll(value);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

		listItemResult.addAll(processHistoryPeriod(categoryCode,listItemCodeInScreen,sid, listItemIfo));

		return listItemResult.stream().filter(i-> !listItemCodeInScreen.contains(i.itemCode())).collect(Collectors.toList());
	}

	/**
	 * dùng cho cps003
	 * Map<String, String> employees : pid - sid
	 * Get list Default item exclude item in screen
	 * @param listCategoryCode
	 * @param listItemCodeInScreen
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Map<String, List<ItemValue>> getListDefaultItem(String categoryCode, List<String> listItemCodeInScreen,
			List<ItemBasicInfo> listItemIfo, Map<String, String> employees) {

		Map<String, List<ItemValue>> itemsBySid = new HashMap<>();
		Map<String, List<ItemValue>> result = new HashMap<>();
		try {
			Method method = FacadeUtils.class.getMethod(FUNCTION_NAME + categoryCode, List.class);
			@SuppressWarnings("unchecked")
			List<ItemValue> value = (List<ItemValue>) method.invoke(this, listItemIfo);
			employees.forEach((k, v) -> {
				itemsBySid.put(v, value);
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		itemsBySid.putAll(processHistoryPeriod(categoryCode, listItemCodeInScreen, employees, listItemIfo));

		itemsBySid.forEach((k, v) -> {
			result.put(k,
					v.stream().filter(i -> !listItemCodeInScreen.contains(i.itemCode())).collect(Collectors.toList()));
		});

		return result;
	}

	/**
	 * Get history item for CPS007
	 * @param categoryCode
	 * @return
	 */
	private List<String> getListHistoryItem(String categoryCode){
		List<String> result = new ArrayList<>();
		if (historyCategoryCodeList.contains(categoryCode)) {
			result.add(startDateItemCodes.get(categoryCode));
			result.add(endDateItemCodes.get(categoryCode));
		}
		return result;
	}

	/**
	 * Set default item for history category
	 * @param categoryCode
	 * @param listItemCodeInScreen
	 * @param sid
	 * @return
	 */
	public List<ItemValue> processHistoryPeriod(String categoryCode,List<String> listItemCodeInScreen, String sid, List<ItemBasicInfo> listItemIfo) {
		List<ItemValue> listItemResult = new ArrayList<>();
		int dataType = DataTypeValue.DATE.value;

		Optional<GeneralDate> hireDate = getHireDate(sid);

		if (!hireDate.isPresent()){
			return listItemResult;
		}

		if (historyCategoryCodeList.contains(categoryCode)) {
			String startDateItemCode = startDateItemCodes.get(categoryCode);
			String endDateItemCode = endDateItemCodes.get(categoryCode);

			if (!listItemCodeInScreen.stream().anyMatch(item -> item.equals(startDateItemCode))) {
				listItemResult
						.add(createItem(startDateItemCode, dataType, hireDate.get().toString(),hireDate.get().toString(), listItemIfo));
			}
			if (!listItemCodeInScreen.stream().anyMatch(item -> item.equals(endDateItemCode))) {
				listItemResult
						.add(createItem(endDateItemCode, dataType, GeneralDate.max().toString(),GeneralDate.max().toString(), listItemIfo));
			}

		}
		return listItemResult;
	}

	/**
	 * Get hire date
	 * @param sid
	 * @return
	 */
	public Optional<GeneralDate> getHireDate(String sid){
		AffCompanyHist affcom = affCompanyHistRepository.getAffCompanyHistoryOfEmployee(sid);
		AffCompanyHistByEmployee hist = affcom.getAffCompanyHistByEmployee(sid);
		if (hist.getHistory().isPresent()){
			return Optional.of(hist.getHistory().get().start());
		}
		return Optional.empty();
	}

	/**
	 * dùng cho màn cps003, tối ưu time
	 * Map<String, String> employees Map<pid, sid>
	 * Set default item for history category
	 * @param categoryCode
	 * @param listItemCodeInScreen
	 * @param sid
	 * @return
	 */
	public Map<String, List<ItemValue>> processHistoryPeriod(String categoryCode, List<String> listItemCodeInScreen,
			Map<String, String> employees, List<ItemBasicInfo> listItemIfo) {

		Map<String, List<ItemValue>> result = new HashMap<>();

		int dataType = DataTypeValue.DATE.value;

		Map<String, Optional<GeneralDate>> hireDate = getHireDate(employees);

		hireDate.forEach((k, v) -> {
			if (!v.isPresent()) {
				result.put(k, new ArrayList<>());
			} else if (historyCategoryCodeList.contains(categoryCode)) {
				String startDateItemCode = startDateItemCodes.get(categoryCode);
				String endDateItemCode = endDateItemCodes.get(categoryCode);

				if (!listItemCodeInScreen.stream().anyMatch(item -> item.equals(startDateItemCode))) {
					result.put(k, Arrays.asList(createItem(startDateItemCode, dataType, v.get().toString(),
							v.get().toString(), listItemIfo)));
				}
				if (!listItemCodeInScreen.stream().anyMatch(item -> item.equals(endDateItemCode))) {
					result.put(k, Arrays.asList(createItem(endDateItemCode, dataType, GeneralDate.max().toString(),
							GeneralDate.max().toString(), listItemIfo)));
				}

			}
		});
		return result;
	}


	/**
	 * dùng cho màn cps003
	 * Get hire date
	 * @param Map<pid, sid>
	 * @return Map<sid, Optional<GeneralDate>>
	 */
	public Map<String, Optional<GeneralDate>> getHireDate(Map<String, String> employees) {
		Map<String, Optional<GeneralDate>> result = new HashMap<>();
		List<AffCompanyHist> affcomLst = affCompanyHistRepository
				.getAffCompanyHistoryOfEmployees(new ArrayList<>(employees.values()));
		employees.forEach((k, v) -> {
			Optional<AffCompanyHist> affcomOpt = affcomLst.stream().filter(c -> c.getPId().equals(k)).findFirst();
			if (affcomOpt.isPresent()) {
				AffCompanyHistByEmployee hist = affcomOpt.get().getAffCompanyHistByEmployee(v);
				if (hist.getHistory().isPresent()) {
					result.put(v, Optional.of(hist.getHistory().get().start()));
				}
			} else {
				result.put(v, Optional.empty());
			}

		});
		return result;
	}

	/**
	 * Get list Default item for CPS007
	 * @param listCategoryCode
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public List<String> getListDefaultItem(List<String> listCategoryCode) {

		List<String> listItemResult = new ArrayList<>();
		listCategoryCode.forEach(category -> {
			try {
				Method method = FacadeUtils.class.getMethod(FUNCTION_NAME + category, List.class);
				@SuppressWarnings("unchecked")
				List<ItemValue> value = (List<ItemValue>) method.invoke(new FacadeUtils(), new ArrayList<ItemBasicInfo>());
				listItemResult.addAll(value.stream().map(i->i.itemCode()).collect(Collectors.toList()));
			} catch (Exception e){
				System.out.println(e.getMessage());
			}

			listItemResult.addAll(getListHistoryItem(category));
		});
		return listItemResult;
	}

}
