package nts.uk.ctx.exio.infra.entity.exo.executionlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.executionlog.ExOutOpMng;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 外部出力動作管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_OP_MNG")
public class OiomtExOutOpMng extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExOutOpMngPk exOutOpMngPk;

	/**
	* 
	*/
	@Basic(optional = false)
	@Column(name = "PRO_CNT")
	public int proCnt;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "ERR_CNT")
	public int errCnt;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "TOTAL_PRO_CNT")
	public int totalProCnt;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "DO_NOT_INTERRUPT")
	public int doNotInterrupt;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "PRO_UNIT")
	public String proUnit;

	/**
	* 
	*/
	@Basic(optional = true)
	@Column(name = "OP_COND")
	public int opCond;

	@Override
	protected Object getKey() {
		return exOutOpMngPk;
	}

	public ExOutOpMng toDomain() {
		return new ExOutOpMng(this.exOutOpMngPk.exOutProId, this.proCnt, this.errCnt, this.totalProCnt,
				this.doNotInterrupt, this.proUnit, this.opCond);

	}

	public static OiomtExOutOpMng toEntity(ExOutOpMng domain) {
		return new OiomtExOutOpMng(new OiomtExOutOpMngPk(domain.getExOutProId()), domain.getProCnt(),
				domain.getErrCnt(), domain.getTotalProCnt(), domain.getDoNotInterrupt().value, domain.getProUnit(),
				domain.getOpCond().value);
	}

}
