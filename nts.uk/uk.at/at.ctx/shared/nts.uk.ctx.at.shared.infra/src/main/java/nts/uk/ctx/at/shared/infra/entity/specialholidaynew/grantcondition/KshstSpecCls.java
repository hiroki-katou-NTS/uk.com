package nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 分類一覧
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPEC_CLS")
public class KshstSpecCls extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstSpecClsPK pk;
	
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false),
    	@JoinColumn(name="SPEC_LEAVE_CD", referencedColumnName="SPEC_LEAVE_CD", insertable = false, updatable = false)
    })
	public KshstSpecialLeaveRestriction specialLeaveRestriction;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
