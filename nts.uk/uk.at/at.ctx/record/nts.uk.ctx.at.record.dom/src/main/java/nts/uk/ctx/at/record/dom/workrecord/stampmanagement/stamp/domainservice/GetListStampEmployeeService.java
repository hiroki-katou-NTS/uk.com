package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;

/**
 * DS : 社員の打刻一覧を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.社員の打刻一覧を取得する
 * @author tutk
 *
 */
public class GetListStampEmployeeService {
	
	/**
	 * [1] 取得する
	 * @param require
	 * @param employeeId
	 * @param date
	 * @return
	 */
	public static Optional<EmployeeStampInfo> get(Require require, String employeeId, GeneralDate date) {
		
		Optional<StampDataOfEmployees> stampDataOfEmployees = GetEmpStampDataService.get(require, employeeId, date);
		
		if(!stampDataOfEmployees.isPresent()) {
			return Optional.empty();
		}
		
		List<StampInfoDisp> listStampInfoDisp = createEmpStampInfo(stampDataOfEmployees.get().getListStampRecord(), stampDataOfEmployees.get().getListStamp());
		
		return Optional.of(new EmployeeStampInfo(employeeId, date, listStampInfoDisp));
	}
	
	/**
	 * [prv-1] 社員の打刻情報を作成する
	 * @param listStampRecord
	 * @param listStamp
	 * @return
	 */
	private static List<StampInfoDisp> createEmpStampInfo(List<StampRecord> listStampRecord,List<Stamp> listStamp) {
		List<StampInfoDisp> datas = new ArrayList<>();
		for (StampRecord stampRecord : listStampRecord) {
			Stamp correctStamp = null;
			for (Stamp stamp : listStamp) {
				if (stampRecord.getStampNumber().equals(stamp.getCardNumber())
						&& stampRecord.getStampDateTime().equals(stamp.getStampDateTime())) {
					correctStamp = stamp;
					break;
				}
			}
			datas.add(new StampInfoDisp(stampRecord.getStampNumber(), stampRecord.getStampDateTime(),
					stampRecord, Optional.ofNullable(correctStamp)));
		}
		return datas;
	}
	
	public static interface Require extends  GetEmpStampDataService.Require {
		/**
		 * [R-1] 社員の打刻カード情報を取得する   StampCardRepository
		 * @param sid
		 * @return
		 */
		List<StampCard> getListStampCard(String sid);
		
		/**
		 * [R-2] 打刻記録を取得する  StampRecordRepository
		 * @param stampNumbers
		 * @param stampDateTime
		 * @return
		 */
		List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate stampDate);
		
		/**
		 * [R-3] 打刻を取得する   StampDakokuRepository
		 * @param stampNumbers
		 * @param stampDateTime
		 * @return
		 */
		List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate stampDateTime);
		
	}

}
