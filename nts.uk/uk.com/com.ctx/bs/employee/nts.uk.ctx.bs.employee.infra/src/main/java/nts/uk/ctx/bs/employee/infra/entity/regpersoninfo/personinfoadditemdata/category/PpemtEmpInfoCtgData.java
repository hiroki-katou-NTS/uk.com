package nts.uk.ctx.bs.employee.infra.entity.regpersoninfo.personinfoadditemdata.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.time.GeneralDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PPEMT_EMP_INFO_CTG_DATA")
public class PpemtEmpInfoCtgData extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpemtEmpInfoCtgDataPk ppemtEmpInfoCtgDataPk;

	@Basic(optional = false)
	@Column(name = "P_INFO_CTG_ID")
	public GeneralDate personInfoCtgId;

	@Basic(optional = false)
	@Column(name = "S_ID")
	public String employeeId;

	@Override
	protected Object getKey() {

		return ppemtEmpInfoCtgDataPk;
	}

}
