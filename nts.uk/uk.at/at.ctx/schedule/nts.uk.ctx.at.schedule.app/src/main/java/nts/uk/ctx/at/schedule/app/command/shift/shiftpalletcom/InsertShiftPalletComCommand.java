package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftCombinationName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPalette;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCom;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftRemarks;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 
 * @author hieult
 *
 */
public class InsertShiftPalletComCommand {
	
	public int unit;
	public String workplaceId;
	public int groupNo;
	public String groupName;
	public int groupUsageAtr;
	public String note;
	public List<InsertShiftPalletCommand> listInsertPatternItemCommand;
	
	public ShiftPaletteCom toDomain() {
		return new ShiftPaletteCom(AppContexts.user().companyId(), groupNo, new ShiftPalette(
				new ShiftPaletteDisplayInfor(new ShiftPaletteName(groupName),
						EnumAdaptor.valueOf(groupUsageAtr, NotUseAtr.class), new ShiftRemarks(note)),
				listInsertPatternItemCommand.stream()
						.map(c -> new ShiftPaletteCombinations(c.patternNo, new ShiftCombinationName(c.patternName),
								c.listInsertWorkPairSetCommand.stream()
										.map(d -> new Combinations(d.pairNo, new ShiftMasterCode(d.shiftCode)))
										.collect(Collectors.toList())))
						.collect(Collectors.toList())));

	}
	
	public ShiftPaletteOrg toDom(){	
		return new ShiftPaletteOrg(
				new TargetOrgIdenInfor(EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class) , Optional.of(workplaceId), Optional.of(workplaceId)),
				groupNo, new ShiftPalette(
						new ShiftPaletteDisplayInfor(new ShiftPaletteName(groupName),
								EnumAdaptor.valueOf(groupUsageAtr, NotUseAtr.class), new ShiftRemarks(note)),
						listInsertPatternItemCommand.stream()
								.map(c -> new ShiftPaletteCombinations(c.patternNo, new ShiftCombinationName(c.patternName),
										c.listInsertWorkPairSetCommand.stream()
												.map(d -> new Combinations(d.pairNo, new ShiftMasterCode(d.shiftCode)))
												.collect(Collectors.toList())))
								.collect(Collectors.toList())));
	}
	
}
