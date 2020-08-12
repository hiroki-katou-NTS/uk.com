///**
// * 2:59:29 PM Feb 1, 2018
// */
//package nts.uk.ctx.at.request.app.command.setting.workplace;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
//
///**
// * @author hungnm
// *
// */
//@Stateless
//public class DeleteApprovalSettingWkpCommandHandler extends CommandHandler<ApprovalSettingWkpCommand> {
//
//	@Inject
//	private RequestOfEachWorkplaceRepository repo;
//
//	@Override
//	protected void handle(CommandHandlerContext<ApprovalSettingWkpCommand> context) {
//		ApprovalSettingWkpCommand command = context.getCommand();
//		repo.remove(command.toDomain());
//	}
//	
//}
