package nts.uk.ctx.exio.ws.exo.execlog;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.command.exo.executionlog.ExOutOpMngCommandDelete;
import nts.uk.ctx.exio.app.command.exo.executionlog.ExOutOpMngCommandInterrupt;
import nts.uk.ctx.exio.app.command.exo.executionlog.ExterOutExecLogCommand;
import nts.uk.ctx.exio.app.command.exo.executionlog.RemoveExOutOpMngCommandHandler;
import nts.uk.ctx.exio.app.command.exo.executionlog.UpdateExOutOpMngInterruptHandler;
import nts.uk.ctx.exio.app.command.exo.executionlog.UpdateExterOutExecLogCommandHandler;
import nts.uk.ctx.exio.app.command.exo.executionlog.UpdateExterOutExecLogUseDeleteFileCommandHandler;
import nts.uk.ctx.exio.app.find.exi.execlog.ExecLogDto;
import nts.uk.ctx.exio.app.find.exi.execlog.ExecLogFinder;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogDto;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogExportService;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogFinder;
import nts.uk.ctx.exio.app.find.exo.executionlog.ErrorContentDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExOutLogDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExOutLogFinder;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExOutOpMngDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExOutOpMngFinder;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExterOutExecLogDto;
import nts.uk.ctx.exio.app.find.exo.executionlog.ExterOutExecLogFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("exio/exo/execlog")
@Produces("application/json")
public class ExternalOutLogWebService extends WebService {

	@Inject
	private ExternalOutLogFinder externalOutLogFinder;

	@Inject
	private ExternalOutLogExportService exportService;

	@Inject
	private UpdateExterOutExecLogUseDeleteFileCommandHandler updateExterOutExecLogUseDeleteFileCommandHandler;
	
	@Inject
	private ExOutOpMngFinder exOutOpMngFinder;
	
	@Inject
	private RemoveExOutOpMngCommandHandler removeExOutOpMngCommandHandler;
	
	@Inject
	private UpdateExterOutExecLogCommandHandler updateExterOutExecLogCommandHandler;
	
	@Inject
	private ExterOutExecLogFinder exterOutExecLogFinder;
	
	@Inject
	private UpdateExOutOpMngInterruptHandler updateExOutOpMngInterruptHandler;
	
	/** The ex out log finder. */
	@Inject
	private ExOutLogFinder exOutLogFinder;
	
	/** The exec log finder. */
	@Inject
	private ExecLogFinder execLogFinder;
	
	@Path("getExternalOutLog/{storeProcessingId}")
	@POST
	public List<ExternalOutLogDto> getExternalOutLogById(@PathParam("storeProcessingId") String storeProcessingId) {
		return this.externalOutLogFinder.getExternalOutLogById(storeProcessingId);
	}

	@POST
	@Path("export")
	public ExportServiceResult exportCsvError(ErrorContentDto command) {
		return this.exportService.start(command);
	}

	@POST
	@Path("useDeleteFile/{outProcessId}")
	public Integer useDeleteFile(@PathParam("outProcessId") String outProcessId) {
		return this.updateExterOutExecLogUseDeleteFileCommandHandler.handle(outProcessId);
	}
	
	@POST
	@Path("findExOutOpMng/{storeProcessingId}")
	public ExOutOpMngDto findExOutOpMng(@PathParam("storeProcessingId") String storeProcessingId) {
		return exOutOpMngFinder.getExOutOpMngById(storeProcessingId);
	}
	
	@POST
	@Path("deleteexOutOpMng")
	public void deleteexOutOpMng(ExOutOpMngCommandDelete command) {
		this.removeExOutOpMngCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateexOutOpMng")
	public void deleteexOutOpMng(ExOutOpMngCommandInterrupt command) {
		this.updateExOutOpMngInterruptHandler.handle(command);
	}
	
	@POST
	@Path("updateFileSize/{storeProcessingId}/{fileId}")
	public void updateFileSize( @PathParam("storeProcessingId") String storeProcessingId,
			@PathParam("fileId") String fileId) {
		String companyId = AppContexts.user().companyId();
		ExterOutExecLogCommand command = new ExterOutExecLogCommand(companyId, storeProcessingId, fileId);
		updateExterOutExecLogCommandHandler.handle(command);
	}
	
	@Path("getExterOutExecLog/{exterOutExecLogProcessId}")
	@POST
	public ExterOutExecLogDto getExterOutExecLogById(@PathParam("exterOutExecLogProcessId") String exterOutExecLogProcessId) {
		return this.exterOutExecLogFinder.getExterOutExecLogById(exterOutExecLogProcessId);
	}
	
	@Path("smileGetExterOutExecLog")
	@POST
	public JavaTypeResult<String> smileGetExterOutExecLog(String exterOutExecLogProcessId) {
		String Cid = AppContexts.user().companyId();
		String fileId = this.exterOutExecLogFinder.getExterOutExecLogByCom(Cid, exterOutExecLogProcessId).getFileId().toString();
		return new JavaTypeResult<String>(fileId);
	}
	
	/**
	 * 	アルゴリズム「外部出力エラーログ設定」を実行する.
	 * @param storeProcessingId the store processing id 外部出力処理ID
	 * @return the ex out error log
	 */
	@POST
	@Path("getExOutErrorLog/{storeProcessingId}")
	public ExOutLogDto getExOutErrorLog( @PathParam("storeProcessingId") String storeProcessingId) {
		return this.exOutLogFinder.getExOutLogDto(storeProcessingId);
	}

	/**
	 * Gets the exec log.
	 *	アルゴリズム「エラー一覧表示」を実行する
	 * @param storeProcessingId the store processing id
	 * @return the exec log
	 */
	@POST
	@Path("getExecLog/{externalProcessId}")
	public ExecLogDto getExecLog( @PathParam("externalProcessId") String externalProcessId) {
		return this.execLogFinder.getErrorList(externalProcessId);
	}
}
