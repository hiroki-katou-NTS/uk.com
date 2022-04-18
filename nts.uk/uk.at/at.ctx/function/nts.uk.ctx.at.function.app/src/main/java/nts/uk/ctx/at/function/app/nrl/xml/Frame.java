package nts.uk.ctx.at.function.app.nrl.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.nrl.crypt.Codryptofy;
import nts.uk.ctx.at.function.app.nrl.exceptions.FrameParsingException;
import nts.uk.ctx.at.function.app.nrl.exceptions.InvalidFrameException;
import nts.uk.ctx.at.function.app.nrl.response.MeanCarryable;

/**
 * Frame.
 * 
 * @author manhnd
 */
@XmlRootElement(name = Element.ROOT)
@NoArgsConstructor
@AllArgsConstructor
public class Frame implements MeanCarryable {
	
	/**
	 * Type
	 */
	private String type;
	
	/**
	 * Content list
	 */
	private List<Content> contents;

	/**
	 * Crack
	 */
	@XmlTransient
	private boolean crack = false;
	
	/**
	 * Body bytes
	 */
	@XmlTransient
	private byte[] bodyBytes;
	
	public Frame(String type, List<Content> contents) {
		this.type = type;
		this.contents = contents;
	}
	
	@XmlAttribute
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement(name = Element.ITEM)
	public List<Content> getContents() {
		return this.contents;
	}
	
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}
	
	public boolean isCracked() {
		return this.crack;
	}
	
	public Frame setCrack(boolean crack) {
		this.crack = crack;
		return this;
	}
	
	public void setBBytes(byte[] bytes) {
		this.bodyBytes = bytes;
	}
	
	public Frame setBodyBytes(byte[] bytes) {
		setBBytes(bytes);
		return this;
	}
	
	@Override
	public void payload(String st) {
		pushBack(Element.PAYLOAD, st);
	}
	
	@Override
	public void bcc(String bcc) {
		pushBack(Element.BCC, bcc);
	}
	
	/**
	 * Push back.
	 * @param name
	 * @param data
	 */
	private void pushBack(String name, String data) {
		if (contents.size() < 2) throw new InvalidFrameException();
		List<Item> head = contents.get(0).getItems();
		List<Item> content = contents.get(1).getItems();
		if (head.size() != content.size()) throw new InvalidFrameException();
		int lastIndex = head.size() - 1;
		if (lastIndex > -1) {
			Item lastItem = head.get(lastIndex);
			if (name.equals(lastItem.getValue())) {
				head.set(lastIndex, new Item(lastIndex, name));
				content.set(lastIndex, new Item(lastIndex, data));
				return;
			}
		}

		head.add(new Item(head.size(), name));
		content.add(new Item(content.size(), data));
	}
	
	/**
	 * Get item.
	 * @param name
	 * @return item
	 */
	public Item getItem(String name) {
		if (contents == null || contents.size() == 0) throw new FrameParsingException();
		Optional<Item> headerItem = contents.get(0).getItems().stream().sorted((i1, i2) -> i1.getIndex() - i2.getIndex())
			.filter(i -> name.equals(i.getValue())).findFirst();
		int index = headerItem.orElseThrow(FrameParsingException::new).getIndex();
		List<Item> dataItems = contents.get(1).getItems();
		if (index < 0 || index >= dataItems.size()) throw new FrameParsingException();
		return dataItems.get(index);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.response.MeanCarryable#putItem(java.lang.String, java.lang.String)
	 */
	@Override
	public void putItem(String name, String data) {
		getItem(name).setValue(data);
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.response.MeanCarryable#pickItem(java.lang.String)
	 */
	@Override
	public String pickItem(String name) {
		return getItem(name).getValue();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.app.nrl.response.MeanCarryable#getBytes(java.util.List)
	 */
	@Override
	public byte[] getBytes(List<String> items) {
		String length = pickItem(Element.LENGTH);
		long len = Long.parseLong(length, 16);
		byte[] byteDatas = new byte[(int)len];
		int i = 0;
		for (String item : items) {
			byte[] bytes =  bodyBytes != null && Element.PAYLOAD.equals(item) ? bodyBytes :
							((Element.NRL_NO.equals(item) || Element.MAC_ADDR.equals(item) ||  Element.CONTRACT_CODE.equals(item)) ?
									asciiCharToBytes(pickItem(item)) : hexStringToBytes(pickItem(item)));
			int arrLen = bytes.length;
			System.arraycopy(bytes, 0, byteDatas, i, arrLen);
			i += arrLen;
		}
		return byteDatas;
	}
	
	/**
	 * Hex string to bytes.
	 * @param s
	 * @return byte array
	 */
	public byte[] hexStringToBytes(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseUnsignedInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}
	
	/**
	 * ASCII char to bytes.
	 * @param s
	 * @return byte array
	 */
	public byte[] asciiCharToBytes(String s) {
		return s.getBytes(Charset.forName("ASCII"));
	}
	
	public String createFormatFrom() {
		StringWriter sw = new StringWriter();
		JAXB.marshal(this, sw);
		String xmlString = sw.toString();
	    BufferedReader reader = new BufferedReader(new StringReader(xmlString));
	    StringBuffer result = new StringBuffer();
	    try {
	    	int lineNumber = 0;
	        String line;
	        while ( (line = reader.readLine() ) != null) {
	        	if(lineNumber != 0) {
	        	String row = line.trim();
	        	if(row.length() >=2 && row.substring(row.length()-2, row.length()).endsWith("/>")) {
	        		if(row.startsWith("<subitem")) {
	        			String endChar = row.substring(0, row.length()-2) + " />";
		        		 result.append(endChar);
	        		}else {
	        			String endChar = line.substring(0, line.length()-2) + " />";
		        		result.append(endChar);
	        		}
	        	}else {
	            result.append(row);
	        	}
	            result.append("\n");
	        	}else {
	        		result.append("<?xml version=\"1.0\" encoding=\"shift_jis\" ?>");
	 	            result.append("\n");
	        		lineNumber++;
	        	}
	        }
            return Codryptofy.convertToShiftJIS(result.toString().trim());
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
}
