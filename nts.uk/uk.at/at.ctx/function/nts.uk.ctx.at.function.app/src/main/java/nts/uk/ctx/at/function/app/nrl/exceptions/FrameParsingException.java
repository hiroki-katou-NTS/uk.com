package nts.uk.ctx.at.function.app.nrl.exceptions;

import nts.arc.layer.infra.file.temp.ApplicationTemporaryFile;

/**
 * Frame parsing exception.
 * 
 * @author manhnd
 */
public class FrameParsingException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private ApplicationTemporaryFile targetFile;
	
	public ApplicationTemporaryFile getTargetFile() {
		return this.targetFile;
	}
	
	public FrameParsingException() {
		super();
	}
	
	public FrameParsingException(String exception) {
		super(exception);
	}
	
	public FrameParsingException(Throwable ex) {
		super(ex);
	}
	
	public FrameParsingException(ApplicationTemporaryFile tmpFile) {
		this.targetFile = tmpFile;
	}

}
