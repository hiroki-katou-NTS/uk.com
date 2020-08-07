package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author ThanhNX
 *
 *         NRLリモートのデータXml
 */
@XmlRootElement(name = NRLRemoteElement.ROOT)
@NoArgsConstructor
@AllArgsConstructor
public class NRLRemoteDataXml {

	private String mac;

	private String payload;

	@XmlElement(name = NRLRemoteElement.PAYLOAD)
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@XmlElement(name = NRLRemoteElement.MAC)
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

}
