/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.statement.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.statement.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.function.dom.statement.WkpHistWithPeriodAdapter;
import nts.uk.ctx.at.function.dom.statement.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.function.dom.statement.dtoimport.ExWorkPlaceHistoryImport;
import nts.uk.ctx.at.function.dom.statement.dtoimport.ExWorkplaceHistItemImport;
import nts.uk.ctx.at.function.dom.statement.dtoimport.WkpHistWithPeriodImport;
import nts.uk.ctx.at.function.dom.statement.dtoimport.WkpInfoHistImport;
import nts.uk.ctx.at.record.dom.stamp.StampAtr;
import nts.uk.ctx.at.record.dom.stamp.StampItem;
import nts.uk.ctx.at.record.dom.stamp.StampRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class DataExport.
 */
@Stateless
public class DataExport {
	
	/** The stamp card repository. */
	@Inject 
	private StampCardRepository stampCardRepository;
	
	/** The work location repository. */
	@Inject
	private WorkLocationRepository workLocationRepository;
	
	/** The work time setting repository. */
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	/** The stamp repository. */
	@Inject
	private StampRepository stampRepository;
	
	/** The employee general info adapter. */
	@Inject
	private EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter;
	
	/** The wkp hist with period adapter. */
	@Inject
	private WkpHistWithPeriodAdapter wkpHistWithPeriodAdapter;
	
	/** The Constant GET_EMPLOYMENT. */
	// 雇用を取得する
	private static final boolean GET_EMPLOYMENT = false;
	
	/** The Constant GET_CLASSIFICATION. */
	// 分類を取得する
	private static final boolean GET_CLASSIFICATION = false;
	
	/** The Constant GET_POSITION. */
	// 職位を取得する
	private static final boolean GET_POSITION = false;
	
	/** The Constant GET_WORKPLACE. */
	// 職場を取得する
	private static final boolean GET_WORKPLACE = true;
	
	/** The Constant GET_DEPARTMENT. */
	// 部門を取得する
	private static final boolean GET_DEPARTMENT = false;
	
	private Map<String, List<WkpInfoHistImport>> mapWkpIdWkpInfo = new HashMap<>();
	// 「打刻」
	private List<StampItem> lstStampItem = new ArrayList<>();
	private Map<String, List<String>> mapEmpIdWkpId = new HashMap<>();
	private GeneralDate dateStampItem;
	private Map<String, DatePeriod> mapWkpIdPeriod;
	private Map<String, List<ExWorkplaceHistItemImport>> mapEmpIdWkps;
	/**
	 * Gets the target data.
	 *
	 * @param lstEmployeeId the lst employee id
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param cardNumNotRegister the card num not register
	 * @return the target data
	 */
	// 打刻一覧対象データ取得処理 (Xử lý lấy data đối tượng statement list)
	public List<StatementList> getTargetData(List<EmployeeInfor> lstEmployeeInfor, GeneralDate startDate, GeneralDate endDate, boolean cardNumNotRegister) {
		
		if (CollectionUtil.isEmpty(lstEmployeeInfor) && !cardNumNotRegister) {
			return Collections.emptyList();
		}
		
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		DatePeriod datePeriod = new DatePeriod(startDate, endDate);
		List<String> lstEmployeeId = new ArrayList<>();
		Map<String, String> mapEmpIdCd = new HashMap<>();
		Map<String, String> mapEmpIdName = new HashMap<>();
		
		List<String> lstWkpID = new ArrayList<>();
		
		// convert list to map store data of employee
		lstEmployeeInfor.stream().forEach(dto -> {
			String employeeId = dto.getEmployeeID();
			lstEmployeeId.add(employeeId);
			mapEmpIdCd.put(employeeId, dto.getEmployeeCD());
			mapEmpIdName.put(employeeId, dto.getEmployeeName());
		});
		
		// data repare for export
		List<StatementList> dataReturn = new ArrayList<>();
		
		// Input.カードNO未登録を判定する(phan đoan Input.card NO chưa đăng ky)
		if (cardNumNotRegister) {
			// ドメインモデル「打刻カード」を取得する(get domain model 「打刻カード」)
			List<StampCard> lstStampCard = stampCardRepository.getLstStampCardByContractCode(contractCode);
			
			// ドメインモデル「打刻」を取得する(get domain model 「打刻」)
			lstStampItem = stampRepository.findByDateCompany(companyId, convertGDT(startDate, "start"), convertGDT(endDate, "end"));
			
			// filter list StampItem have カード番号 but don't exist in StampCard
			lstStampItem = getStampItemExcludeStampCard(lstStampItem, lstStampCard);
		} else {
			// Imported「社員の履歴情報」 を取得する(get Imported「社員の履歴情報」)
			EmployeeGeneralInfoImport employeeGeneralInfoDto = employeeGeneralInfoAdapter.getEmployeeGeneralInfo(lstEmployeeId, datePeriod, GET_EMPLOYMENT, GET_CLASSIFICATION, GET_POSITION, GET_WORKPLACE, GET_DEPARTMENT);
			
			// 取得した「社員の履歴情報」から使用している職場IDを抽出する(extract using workplace ID from get 「社員の履歴情報」- history information of employee)
			
			List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports = employeeGeneralInfoDto.getExWorkPlaceHistoryImports().stream().collect(Collectors.toList());
			
			mapEmpIdWkps = exWorkPlaceHistoryImports.stream().collect(Collectors.toMap(ExWorkPlaceHistoryImport::getEmployeeId, ExWorkPlaceHistoryImport::getWorkplaceItems));
			
			mapEmpIdWkpId = exWorkPlaceHistoryImports.stream().collect(Collectors.toMap(
																			ExWorkPlaceHistoryImport::getEmployeeId,
																			listItem -> listItem.getWorkplaceItems().stream().map(dto -> dto.getWorkplaceId()).collect(Collectors.toList())));
			
			List<ExWorkplaceHistItemImport> lstTemp = new ArrayList<>(); 
			exWorkPlaceHistoryImports.forEach(domain -> {
				lstTemp.addAll(domain.getWorkplaceItems());
			});
			
			mapWkpIdPeriod = lstTemp.stream().collect(Collectors.toMap(ExWorkplaceHistItemImport::getWorkplaceId, ExWorkplaceHistItemImport::getPeriod, (objDup1, objDup2) -> {return objDup2;}));
			
			
			mapEmpIdWkpId.entrySet().stream().forEach(dto -> lstWkpID.addAll(dto.getValue()));
			
			// Imported「職場履歴情報」を取得する(get Imported「職場履歴情報」)		
			List<WkpHistWithPeriodImport> lstWkpHistWithPeriodExport = wkpHistWithPeriodAdapter.getLstHistByWkpsAndPeriod(lstWkpID, datePeriod);
			mapWkpIdWkpInfo = lstWkpHistWithPeriodExport.stream().collect(Collectors.toMap(WkpHistWithPeriodImport::getWkpId, WkpHistWithPeriodImport::getWkpInfoHistLst));
			
			// ドメインモデル「打刻カード」を取得する(get domain model「打刻カード」)
			List<StampCard> lstStampCard = stampCardRepository.getLstStampCardByLstSidAndContractCd(lstEmployeeId, contractCode);
						
			// ドメインモデル「打刻」を取得する(get domain model 「打刻」)
			List<String> lstStampCardNumber = lstStampCard.stream()
												.map(domain -> domain.getStampNumber().v())
												.collect(Collectors.toList());
			lstStampItem = stampRepository.findByEmployeeID_Fix(companyId, lstStampCardNumber, convertGDT(startDate, "start"), convertGDT(endDate, "end"));
		}
		
		// ドメインモデル「勤務場所」を取得する(get domain model 「勤務場所」- workplace) : only comment.
		
		List<String> lstWorktimeCode = lstStampItem.stream().map(domain -> domain.getSiftCd().v()).distinct().collect(Collectors.toList());
		
		// ドメインモデル「就業時間帯の設定」を取得する(get domain model 「就業時間帯の設定」- setting time zone lam việc)
		Map<String, String> mapWorkCdWorkName = workTimeSettingRepository.getListWorkTimeSetByListCode(companyId, lstWorktimeCode)
																.stream()
																.collect(Collectors.toMap( item -> item.getWorktimeCode().v() ,item -> item.getWorkTimeDisplayName().getWorkTimeName().v()));
		
		// 取得したデータを打刻一覧に設定する(setting get data on stamp list)
		if (cardNumNotRegister) {
			lstStampItem.stream().forEach(domain -> {
				StatementList dto = new StatementList();
				dto.setCardNo(domain.getCardNumber().v());
				dto.setDate(domain.getDate());
				dto.setAtdType(getAtdType(EnumAdaptor.valueOf(domain.getStampAtr().value, StampAtr.class)));
				dto.setWorkTimeZone(mapWorkCdWorkName.get(domain.getSiftCd().v()));
				dto.setTime(convertToTime(domain.getAttendanceTime().v()));
				dataReturn.add(dto);
			});
		} else {
			lstStampItem.stream().forEach(objStampItem -> {
				String employeeId = objStampItem.getEmployeeId();
				if (mapEmpIdWkps.get(employeeId) != null) {
					mapEmpIdWkps.get(employeeId).forEach(obj -> {
						String wkpId = obj.getWorkplaceId();
						// Check workplace is exist
						if (mapWkpIdWkpInfo.containsKey(wkpId)) {
							dateStampItem = objStampItem.getDate().toDate();
							// Date period Employee corresponding to workplace
							DatePeriod wkpDatePeriod = obj.getPeriod();
							WkpInfoHistImport obj2 = mapWkpIdWkpInfo.get(wkpId).get(0);
							if (wkpDatePeriod.start().beforeOrEquals(dateStampItem) 
									&& wkpDatePeriod.end().afterOrEquals(dateStampItem)
									/*&& wkpDatePeriod.start().after(obj2.getPeriod().start())
									&& wkpDatePeriod.end().before(obj2.getPeriod().end())*/
								) {
								StatementList dto = new StatementList();
								dto.setWkpCode(obj2.getWkpCode());
								dto.setWkpName(obj2.getWkpDisplayName());
								dto.setEmpCode(mapEmpIdCd.get(employeeId));
								dto.setEmpName(mapEmpIdName.get(employeeId));
								dto.setCardNo(objStampItem.getCardNumber().v());
								dto.setDate(objStampItem.getDate());
								dto.setAtdType(getAtdType(EnumAdaptor.valueOf(objStampItem.getStampAtr().value, StampAtr.class)));
								dto.setWorkTimeZone(mapWorkCdWorkName.get(objStampItem.getSiftCd().v()));
								dto.setTime(convertToTime(objStampItem.getAttendanceTime().v()));
								dataReturn.add(dto);
							}
						}
					});
				}
			});
		}
		
		return dataReturn;
	}
	
	/**
	 * Convert to time.
	 *
	 * @param totalMinute the total minute
	 * @return the string
	 */
	// convert number to hour
	private String convertToTime(int totalMinute) {
	    int MINUTES_IN_AN_HOUR = 60;
	    int hours = totalMinute / MINUTES_IN_AN_HOUR;
	    int minutes = totalMinute % MINUTES_IN_AN_HOUR ;

	    return hours + ":" + (minutes == 0 ? "00" : minutes < 10 ? "0" + minutes : minutes) ;
	}
	
	/**
	 * Gets the stamp item exclude stamp card.
	 *
	 * @param lstStampItem the lst stamp item
	 * @param lstStampCard the lst stamp card
	 * @return the stamp item exclude stamp card
	 */
	private List<StampItem> getStampItemExcludeStampCard(List<StampItem> lstStampItem, List<StampCard> lstStampCard) {
		Set<String> setStampCard = lstStampCard.stream().map(domain -> domain.getStampNumber().v()).collect(Collectors.toSet());
		
		return lstStampItem.stream().filter(domain -> !setStampCard.contains(domain.getCardNumber().v()))
								.collect(Collectors.toList());
	}
	
	/**
	 * Convert GDT.
	 *
	 * @param date the date
	 * @return the general date time
	 */
	private GeneralDateTime convertGDT(GeneralDate date, String type) {
		if (type.compareTo("start") == 0) {
			return GeneralDateTime.ymdhms(date.year(), date.month(), date.day(), 0, 0, 0);
		} 
		return GeneralDateTime.ymdhms(date.year(), date.month(), date.day(), 23, 59, 59);
		
	}
	
	/**
	 * Gets the atd type.
	 *
	 * @param type the type
	 * @return the atd type
	 */
	private String getAtdType(StampAtr type) {
		String result = StringUtils.EMPTY;
		switch (type) {
			case ATTENDANCE:
				result = TextResource.localize("Com_WorkIn");
				break;
			case WORKONTIME:
				result = TextResource.localize("Com_WorkOut");
				break;
			case INTRODUCTION:
				result = TextResource.localize("Com_GateIn");
				break;
			case EXIT:
				result = TextResource.localize("Com_GateOut");
				break;
			case GOINGOUT:
				result = TextResource.localize("Com_Out");
				break;
			case RETURN:
				result = TextResource.localize("Com_In");
				break;
			case SUPPORT_START:
				result = "";
				break;
			case EMERGENCY_START:
				result = TextResource.localize("Com_ExtraIn");
				break;
			case SUPPORT_END:
				result = "";
				break;
			case EMERGENCY_END:
				result = TextResource.localize("Com_ExtraOut");
				break;
			case PCLOGON:
				result = TextResource.localize("Com_LogOn");
				break;
			case PCLOGOFF:
				result = TextResource.localize("Com_LogOff");
				break;
			}
		return result;
	}
}
