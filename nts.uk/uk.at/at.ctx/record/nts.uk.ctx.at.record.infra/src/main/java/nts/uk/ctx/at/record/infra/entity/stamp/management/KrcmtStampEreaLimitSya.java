package nts.uk.ctx.at.record.infra.entity.stamp.management;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaLimit;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_STAMP_EREA_LIMIT_SYA")
@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * 
 * @author NWS_vandv
 *
 */
public class KrcmtStampEreaLimitSya extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmStampEreaLimitSyaPK PK;
	/** 契約コード */
	@NotNull
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	/** 会社ID */
	@NotNull
	@Column(name = "CID")
	private String cId;
	
	/** 位置情報を利用する */
	@NotNull
	@Column(name = "LOCATION_INFO_USE")
	private int locationInforUse;
	
	/** 制限方法 */
	@NotNull
	@Column(name = "AREA_LIMIT_ATR")
	private int areaLimitAtr;

	public EmployeeStampingAreaRestrictionSetting toDomain() {
		 NotUseAtr locationInforUse = NotUseAtr.toEnum(this.locationInforUse);
		 StampingAreaLimit areaLimitAtr = StampingAreaLimit.toEnum(this.areaLimitAtr);
		 StampingAreaRestriction areaRestriction = new StampingAreaRestriction(locationInforUse,areaLimitAtr);
		 EmployeeStampingAreaRestrictionSetting areaRestrictionSetting = new EmployeeStampingAreaRestrictionSetting(cId,areaRestriction);
		
		return areaRestrictionSetting;
	}
	public static KrcmtStampEreaLimitSya toEntity(EmployeeStampingAreaRestrictionSetting restrictionSetting) {
		String contractCd = AppContexts.user().contractCode();
		String cId = AppContexts.user().companyId();
		KrcmtStampEreaLimitSya krcmtStampEreaLimitSya = new KrcmtStampEreaLimitSya();
		KrcmStampEreaLimitSyaPK ereaLimitSyaPK = new KrcmStampEreaLimitSyaPK(restrictionSetting.getEmployeeId());
		krcmtStampEreaLimitSya.setContractCd(contractCd);
		krcmtStampEreaLimitSya.setCId(cId);
		krcmtStampEreaLimitSya.setPK(ereaLimitSyaPK);
		krcmtStampEreaLimitSya.setAreaLimitAtr(restrictionSetting.getStampingAreaRestriction().getStampingAreaLimit().value);
		krcmtStampEreaLimitSya.setLocationInforUse(restrictionSetting.getStampingAreaRestriction().getUseLocationInformation().value);
		return krcmtStampEreaLimitSya;
	}
	
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
}






