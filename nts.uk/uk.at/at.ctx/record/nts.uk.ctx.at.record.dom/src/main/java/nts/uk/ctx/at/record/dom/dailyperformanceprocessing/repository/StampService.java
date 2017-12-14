package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.StampReflectRangeOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.TimeZoneOutput;
import nts.uk.ctx.at.record.dom.stamp.StampItem;
import nts.uk.ctx.at.record.dom.stamp.StampRepository;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardItem;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageContent;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.ErrMessageResource;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionContent;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;

public class StampService {
	@Inject
	private ErrMessageInfoRepository errRepo;
	@Inject
	private StampRepository stampRepo;
	@Inject
	private StampCardRepository stampCardRepo;

	private List<String> stampNumber;

	private List<StampItem> lstStampItem;

	private List<StampItem> lstStampItemOutput = new ArrayList<StampItem>();

	public List<StampItem> getLstStampItem() {
		return lstStampItem;
	}

	public void setLstStampItem(List<StampItem> lstStampItem) {
		this.lstStampItem = lstStampItem;
	}

	public List<String> getStampNumber() {
		return stampNumber;
	}

	public void setStampNumber(List<String> stampNumber) {
		this.stampNumber = stampNumber;
	}

	public List<StampItem> handleData(StampReflectRangeOutput s, ExecutionType reCreateAttr,
			String empCalAndSumExecLogID, GeneralDate date, String employeeId, String companyId) {
		if (s == null) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(companyId, empCalAndSumExecLogID,
					new ErrMessageResource("009"), EnumAdaptor.valueOf(0, ExecutionContent.class), date,
					new ErrMessageContent("Msg_466"));
			errRepo.add(employmentErrMes);
			return null;
		}
		List<StampCardItem> lstStampCardItem = this.stampCardRepo.findByEmployeeID(employeeId);
		if (lstStampCardItem != null) {
			int stampCardSize = lstStampCardItem.size();
			if (stampCardSize > 0) {
				List<String> lstStampNum = new ArrayList<String>();
				if (stampCardSize > 10) {
					stampCardSize = 10;
				}
				for (int i = 0; i < stampCardSize; i++) {
					lstStampNum.add(lstStampCardItem.get(i).getCardNumber().v());
				}
				this.setStampNumber(lstStampNum);
			}
		}

		if (this.stampNumber == null || this.stampNumber.isEmpty()) {
			ErrMessageInfo employmentErrMes = new ErrMessageInfo(companyId, empCalAndSumExecLogID,
					new ErrMessageResource("008"), EnumAdaptor.valueOf(0, ExecutionContent.class), date,
					new ErrMessageContent("Msg_433"));
			errRepo.add(employmentErrMes);
			return null;
		}
		this.setLstStampItem(this.stampRepo.findByListCardNo(stampNumber));
		if (reCreateAttr.value == 0) {
			this.lstStampItem.forEach(x -> {
				int attendanceClock = x.getAttendanceTime().v();
				TimeZoneOutput stampRange = s.getStampRange();
				boolean reflectClass = false;
				if (x.getDate().compareTo(date) == 0 && attendanceClock >= stampRange.getStart().v()
						&& attendanceClock <= stampRange.getEnd().v() && reflectClass == false) {
					this.lstStampItemOutput.add(x);
				}

			});
			this.lstStampItemOutput.sort(Comparator.comparing(StampItem::getDate));
			this.lstStampItemOutput.sort(Comparator.comparing(StampItem::getAttendanceTime));

			return this.lstStampItemOutput;
		}

		this.lstStampItem.forEach(x -> {
			int attendanceClock = x.getAttendanceTime().v();
			TimeZoneOutput stampRange = s.getStampRange();
			if (x.getDate().compareTo(date) == 0 && attendanceClock >= stampRange.getStart().v()
					&& attendanceClock <= stampRange.getEnd().v()) {
				this.lstStampItemOutput.add(x);
			}
		});
		this.lstStampItemOutput.sort(Comparator.comparing(StampItem::getDate));
		this.lstStampItemOutput.sort(Comparator.comparing(StampItem::getAttendanceTime));
		return this.lstStampItemOutput;
	}

}
