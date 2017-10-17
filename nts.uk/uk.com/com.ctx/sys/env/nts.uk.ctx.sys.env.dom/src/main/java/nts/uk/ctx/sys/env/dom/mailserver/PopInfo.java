/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailserver;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class PopInfo.
 */
@Getter
@Setter
// POP情報
public class PopInfo extends DomainObject {
	
	/** The ip version. */
	// IPバージョン
	private IpVersion ipVersion;
	
	/** The server. */
	// サーバ
	private Server server;
	
	/** The use server. */
	// サーバ使用
	private UseServer useServer;
	
	/** The time out. */
	// タイムアウト時間
	private TimeOut timeOut;
	
	/** The port. */
	// ポート
	private Port port;
	
	/**
	 * Instantiates a new pop info.
	 *
	 * @param ipVersion the ip version
	 * @param server the server
	 * @param useServer the use server
	 * @param timeOut the time out
	 * @param port the port
	 */
	public PopInfo(IpVersion ipVersion, Server server, UseServer useServer, TimeOut timeOut, Port port){
		this.ipVersion = ipVersion;
		this.server = server;
		this.useServer = useServer;
		this.timeOut = timeOut;
		this.port = port;
	}
}
