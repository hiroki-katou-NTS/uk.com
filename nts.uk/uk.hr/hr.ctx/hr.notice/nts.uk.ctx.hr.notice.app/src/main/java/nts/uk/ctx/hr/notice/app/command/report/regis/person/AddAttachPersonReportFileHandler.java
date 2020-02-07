/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachPersonReportFileRepository;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachmentPersonReportFile;

/**
 * @author laitv
 *
 */
@Stateless
public class AddAttachPersonReportFileHandler extends CommandHandler<AddDocumentReportCommand> {

	@Inject
	private AttachPersonReportFileRepository repo;

	@Override
	protected void handle(CommandHandlerContext<AddDocumentReportCommand> context) {
		AddDocumentReportCommand command = context.getCommand();
		
		GeneralDateTime fileStorageDate = GeneralDateTime.now();
		AttachmentPersonReportFile domain = AttachmentPersonReportFile.createFromJavaType(command.cid, command.reportID,
				command.docID, command.docName, command.fileId, command.fileName,
				command.fileAttached == 1 ? true : false, fileStorageDate, command.mimeType, command.fileTypeName,
				command.fileSize, command.delFlg == 1 ? true : false, command.sampleFileID, command.sampleFileName);

		repo.add(domain);
	}
}
