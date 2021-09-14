package nts.uk.ctx.exio.app.input.develop.diagnose;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainRepository;
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
				
				item.diagnose().ifLeft(e -> errors.add(e));
				
				val wiOpt = workspace.getItem(item.getItemNo());
				if (!wiOpt.isPresent()) {
					errors.add("ワークスペースに定義が無い：" + item);
				}
				
				WorkspaceItem wi = wiOpt.get();
				if (!wi.diagnose(item)) {
					errors.add("受入可能項目とワークスペースの型が一致しない：" + item + ", " + wi);
				}
			});
		}
		
		return new DiagnoseResult(errors);
	}
}
