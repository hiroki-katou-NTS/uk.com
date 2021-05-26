package nts.uk.ctx.at.request.pubimp.appdisplayname;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.pub.appdisplayname.export.AppDispNameExport;
import nts.uk.ctx.at.request.pub.appdisplayname.export.AppDisplayNamePub;

/**
 * @author anhdt
 *
 */
@Stateless
public class AppDisplayNamePubImpl implements AppDisplayNamePub {
	
//	@Inject
//	private AppDispNameRepository appDispNameRepository;
	
	@Override
	public List<AppDispNameExport> getAppDisplayNameByCid() {
		return Collections.emptyList();
//		return appDispNameRepository.getAll()
//				.stream()
//				.map(item -> AppDispNameExport.builder()
//						.companyId(item.getCompanyId())
//						.appType(item.getAppType().value)
//						.dispName( item.getDispName() == null ? "" : item.getDispName().v())
//						.build())
//				.collect(Collectors.toList());
	}

}
