package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.createdailyresults.CreateDailyResults;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck.CalculationErrorCheckService;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.TemporarilyReflectStampDailyAttd;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ICorrectionAttendanceRule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeReflectFromWorkinfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectStampDomainServiceImpl implements ReflectStampDomainService {


	@Inject
	private StampDomainService stampDomainService;

	@Inject
	private TimeReflectFromWorkinfo timeReflectFromWorkinfo;
	
	@Inject
	private DailyRecordConverter dailyRecordConverter;
	
	@Inject
	private CreateDailyResults createDailyResults;
	
	@Inject
	private CalculationErrorCheckService calculationErrorCheckService;
	
	@Inject
	private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;
	
	@Inject
	private ICorrectionAttendanceRule iCorrectionAttendanceRule;
	
	@Override
	public OutputAcquireReflectEmbossingNew acquireReflectEmbossingNew(String companyID, String employeeID,
			GeneralDate processingDate, ExecutionTypeDaily executionType, EmbossingExecutionFlag flag,
			IntegrationOfDaily integrationOfDaily, ChangeDailyAttendance changeDailyAtt,CacheCarrier carrier) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		
		//??????????????????????????????????????????????????????
		OutputTimeReflectForWorkinfo outputTimeReflectForWorkinfo = timeReflectFromWorkinfo.get(companyID, employeeID, processingDate,
				integrationOfDaily.getWorkInformation());
		
		if(!outputTimeReflectForWorkinfo.getError().isEmpty()) {
			return new OutputAcquireReflectEmbossingNew(listErrorMessageInfo, new ArrayList<>(), integrationOfDaily, changeDailyAtt);
		}
		
		switch(outputTimeReflectForWorkinfo.getEndStatus()) {
		case NO_WORK_TIME:
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("022"), new ErrMessageContent(TextResource.localize("Msg_591"))));
			break;
		case NO_WORK_TYPE :
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("023"), new ErrMessageContent(TextResource.localize("Msg_590"))));
			break;
		case NO_HOLIDAY_SETTING:
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("023"), new ErrMessageContent(TextResource.localize("Msg_1678"))));
			break;
		case NO_WORK_CONDITION:
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("023"), new ErrMessageContent(TextResource.localize("Msg_430"))));
			break;
		default: //NORMAL
		}
		if(!listErrorMessageInfo.isEmpty()) {
			return new OutputAcquireReflectEmbossingNew(listErrorMessageInfo, new ArrayList<>(), integrationOfDaily, changeDailyAtt);
		}
		
		if(outputTimeReflectForWorkinfo.getStampReflectRangeOutput().getStampRange().getStart() == null ||
				outputTimeReflectForWorkinfo.getStampReflectRangeOutput().getStampRange().getEnd() == null) {
			listErrorMessageInfo.add(new ErrorMessageInfo(companyID, employeeID, processingDate, ExecutionContent.DAILY_CREATION,
					new ErrMessageResource("022"), new ErrMessageContent(TextResource.localize("Msg_591"))));
			return new OutputAcquireReflectEmbossingNew(listErrorMessageInfo, new ArrayList<>(), integrationOfDaily, changeDailyAtt);
		}
		
		//?????????????????????
		List<Stamp> lstStamp = this.stampDomainService.handleDataNew(outputTimeReflectForWorkinfo.getStampReflectRangeOutput(),
                processingDate, employeeID, companyID,flag, carrier);
		if (!lstStamp.isEmpty()) {

			// ????????????????????????????????????????????????
			// ?????????????????????????????????????????????????????????
			DailyRecordToAttendanceItemConverter converter = dailyRecordConverter.createDailyConverter()
					.setData(integrationOfDaily).completed();

			// ???????????????????????????????????????
			for (Stamp stamp : lstStamp) {
				// ??????????????????????????????
				if (stamp.getImprintReflectionStatus().canReflectedOnTargetDate(processingDate)) {
					// ?????????????????????
					List<ErrorMessageInfo> listE = temporarilyReflectStampDailyAttd.reflectStamp(companyID, stamp,
							outputTimeReflectForWorkinfo.getStampReflectRangeOutput(), integrationOfDaily,
							changeDailyAtt);
					// do thu???t to??n ???????????????????????????????????????????????????????????? c?? th??? t???o ra nhi???u l???i gi???ng nhau, n??n
					// c???n b??? nh???ng l???i gi???ng nhau trong 1 ng??y.
					listE = listE.stream().distinct().collect(Collectors.toList());
					listErrorMessageInfo.addAll(listE);
				}
			}
			// ??????????????????????????????ID?????????????????????
			List<Integer> attendanceItemIdList = integrationOfDaily.getEditState().stream()
					.filter(c -> c.getEditStateSetting() != EditStateSetting.REFLECT_APPLICATION)
					.map(editState -> editState.getAttendanceItemId()).distinct().collect(Collectors.toList());
			List<ItemValue> listItemValue = converter.convert(attendanceItemIdList);
			// ??????????????????????????????????????????
			if (!attendanceItemIdList.isEmpty()) {
				integrationOfDaily = createDailyResults.restoreData(converter, integrationOfDaily, listItemValue);
			}
		}
		//??????????????????????????????
		integrationOfDaily = iCorrectionAttendanceRule.process(integrationOfDaily, changeDailyAtt);
		// ?????????????????????
		integrationOfDaily = calculationErrorCheckService.errorCheck(companyID, employeeID, processingDate, integrationOfDaily, true);
		
		return new OutputAcquireReflectEmbossingNew(listErrorMessageInfo,lstStamp,integrationOfDaily, changeDailyAtt);
	}
	
	
}
