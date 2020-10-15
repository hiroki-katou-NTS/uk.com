package nts.uk.ctx.at.function.app.nrl.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.file.temp.ApplicationTemporaryFile;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.data.MarshalResult;
import nts.uk.ctx.at.function.app.nrl.exceptions.FrameParsingException;
import nts.uk.ctx.at.function.app.nrl.exceptions.InvalidFrameException;

/**
 * Default XDocument.
 * 
 * @author manhnd
 */
@RequestScoped
public class DefaultXDocument extends XDocument<Frame> {
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.xml.XDocument#dunmarshal(nts.arc.layer.infra.file.temp.ApplicationTemporaryFile)
	 */
	public Optional<Frame> dunmarshal(ApplicationTemporaryFile tmpFile) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(tmpFile.createInputStream()))) {
			byte[] payload = null, origPl = null;
			String line = null;
			Frame frame = null;
			List<Content> contents = null;
			Content head = null, data = null;
			List<Item> headItems = null, dataItems = null;
			Command command = null;
			Stack<String> layers = new Stack<>();
			int hdrIndex = -1, plIndex = -1, lengthIndex = -1;
			
			while ((line = readLine(br)) != null) {
				String layer = layers.size() > 0 ? layers.peek() : null; 
				if (line.startsWith("<" + Element.ROOT) && layers.size() == 0) {
					frame = new Frame();
					contents = new ArrayList<>();
					frame.setContents(contents);
					
					frame.setType(tamperAttr(br, line, Element.TYPE_ATTR));
					layers.push(Element.ROOT);
				} else if (line.startsWith("<" + Element.ITEM) && Element.ROOT.equals(layer)) {
					String type = tamperAttr(br, line, Element.TYPE_ATTR);
					if (DefaultValue.HEAD_TYPE.equals(type)) {
						head = new Content();
						head.setType(DefaultValue.HEAD_TYPE);
						headItems = new ArrayList<>();
						head.setItems(headItems);
						if (contents == null) throw new FrameParsingException();
						contents.add(head);
						layers.push(DefaultValue.HEAD_TYPE);
					} else if (DefaultValue.DATA_TYPE.equals(type)) {
						data = new Content();
						data.setType(DefaultValue.DATA_TYPE);
						dataItems = new ArrayList<>();
						data.setItems(dataItems);
						if (contents == null) throw new FrameParsingException();
						contents.add(data);
						layers.push(DefaultValue.DATA_TYPE);
					}
				} else if (line.startsWith("<" + Element.SUBITEM) && DefaultValue.HEAD_TYPE.equals(layer)) {
					String index = tamperAttr(br, line, Element.INDEX_ATTR);
					String value = tamperAttr(br, line, Element.VALUE_ATTR);
					
					if (Element.HDR.equals(value)) {
						hdrIndex = Integer.parseInt(index);
					} else if (Element.LENGTH.equals(value)) {
						lengthIndex = Integer.parseInt(index);
					} else if (Element.PAYLOAD.equals(value)) {
						plIndex = Integer.parseInt(index);
					}
					headItems.add(new Item(Integer.parseInt(index), value));
				} else if (line.startsWith("<" + Element.SUBITEM) && DefaultValue.DATA_TYPE.equals(layer)) {
					String index = tamperAttr(br, line, Element.INDEX_ATTR);
					String value = tamperAttr(br, line, Element.VALUE_ATTR);
					int elmIndex = Integer.parseInt(index);
					
					if (hdrIndex == elmIndex) {
						command = Command.findName(value.trim()).orElseThrow(InvalidFrameException::new);
					} else if (lengthIndex == elmIndex) {
						int pktLength = Integer.decode("0x" + value);
						MarshalResult result = new MarshalResult();
						payload = decryptPayload(tmpFile.createInputStream(), pktLength, command, result);
						origPl = result.getOrigPlBytes();
					} 
					if (plIndex == elmIndex) dataItems.add(new Item(elmIndex, Codryptofy.encodeUTF8(payload)));
					else dataItems.add(new Item(elmIndex, value));
				} else if (line.startsWith("</" + Element.ITEM)) {
					layers.pop();
				} else if (line.startsWith("</" + Element.ROOT)) {
					layers.pop();
					break;
				}
			}
			return frame != null ? Optional.of(frame.setBodyBytes(origPl).setCrack(true)) : Optional.empty();
		} catch (IOException e) {
			throw new FrameParsingException(e);
		}
	}
	
	/**
	 * Tamper attr.
	 * @param br
	 * @param line
	 * @param attr
	 * @return value
	 * @throws IOException
	 */
	private String tamperAttr(BufferedReader br, String line, String attr) throws IOException {
		int attrIndex = line.indexOf(attr);
		int start = line.indexOf(Element.DQ, attrIndex);
		if (start == -1) throw new FrameParsingException();
		int end = -1;
		end = line.indexOf(Element.DQ, start + 1);
		if (end != -1) return line.substring(start + 1, end);
		
		StringBuilder sb = new StringBuilder();
		sb.append(line.substring(start + 1));
		String next = null;
		while (end == -1) {
			if (next != null) sb.append(next);
			next = readLine(br);
			if (next == null) break;
			end = next.indexOf(Element.DQ);
		}
		return sb.append(next.substring(0, end)).toString();
	}
	
	/**
	 * Read line.
	 * @param br
	 * @return line text
	 * @throws IOException
	 */
	private String readLine(BufferedReader br) throws IOException {
		StringBuilder sb = new StringBuilder();
		char c;
		while ((c = (char) br.read()) != -1) {
			sb.append(c);
			if (c == '\n' || c == '\r') return sb.toString();
		}
		return null;
	}
}
