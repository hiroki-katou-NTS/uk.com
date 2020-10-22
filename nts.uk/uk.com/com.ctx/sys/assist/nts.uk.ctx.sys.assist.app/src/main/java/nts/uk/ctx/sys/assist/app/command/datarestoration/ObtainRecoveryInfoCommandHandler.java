package nts.uk.ctx.sys.assist.app.command.datarestoration;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerZipFileTempService;

@Stateless
public class ObtainRecoveryInfoCommandHandler extends CommandHandlerWithResult<ObtainRecoveryInfoCommand, ServerZipfileValidateStatusDto> {
	@Inject
	private ServerZipFileTempService serverZipFileTempService;

	protected ServerZipfileValidateStatusDto handle(CommandHandlerContext<ObtainRecoveryInfoCommand> context) {
		ServerPrepareMng serverPrepareMng = serverZipFileTempService.handleServerZipFile(context.getCommand().getDataRecoveryProcessId(), context.getCommand().getStoreProcessingId());
		if (serverPrepareMng.getPassword().isPresent()
				&& !StringUtils.isEmpty(serverPrepareMng.getPassword().get().v())) {
			return new ServerZipfileValidateStatusDto(false, 
									String.format("%s/%s", serverPrepareMng.getFileId().get(), serverPrepareMng.getUploadFileName().get()));
		} 
		return convertToStatus(serverPrepareMng.getOperatingCondition());
	}

	private static ServerZipfileValidateStatusDto convertToStatus(ServerPrepareOperatingCondition condition){
		switch (condition) {
		case UPLOAD_FAILED:
			return new ServerZipfileValidateStatusDto(false, "Msg_610");
		case PASSWORD_DIFFERENCE:
			return new ServerZipfileValidateStatusDto(false, "Msg_606");
		case EXTRACTION_FAILED:
			return new ServerZipfileValidateStatusDto(false, "Msg_607");
		case TABLE_LIST_FAULT:
			return new ServerZipfileValidateStatusDto(false, "Msg_608");
		case CAN_NOT_SAVE_SURVEY:
			return new ServerZipfileValidateStatusDto(false, "Msg_605");
		case FILE_CONFIG_ERROR:
			return new ServerZipfileValidateStatusDto(false, "Msg_608");
		case NO_SEPARATE_COMPANY:
			return new ServerZipfileValidateStatusDto(false, "Msg_631");
		case TABLE_ITEM_DIFFERENCE:
			return new ServerZipfileValidateStatusDto(false, "Msg_609");
		case EM_LIST_ABNORMALITY:
			return new ServerZipfileValidateStatusDto(false, "Msg_670");
		case CHECK_COMPLETED:
			return new ServerZipfileValidateStatusDto(true, "");
		default:
			return new ServerZipfileValidateStatusDto(false, "");
		}
	}
}

