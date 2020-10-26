package nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCom;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteComRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ShiftPalletComFinder {
	@Inject
	private ShiftPaletteComRepository shiftPalletsComRepository;
	
	
	public List<ComPatternScreenDto> getShiftPalletCom() {
		String companyId = AppContexts.user().companyId();
		List<ShiftPaletteCom> listShiftPalletsCom = shiftPalletsComRepository.findShiftPallet(companyId);
		
		List<ComPatternScreenDto> listShiftPalletsComDto = listShiftPalletsCom.stream()
			.map(c -> new ComPatternScreenDto(c.getPage(),
					c.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
					c.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
					c.getShiftPallet().getDisplayInfor().getRemarks().v(),
					c.getShiftPallet().getCombinations().stream()
														.map(d -> new PatternItemScreenDto(d.getPositionNumber(),
																						   d.getCombinationName().v(),
																						   d.getCombinations().stream()
														.map(e -> new WorkPairSetScreenDto(e.getOrder(),
																 						   e.getShiftCode().v()))
																							.collect(Collectors.toList())))			   
																							.collect(Collectors.toList())))
																							.collect(Collectors.toList());
		return listShiftPalletsComDto;									
		
	}
	
	/** 
	 *<<Query>> 会社別シフトパレットの一覧を取得する
	 */
	public List<ShiftPalletComDto> getShiftPaletteByCompany() {
		return shiftPalletsComRepository
				.findShiftPallet(AppContexts.user().companyId())
				.stream().map(i-> new ShiftPalletComDto(i.getPage(), 
						i.getShiftPallet().getDisplayInfor().getShiftPalletName().v()))
				.collect(Collectors.toList());
	}
}
