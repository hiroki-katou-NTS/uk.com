package nts.uk.ctx.bs.employee.app.find.groupcommonmaster;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMaster;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GroupCommonMasterFinder {

	/**
	 * 起動する
	 * 
	 * @return 共通マスタリスト(List)
	 */
	public List<GroupCommonMasterDto> getGroupCommonMaster() {

		// アルゴリズム [グループ会社共通マスタの取得] を実行する(Thực hiện thuật toán [Get group company
		// common master])
		String contractCode = AppContexts.user().contractCode();

		GroupCommonMaster master = new GroupCommonMaster();
		// 選択共通マスタオブジェクトを生成する(Tạo selection common master object)
		List<GroupCommonMasterDto> groupMasterDtos = master.getGroupCommonMaster(contractCode).stream()
				.map(x -> new GroupCommonMasterDto(x)).collect(Collectors.toList());

		if (CollectionUtil.isEmpty(groupMasterDtos)) {
			return groupMasterDtos;
		}

		return groupMasterDtos;

	}

	/**
	 * 共通マスタの選択処理
	 * 
	 * @param 共通マスタID
	 * @return 共通マスタリスト (List)
	 */
	public List<GroupCommonItemDto> getGroupCommonMasterItems(String commonMasterId) {

		String contractCode = AppContexts.user().contractCode();
		GroupCommonMaster master = new GroupCommonMaster();
		// アルゴリズム [グループ会社共通マスタ項目の取得] を実行する (Thực hiện thuật
		// toán"getGroupCommonMasterItem" )

		List<GroupCommonItemDto> groupItems = master.getGroupCommonMasterItem(contractCode, commonMasterId).stream()
				.map(x -> new GroupCommonItemDto(x)).sorted(Comparator.comparing(GroupCommonItemDto::getDisplayNumber))
				.collect(Collectors.toList());

		if (CollectionUtil.isEmpty(groupItems)) {
			return groupItems;
		}

		String companyId = AppContexts.user().companyId();
		// アルゴリズム [グループ会社共通マスタ項目の使用設定の取得] を実行する (Thực hiện thuật toán
		// "getGroupCommonMasterUsage")
		List<String> companyUsages = master.getGroupCommonMasterUsage(contractCode, commonMasterId, companyId);
		// 使用設定オブジェクトを更新する (Cập nhật Use settings object)
		groupItems.forEach(item -> {
			companyUsages.stream().filter(u -> u.equals(item.getCommonMasterItemId())).findFirst().ifPresent(u -> {
				item.setUseSetting(1);
			});
		});

		return groupItems;
	}

}
