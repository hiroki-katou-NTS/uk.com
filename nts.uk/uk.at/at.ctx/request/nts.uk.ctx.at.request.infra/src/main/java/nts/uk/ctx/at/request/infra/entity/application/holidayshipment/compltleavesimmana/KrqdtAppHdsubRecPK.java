package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.compltleavesimmana;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable

/**
 * 振休振出同時申請管理PK
 * 
 * @author sonnlb
 */
public class KrqdtAppHdsubRecPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "CID")
	private String cId;
	
	@Basic(optional = false)
	@Column(name = "REC_APP_ID")
	private String recAppID;

	@Basic(optional = false)
	@Column(name = "HDSUB_APP_ID")
	private String absenceLeaveAppID;

}
