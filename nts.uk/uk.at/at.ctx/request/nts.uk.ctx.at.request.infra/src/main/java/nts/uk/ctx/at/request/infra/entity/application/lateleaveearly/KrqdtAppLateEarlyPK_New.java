package nts.uk.ctx.at.request.infra.entity.application.lateleaveearly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author anhnm
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrqdtAppLateEarlyPK_New implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String cId;
	
	@Column(name = "APP_ID")
	public String appId;

}
