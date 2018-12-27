package nts.uk.ctx.pereg.app.find.person.setting.selectionitem.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.SelectionInitDto;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.Selection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.SelectionRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.PerInfoSelectionItem;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrder;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.SelectionItemOrderRepository;
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

	// fix bug: 23.2.2018
	@Inject
	private IPerInfoSelectionItemRepository selectionItemRpo;

	// アルゴリズム「選択肢履歴選択時処理」を実行する(Thực thi xử lý chọn 選択肢履歴)
	public List<SelectionDto> getAllSelection() {
		String contractCode = AppContexts.user().contractCode();
		return this.selectionRepo.getAllSelectByHistId(contractCode).stream()
				.map(i -> SelectionDto.fromDomainSelection(i)).collect(Collectors.toList());
	}

	public List<SelectionItemOrderDto> getHistIdSelection(String histId) {
		
		// lay selection
		Map<String, Selection> selectionMap = this.selectionRepo.getAllSelectByHistId(histId).stream()
				.collect(Collectors.toMap(selection -> selection.getSelectionID(), selection -> selection));
		
		if (selectionMap.isEmpty()) {
			return new ArrayList<>();
		} 
		
		List<SelectionItemOrder> selectionOrderList = this.selectionOrderRpo.getAllOrderSelectionByHistId(histId);
		PerInfoSelectionItem selectionItem = selectionItemRpo.getSelectionItemByHistId(histId).get();
		
		return selectionOrderList.stream().map(selectionOrder -> {
			Selection selection = selectionMap.get(selectionOrder.getSelectionID());
			return SelectionItemOrderDto.fromSelectionOrder(selectionOrder, selection,
					selectionItem.getFormatSelection().getCharacterType().value);
		}).collect(Collectors.toList());
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
	public List<SelectionInitDto> getAllSelectionByHistoryId(SelectionInitQuery query) {
		GeneralDate today = GeneralDate.today();
		String companyId = AppContexts.user().companyId();
		String selectionItemId = query.getSelectionItemId();
		List<Selection> selectionList = new ArrayList<>();
			
		selectionList = this.selectionRepo.getAllSelectionByCompanyId(companyId, selectionItemId, today);
		
		return selectionList.stream().map(c -> SelectionInitDto.fromDomainSelection(c)).collect(Collectors.toList());
	}

	// Lanlt
	/**
	 * for companyID
	 * 
	 * @param selectionItemId
	 * @param baseDate
	 * @return
	 */
	public List<SelectionInitDto> getAllSelectionByCompanyId(String selectionItemId, GeneralDate date, PersonEmployeeType perEmplType) {
		
		String companyId = AppContexts.user().companyId();
		
		List<SelectionInitDto> selectionLst = new ArrayList<>();
		List<Selection> domainLst = this.selectionRepo.getAllSelectionByCompanyId(companyId, selectionItemId, date);
		if (domainLst != null) {

			selectionLst = domainLst.stream().map(c -> SelectionInitDto.fromDomainSelection(c))
					.collect(Collectors.toList());
		}

		return selectionLst;

	}

	public List<ComboBoxObject> getAllComboxByHistoryId(SelectionQuery query) {
		GeneralDate baseDateConvert = GeneralDate.fromString(query.getBaseDate(), "yyyy-MM-dd");
		SelectionItemDto selectionItemDto = null;
		String companyId = AppContexts.user().companyId();
		if (query.getSelectionItemRefType() == 2) {
			return this.selectionRepo.getAllSelectionByCompanyId(companyId, query.getSelectionItemId(), baseDateConvert)
					.stream().map(c -> new ComboBoxObject(c.getSelectionID(), c.getSelectionName().toString()))
					.collect(Collectors.toList());
		} else if (query.getSelectionItemRefType() == 1) {
			selectionItemDto = SelectionItemDto.createMasterRefDto(query.getSelectionItemId(),
					query.getSelectionItemRefType());
			return this.comboBoxFactory.getComboBox(selectionItemDto, AppContexts.user().employeeId(), baseDateConvert,
					true, PersonEmployeeType.EMPLOYEE, true, query.getCategoryCode(),null, true);

		}
		return new ArrayList<>();

	}

}
