package nts.uk.ctx.at.shared.infra.entity.employmentrules.temporarywork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.employmentrules.temporarywork.TemporaryWorkUseManage;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 臨時勤務利用管理
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_OUTING_MANAGEMENT")
public class KrcdtTemporaryWorkUseManage extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTemporaryWorkUseManagePK krcdtTemporaryWorkUseManagePK;

	@Column(name = "USE_CLASSIFICATION")
	public int useClassification;

	@Override
	protected Object getKey() {
		return this.krcdtTemporaryWorkUseManagePK;
	}

	public TemporaryWorkUseManage toDomain() {
		TemporaryWorkUseManage temporaryWorkUseManage = new TemporaryWorkUseManage(
				this.krcdtTemporaryWorkUseManagePK.companyID,
				EnumAdaptor.valueOf(this.useClassification, NotUseAtr.class));
		return temporaryWorkUseManage;
	}
}
