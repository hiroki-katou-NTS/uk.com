package nts.uk.ctx.sys.gateway.infra.entity.singlesignon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the SGWMT_WINDOW_ACC database table.
 * 
 */
@Setter
@Getter
@Entity
@Table(name="SGWMT_WINDOW_ACC")
public class SgwmtWindowAcc implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SgwmtWindowAccPK sgwmtWindowAccPK;
		
	@Column(name="HOST_NAME")
	private String hostName;

	@Column(name="USER_NAME")
	private String userName;

	@Column(name="USE_ATR")
	private Integer useAtr;
	

	public SgwmtWindowAcc() {
		super();
	}
	
	

}