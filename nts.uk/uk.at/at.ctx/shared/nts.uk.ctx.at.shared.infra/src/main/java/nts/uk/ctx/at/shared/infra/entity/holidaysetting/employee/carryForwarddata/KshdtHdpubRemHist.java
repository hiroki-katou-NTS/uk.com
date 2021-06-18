package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.carryForwarddata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHDT_HDPUB_REM_HIST")
public class KshdtHdpubRemHist extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KshdtHdpubRemHistPK pk;
	
	@Column(name = "ID")
	public int remainmngid;
	
	/** 期限日 */
	@Column(name = "DEADLINE")
	public GeneralDate deadline;
	
	/** 登録種別 */
	@Column(name = "REGISTER_TYPE")
	public int registerType;
	
	/** 繰越数 */
	@Column(name = "CARRIEDFORWARD")
	public int carriedforward;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
