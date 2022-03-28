package nts.uk.screen.at.ws.dailyperformance.correction.mobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.DailyPerformanceMobileCodeFinder;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.DailyPerformanceCodeDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.screen.at.app.dailymodify.mobile.DailyModifyMobileCommandFacade;
import nts.uk.screen.at.app.dailymodify.mobile.dto.DPMobileAdUpParamDto;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCorrectionInitParam;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataResultAfterIU;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataResultAfterIUDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.InputGetDataOlds;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.InitScreenMob;
import nts.uk.screen.at.app.dailyperformance.correction.mobile.UpdateConfirmAllMob;

@Path("screen/at/correctionofdailyperformance")
@Produces("application/json")
public class DailyPerformanceMobileWebService {

//	@Inject
//	private HttpSubSession session;

	@Inject
	private DailyModifyMobileCommandFacade dailyModifyMobiCommandFacade;
	
	@Inject
	private InitScreenMob initScreenMob;
	
	@Inject
	private UpdateConfirmAllMob updateConfirmAllMob;
	
	@Inject
	private DailyPerformanceMobileCodeFinder dailyPerformanceMobileCodeFinder;
	
	@Inject
	private DailyPerformanceCorrectionProcessor processor;

	@POST
	@Path("addUpMobile")
	@SuppressWarnings("unchecked")
	public DataResultAfterIUDto addAndUpdate(DPMobileAdUpParamDto param) {
		List<DailyRecordDto> domainOlds =  this.getDataOld(param.getDataSessionDto().getInputGetDataOlds());
		//TODO: val domain  = param.getDataSessionDto().getDomainEdits();
		List<DailyRecordDto> dailyEdits = new ArrayList<>();
//		if (domain == null) {
			dailyEdits = cloneListDto(domainOlds);
		//TODO: 
//		} else {
//			dailyEdits = (List<DailyRecordDto>) domain;
//		}
		param.getDpMobileAdUpParam().setDailyEdits(dailyEdits);
		param.getDpMobileAdUpParam().setDailyOlds(domainOlds);
		//TODO: param.getDpMobileAdUpParam().setDailyOldForLog(param.getDataSessionDto().getDomainOldForLog());
		param.getDpMobileAdUpParam().setLstAttendanceItem(param.getDataSessionDto().getItemIdRCs());
		param.getDpMobileAdUpParam().setApprovalConfirmCache(param.getDataSessionDto().getApprovalConfirmCache());
		param.getDpMobileAdUpParam().setStateParam(param.getDataSessionDto().getDpStateParam());
		Object objectCacheMonth = param.getDataSessionDto().getDomainMonthOpt();
		Optional<MonthlyRecordWorkDto> domainMonthOpt = objectCacheMonth == null ? Optional.empty()
				: (Optional<MonthlyRecordWorkDto>) objectCacheMonth;
		param.getDpMobileAdUpParam().setDomainMonthOpt(domainMonthOpt);
		DataResultAfterIU dataResultAfterIU = dailyModifyMobiCommandFacade.insertItemDomain(param.getDpMobileAdUpParam());
		// TODO: set cache month
		if (dataResultAfterIU.getDomainMonthOpt().isPresent()) {
			param.getDataSessionDto().setDomainMonthOpt(dataResultAfterIU.getDomainMonthOpt().get());
		}
		DataResultAfterIUDto result = new DataResultAfterIUDto(dataResultAfterIU, param.getDataSessionDto());
		return result;
	}

	private List<DailyRecordDto> getDataOld(InputGetDataOlds inputGetDataOlds) {
		Set<Pair<String, GeneralDate>> setErrorEmpDate = new HashSet<>();
		 
		if(inputGetDataOlds.getDisplayFormat() == 2) {
			 setErrorEmpDate = inputGetDataOlds.getDPErrorDto().stream().map(x -> Pair.of(x.getEmployeeId(), x.getProcessingDate())).collect(Collectors.toSet());
		}
		List<DailyRecordDto> domainOld =  processor.getDailyRecordOldDto(inputGetDataOlds.getEmployeeIds(),
				inputGetDataOlds.getDateRange(), setErrorEmpDate);
		return domainOld;
	}
	
	@POST
	@Path("initMOB")
	public DailyPerformanceCorrectionDto initScreen(DPCorrectionInitParam param) throws InterruptedException{
//		param.dpStateParam = (DPCorrectionStateParam)session.getAttribute("dpStateParam");
		param.dpStateParam = param.dpStateParamSession;
		DailyPerformanceCorrectionDto dtoResult = this.initScreenMob.initMOB(param);
//		session.setAttribute("dpStateParam", dtoResult.getStateParam());
		if (dtoResult.getErrorInfomation() != 0 || !dtoResult.getErrors().isEmpty()) {
			return dtoResult;
		}
		
		//
		DataSessionDto dataSessionDto = new DataSessionDto();
		dataSessionDto.setDpStateParam(dtoResult.getStateParam());
		//dataSessionDto.setDomainOlds(dtoResult.getDomainOld());
		//TODO: dataSessionDto.setDomainOldForLog(cloneListDto(dtoResult.getDomainOld()));
		//TODO: dataSessionDto.setDomainEdits(null);
		dataSessionDto.setItemIdRCs(dtoResult.getLstControlDisplayItem() == null ? null : dtoResult.getLstControlDisplayItem().getMapDPAttendance());
		dataSessionDto.setDataSource(dtoResult.getLstData());
		dataSessionDto.setClosureId(dtoResult.getClosureId());
		dataSessionDto.setResultReturn(null);
		dataSessionDto.setApprovalConfirmCache(dtoResult.getApprovalConfirmCache());
		dataSessionDto.setLstSidDateErrorCalc(Collections.emptyList());
		dataSessionDto.setErrorAllCalc(false);
		dtoResult.setDataSessionDto(dataSessionDto);
		
//		session.setAttribute("domainOlds", dtoResult.getDomainOld());		
		//add
//		session.setAttribute("domainOldForLog", cloneListDto(dtoResult.getDomainOld()));
//		session.setAttribute("domainEdits", null);
//		session.setAttribute("itemIdRCs", dtoResult.getLstControlDisplayItem() == null ? null : dtoResult.getLstControlDisplayItem().getMapDPAttendance());
//		session.setAttribute("dataSource", dtoResult.getLstData());
//		session.setAttribute("closureId", dtoResult.getClosureId());
//		session.setAttribute("resultReturn", null);
//		session.setAttribute("approvalConfirm", dtoResult.getApprovalConfirmCache());
		dtoResult.setApprovalConfirmCache(null);
		dtoResult.setLstCellState(dtoResult.getMapCellState().values().stream().collect(Collectors.toList()));
		dtoResult.setMapCellState(null);
//		removeSession();
		dtoResult.setDomainOld(Collections.emptyList());
		return dtoResult;
	}
	
	@POST
	@Path("confirmAll")
	@SuppressWarnings("unchecked")
	public void confirmAll(ConfirmAllInput dataCheckSign) throws InterruptedException{
//		List<DailyRecordDto> dailyRecordDtos = (List<DailyRecordDto>) session.getAttribute("domainOlds");
		updateConfirmAllMob.confirmAll(dataCheckSign.getListDPItemCheckBox(), dataCheckSign.getDailyRecordDtos());
		return;
	}
	
	private List<DailyRecordDto> cloneListDto(List<DailyRecordDto> dtos) {
		if (dtos == null)
			return new ArrayList<>();
		return dtos.stream().map(x -> x.clone()).collect(Collectors.toList());
	}
	
//	private void removeSession() {
//		session.setAttribute("lstSidDateErrorCalc", Collections.emptyList());
//		session.setAttribute("errorAllCalc", false);
//	}
	
	@POST
	@Path("getFormatList")
	public List<DailyPerformanceCodeDto> getAuthorityDailyFormat() {
		return this.dailyPerformanceMobileCodeFinder.findAll();
	}

}
