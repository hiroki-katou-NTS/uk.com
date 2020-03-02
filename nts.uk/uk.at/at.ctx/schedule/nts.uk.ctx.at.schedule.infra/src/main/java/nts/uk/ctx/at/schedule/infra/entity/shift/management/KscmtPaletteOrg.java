package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 組織別シフトパレット
 * @author phongtq
 *
 */

@Entity
@Table(name = "KSCMT_PALETTE_ORG")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPaletteOrg extends UkJpaEntity{
	
	@EmbeddedId
	public KscmtPaletteOrgPk pk;
	
	/** 名称 */
	@Column(name = "PAGE_NAME")
	public String pageName;
	
	/** 使用区分 */
	@Column(name = "USE_ATR")
	public int useAtr;
	
	/** 備考 */
	@Column(name = "NOTE")
	public String note;

	@Override
	protected Object getKey() {
		return pk;
	}

	public static KscmtPaletteOrg fromDomain(ShiftPalletsOrg shiftPalletsOrg ){
		Optional <String> workplaceID = Optional.empty();
		if (shiftPalletsOrg.getTargeOrg().getUnit().value == 0 )
			workplaceID = shiftPalletsOrg.getTargeOrg().getWorkplaceId();
		else
			workplaceID = shiftPalletsOrg.getTargeOrg().getWorkplaceGroupId();
		KscmtPaletteOrgPk pk = new KscmtPaletteOrgPk(AppContexts.user().companyId(), shiftPalletsOrg.getTargeOrg().getUnit().value ,workplaceID.get(),shiftPalletsOrg.getPage());
		
		return new KscmtPaletteOrg(pk, shiftPalletsOrg.getShiftPallet().getDisplayInfor().getShiftPalletName().v(), shiftPalletsOrg.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value, shiftPalletsOrg.getShiftPallet().getDisplayInfor().getRemarks().v());
	}
	
	public ShiftPalletsOrg toDomain() {
		String workplaceId = null;
		String groupWorkplaceId = null;
		if (pk.targetUnit == 0) {
			workplaceId = pk.targetId;
		} else if (pk.targetUnit == 1)
			groupWorkplaceId = pk.targetId;
		return new ShiftPalletsOrg(
				new TargetOrgIdenInfor(EnumAdaptor.valueOf(pk.targetUnit, TargetOrganizationUnit.class), workplaceId,
						groupWorkplaceId),
				pk.page, null);
	}
}
