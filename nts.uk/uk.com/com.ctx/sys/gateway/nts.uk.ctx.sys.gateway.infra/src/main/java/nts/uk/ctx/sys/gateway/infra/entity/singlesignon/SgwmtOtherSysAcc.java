package nts.uk.ctx.sys.gateway.infra.entity.singlesignon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the SGWMT_OTHER_SYS_ACC database table.
 * 
 */
@Setter
@Getter
@Entity
@Table(name="SGWMT_OTHER_SYS_ACC")
public class SgwmtOtherSysAcc implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SgwmtOtherSysAccPK sgwmtOtherSysAccPK;

	@Column(name="USE_ATR")
	private Integer useAtr;

	public SgwmtOtherSysAcc() {
		super();
	}
	

}