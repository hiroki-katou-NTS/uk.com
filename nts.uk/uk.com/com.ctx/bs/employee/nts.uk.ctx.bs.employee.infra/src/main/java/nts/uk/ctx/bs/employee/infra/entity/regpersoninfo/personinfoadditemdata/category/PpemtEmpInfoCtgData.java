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

@Entity
@AllArgsConstructor
@NoArgsConstructor
// 社員情報カテゴリデータ
@Table(name = "PPEMT_EMP_INFO_CTG_DATA")
public class PpemtEmpInfoCtgData extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpemtEmpInfoCtgDataPk ppemtEmpInfoCtgDataPk;

	// 個人情報カテゴリID
	@Basic(optional = false)
	@Column(name = "PER_INFO_CTG_ID")
	public String personInfoCtgId;

	// 社員ID
	@Basic(optional = false)
	@Column(name = "S_ID")
	public String employeeId;

	@Override
	protected Object getKey() {

		return ppemtEmpInfoCtgDataPk;
	}

}
