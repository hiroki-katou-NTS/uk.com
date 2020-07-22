/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getshiftpalette;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgDto;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.ComPatternScreenDto;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.PatternItemScreenDto;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.WorkPairSetScreenDto;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * <<ScreenQuery>> シフトパレットを取得する
 *
 */

@Stateless
public class GetShiftPalette {
	
	@Inject
	private ShiftPalletsComRepository shiftPalletsComRepository;
	@Inject
	private ShiftPalletsOrgRepository shiftPalletsOrgRepository;
	
	public GetShiftPaletteResult getData(GetShiftPaletteParam param) {
		
		GetShiftPaletteResult result = new GetShiftPaletteResult();
		if (param.shiftPalletUnit == ShiftPalletUnit.COMPANY.value) {
			
			result = getShiftPalletCom(param);
			
		}else if (param.shiftPalletUnit == ShiftPalletUnit.WORKPLACE.value) {
			
			result = getbyWorkPlaceId(param);
		}
		
		
		
		
		return result;
	}
	
	public GetShiftPaletteResult getShiftPalletCom(GetShiftPaletteParam param) {
		String companyId = AppContexts.user().companyId();
		// step 1.1
		List<ShiftPalletsCom> listShiftPalletsCom = shiftPalletsComRepository.findShiftPalletUse(companyId);
		
		List<PageInfo> listPageInfo = new ArrayList<>(); // List<ページ, 名称>
		Optional<TargetShiftPalette> targetShiftPalette = Optional.empty(); // 対象のシフトパレット： Optional<ページ, シフトパレット>
		
		if (listShiftPalletsCom.isEmpty()) {
			return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>());
		}
		
		
		for (int i = 0; i < listShiftPalletsCom.size(); i++) {
			ShiftPalletsCom shiftPalletsCom = listShiftPalletsCom.get(i);
			listPageInfo.add(new PageInfo(shiftPalletsCom.getPage(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v()));
		}
		
		if (param.dataLocalstorageEmpty) {
			ShiftPalletsCom shiftPalletsCom = listShiftPalletsCom.stream().filter(i -> i.getPage() == 0).findFirst().get();
			ComPatternScreenDto shiftPallet = new ComPatternScreenDto(shiftPalletsCom.getPage(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getRemarks().v(),
					shiftPalletsCom.getShiftPallet().getCombinations().stream()
														.map(d -> new PatternItemScreenDto(d.getPositionNumber(),
																						   d.getCombinationName().v(),
																						   d.getCombinations().stream()
														.map(e -> new WorkPairSetScreenDto(e.getOrder(),
																 						   e.getShiftCode().v()))
																							.collect(Collectors.toList())))			   
																							.collect(Collectors.toList()));
			TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(0, shiftPallet, null);
			targetShiftPalette = Optional.of(targetShiftPalettea);
		}else{
			ShiftPalletsCom shiftPalletsCom = listShiftPalletsCom.stream().filter(i -> i.getPage() == param.getPageNumber()).findFirst().get();
			ComPatternScreenDto shiftPallet = new ComPatternScreenDto(shiftPalletsCom.getPage(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getRemarks().v(),
					shiftPalletsCom.getShiftPallet().getCombinations().stream()
														.map(d -> new PatternItemScreenDto(d.getPositionNumber(),
																						   d.getCombinationName().v(),
																						   d.getCombinations().stream()
														.map(e -> new WorkPairSetScreenDto(e.getOrder(),
																 						   e.getShiftCode().v()))
																							.collect(Collectors.toList())))			   
																							.collect(Collectors.toList()));
			TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(0, shiftPallet, null);
			targetShiftPalette = Optional.of(targetShiftPalettea);
		}
		return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>());									
	}
	
	
	public GetShiftPaletteResult getbyWorkPlaceId(GetShiftPaletteParam param) {
		// 0 = work place
		// step 1.2
		List<ShiftPalletsOrg> listShiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(0, param.getWorkplaceId());
		
		List<PageInfo> listPageInfo = new ArrayList<>(); // List<ページ, 名称>
		Optional<TargetShiftPalette> targetShiftPalette = Optional.empty(); // 対象のシフトパレット： Optional<ページ, シフトパレット>
		
		if (listShiftPalletsOrg.isEmpty()) {
			return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>());
		}
		
		for (int i = 0; i < listShiftPalletsOrg.size(); i++) {
			ShiftPalletsOrg shiftPalletsCom = listShiftPalletsOrg.get(i);
			listPageInfo.add(new PageInfo(shiftPalletsCom.getPage(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v()));
		}
		
		if (param.dataLocalstorageEmpty) {
			ShiftPalletsOrg shiftPalletsOrg = listShiftPalletsOrg.stream().filter(i -> i.getPage() == 0).findFirst().get();
			ShiftPalletsOrgDto shiftPalletsOrgDto = new ShiftPalletsOrgDto(shiftPalletsOrg, param.getWorkplaceId());
			TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(0, null, shiftPalletsOrgDto);
			targetShiftPalette = Optional.of(targetShiftPalettea);
			
		}else{
			ShiftPalletsOrg shiftPalletsOrg = listShiftPalletsOrg.stream().filter(i -> i.getPage() == param.getPageNumber()).findFirst().get();
			ShiftPalletsOrgDto shiftPalletsOrgDto = new ShiftPalletsOrgDto(shiftPalletsOrg, param.getWorkplaceId());
			TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(0, null, shiftPalletsOrgDto);
			targetShiftPalette = Optional.of(targetShiftPalettea);
		}
		
		return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>());	
	}

}
