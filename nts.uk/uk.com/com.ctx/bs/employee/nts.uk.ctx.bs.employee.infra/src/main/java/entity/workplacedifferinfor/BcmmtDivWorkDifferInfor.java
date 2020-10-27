package entity.workplacedifferinfor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BSYMT_DEP_WKP_DIFFERENT")
public class BcmmtDivWorkDifferInfor extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BcmmtDivWorkDifferInforPK bcmmtDivWorkDifferInforPK;
	
	/** 職場登録区分 */
	@Column(name = "WKP_REG_ATR")
	public int regWorkDiv;
	
	@Override
	protected Object getKey() {
		return bcmmtDivWorkDifferInforPK;
	}
	
	public BcmmtDivWorkDifferInfor(BcmmtDivWorkDifferInforPK bcmmtDivWorkDifferInforPK){
		super();
		this.bcmmtDivWorkDifferInforPK = bcmmtDivWorkDifferInforPK;
	}
}
