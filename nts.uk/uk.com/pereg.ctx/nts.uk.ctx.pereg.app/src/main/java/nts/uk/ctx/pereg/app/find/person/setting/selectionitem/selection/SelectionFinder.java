package nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.SelectionInitDto;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionItemOrderRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;

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

	@Inject
	private ComboBoxRetrieveFactory comboBoxFactory;

	// アルゴリズム「選択肢履歴選択時処理」を実行する(Thực thi xử lý chọn 選択肢履歴)
	public List<SelectionDto> getAllSelection() {
		String contractCode = AppContexts.user().contractCode();
		return this.selectionRepo.getAllSelectByHistId(contractCode).stream()
				.map(i -> SelectionDto.fromDomainSelection(i)).collect(Collectors.toList());
	}

	// check history ID:
	public List<SelectionItemOrderDto> getHistIdSelection(String histId) {
		List<SelectionItemOrderDto> orderList = new ArrayList<SelectionItemOrderDto>();

		// lay selection
		List<Selection> selectionList = this.selectionRepo.getAllSelectByHistId(histId);

		// kiem tra so luong item lay duoc
		if (selectionList.isEmpty()) {
			return orderList;
		} else {
			String getByHisId = selectionList.get(0).getHistId();
			List<SelectionItemOrder> orderDomainlst = this.selectionOrderRpo.getAllOrderSelectionByHistId(getByHisId);

			if (!orderDomainlst.isEmpty()) {
				orderList = orderDomainlst.stream().map(i -> {
					Selection selectionItem = selectionList.stream()
							.filter(s -> s.getSelectionID().equals(i.getSelectionID())).findFirst().orElse(null);
					return SelectionItemOrderDto.fromSelectionOrder(i, selectionItem);
				}).collect(Collectors.toList());
			}

			return orderList;
		}

	}

	// Lanlt
	public List<SelectionInitDto> getAllSelectionByHistoryId(String selectionItemId, String baseDate) {
		GeneralDate baseDateConvert = GeneralDate.fromString(baseDate, "yyyy-MM-dd");
		return this.selectionRepo.getAllSelectionByHistoryId(selectionItemId, baseDateConvert).stream()
				.map(c -> SelectionInitDto.fromDomainSelection(c)).collect(Collectors.toList());

	}

	// ham nay su dung chu y selectionItemClsAtr co gia tri la 0 vs 1
	// con bang itemCommon thi co gia tri la 1 vs 2 ko map vs nhau
	// do do ma ham nay phai chuyen doi de co du lieu chinh xac
	public List<SelectionInitDto> getAllSelectionByHistoryId(String selectionItemId, String baseDate,
			int selectionItemClsAtr) {
		GeneralDate baseDateConvert = GeneralDate.fromString(baseDate, "yyyy-MM-dd");
		List<SelectionInitDto> selectionLst = new ArrayList<>();
		if (selectionItemClsAtr == 1) {
			selectionLst = this.selectionRepo.getAllSelectionByHistoryId(selectionItemId, baseDateConvert, 0).stream()
					.map(c -> SelectionInitDto.fromDomainSelection(c)).collect(Collectors.toList());
		} else if (selectionItemClsAtr == 2) {
			selectionLst = this.selectionRepo.getAllSelectionByHistoryId(selectionItemId, baseDateConvert, 1).stream()
					.map(c -> SelectionInitDto.fromDomainSelection(c)).collect(Collectors.toList());
		}
		return selectionLst;

	}

	// Lanlt
	/**
	 * for companyID
	 * 
	 * @param selectionItemId
	 * @param baseDate
	 * @return
	 */
	public List<SelectionInitDto> getAllSelectionByCompanyId(String selectionItemId, GeneralDate date) {
		String companyId = AppContexts.user().companyId();
		List<SelectionInitDto> selectionLst = new ArrayList<>();
		List<Selection> domainLst = this.selectionRepo.getAllSelectionByCompanyId(companyId, selectionItemId, date);
		if (domainLst != null) {

			selectionLst = domainLst.stream().map(c -> SelectionInitDto.fromDomainSelection(c))
					.collect(Collectors.toList());
		}

		return selectionLst;

	}

	public List<ComboBoxObject> getAllComboxByHistoryId(SelectionQuery dto) {
		GeneralDate baseDateConvert = GeneralDate.fromString(dto.getBaseDate(), "yyyy-MM-dd");
		SelectionItemDto selectionItemDto = null;
		String companyId = AppContexts.user().companyId();
		if (dto.getSelectionItemRefType() == 2) {
			return this.selectionRepo.getAllSelectionByCompanyId(companyId, dto.getSelectionItemId(), baseDateConvert)
					.stream().map(c -> new ComboBoxObject(c.getSelectionID(), c.getSelectionName().toString()))
					.collect(Collectors.toList());
		} else if (dto.getSelectionItemRefType() == 1) {
			selectionItemDto = SelectionItemDto.createMasterRefDto(dto.getSelectionItemId());
			return this.comboBoxFactory.getComboBox(selectionItemDto, AppContexts.user().employeeId(), baseDateConvert,
					true, true);

		}
		return new ArrayList<>();

	}

}
