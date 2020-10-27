package nts.uk.ctx.pereg.infra.entity.person.additemdata.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
// 社員情報カテゴリデータ
@Table(name = "PPEMT_EMP_INFO_CTG_DATA")
public class PpemtEmpInfoCtgData extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// レコードID
	@Id
	@Column(name = "RECORD_ID")
	public String recordId;
	
	// 個人情報カテゴリID
	@Basic(optional = false)
	@Column(name = "PER_INFO_CTG_ID")
	public String personInfoCtgId;

	// 社員ID
	@Basic(optional = false)
	@Column(name = "SID")
	public String employeeId;

	@Override
	protected Object getKey() {
		return recordId;
	}

}
