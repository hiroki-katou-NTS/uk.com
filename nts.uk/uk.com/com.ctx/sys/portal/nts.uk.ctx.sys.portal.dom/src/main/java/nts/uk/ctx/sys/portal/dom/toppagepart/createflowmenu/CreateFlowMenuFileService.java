package nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu;

import java.io.IOException;

import javax.ejb.Stateless;

@Stateless
public interface CreateFlowMenuFileService {

	/**
	 * Perform copy an uploaded file
	 * @param fileId fileId of the file to be copied
	 * @return fileId of the copied file
	 * @throws IOException
	 */
	String copyFile(String fileId) throws IOException;

	/**
	 * Delete the layout fileId and all the uploaded files
	 * @param layout
	 */
	void deleteUploadedFiles(FlowMenuLayout layout);
	
	/**
	 * Delete the layout fileId only
	 * @param layout
	 */
	void deleteLayout(FlowMenuLayout layout);
}
