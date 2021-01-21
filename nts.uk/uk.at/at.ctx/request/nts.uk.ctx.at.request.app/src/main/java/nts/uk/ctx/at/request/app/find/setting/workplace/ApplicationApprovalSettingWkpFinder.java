///**
// * 2:43:06 PM Jan 30, 2018
// */
//package nts.uk.ctx.at.request.app.find.setting.workplace;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
//
///**
// * @author hungnm
// *
// */
//@Stateless
//public class ApplicationApprovalSettingWkpFinder {
//
//	@Inject
//	private RequestOfEachWorkplaceRepository repo;
//
//	public List<ApplicationApprovalSettingWkpDto> getAll(List<String> lstWkpId) {
//		return repo.getAll(lstWkpId).stream().map((domain) -> {
//			return ApplicationApprovalSettingWkpDto.fromDomain(domain);
//		}).collect(Collectors.toList());
//	}
//}
