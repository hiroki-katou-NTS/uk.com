package nts.uk.ctx.sys.portal.app.find.toppagealarm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.google.common.base.Functions;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.IdentificationKey;
import nts.uk.ctx.sys.portal.dom.toppagealarm.LinkURL;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmDataRepository;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLog;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmLogRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ToppageAlarmDataFinder {
	
	@Inject
	private ToppageAlarmDataRepository toppageAlarmDataRepo;
	
	@Inject
	private ToppageAlarmLogRepository toppageAlarmLogRepo;

	/**
	 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG031_トップページアラーム.トップページアラームver4.A:トップページアラーム.メニュー別OCD.アラームデータを表示する.アラームデータを表示する
	 * @param displayType
	 */
	public List<AlarmDisplayDataDto> findAlarmData(int displayType) {
		// 1: get 就業担当者か
		LoginUserContext currentUser = AppContexts.user();

		GeneralDateTime lastYear = GeneralDateTime.now().addYears(-1);
		List<ToppageAlarmData> listAlarmData = new ArrayList<>();
		if (displayType == 0) {
			// 2: 表示モード＝未読のみ: get未読(ログイン会社ID、ログイン社員ID): トップページアラームデータ
			listAlarmData = this.toppageAlarmDataRepo.getUnread(currentUser.companyId(), currentUser.employeeId(), lastYear);
		} else {
			// 2.1: 表示モード＝全て表示: get未読と既読(ログイン会社ID,ログイン社員ID): トップページアラームデータ
			listAlarmData = this.toppageAlarmDataRepo.getAll(currentUser.companyId(), currentUser.employeeId(), lastYear);
		}
		Map<String, ToppageAlarmLog> mapAlarmLog = this.toppageAlarmLogRepo.getByEmployee(
						currentUser.companyId(), 
						currentUser.employeeId(), 
						lastYear).stream()
				.collect(Collectors.toMap(item -> this.buildUniqueKey(
								item.getCid(), 
								item.getAlarmClassification(), 
								item.getIdentificationKey(), 
								item.getDisplaySId(), 
								item.getDisplayAtr()), 
						Functions.identity()));
		
		// 3: 並び順：  発生日時順、識別キー、表示社員区分
		Comparator<ToppageAlarmData> compareByAlarmClassification = (o1, o2) -> {
			int returnValue = 0;
			Integer firstItemIndex = o1.getAlarmClassification().order;
			Integer secondItemIndex = o2.getAlarmClassification().order;
			if (firstItemIndex > secondItemIndex) {
			    returnValue = 1;
			} else if (firstItemIndex < secondItemIndex) {
			    returnValue = -1;
			}
			return returnValue;
		};
		Comparator<ToppageAlarmData> compareByOccurrenceDateTime = Comparator.comparing(ToppageAlarmData::getOccurrenceDateTime);
		Comparator<ToppageAlarmData> compareByIdenKey = Comparator.comparing(ToppageAlarmData::getIdentificationKey);
		Comparator<ToppageAlarmData> compareBySid = Comparator.comparing(ToppageAlarmData::getDisplaySId);
		return listAlarmData.stream()
				.sorted(compareByAlarmClassification
						.thenComparing(compareByOccurrenceDateTime)
						.thenComparing(compareByIdenKey)
						.thenComparing(compareBySid))
				.map(item -> {
					ToppageAlarmLog alarmLog = mapAlarmLog.get(this.buildUniqueKey(
							item.getCid(), 
							item.getAlarmClassification(), 
							item.getIdentificationKey(), 
							item.getDisplaySId(), 
							item.getDisplayAtr()));
					AlarmDisplayDataDto dto = AlarmDisplayDataDto.builder()
							.alarmClassification(item.getAlarmClassification().value)
							.occurrenceDateTime(item.getOccurrenceDateTime())
							.displayMessage(item.getDisplayMessage().v())
							.companyId(item.getCid())
							.sId(item.getDisplaySId())
							.displayAtr(item.getDisplayAtr().value)
							.identificationKey(item.getIdentificationKey().v())
							.linkUrl(item.getLinkUrl().map(LinkURL::v).orElse(null))
							.alreadyDatetime(alarmLog == null ? null : alarmLog.getAlreadyDatetime())
							.build();
					return dto;
				})
				.collect(Collectors.toList());
	} 
	
	private String buildUniqueKey(String cid, AlarmClassification alarmClas, IdentificationKey idenKey, String sId, DisplayAtr displayAtr) {
		return cid + String.valueOf(alarmClas.value) + idenKey.v() + sId + String.valueOf(displayAtr.value);
	}
	
}
