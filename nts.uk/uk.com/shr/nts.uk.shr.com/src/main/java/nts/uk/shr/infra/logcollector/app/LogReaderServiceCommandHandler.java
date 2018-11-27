package nts.uk.shr.infra.logcollector.app;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import lombok.SneakyThrows;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFile;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.logcollector.dom.LogAccessInfo;
import nts.uk.shr.infra.logcollector.dom.LogAccessInfoRepository;
import nts.uk.shr.infra.logcollector.dom.StackTraceInTime;

@Stateless
public class LogReaderServiceCommandHandler extends AsyncCommandHandler<LogReadRequest>{

	private static final String DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	private static final String DEFAULT_FILE_NAME = "SERVER_LOG.TXT";
	private static final String DEFAULT_FILE_TYPE = "text/plain";
	public static final String DEFAULT_ENCODE = "Shift_JIS";
	
	@Inject
	private LogAccessInfoRepository logAccessRepo;
	
	@Inject
	private StoredFileInfoRepository fileInfoRepository;

	@Inject
	private StoredFileStreamService fileStreamService;
	
	@Override
	@SneakyThrows
	protected void handle(CommandHandlerContext<LogReadRequest> context) {
		TaskDataSetter dataSetter = context.asAsync().getDataSetter();
		dataSetter.setData("startTime", GeneralDateTime.now().toString(DATETIME_FORMAT));
		
		if (context.getCommand().getStart() == null) 
			context.getCommand().setStart(GeneralDateTime.now());
		if (context.getCommand().getEnd() == null) 
			context.getCommand().setEnd(GeneralDateTime.now());
		
		List<LogAccessInfo> accessInfos = logAccessRepo.getBy(context.getCommand().getDomains(), context.getCommand().getHosts());
		
		Map<String, List<LogAccessInfo>> targetMap = accessInfos.stream().collect(
														Collectors.groupingBy(c -> c.getDomain() + c.getHost() + c.getLocation(), Collectors.toList()));
		
		ApplicationTemporaryFileFactory temporartFac = CDI.current().select(ApplicationTemporaryFileFactory.class).get();
		
		List<StackTraceInTime> stack = targetMap.entrySet().stream().map(info -> {
			ApplicationTemporaryFile audit = temporartFac.createTempFile();
			FileEntryFilter filter = new FileEntryFilter(context.getCommand().getStart().localDateTime(), 
														context.getCommand().getEnd().localDateTime());
			
			establish(info.getValue().get(0), audit);
			
			StackTraceInTime trace = filter.scan(audit);
			trace.setNode(info.getValue().get(0).getHost());
			audit.dispose();
			return trace;
			
		}).collect(Collectors.toList());

		ApplicationTemporaryFile mainAudit = temporartFac.createTempFile();
		
		try (OutputStream os = mainAudit.createOutputStream()){
			try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, Charset.forName(DEFAULT_ENCODE)))){
				writeLog(stack, bw);
			}
		} finally {
			StoredFileInfo fileInfo = fileInfo(mainAudit, ((AsyncCommandHandlerContext<LogReadRequest>) context).getTaskId());
			
			fileInfoRepository.add(fileInfo);
			fileStreamService.store(fileInfo, mainAudit.createInputStream());
			
			mainAudit.dispose();
		}

		dataSetter.setData("endTime", GeneralDateTime.now().toString(DATETIME_FORMAT));
	}
	
	/**
	 * Establish.
	 * @param info log access info
	 * @return file
	 */
	@SneakyThrows
	private void establish(LogAccessInfo info, ApplicationTemporaryFile audit) {
		SmbFile file = ShareConnection.getSmbFile(info);
		try (InputStream is = new SmbFileInputStream(file)) {
			IOUtils.copyLarge(is, audit.createOutputStream());
			audit.closeOutputStream();
		}
	}
	
	/**
	 * Write log.
	 * @param stack stack trace
	 * @param os output stream
	 */
	private void writeLog(List<StackTraceInTime> stack, BufferedWriter bw) {		
		stack.stream().forEach(s -> {
			s.write(bw);
		});
	}
	
	private StoredFileInfo fileInfo(ApplicationTemporaryFile tempFile, String fileId) {
		return StoredFileInfo.createNewTemporaryWithId(fileId, DEFAULT_FILE_NAME, 
														DEFAULT_FILE_TYPE, tempFile.getPath().toFile().length());
	}
}
