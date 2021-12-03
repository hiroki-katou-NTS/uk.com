package nts.uk.ctx.at.record.infra.entity.stamp.management;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;
import nts.uk.shr.com.context.AppContexts;

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
public class KrcmtStampEreaLimitSya implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 契約コード */
	@NotNull
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	/** 会社ID */
	@NotNull
	@Column(name = "CID")
	private String cId;
	
	/** 社員ID */
	@Id
	@NotNull
	@Column(name = "SID")
	private String sId;
	
	/** 位置情報を利用する */
	@NotNull
	@Column(name = "LOCATION_INFO_USE", columnDefinition ="0")
	private int locationInforUse;
	
	/** 制限方法 */
	@NotNull
	@Column(name = "AREA_LIMIT_ATR",columnDefinition ="0")
	private int areaLimitAtr;

	
	public StampingAreaRestriction toDomain() {
		StampingAreaRestriction areaRestriction = new StampingAreaRestriction(this.areaLimitAtr, this.locationInforUse);
		return areaRestriction;
	}
	public static KrcmtStampEreaLimitSya toEntity(String emplId,StampingAreaRestriction stampingAreaRestriction) {
		String contractCd = AppContexts.user().contractCode();
		String cId = AppContexts.user().companyId();
		KrcmtStampEreaLimitSya krcmtStampEreaLimitSya = new KrcmtStampEreaLimitSya();
		krcmtStampEreaLimitSya.setContractCd(contractCd);
		krcmtStampEreaLimitSya.setCId(cId);
		krcmtStampEreaLimitSya.setSId(emplId);
		krcmtStampEreaLimitSya.setAreaLimitAtr(stampingAreaRestriction.getStampingAreaLimit().value);
		krcmtStampEreaLimitSya.setLocationInforUse(stampingAreaRestriction.getUseLocationInformation().value);
		return krcmtStampEreaLimitSya;
	}
	
}






