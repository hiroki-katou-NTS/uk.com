package nts.uk.ctx.at.request.infra.entity.setting.company.applicationcommonsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQST_APP_COMMON_SET")
public class KrqstAppCommonSet extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String companyId;
	/** 所属職場名表示 */
	@Column(name = "SHOW_WKP_NAME_BELONG")
	public int showWkpNameBelong;
	@Override
	protected Object getKey() {
		return companyId;
	}
}
