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
	
	/** 締めID */
	@Column(name = "CLOSURE_ID")
	@Basic(optional = false)
	public int closureId;

	/** 締め日.日 */
	@Column(name = "CLOSURE_DAY")
	@Basic(optional = false)
	public int closeDay;

	/** 締め日.末日とする */
	@Column(name = "IS_LAST_DAY")
	@Basic(optional = false)
	public int isLastDay;
	
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
