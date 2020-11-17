package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * Refactor5
 * @author hoangnd
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqdtAppOverTimeDetMPK implements Serializable{
	
	public static final long serialVersionUID = 1L;
	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 申請ID
	 */
	@Basic(optional = false)
	@Column(name = "APP_ID")
	public String appId;
	
	@Basic(optional = false)
	@Column(name = "START_YM")
	public Integer startYm;
	
	@Basic(optional = false)
	@Column(name = "END_YM")
	public Integer endYm;
}
