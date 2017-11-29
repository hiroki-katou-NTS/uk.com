package nts.uk.ctx.bs.employee.infra.entity.employee.mngdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BSYMT_AFF_COM_INFO")
public class BsymtAffCompanyInfo extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BsymtAffCompanyInfoPk bsymtAffCompanyInfoPk;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "EMP_CODE")
	public String employeeCode;

	@Column(name = "DEL_STATUS")
	public int delStatus;

	@Column(name = "DEL_DATE")
	public GeneralDate delDateTmp;

	@Column(name = "REMV_REASON")
	public String removeReason;

	@Column(name = "EXT_CODE")
	public String extCode;

	@Override
	protected Object getKey() {
		return bsymtAffCompanyInfoPk;
	}
}
*/