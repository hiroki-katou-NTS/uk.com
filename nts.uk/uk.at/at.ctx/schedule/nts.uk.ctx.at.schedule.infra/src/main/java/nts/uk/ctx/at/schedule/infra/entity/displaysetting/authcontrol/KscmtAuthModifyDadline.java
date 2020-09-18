package nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheAuthModifyDeadline;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CorrectDeadline;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author hiroko_miura
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name = "KSCMT_AUTH_MODIFYDEADLINE")
public class KscmtAuthModifyDadline extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtAuthModifyDadlinePk kscmtAuthModifyDadlinePk;

	/**
	 * 利用区分
	 */
	@Column(name = "USE_ATR")
	public int useAtr;

	/**
	 * 修正期限
	 */
	@Column(name = "DEADLINE")
	public int deadLine;

	@Override
	protected Object getKey() {
		return this.kscmtAuthModifyDadlinePk;
	}
	
	/**
	 * convert to entity
	 * @param domain
	 * @return entity
	 */
	public static KscmtAuthModifyDadline of(String companyId, ScheAuthModifyDeadline domain) {
		KscmtAuthModifyDadlinePk pk = new KscmtAuthModifyDadlinePk(companyId, domain.getRoleId());
		
		KscmtAuthModifyDadline entity = new KscmtAuthModifyDadline(pk
				, domain.getUseAtr().value
				, domain.getDeadLine().v());
		
		return entity;
	}
	
	/**
	 * convert to domain
	 * @param entity
	 * @return domain
	 */
	public static ScheAuthModifyDeadline toDomain(KscmtAuthModifyDadline entity) {
		ScheAuthModifyDeadline domain = new ScheAuthModifyDeadline(entity.kscmtAuthModifyDadlinePk.roleId
				, NotUseAtr.valueOf(entity.useAtr)
				, new CorrectDeadline(entity.deadLine));
		
		return domain;
	}
}
