package find.person.setting.selectionitem.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrder;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class SelectionFinder {

	@Inject
	private SelectionRepository selectionRepo;

	@Inject
	private SelectionItemOrderRepository selectionOrderRpo;

	// アルゴリズム「選択肢履歴選択時処理」を実行する(Thực thi xử lý chọn 選択肢履歴)
	public List<SelectionDto> getAllSelection() {
		String contractCode = AppContexts.user().contractCode();
		return this.selectionRepo.getAllHistorySelection(contractCode).stream()
				.map(i -> SelectionDto.fromDomainSelection(i)).collect(Collectors.toList());
	}

	// check history ID:
	public List<SelectionItemOrderDto> getHistIdSelection(String histId) {

		List<SelectionItemOrderDto> orderList = new ArrayList<SelectionItemOrderDto>();

		// lay selection
		List<Selection> selectionList = this.selectionRepo.getAllHistorySelection(histId);
		// kiem tra so luong item lay duoc
		if (selectionList.isEmpty()) {

			return orderList;
		} else {

			// xu ly neu item >0

			//String selectionId = selectionList.get(0).getSelectionID();
			
			String getByHisId = selectionList.get(0).getHistId();
			
			// step1 :lay thang OrderAndDefaultValuesOfOption va map no ve
			// SelectionItemOrderDto

			List<SelectionItemOrder> orderDomainlst = this.selectionOrderRpo.getAllOrderItemSelection(getByHisId);

			if (!orderDomainlst.isEmpty()) {
				orderList = orderDomainlst.stream().map(i -> {
					Selection selectionItem = selectionList.stream()
							.filter(s -> s.getSelectionID().equals(i.getSelectionID())).findFirst().orElse(null);
					
						return SelectionItemOrderDto.fromSelectionOrder(i, selectionItem);
					

				}).collect(Collectors.toList());
			}

			// step 2: tra ve list sau khi map xong
			return orderList;
			// neu item == 0

		}

		// Optional<Selection> optHist =
		// this.selectionRepo.getHistSelection(histId);
		// if (optHist == null) {
		// // 選択している「個人情報の選択項目」の「選択肢履歴」の件数をチェックする
		//
		// } else {
		// // ドメインモデル「選択肢の並び順」を取得する(Lấy Domain Model 「選択肢の並び順」)
		//
		//
		// }

		// return SelectionDto.fromDomainSelection(optHist.get());
	}
}
