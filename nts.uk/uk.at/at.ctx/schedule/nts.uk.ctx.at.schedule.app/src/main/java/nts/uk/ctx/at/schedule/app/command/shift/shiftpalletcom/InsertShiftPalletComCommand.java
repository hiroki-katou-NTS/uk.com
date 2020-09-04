package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftCombinationName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPallet;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftRemarks;
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
	
	public ShiftPalletsCom toDomain() {
		return new ShiftPalletsCom(AppContexts.user().companyId(), groupNo, new ShiftPallet(
				new ShiftPalletDisplayInfor(new ShiftPalletName(groupName),
						EnumAdaptor.valueOf(groupUsageAtr, NotUseAtr.class), new ShiftRemarks(note)),
				listInsertPatternItemCommand.stream()
						.map(c -> new ShiftPalletCombinations(c.patternNo, new ShiftCombinationName(c.patternName),
								c.listInsertWorkPairSetCommand.stream()
										.map(d -> new Combinations(d.pairNo, new ShiftMasterCode(d.shiftCode)))
										.collect(Collectors.toList())))
						.collect(Collectors.toList())));

	}
	
	public ShiftPalletsOrg toDom(){	
		return new ShiftPalletsOrg(
				new TargetOrgIdenInfor(EnumAdaptor.valueOf(unit, TargetOrganizationUnit.class) , Optional.of(workplaceId), Optional.of(workplaceId)),
				groupNo, new ShiftPallet(
						new ShiftPalletDisplayInfor(new ShiftPalletName(groupName),
								EnumAdaptor.valueOf(groupUsageAtr, NotUseAtr.class), new ShiftRemarks(note)),
						listInsertPatternItemCommand.stream()
								.map(c -> new ShiftPalletCombinations(c.patternNo, new ShiftCombinationName(c.patternName),
										c.listInsertWorkPairSetCommand.stream()
												.map(d -> new Combinations(d.pairNo, new ShiftMasterCode(d.shiftCode)))
												.collect(Collectors.toList())))
								.collect(Collectors.toList())));
	}
	
}
