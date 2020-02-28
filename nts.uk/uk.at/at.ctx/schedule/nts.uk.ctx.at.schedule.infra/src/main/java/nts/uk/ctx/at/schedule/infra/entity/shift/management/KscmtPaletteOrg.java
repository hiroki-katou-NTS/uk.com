package nts.uk.ctx.at.schedule.infra.entity.shift.management;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPallet;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftRemarks;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
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
		return null;
		/*return new ShiftPalletsOrg(new TargetOrgIdenInfor(new TargetOrganizationUnit(pk.targetUnit), pk.targetId),
				pk.page,
				new ShiftPallet(
						new ShiftPalletDisplayInfor(new ShiftPalletName(pageName),
								EnumAdaptor.valueOf(useAtr, NotUseAtr.class), new ShiftRemarks(note)),
						cmpCombis.stream().map(x -> x.toDomain()).collect(Collectors.toList())));*/
	}
}
