package nts.uk.ctx.at.function.app.nrl.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

import nts.arc.layer.infra.file.temp.ApplicationTemporaryFile;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.MarshalResult;
import nts.uk.ctx.at.function.app.nrl.exceptions.InvalidFrameException;

/**
 * XDocument
 * 
 * @author manhnd
 *
 * @param <T>
 */
public abstract class XDocument<T> {
	
	/**
	 * Temporary file factory
	 */
	@Inject
	private ApplicationTemporaryFileFactory tempFileFactory;

	/**
	 * Marshal.
	 * @param ctx
	 * @param obj
	 * @return stream
	 */
	public OutputStream marshal(JAXBContext ctx, T obj) {
		try (OutputStream os = tempFileFactory.createTempFile().createOutputStream()) {
			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, Codryptofy.SHIFT_JIS.name());
			marshaller.marshal(obj, os);
			return os;
		} catch (JAXBException | IOException ex) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Unmarshal.
	 * @param ctx
	 * @param is
	 * @param result
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T unmarshal(JAXBContext ctx, InputStream is, MarshalResult result) {
		InputStream clonedIs = null;
		ApplicationTemporaryFile tmpFile = tempFileFactory.createTempFile();
		result.setFile(tmpFile);
		OutputStream os = tmpFile.createOutputStream();
		
		try {
			IOUtils.copy(is, os);
			os.flush();
			clonedIs = tmpFile.createInputStream();
			Unmarshaller um = ctx.createUnmarshaller();
			return (T) um.unmarshal(clonedIs);
		} catch (Exception ex) {
			return dunmarshal(tmpFile).orElseThrow(InvalidFrameException::new);
		} finally {
			if (Objects.nonNull(tmpFile)) {
				tmpFile.dispose();
			}
		}
	}
	
	/**
	 * Unmarshal.
	 * @param is
	 * @return T
	 */
	public T unmarshal(InputStream is) {
		ApplicationTemporaryFile tmpFile = tempFileFactory.createTempFile();
		OutputStream os = tmpFile.createOutputStream();
		
		try {
			IOUtils.copy(is, os);
			os.flush();
			return dunmarshal(tmpFile).orElseThrow(InvalidFrameException::new);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (Objects.nonNull(tmpFile)) {
				tmpFile.dispose();
			}
		}
	}
	
	/**
	 * Decrypt payload.
	 * @param input
	 * @param pktLength
	 * @param cmd
	 * @param result
	 * @return byte array
	 */
	public byte[] decryptPayload(InputStream input, int pktLength, Command cmd, MarshalResult result) {
		try {
			int start, length;
			switch (cmd) {
				case ALL_IO_TIME:
					start = DefaultValue.ALL_IO_PLS;
					length = DefaultValue.ALL_IO_PKT_LEN_XPL;
					break;
				case ALL_PETITIONS:
					start = DefaultValue.ALL_PETITIONS_PLS;
					length = DefaultValue.ALL_PETITIONS_PKT_LEN_XPL;
					break;
				default:
					return null;
			}
			byte[] inputBytes = IOUtils.toByteArray(input);
			length = pktLength - length;
			byte[] bytes = new byte[length];
			System.arraycopy(inputBytes, start, bytes, 0, length);
			result.setOrigPlBytes(bytes);
			InputStream encryptedPayloadIs = new ByteArrayInputStream(bytes);
			InputStream decryptedPayloadIs = Codryptofy.aesDecrypt(encryptedPayloadIs, length);
			return IOUtils.toByteArray(decryptedPayloadIs);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Unmarshal.
	 * @param tmpFile
	 * @return optional T
	 */
	protected abstract Optional<T> dunmarshal(ApplicationTemporaryFile tmpFile);
}
