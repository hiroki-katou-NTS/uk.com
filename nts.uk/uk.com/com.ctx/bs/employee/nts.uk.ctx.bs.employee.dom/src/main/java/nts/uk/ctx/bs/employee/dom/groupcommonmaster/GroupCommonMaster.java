package nts.uk.ctx.bs.employee.dom.groupcommonmaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.employeelicense.ContractCode;

/**
 * グループ会社共通マスタ
 * 
 * @author sonnlb
 *
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@Stateless
public class GroupCommonMaster extends AggregateRoot {

	// 契約コード
	private ContractCode contractCode;

	// 共通マスタID
	private String commonMasterId;

	// 共通マスタコード
	private CommonMasterCode commonMasterCode;

	// 共通マスタ名
	private CommonMasterName commonMasterName;

	// 備考
	private String commonMasterMemo;

	// 共通マスタ項目

	private List<CommonMasterItem> commonMasterItems;

	private GroupCommonMasterRepository groupMasterRepo;

	public GroupCommonMaster(ContractCode contractCode, String commonMasterId, CommonMasterCode commonMasterCode,
			CommonMasterName commonMasterName, String commonMasterMemo) {
	}

	/**
	 * グループ会社共通マスタの取得
	 * 
	 * @param 契約コード
	 * 
	 * @return 共通マスタリスト
	 */
	public List<GroupCommonMaster> getGroupCommonMaster(String contractCode) {

		// ドメインモデル [グループ会社共通マスタ] を取得する

		List<GroupCommonMaster> ListMaster = this.groupMasterRepo.getByContractCode(contractCode);

		if (!CollectionUtil.isEmpty(ListMaster)) {
			return ListMaster;
		}

		// アルゴリズム [グループ会社共通マスタの追加] を実行する
		List<GroupCommonMaster> newListMaster = new ArrayList<GroupCommonMaster>();

		newListMaster.add(new GroupCommonMaster(new ContractCode(contractCode), "M000011", new CommonMasterCode("04"),
				new CommonMasterName("地域"), "部門マスタ、職場マスタ"));

		newListMaster.add(new GroupCommonMaster(new ContractCode(contractCode), "M000031", new CommonMasterCode("01"),
				new CommonMasterName("雇用形態"), "雇用マスタ"));

		newListMaster.add(new GroupCommonMaster(new ContractCode(contractCode), "M000041", new CommonMasterCode("02"),
				new CommonMasterName("分類区分"), "分類マスタ1"));

		newListMaster.add(new GroupCommonMaster(new ContractCode(contractCode), "M000051", new CommonMasterCode("03"),
				new CommonMasterName("職位グループ"), "職位マスタ"));

		this.addGroupCommonMaster(contractCode, newListMaster);

		return newListMaster.stream().sorted(Comparator.comparing(GroupCommonMaster::getCommonMasterCode))
				.collect(Collectors.toList());
	}

	/**
	 * グループ会社共通マスタの追加
	 * 
	 * @param 契約コード
	 * @param 共通マスタリスト
	 * 
	 */
	public void addGroupCommonMaster(String contractCode, List<GroupCommonMaster> ListMaster) {

		ListMaster.forEach(master -> {
			master.setContractCode(new ContractCode(contractCode));
		});

		this.groupMasterRepo.addListGroupCommonMaster(ListMaster);
	}

	/**
	 * グループ会社共通マスタ項目の取得
	 * 
	 * @param 契約コード
	 * @param 共通マスタID
	 * 
	 * @return 共通マスタリスト
	 */
	public List<CommonMasterItem> getGroupCommonMasterItem(String contractCode, String commonMasterId) {

		// ドメインモデル [グループ会社共通マスタ] を取得する
		Optional<GroupCommonMaster> masterOpt = this.groupMasterRepo.getByContractCodeAndId(contractCode,
				commonMasterId);

		if (!masterOpt.isPresent()) {
			return Collections.emptyList();
		}

		return masterOpt.get().getCommonMasterItems();
	}

	/**
	 * グループ会社共通マスタ項目の使用設定の取得
	 * 
	 * @param 契約コード
	 * @param 共通マスタID
	 * @param 会社ID
	 * @return
	 * 
	 * @return 共通マスタリスト
	 */
	public List<String> getGroupCommonMasterUsage(String contractCode, String commonMasterId, String companyId) {

		// ドメインモデル [グループ会社共通マスタ] を取得する
		Optional<GroupCommonMaster> masterOpt = this.groupMasterRepo.getByContractCodeAndId(contractCode,
				commonMasterId);

		if (!masterOpt.isPresent()) {
			return Collections.emptyList();
		}

		if (CollectionUtil.isEmpty(masterOpt.get().getCommonMasterItems())) {
			return Collections.emptyList();
		}

		return masterOpt.get().getCommonMasterItems().stream()
				.filter(x -> isMathCompanyId(x.getNotUseCompanyList(), companyId)).collect(Collectors.toList()).stream()
				.map(x -> x.getCommonMasterItemId()).collect(Collectors.toList());
	}

	/**
	 * グループ会社共通マスタ項目の使用設定の更新
	 * 
	 * @param 契約コード
	 * @param 共通マスタID
	 * @param 会社ID
	 * @param 更新項目リスト
	 */
	public void updateGroupCommonMasterUsage(String contractCode, String commonMasterId, String companyId,
			List<String> masterItemIds) {
		// アルゴリズム [グループ会社共通マスタ項目の使用設定の取得] を実行する
		List<String> usageList = this.getGroupCommonMasterUsage(contractCode, commonMasterId, companyId);

		if (!CollectionUtil.isEmpty(usageList)) {
			// ドメインモデル [グループ会社共通マスタ] を削除する
			this.groupMasterRepo.removeGroupCommonMasterUsage(contractCode, commonMasterId, companyId, masterItemIds);
		}
		// ドメインモデル [グループ会社共通マスタ] を追加する
		this.groupMasterRepo.addGroupCommonMasterUsage(contractCode, commonMasterId, companyId, masterItemIds);

	}

	private boolean isMathCompanyId(List<NotUseCompany> notUseCompanyList, String companyId) {

		Optional<NotUseCompany> notUseCompanyOpt = notUseCompanyList.stream()
				.filter(x -> x.getCompanyId().equals(companyId)).findFirst();

		if (notUseCompanyOpt.isPresent()) {
			return true;
		}

		return false;
	}
}
