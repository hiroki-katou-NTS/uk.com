package nts.uk.ctx.sys.assist.infra.entity.salary;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.salary.EmpTiedProYear;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 処理年月に紐づく雇用
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_TIED_PRO_YEAR")
public class QpbmtEmpTiedProYear extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtEmpTiedProYearPk empTiedProYearPk;

	/**
	 * 雇用コード
	 */
	@Basic(optional = false)
	@Column(name = "EMPLOYMENT_CODE")
	public String employmentCode;

	@Override
	protected Object getKey() {
		return empTiedProYearPk;
	}

	public EmpTiedProYear toDomain() {
		return new EmpTiedProYear(this.empTiedProYearPk.cid, this.empTiedProYearPk.processCateNo, this.employmentCode);
	}

	public static QpbmtEmpTiedProYear toEntity(EmpTiedProYear domain) {
		return new QpbmtEmpTiedProYear(new QpbmtEmpTiedProYearPk(domain.getCid(), domain.getProcessCateNo()),
				domain.getEmploymentCode().v());
	}

}
