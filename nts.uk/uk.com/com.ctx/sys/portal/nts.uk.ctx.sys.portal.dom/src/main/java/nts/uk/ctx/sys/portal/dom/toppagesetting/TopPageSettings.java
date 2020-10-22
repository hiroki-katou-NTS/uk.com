package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPageSettings.
 * DomainObject トップページ設定
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//TODO AggregateRoot OR DomainObject??
public class TopPageSettings extends AggregateRoot {

	/** 
	 * The top menu code.
	 * 	トップメニューコード
	 **/
	protected TopMenuCode topMenuCode;

	/** 
	 * The menu login. 
	 * 	ログインメニュー 
	 **/
	protected MenuLogin menuLogin;
	
	/** 
	 * The switching date. 
	 * 	切換日
	 **/
	protected SwitchingDate switchingDate;
}
