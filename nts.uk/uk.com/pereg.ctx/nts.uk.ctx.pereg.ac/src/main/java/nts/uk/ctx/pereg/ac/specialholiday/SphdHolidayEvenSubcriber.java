package nts.uk.ctx.pereg.ac.specialholiday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NResources;
import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.uk.ctx.at.shared.dom.specialholiday.event.SpecialHolidayDomainEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryName;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SphdHolidayEvenSubcriber implements DomainEventSubscriber<SpecialHolidayDomainEvent> {

	@Inject
	private PerInfoCategoryRepositoty ctgRepo;

	@Inject
	private PerInfoItemDefRepositoty itemRepo;

	@Inject
	private I18NResources resources;

	@Override
	public Class<SpecialHolidayDomainEvent> subscribedToEventType() {
		return SpecialHolidayDomainEvent.class;
	}

	private static final List<String> lstCtgCd1 = Arrays.asList(new String[] { "CS00025", "CS00026", "CS00027",
			"CS00028", "CS00029", "CS00030", "CS00031", "CS00032", "CS00033", "CS00034", "CS00049", "CS00050",
			"CS00051", "CS00052", "CS00053", "CS00054", "CS00055", "CS00056", "CS00057", "CS00058" });
	private static final List<String> lstCtgCd2 = Arrays.asList(new String[] { "CS00039", "CS00040", "CS00041",
			"CS00042", "CS00043", "CS00044", "CS00045", "CS00046", "CS00047", "CS00048", "CS00059", "CS00060",
			"CS00061", "CS00062", "CS00063", "CS00064", "CS00065", "CS00066", "CS00067", "CS00068" });

	@Override
	public void handle(SpecialHolidayDomainEvent domainEvent) {
		int spcHdCode = domainEvent.getSpecialHolidayCode().v();
		List<String> ctgCds = getCtgCds(spcHdCode);
		String loginCompanyId = AppContexts.user().companyId();
		/**
		 * アルゴリズム「個人情報カテゴリを取得する」を実行する get List<PersonInfoCategory> by CategoryCodes and
		 * login CompanyId
		 */
		List<PersonInfoCategory> ctgLst = ctgRepo.getPerCtgByListCtgCd(ctgCds, loginCompanyId);

		if (ctgLst.size() > 0) {
			updateCtg(domainEvent, ctgLst, loginCompanyId, ctgCds);
		}
	}

	private void updateCtg(SpecialHolidayDomainEvent domainEvent, List<PersonInfoCategory> ctgLst, String loginCompanyId,
			List<String> ctgCds) {
		List<PersonInfoCategory> ctgUpdateList = new ArrayList<>();
		List<PersonInfoItemDefinition> updateItems = new ArrayList<>();
		String contractCd = AppContexts.user().contractCode();

		if (domainEvent.isUse()) {
			/**
			 * 【更新内容】 廃止区分 ＝ 廃止しない カテゴリ名称 ＝ パラメータ．特別休暇名称 + #CPS001_133（○○情報） or カテゴリ名称 ＝
			 * パラメータ．特別休暇名称 + #CPS001_134（○○付与残数）
			 */
			for (PersonInfoCategory x : ctgLst) {
				String domainEventName = domainEvent.getSpecialHolidayName() == null ? ""
						: domainEvent.getSpecialHolidayName().v();
				String name = "";

				if (lstCtgCd1.contains(x.getCategoryCode().v())) {
					name = domainEventName + resources.localize("CPS001_133").get();
				} else if (lstCtgCd2.contains(x.getCategoryCode().v())) {
					name = domainEventName + resources.localize("CPS001_134").get();
				}

				x.setDomainNameAndAbolition(new CategoryName(name), 0);
				ctgUpdateList.add(x);
				updateItems.addAll(
						getUpdateItems(domainEventName, x.getCategoryCode().v(), contractCd, true, domainEvent.getTypeTime() ,loginCompanyId));
			}
		} else {
			/**
			 * 共通アルゴリズム「契約内ゼロ会社の会社IDを取得する」を実行する
			 */
			String updateCompanyId = AppContexts.user().zeroCompanyIdInContract();

			/**
			 * アルゴリズム「個人情報カテゴリを取得する」を実行する get List<PersonInfoCategory> by CategoryCodes and
			 * zero CompanyId
			 */
			List<PersonInfoCategory> ctgLstComZero = ctgRepo.getPerCtgByListCtgCd(ctgCds, updateCompanyId);

			/**
			 * 【更新内容】 廃止区分 ＝ 廃止する カテゴリ名称 ＝ 取得したゼロ会社の「個人情報カテゴリ」．カテゴリ名
			 * 
			 */
			for (PersonInfoCategory x : ctgLst) {
				PersonInfoCategory ctgInComZero = ctgLstComZero.stream().filter(c -> {
					return c.getCategoryCode().v().equals(x.getCategoryCode().v());
				}).collect(Collectors.toList()).get(0);

				x.setDomainNameAndAbolition(ctgInComZero.getCategoryName(), 1);
				ctgUpdateList.add(x);

				String domainEventName = domainEvent.getSpecialHolidayName() == null ? ""
						: domainEvent.getSpecialHolidayName().v();

				updateItems.addAll(getUpdateItems(domainEventName, x.getCategoryCode().v(), contractCd, false, domainEvent.getTypeTime()
						, loginCompanyId, updateCompanyId));
			}

		}
		/**
		 * アルゴリズム「個人情報カテゴリを更新する」を実行する update PersonInfoCategory
		 */
		ctgRepo.updateAbolition(ctgUpdateList, loginCompanyId);
		/**
		 * アルゴリズム「個人情報項目定義を更新する」を実行する update PersonInfoItemDefinition
		 */
		itemRepo.updateItemDefNameAndAbolition(updateItems, loginCompanyId);
	}

	private List<PersonInfoItemDefinition> getUpdateItems(String spHDName, String ctgId, String contractCode,
			boolean isEffective, TypeTime typeTime, String... companyId) {
		return itemRepo.getItemDefByCtgCdAndComId(ctgId, companyId[0]).stream().filter(f -> {
			String itemCode = f.getItemCode().v();
			Optional<String> newItemName = getNewItemName(itemCode, spHDName);

			if (newItemName.isPresent()) {
				if (isEffective) {
					/**
					 * 【更新内容】 廃止区分 ＝ 廃止しない 項目名称 ＝
					 */
					f.setItemName(newItemName.get());
					f.setIsAbolition(getAbolition(itemCode, typeTime));
				} else {
					/**
					 * 【更新内容】 廃止区分 ＝ 廃止する 項目名 ＝ 取得したゼロ会社の「個人情報項目定義」．項目名
					 */
					Map<String, String> mapItemNameInZeroCom = itemRepo.getItemDefByCtgCdAndComId(ctgId, companyId[1])
							.stream().collect(Collectors.toMap(x -> x.getItemCode().v(), x -> x.getItemName().v()));

					if (mapItemNameInZeroCom.containsKey(itemCode)) {
						f.setItemName(mapItemNameInZeroCom.get(itemCode));
					}
				}

				return true;
			} else {
				return false;
			}
		}).collect(Collectors.toList());
	}

	private List<String> getCtgCds(int spcHdCode) {
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, Arrays.asList(new String[] { "CS00025", "CS00039" }));
				put(2, Arrays.asList(new String[] { "CS00026", "CS00040" }));
				put(3, Arrays.asList(new String[] { "CS00027", "CS00041" }));
				put(4, Arrays.asList(new String[] { "CS00028", "CS00042" }));
				put(5, Arrays.asList(new String[] { "CS00029", "CS00043" }));
				put(6, Arrays.asList(new String[] { "CS00030", "CS00044" }));
				put(7, Arrays.asList(new String[] { "CS00031", "CS00045" }));
				put(8, Arrays.asList(new String[] { "CS00032", "CS00046" }));
				put(9, Arrays.asList(new String[] { "CS00033", "CS00047" }));
				put(10, Arrays.asList(new String[] { "CS00034", "CS00048" }));
				put(11, Arrays.asList(new String[] { "CS00049", "CS00059" }));
				put(12, Arrays.asList(new String[] { "CS00050", "CS00060" }));
				put(13, Arrays.asList(new String[] { "CS00051", "CS00061" }));
				put(14, Arrays.asList(new String[] { "CS00052", "CS00062" }));
				put(15, Arrays.asList(new String[] { "CS00053", "CS00063" }));
				put(16, Arrays.asList(new String[] { "CS00054", "CS00064" }));
				put(17, Arrays.asList(new String[] { "CS00055", "CS00065" }));
				put(18, Arrays.asList(new String[] { "CS00056", "CS00066" }));
				put(19, Arrays.asList(new String[] { "CS00057", "CS00067" }));
				put(20, Arrays.asList(new String[] { "CS00058", "CS00068" }));
			}
		};

		return map.get(spcHdCode);
	}
	
	private IsAbolition getAbolition(String itemCode, TypeTime typeTime) {
		if (mapICdFull.containsKey(itemCode)) {
			int key = mapICdFull.get(itemCode);
			
			switch (key) {
				case 4:
					return typeTime == TypeTime.GRANT_START_DATE_SPECIFY ? IsAbolition.NOT_ABOLITION : IsAbolition.ABOLITION;
				case 5:
					return typeTime == TypeTime.GRANT_START_DATE_SPECIFY ? IsAbolition.ABOLITION : IsAbolition.NOT_ABOLITION;
				default:
					return IsAbolition.NOT_ABOLITION;
			}
		}

		return IsAbolition.NOT_ABOLITION;
	}

	private Optional<String> getNewItemName(String itemCode, String name) {
		Map<Integer, String> mapText = new HashMap<Integer, String>() {
			private static final long serialVersionUID = 1L;
			{
				put(1, name + resources.localize("CPS001_139").get());
				put(2, name + resources.localize("CPS001_140").get());
				put(3, name + resources.localize("CPS001_141").get());
				put(4, name + resources.localize("CPS001_142").get());
				put(5, name + resources.localize("CPS001_143").get());
				put(6, name + resources.localize("CPS001_144").get());
				put(7, name + resources.localize("CPS001_145").get());
				put(8, name + resources.localize("CPS001_135").get());
				put(9, name + resources.localize("CPS001_136").get());
				put(10, name + resources.localize("CPS001_137").get());
				put(11, name + resources.localize("CPS001_138").get());

			}
		};

		if (mapICdFull.containsKey(itemCode)) {
			int key = mapICdFull.get(itemCode);
			return Optional.of(mapText.get(key));
		}

		return Optional.empty();
	}

	private static final Map<String, Integer> mapICdFull = new HashMap<String, Integer>() {
		private static final long serialVersionUID = -1247243373180819620L;
		{
			// 特別休暇コード＝01の場
			put("IS00295", 1);
			put("IS00296", 2);
			put("IS00297", 3);
			put("IS00298", 4);
			put("IS00299", 5);
			put("IS00300", 6);
			put("IS00301", 7);
			put("IS00409", 8);
			put("IS00410", 9);
			put("IS00411", 10);
			put("IS00412", 11);

			// 特別休暇コード＝02の場
			put("IS00302", 1);
			put("IS00303", 2);
			put("IS00304", 3);
			put("IS00305", 4);
			put("IS00306", 5);
			put("IS00307", 6);
			put("IS00308", 7);
			put("IS00424", 8);
			put("IS00425", 9);
			put("IS00426", 10);
			put("IS00427", 11);

			// 特別休暇コード＝03の場
			put("IS00309", 1);
			put("IS00310", 2);
			put("IS00311", 3);
			put("IS00312", 4);
			put("IS00313", 5);
			put("IS00314", 6);
			put("IS00315", 7);
			put("IS00439", 8);
			put("IS00440", 9);
			put("IS00441", 10);
			put("IS00442", 11);

			// 特別休暇コード＝04の場
			put("IS00316", 1);
			put("IS00317", 2);
			put("IS00318", 3);
			put("IS00319", 4);
			put("IS00320", 5);
			put("IS00321", 6);
			put("IS00322", 7);
			put("IS00454", 8);
			put("IS00455", 9);
			put("IS00456", 10);
			put("IS00457", 11);

			// 特別休暇コード＝05の場
			put("IS00323", 1);
			put("IS00324", 2);
			put("IS00325", 3);
			put("IS00326", 4);
			put("IS00327", 5);
			put("IS00328", 6);
			put("IS00329", 7);
			put("IS00469", 8);
			put("IS00470", 9);
			put("IS00471", 10);
			put("IS00472", 11);

			// 特別休暇コード＝06の場
			put("IS00330", 1);
			put("IS00331", 2);
			put("IS00332", 3);
			put("IS00333", 4);
			put("IS00334", 5);
			put("IS00335", 6);
			put("IS00336", 7);
			put("IS00484", 8);
			put("IS00485", 9);
			put("IS00486", 10);
			put("IS00487", 11);

			// 特別休暇コード＝07の場
			put("IS00337", 1);
			put("IS00338", 2);
			put("IS00339", 3);
			put("IS00340", 4);
			put("IS00341", 5);
			put("IS00342", 6);
			put("IS00343", 7);
			put("IS00499", 8);
			put("IS00500", 9);
			put("IS00501", 10);
			put("IS00502", 11);

			// 特別休暇コード＝08の場
			put("IS00344", 1);
			put("IS00345", 2);
			put("IS00346", 3);
			put("IS00347", 4);
			put("IS00348", 5);
			put("IS00349", 6);
			put("IS00350", 7);
			put("IS00514", 8);
			put("IS00515", 9);
			put("IS00516", 10);
			put("IS00517", 11);

			// 特別休暇コード＝09の場
			put("IS00351", 1);
			put("IS00352", 2);
			put("IS00353", 3);
			put("IS00354", 4);
			put("IS00355", 5);
			put("IS00356", 6);
			put("IS00357", 7);
			put("IS00529", 8);
			put("IS00530", 9);
			put("IS00531", 10);
			put("IS00532", 11);

			// 特別休暇コード＝10の場
			put("IS00358", 1);
			put("IS00359", 2);
			put("IS00360", 3);
			put("IS00361", 4);
			put("IS00362", 5);
			put("IS00363", 6);
			put("IS00364", 7);
			put("IS00544", 8);
			put("IS00545", 9);
			put("IS00546", 10);
			put("IS00547", 11);

			// 特別休暇コード＝11の場
			put("IS00559", 1);
			put("IS00560", 2);
			put("IS00561", 3);
			put("IS00562", 4);
			put("IS00563", 5);
			put("IS00564", 6);
			put("IS00565", 7);
			put("IS00629", 8);
			put("IS00630", 9);
			put("IS00631", 10);
			put("IS00632", 11);

			// 特別休暇コード＝12の場
			put("IS00566", 1);
			put("IS00567", 2);
			put("IS00568", 3);
			put("IS00569", 4);
			put("IS00570", 5);
			put("IS00571", 6);
			put("IS00572", 7);
			put("IS00644", 8);
			put("IS00645", 9);
			put("IS00646", 10);
			put("IS00647", 11);

			// 特別休暇コード＝13の場
			put("IS00573", 1);
			put("IS00574", 2);
			put("IS00575", 3);
			put("IS00576", 4);
			put("IS00577", 5);
			put("IS00578", 6);
			put("IS00579", 7);
			put("IS00659", 8);
			put("IS00660", 9);
			put("IS00661", 10);
			put("IS00662", 11);

			// 特別休暇コード＝14の場
			put("IS00580", 1);
			put("IS00581", 2);
			put("IS00582", 3);
			put("IS00583", 4);
			put("IS00584", 5);
			put("IS00585", 6);
			put("IS00586", 7);
			put("IS00674", 8);
			put("IS00675", 9);
			put("IS00676", 10);
			put("IS00677", 11);

			// 特別休暇コード＝15の場
			put("IS00587", 1);
			put("IS00588", 2);
			put("IS00589", 3);
			put("IS00590", 4);
			put("IS00591", 5);
			put("IS00592", 6);
			put("IS00593", 7);
			put("IS00689", 8);
			put("IS00690", 9);
			put("IS00691", 10);
			put("IS00692", 11);

			// 特別休暇コード＝16の場
			put("IS00594", 1);
			put("IS00595", 2);
			put("IS00596", 3);
			put("IS00597", 4);
			put("IS00598", 5);
			put("IS00599", 6);
			put("IS00600", 7);
			put("IS00704", 8);
			put("IS00705", 9);
			put("IS00706", 10);
			put("IS00707", 11);

			// 特別休暇コード＝17の場
			put("IS00601", 1);
			put("IS00602", 2);
			put("IS00603", 3);
			put("IS00604", 4);
			put("IS00605", 5);
			put("IS00606", 6);
			put("IS00607", 7);
			put("IS00719", 8);
			put("IS00720", 9);
			put("IS00721", 10);
			put("IS00722", 11);

			// 特別休暇コード＝18の場
			put("IS00608", 1);
			put("IS00609", 2);
			put("IS00610", 3);
			put("IS00611", 4);
			put("IS00612", 5);
			put("IS00613", 6);
			put("IS00614", 7);
			put("IS00734", 8);
			put("IS00735", 9);
			put("IS00736", 10);
			put("IS00737", 11);

			// 特別休暇コード＝19の場
			put("IS00615", 1);
			put("IS00616", 2);
			put("IS00617", 3);
			put("IS00618", 4);
			put("IS00619", 5);
			put("IS00620", 6);
			put("IS00621", 7);
			put("IS00749", 8);
			put("IS00750", 9);
			put("IS00751", 10);
			put("IS00752", 11);

			// 特別休暇コード＝20の場
			put("IS00622", 1);
			put("IS00623", 2);
			put("IS00624", 3);
			put("IS00625", 4);
			put("IS00626", 5);
			put("IS00627", 6);
			put("IS00628", 7);
			put("IS00764", 8);
			put("IS00765", 9);
			put("IS00766", 10);
			put("IS00767", 11);
		}
	};
}
