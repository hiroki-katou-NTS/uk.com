package nts.uk.ctx.sys.log.ac.reference.record;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  author: thuongtv
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.pub.employee.export.dto.PersonEmpBasicInfoDto;
import nts.uk.ctx.sys.log.dom.reference.PersonEmpBasicInfoImport;
import nts.uk.ctx.sys.log.dom.reference.WebMenuAdapter;
import nts.uk.ctx.sys.portal.pub.webmenu.ProgramNameDto;
import nts.uk.ctx.sys.portal.pub.webmenu.WebMenuPub;

@Stateless
public class WebMenuAdapterImpl implements WebMenuAdapter {

	@Inject
	private WebMenuPub webMenuPub;

	@Override
	public Map<String,String> getWebMenuByCId(String cId) {
		Map<String,String> mapReturn= new HashMap<>();
		List<ProgramNameDto> lstProgramName = webMenuPub.findProgramNames(cId);
		if(!CollectionUtil.isEmpty(lstProgramName)){
			for (ProgramNameDto programNameDto : lstProgramName) {
				String key = programNameDto.getProgramId()+programNameDto.getScreenId()+ programNameDto.getQueryString();
				if(!mapReturn.containsKey(key)){
					mapReturn.put(key, programNameDto.getDisplayName());
				}
			}
		}
		return mapReturn;
	}

}
