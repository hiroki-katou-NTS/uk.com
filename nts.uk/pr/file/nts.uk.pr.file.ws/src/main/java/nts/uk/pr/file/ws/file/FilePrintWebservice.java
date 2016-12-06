package nts.uk.pr.file.ws.file;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import nts.arc.file.FileMetaData;
import nts.uk.pr.file.infra.file.FileTaskRepository;

@Path("/file/file")
public class FilePrintWebservice {

	@Inject
	private FileTaskRepository fileTask;

	@POST
	@Path("isfinished")
	public Boolean isFinished(String taskId) {
		return this.fileTask.isFinishedTask(taskId);
	}

	@GET
	@Path("downloadreport/{taskid}")
	public Response downloadReport(@PathParam("taskid") String taskId) throws UnsupportedEncodingException {
		FileMetaData fileMeta = this.fileTask.getFileMetaData(taskId);
		String encodedFileName = URLEncoder.encode(fileMeta.getName(), "UTF-8").replaceAll("\\+", "%20");

		// Cache control go here.
		// 1 year expired.
		long cacheAge = 365L*24*60*60;
		return Response.ok(this.fileTask.downloadFile(fileMeta.getFileId()), "text/html")
				.encoding("UTF-8")
				.header("Content-Disposition", String.format("attachment; filename=\"%s\"", encodedFileName))
				.header("Cache-Control", "max-age=" + cacheAge)
				.build();
	}
}
