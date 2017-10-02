package nts.uk.ctx.sys.gateway.dom.mailserver;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class SmtpInfo.
 */
@Getter
@Setter
public class SmtpInfo extends DomainObject {
	
	/** The ip version. */
	private IpVersion ipVersion;
	
	/** The server. */
	private Server server;
	
	/** The time out. */
	private TimeOut timeOut;
	
	/** The port. */
	private Port port;
	
	/**
	 * Instantiates a new smtp info.
	 *
	 * @param ipVersion the ip version
	 * @param server the server
	 * @param timeOut the time out
	 * @param port the port
	 */
	public SmtpInfo(IpVersion ipVersion, Server server, TimeOut timeOut, Port port){
		this.ipVersion = ipVersion;
		this.server = server;
		this.timeOut = timeOut;
		this.port = port;
	}
}
