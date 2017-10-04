package nts.uk.ctx.sys.env.dom.mailserver;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/** POP�?報. */
@Getter
@Setter
public class PopInfo extends DomainObject {
	
	/** IPバ�?�ジョン. */
	private IpVersion ipVersion;
	
	/** サー�? . */
	private Server server;
	
	/** サーバ使用. */
	private UseServer useServer;
	
	/** タイ�?アウト時�?. */
	private TimeOut timeOut;
	
	/** ポ�?��?. */
	private Port port;
	
	/**
	 * 
	 * @param ipVersion
	 * @param server
	 * @param useServer
	 * @param timeOut
	 * @param port
	 */
	public PopInfo(IpVersion ipVersion, Server server, UseServer useServer, TimeOut timeOut, Port port){
		this.ipVersion = ipVersion;
		this.server = server;
		this.useServer = useServer;
		this.timeOut = timeOut;
		this.port = port;
	}
}
