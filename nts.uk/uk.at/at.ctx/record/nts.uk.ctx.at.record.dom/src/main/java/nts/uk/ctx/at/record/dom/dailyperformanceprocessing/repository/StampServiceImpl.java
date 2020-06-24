package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectRangeOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.TimeZoneOutput;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardItem;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardtemRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class StampServiceImpl implements StampDomainService {

	@Inject
	private ErrMessageInfoRepository errRepo;
	@Inject
	private StampCardtemRepository stampCardRepo;
	
	@Inject
	private StampDakokuRepository stampDakokuRepository;

	//打刻を取得する
    public List<Stamp> handleData(StampReflectRangeOutput s,
            String empCalAndSumExecLogID, GeneralDate date, String employeeId, String companyId,RecreateFlag recreateFlag) {
		if (s.getLstStampReflectTimezone().isEmpty()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId, empCalAndSumExecLogID,
					new ErrMessageResource("009"), EnumAdaptor.valueOf(0, ExecutionContent.class), date,
					new ErrMessageContent(TextResource.localize("Msg_466")));
			errRepo.add(employmentErrMes);
			return null;
		}
		List<StampCardItem> lstStampCardItem = this.stampCardRepo.findByEmployeeID(employeeId);
		ArrayList<String> stampNumber = new ArrayList<String>();
		if (lstStampCardItem != null) {
			int stampCardSize = lstStampCardItem.size();
			if (stampCardSize > 0) {
				if (stampCardSize > 10) {
					stampCardSize = 10;
				}
				for (int i = 0; i < stampCardSize; i++) {
					stampNumber.add(lstStampCardItem.get(i).getCardNumber().v());
				}

			}
		}

		// if (stampNumber == null || stampNumber.isEmpty()) {
		// ErrMessageInfo employmentErrMes = new ErrMessageInfo(employeeId,
		// empCalAndSumExecLogID,
		// new ErrMessageResource("008"), EnumAdaptor.valueOf(0,
		// ExecutionContent.class), date,
		// new ErrMessageContent(TextResource.localize("Msg_433")));
		// errRepo.add(employmentErrMes);
		// return null;
		// }

//		List<StampItem> lstStampItemOutput = new ArrayList<StampItem>();
		List<Stamp> lstStampOutput = new ArrayList<>();

		if (stampNumber != null && !stampNumber.isEmpty()) {

			List<Stamp> listStamp = stampDakokuRepository.getByListCard(stampNumber);
            if (recreateFlag != RecreateFlag.CREATE_DAILY) {
            	for(Stamp x :listStamp){
					int attendanceClock = x.getStampDateTime().clockHourMinute().v();
					TimeZoneOutput stampRange = s.getStampRange();
					if (x.getStampDateTime().year()==date.year()&& x.getStampDateTime().month() == date.month() && x.getStampDateTime().day() == date.day()
							&& attendanceClock >= stampRange.getStart().v().intValue()
							&& attendanceClock <= stampRange.getEnd().v().intValue()
							&& x.isReflectedCategory() == false) {//打刻．反映済み区分　=　false
						lstStampOutput.add(x);
					}
					lstStampOutput.addAll(findStempItemNext(listStamp, date.addDays(1), stampRange, x.isReflectedCategory()));
				}
            	listStamp = listStamp.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
            	listStamp.sort(Comparator.comparing(Stamp::getStampDateTime));

				return lstStampOutput;
			}
             
			for(Stamp x : listStamp) {
				int attendanceClock = x.getStampDateTime().clockHourMinute().v();
				TimeZoneOutput stampRange = s.getStampRange();
				if (x.getStampDateTime().year()==date.year()&& x.getStampDateTime().month() == date.month() && x.getStampDateTime().day() == date.day()
						&& attendanceClock >= stampRange.getStart().v().intValue()
						&& attendanceClock <= stampRange.getEnd().v().intValue()) {
					lstStampOutput.add(x);
				}
				lstStampOutput.addAll(findStempItemNext(listStamp, date.addDays(1), stampRange, x.isReflectedCategory()));
			};
			lstStampOutput = lstStampOutput.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
			lstStampOutput.sort(Comparator.comparing(Stamp::getStampDateTime));
		}
		return lstStampOutput;
	}

	private List<Stamp> findStempItemNext(List<Stamp> lstStamp, GeneralDate tomorow, TimeZoneOutput stampRange, boolean reflectedAtr){
		List<Stamp> lstStampResult = lstStamp.stream().filter(x -> x.getStampDateTime().year() == tomorow.year()
				&& x.getStampDateTime().month() == tomorow.month() && x.getStampDateTime().day() == tomorow.day() 
				&& x.getStampDateTime().clockHourMinute().v() >= 0
				&& x.getStampDateTime().clockHourMinute().v() <= stampRange.getStart().v().intValue()
				&& (reflectedAtr == true)).map(x ->{
					x.setAttendanceTime(new AttendanceTime(x.getStampDateTime().clockHourMinute().v() + 1440));
					return x;
				}).collect(Collectors.toList());
		
		return lstStampResult;
	}
}
