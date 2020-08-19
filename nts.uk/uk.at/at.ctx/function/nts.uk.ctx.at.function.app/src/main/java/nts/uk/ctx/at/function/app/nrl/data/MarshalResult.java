package nts.uk.ctx.at.function.app.nrl.data;

import java.io.InputStream;
import java.util.Objects;

import lombok.Setter;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFile;

/**
 * Marshal result.
 * 
 * @author manhnd
 */
@Setter
public class MarshalResult {
	
	/**
	 * Temporary file
	 */
	private ApplicationTemporaryFile file;
	
	/**
	 * Bytes
	 */
	private byte[] origPlBytes;
	
	public InputStream toInputStream() {
		return file.createInputStream();
	}
	
	public byte[] getOrigPlBytes() {
		return this.origPlBytes;
	}
	
	public void dispose() {
		if (Objects.nonNull(file)) {
			file.dispose();
		}
	}
}
