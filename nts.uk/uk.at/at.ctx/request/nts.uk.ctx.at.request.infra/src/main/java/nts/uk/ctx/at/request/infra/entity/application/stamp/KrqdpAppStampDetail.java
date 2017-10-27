package nts.uk.ctx.at.request.infra.entity.application.stamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqdpAppStampDetail {

	@Column(name="CID")
	public String companyID;
	
	@Column(name="APP_ID")
	public String appID;
	
	@Column(name="STAMP_REQUEST_MODE")
	public Integer stampRequestMode;
	
	@Column(name="STAMP_ATR")
	public Integer stampAtr;
	
	@Column(name="STAMP_FRAME_NO")
	public Integer stampFrameNo;
	
}
