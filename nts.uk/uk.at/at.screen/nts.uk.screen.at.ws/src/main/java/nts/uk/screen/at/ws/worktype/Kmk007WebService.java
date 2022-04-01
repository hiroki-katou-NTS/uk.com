package nts.uk.screen.at.ws.worktype;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.worktype.WorkTypeDto;
import nts.uk.screen.at.app.worktype.WorkTypeOut;
import nts.uk.screen.at.app.worktype.WorkTypeProcessor;

/**
 * The Class Work Type Query Web Service.
 * 
 * @author sonnh
 *
 */
@Path("at/screen/worktype")
@Produces("application/json")
public class Kmk007WebService {
	
	@Inject
	private WorkTypeProcessor workTypeProcessor;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("find-hd-ship-kaf022")
	public List<WorkTypeDto> findHdShipKaf022(HDShipKAF022 param){
		return workTypeProcessor.findHdShipKaf022(param.getOneDayAtr(), 
													param.getMorningAtr2(), param.getAfternoon2(), 
													param.getMorningAtr3(), param.getAfternoon3(), 
													param.getMorningAtr4(), param.getAfternoon4(), 
													param.getMorningAtr5(), param.getAfternoon5());
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("find-hdtime-kaf022")
	public List<WorkTypeDto> findHdTimeKaf022(){
		return workTypeProcessor.findHdTimeKaf022();
	}
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("find-bounce-kaf022")
	public List<WorkTypeDto> findBounceKaf022(List<Integer> halfDay){
		return workTypeProcessor.findBounceKaf022(halfDay);
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("find-work-change-kaf022")
	public List<WorkTypeDto> findWkChangeKaf022(){
		return workTypeProcessor.findWkChangeKaf022();
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("find-absence-kaf022")
	public List<WorkTypeDto> findAbsenceKaf022(AbsenceKAF022 param){
		return workTypeProcessor.findAbsenceKaf022(param.getOneDayAtr(), param.getMorningAtr(), param.getAfternoonAtr());
	}

	/**
	 * Find special.
	 *
	 * @return the list
	 */
	@POST
	@Path("find-absence-special-kaf022")
	public List<WorkTypeDto> findAbsenceSpecialKaf022(){
		return workTypeProcessor.findAbsenceSpecialKaf022();
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("find-ot-kaf022")
	public List<WorkTypeDto> findOtKaf022(){
		return workTypeProcessor.findOtKaf022();
	}

	@POST
	@Path("find-business-trip-kaf022")
	public List<WorkTypeDto> findBusinessTripKaf022(List<Integer> oneDayAtrs) {
		return workTypeProcessor.findBusinessTripKaf022(oneDayAtrs);
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public WorkTypeOut findAll(){
		return workTypeProcessor.findWorkTypeAll();
	}
	
	/**
	 * Find work type by 分類.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/dailyworktype")
	public List<WorkTypeDto> findByDailyWorkType(DailyWorkTypeAtrParam param){
		return workTypeProcessor.findWorkTypeByDailyWorkType(param.getDailyWorkTypeAtr());
	}
	
	@POST
	@Path("find/dailyworktypekdw006")
	public List<WorkTypeDto> findDailyWorkType(DailyWorkTypeAtrParam param){
		return workTypeProcessor.findWorkTypeDailyWorkType(param.getDailyWorkTypeAtr());
	}

	/**
	 * 
	 * @return
	 */
	@POST
	@Path("findAllSpe")
	public List<WorkTypeDto> findByDailyWorkTypeSpe(){
		return workTypeProcessor.findWorkTypeSpe();
	}
	
	/**
	 * 
	 * @return
	 */
	@POST
	@Path("findAllDisp")
	public List<WorkTypeDto> findByDailyWorkTypeDisp(){
		return workTypeProcessor.findWorkTypeDisp();
	}
	
	/**
	 * Gets the medical option.
	 *
	 * @return the medical option
	 */
	@POST
	@Path("getMedicalOption")
	public boolean getMedicalOption() {
		return this.workTypeProcessor.getMedicalOption();
	}
}
