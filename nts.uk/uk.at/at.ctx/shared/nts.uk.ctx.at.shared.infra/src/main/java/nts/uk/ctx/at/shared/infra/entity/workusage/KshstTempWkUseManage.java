package nts.uk.ctx.at.shared.infra.entity.workusage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KSHST_TEMP_WK_USE_MANAGE database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KSHST_TEMP_WK_USE_MANAGE")
public class KshstTempWkUseManage extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KshstTempWkUseManagePK id;

	@Column(name="USE_CLASSIFICATION")
	private int useClassification;

	@Override
	protected Object getKey() {
		return id;
	}
}