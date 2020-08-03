package nts.uk.ctx.at.function.app.nrlremote.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.app.nrl.xml.Element;

/**
 * @author ThanhNX
 *
 *         NRLリモートのデータXml
 */
@XmlRootElement(name = NRLRemoteElement.ROOT)
@NoArgsConstructor
@AllArgsConstructor
public class NRLRemoteDataXml {

	// NRL_No fix
	private String type;

	@XmlElement(name = Element.ITEM)
	private String payload;

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

}
