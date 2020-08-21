package nts.uk.screen.at.ws.ksu001;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksu001.changemode.NextMonthFinder;
import nts.uk.screen.at.app.ksu001.changemode.PreMonthFinder;
import nts.uk.screen.at.app.ksu001.changepage.ChangePageParam;
import nts.uk.screen.at.app.ksu001.changepage.GetDataWhenChangePage;
import nts.uk.screen.at.app.ksu001.changepage.GetShiftPalChangePageResult;
import nts.uk.screen.at.app.ksu001.getshiftpalette.GetShiftPalette;
import nts.uk.screen.at.app.ksu001.getshiftpalette.GetShiftPaletteParam;
import nts.uk.screen.at.app.ksu001.getshiftpalette.GetShiftPaletteResult;
import nts.uk.screen.at.app.ksu001.start.StartKSU001;
import nts.uk.screen.at.app.ksu001.start.StartKSU001Dto;
import nts.uk.screen.at.app.ksu001.start.StartKSU001Param;

/**
 * 
 * @author laitv
 *
 */
@Path("screen/at/schedule")
@Produces("application/json")
public class KSU001WebService extends WebService{

	@Inject
	private StartKSU001 startKSU001;
	@Inject
	private NextMonthFinder nextMonthFinder;
	@Inject
	private PreMonthFinder preMonthFinder;
	@Inject
	private GetShiftPalette getShiftPalette;
	@Inject
	private GetDataWhenChangePage getDataWhenChangePage;
	
	@POST
	@Path("start")
	public StartKSU001Dto getDataStartScreen(StartKSU001Param param){
		StartKSU001Dto data = startKSU001.getData(param);
		return data;
	}
	
	@POST
	@Path("shift")
	public StartKSU001Dto getDataShiftMode(StartKSU001Param param){
		StartKSU001Dto data = startKSU001.getData(param);
		return data;
	}
	
	@POST
	@Path("shortname")
	public StartKSU001Dto getDataShortNameMode(StartKSU001Param param){
		StartKSU001Dto data = startKSU001.getData(param);
		return data;
	}
	
	@POST
	@Path("time")
	public StartKSU001Dto getDataTimeMode(StartKSU001Param param){
		StartKSU001Dto data = startKSU001.getData(param);
		return data;
	}
	
	@POST
	@Path("next-month")
	public StartKSU001Dto getDataNextMonth(StartKSU001Param param){
		StartKSU001Dto data = nextMonthFinder.getDataStartScreen(param);
		return data;
	}

	
	@POST
	@Path("pre-month")
	public StartKSU001Dto getDataPreMonth(StartKSU001Param param){
		StartKSU001Dto data = preMonthFinder.getDataStartScreen(param);
		return data;
	}
	
	@POST
	@Path("getShiftPallets") // get cho cả 2 trường hợp company và workPlace , goi sau khi đ
	public GetShiftPaletteResult getShiftPallets(GetShiftPaletteParam param) {
		return getShiftPalette.getDataShiftPallet(param);
	}
	
	@POST
	@Path("change-page") // get cho cả 2 trường hợp company và workPlace , goi sau khi đ
	public GetShiftPalChangePageResult getShiftPallet(ChangePageParam param) {
		return getDataWhenChangePage.gatData(param);
	}

	
	
}
