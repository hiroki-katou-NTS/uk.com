package nts.uk.shr.infra.file.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.logging.log4j.util.Strings;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.upload.command.FileUploadCommand;
import nts.arc.layer.infra.file.upload.command.IFileUpload;
import nts.arc.layer.infra.file.upload.command.UploadedFile;

public class DefaultFileUploadInvoker implements IFileUpload {
	private FileUploadCommand command;

	public DefaultFileUploadInvoker(FileUploadCommand command) {
		this.command = command;
	}

	@Override
	public List<StoredFileInfo> upload(MultipartFormDataInput input) {
		try {

			List<StoredFileInfo> uploadedFiles = new ArrayList<>();
			Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
			List<InputPart> inputParts = uploadForm.get("userfile");
			String fileStereoType = getFileStereoType(uploadForm);
			for (InputPart inputPart : inputParts) {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				String fileName = getFileName(header);

				// convert the uploaded file to inputstream
				File inputFile = inputPart.getBody(File.class, null);
				UploadedFile uploadedFile = new UploadedFile(fileName, fileStereoType, inputFile);
				uploadedFiles.add(command.upload(uploadedFile));
			}
			return uploadedFiles;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getFileStereoType(Map<String, List<InputPart>> uploadForm) throws IOException {
		List<InputPart> inputParts = uploadForm.get("stereotype");

		for (InputPart inputPart : inputParts) {
			return inputPart.getBodyAsString();
		}
		return Strings.EMPTY;

	}

	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

}
