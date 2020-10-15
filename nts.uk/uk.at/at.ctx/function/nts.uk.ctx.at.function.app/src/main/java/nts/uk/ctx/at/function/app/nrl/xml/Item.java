package nts.uk.ctx.at.function.app.nrl.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Item.
 * 
 * @author manhnd
 */
@XmlRootElement(name = Element.SUBITEM)
public class Item {

	/**
	 * Index
	 */
	private int index;
	
	/**
	 * Value 
	 */
	private String value;
	
	public Item() {}
	
	public Item(int index, String value) {
		this.index = index;
		this.value = value;
	}

	@XmlAttribute
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	@XmlAttribute	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
