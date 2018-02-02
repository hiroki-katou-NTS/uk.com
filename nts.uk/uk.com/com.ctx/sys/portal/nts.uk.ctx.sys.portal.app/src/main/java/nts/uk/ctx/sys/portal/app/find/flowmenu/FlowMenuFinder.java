package nts.uk.ctx.sys.portal.app.find.flowmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hieult
 */
@Stateless
public class FlowMenuFinder {
	
	@Inject
	private FlowMenuRepository repository;
	
	@Inject
	private FileStorage fileStorage;
	
	public List<FlowMenuDto> getAllFlowMenu() {
		String companyID = AppContexts.user().companyId();
		List<FlowMenu> flowMenus = repository.findAll(companyID);
		List<FlowMenuDto> flowMenuDtos = new ArrayList<FlowMenuDto>();
		for (FlowMenu flowMenu : flowMenus) {
			if(StringUtils.isEmpty(flowMenu.getFileID()) || !fileStorage.getInfo(flowMenu.getFileID()).isPresent()) {
				flowMenuDtos.add(FlowMenuDto.fromDomain(flowMenu, null));
				continue;
			}
			StoredFileInfo fileInfo = fileStorage.getInfo(flowMenu.getFileID()).get();
			flowMenuDtos.add(FlowMenuDto.fromDomain(flowMenu, fileInfo));
		}
		return flowMenuDtos;
	}
	
	public FlowMenuDto getFlowMenu(String toppagePartID) {
		String companyID = AppContexts.user().companyId();
//		FlowMenu flowMenu = repository.findByCode(companyID, toppagePartID).get();
		//hoatt
		Optional<FlowMenu> flowMenu = repository.findByCode(companyID, toppagePartID);
		if(!flowMenu.isPresent()){
			return null;
		}
		StoredFileInfo fileInfo = fileStorage.getInfo(flowMenu.get().getFileID()).get();
		return FlowMenuDto.fromDomain(flowMenu.get(), fileInfo);
	}
	
}
