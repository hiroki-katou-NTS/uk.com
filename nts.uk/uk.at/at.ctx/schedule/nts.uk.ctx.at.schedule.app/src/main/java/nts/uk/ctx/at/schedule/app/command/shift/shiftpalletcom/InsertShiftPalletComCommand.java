package nts.uk.ctx.at.schedule.app.command.shift.shiftpalletcom;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.shift.management.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftCombinationName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPallet;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletCode;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletName;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftRemarks;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertShiftPalletComCommand {
	
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
										.map(d -> new Combinations(d.pairNo, new ShiftPalletCode(d.workTimeCode)))
										.collect(Collectors.toList())))
						.collect(Collectors.toList())));

	}
	
}
