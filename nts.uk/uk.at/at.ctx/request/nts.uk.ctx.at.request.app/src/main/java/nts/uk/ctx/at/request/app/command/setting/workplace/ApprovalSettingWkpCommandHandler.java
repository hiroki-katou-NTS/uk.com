///**
// * 3:38:52 PM Jan 30, 2018
// */
//package nts.uk.ctx.at.request.app.command.setting.workplace;
//
//import java.util.List;
//import java.util.Optional;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplace;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
//
///**
// * @author hungnm
// *
// */
//@Stateless
//public class ApprovalSettingWkpCommandHandler extends CommandHandler<List<ApprovalSettingWkpCommand>> {
//
//	@Inject
//	private RequestOfEachWorkplaceRepository repo;
//
//	@Override
//	protected void handle(CommandHandlerContext<List<ApprovalSettingWkpCommand>> context) {
//		List<ApprovalSettingWkpCommand> lstCommand = context.getCommand();
//		lstCommand.stream().forEach(command -> {
//			Optional<RequestOfEachWorkplace> existedSetting = repo.getRequestByWorkplace(command.getCompanyId(),
//					command.getWkpId());
//			if (existedSetting.isPresent()) {
//				repo.update(command.toDomain());
//			} else {
//				repo.add(command.toDomain());
//			}
//		});
//	}
//
//}
