package nts.uk.ctx.at.function.app.nrl.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Content.
 * 
 * @author manhnd
 */
@XmlRootElement(name = Element.ITEM)
@NoArgsConstructor
@AllArgsConstructor
public class Content {
	
	/**
	 * Type
	 */
	private String type;
	
	/**
	 * Items
	 */
	private List<Item> items;

	@XmlAttribute
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = Element.SUBITEM)
	public List<Item> getItems() {
		return this.items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
