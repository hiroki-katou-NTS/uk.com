package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantcondition;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 分類一覧
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_COND_CLS")
public class KshmtHdspCondCls extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshmtHdspCondClsPK pk;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
