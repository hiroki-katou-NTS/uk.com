package nts.uk.ctx.exio.app.input.develop.diagnose;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainRepository;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspaceRepository;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;

/**
 * 外部受入のシステム固定値に不整合が無いか診断する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DiagnoseExternalImportConstants {
	
	@Inject
	private ImportingDomainRepository importingDomainRepo;
	
	@Inject
	private ImportableItemsRepository importableItemRepo;
	
	@Inject
	private DomainWorkspaceRepository workspaceRepo;

	public DiagnoseResult diagnose() {
		
		List<String> errors = new ArrayList<>();
		
		val domains = importingDomainRepo.findAll();
		
		for (val domain : domains) {
			
			val importableItems = importableItemRepo.get(domain.getDomainId());
			val workspace = workspaceRepo.get(domain.getDomainId());

			importableItems.forEach(item -> {
				
				if (!noCheck(item)) {
					item.diagnose().ifLeft(e -> errors.add(e));
				}
				
				val wiOpt = workspace.getItem(item.getItemNo());
				if (!wiOpt.isPresent()) {
					errors.add("ワークスペースに定義が無い：" + item);
				}
				
				WorkspaceItem wi = wiOpt.get();
				
				if (noCheck(item, wi)) {
					return;
				}
				
				if (!wi.diagnose(item)) {
					errors.add("受入可能項目とワークスペースの型が一致しない：" + item + ", " + wi);
				}
			});
		}
		
		return new DiagnoseResult(errors);
	}
	
	private static boolean noCheck(ImportableItem item) {
		return 
				// 休職休業の休職休業区分
				(item.getDomainId() == ImportingDomainId.TEMP_ABSENCE_HISTORY
						&& item.getItemNo() == 4)
				
				// 労働条件の契約時間
				|| (item.getDomainId() == ImportingDomainId.WORKING_CONDITION
						&& item.getItemNo() == 8)
				;
	}
	
	private static boolean noCheck(ImportableItem item, WorkspaceItem wi) {
		return
				// 個人基本の名称41のやつ
				(item.getDomainId() == ImportingDomainId.EMPLOYEE_BASIC
					&& wi.getDataTypeConfig().getLength() == 41)
				
				// 休職休業
				// - 休職休業区分
				// - 備考　GenericStringのlength=100のやつ
				|| (item.getDomainId() == ImportingDomainId.TEMP_ABSENCE_HISTORY
						&& (item.getItemNo() == 4 || item.getItemNo() == 5)
				)
				
				;
	}
}
