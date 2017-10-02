package nts.uk.ctx.sys.gateway.dom.mailserver;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/** POP情報. */
@Getter
@Setter
public class PopInfo extends DomainObject {
	
	/** IPバージョン. */
	private IpVersion ipVersion;
	
	/** サーバ . */
	private Server server;
	
	/** サーバ使用. */
	private UseServer useServer;
	
	/** タイムアウト時間. */
	private TimeOut timeOut;
	
	/** ポート. */
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
