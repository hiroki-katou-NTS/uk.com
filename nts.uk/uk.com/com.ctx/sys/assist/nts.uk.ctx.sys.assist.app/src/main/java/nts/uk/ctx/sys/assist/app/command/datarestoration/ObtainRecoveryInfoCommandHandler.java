package nts.uk.ctx.sys.assist.app.command.datarestoration;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerZipFileTempService;

@Stateless
public class ObtainRecoveryInfoCommandHandler extends CommandHandlerWithResult<ObtainRecoveryInfoCommand, ServerZipfileValidateStatus> {
	@Inject
	private ServerZipFileTempService serverZipFileTempService;

	protected ServerZipfileValidateStatus handle(CommandHandlerContext<ObtainRecoveryInfoCommand> context) {
		ServerPrepareMng serverPrepareMng = serverZipFileTempService.handleServerZipFile(context.getCommand().getDataRecoveryProcessId(), context.getCommand().getStoreProcessingId());
		return convertToStatus(serverPrepareMng.getOperatingCondition());
	}

	private static ServerZipfileValidateStatus convertToStatus(ServerPrepareOperatingCondition condition){
		switch (condition) {
		case UPLOAD_FAILED:
			return new ServerZipfileValidateStatus(false, "Msg_610");
		case PASSWORD_DIFFERENCE:
			return new ServerZipfileValidateStatus(false, "Msg_606");
		case EXTRACTION_FAILED:
			return new ServerZipfileValidateStatus(false, "Msg_607");
		case TABLE_LIST_FAULT:
			return new ServerZipfileValidateStatus(false, "Msg_608");
		case CAN_NOT_SAVE_SURVEY:
			return new ServerZipfileValidateStatus(false, "Msg_605");
		case FILE_CONFIG_ERROR:
			return new ServerZipfileValidateStatus(false, "Msg_608");
		case NO_SEPARATE_COMPANY:
			return new ServerZipfileValidateStatus(false, "Msg_631");
		case TABLE_ITEM_DIFFERENCE:
			return new ServerZipfileValidateStatus(false, "Msg_609");
		case EM_LIST_ABNORMALITY:
			return new ServerZipfileValidateStatus(false, "Msg_670");
		case CHECK_COMPLETED:
			return new ServerZipfileValidateStatus(true, "");
		default:
			return new ServerZipfileValidateStatus(false, "");
		}
	}
}

