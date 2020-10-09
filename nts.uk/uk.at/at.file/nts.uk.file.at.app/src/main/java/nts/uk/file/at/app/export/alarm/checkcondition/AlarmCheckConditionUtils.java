package nts.uk.file.at.app.export.alarm.checkcondition;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.i18n.I18NText;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ConditionAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.RangeCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeMonCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimeDuration;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.CheckedTimesValueDay;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class AlarmCheckConditionUtils {
	//Common
	public static final String KAL003_212 = "コード";
	public static final String KAL003_213 = "名称";
	public static final String KAL003_214 = "カテゴリ";
	
	//Target condition
	public static final String KAL003_215 = "チェック対象範囲 雇用";
	public static final String KAL003_216 = "チェック対象範囲 雇用対象";
	public static final String KAL003_217 = "チェック対象範囲 分類";
	public static final String KAL003_218 = "チェック対象範囲 分類対象";
	public static final String KAL003_219 = "チェック対象範囲 職位";
	public static final String KAL003_220 = "チェック対象範囲 職位対象";
	public static final String KAL003_221 = "チェック対象範囲 勤務種別";
	public static final String KAL003_222 = "チェック対象範囲 勤務種別対象";
	public static final String KAL003_223 = "チェック条件";
	
	//daily
	public static final String KAL003_229 = "日別実績のエラーアラーム";
	public static final String KAL003_230 = "チェック条件 NO";
	public static final String KAL003_231 = "チェック条件 名称";
	public static final String KAL003_232 = "チェック条件 チェック項目";
	public static final String KAL003_233 = "チェック条件 対象とする勤務種類";
	public static final String KAL003_234 = "チェック条件 勤務種類";
	public static final String KAL003_235 = "チェック条件 チェック条件";
	public static final String KAL003_236 = "チェック条件 条件";
	public static final String KAL003_237 = "チェック条件 値１";
	public static final String KAL003_238 = "チェック条件 値２";
	public static final String KAL003_239 = "チェック条件 複合条件 グループ１";
	public static final String KAL003_240 = "チェック条件 複合条件 計算式１_1";
	public static final String KAL003_241 = "チェック条件 複合条件 計算式１_2";
	public static final String KAL003_242 = "チェック条件 複合条件 計算式１_3";
	public static final String KAL003_243 = "チェック条件 複合条件 グループ２1";
	public static final String KAL003_244 = "チェック条件 複合条件 グループ２";
	public static final String KAL003_245 = "チェック条件 複合条件 計算式２_1";
	public static final String KAL003_246 = "チェック条件 複合条件 計算式２_2";
	public static final String KAL003_247 = "チェック条件 複合条件 計算式２_3";
	public static final String KAL003_248 = "チェック条件 複合条件 グループ1とグループ2の条件";
	public static final String KAL003_249 = "チェック条件 表示するメッセージ";
	public static final String KAL003_250 = "固定チェック条件";
	
	//multiple month
	public static final String KAL003_251 = "アラームリストのチェック条件 NO";
	public static final String KAL003_252 = "アラームリストのチェック条件 名称";
	public static final String KAL003_253 = "チェック条件 チェック項目";
	public static final String KAL003_254 = "チェック条件 勤務種類";
	public static final String KAL003_255 = "チェック条件 条件";
	public static final String KAL003_256 = "チェック条件 値1";
	public static final String KAL003_257 = "チェック条件 値2";
	public static final String KAL003_258 = "チェック条件 計算式 条件";
	public static final String KAL003_259 = "チェック条件 計算式 回数";
	public static final String KAL003_260 = "チェック条件 連続期間";
	public static final String KAL003_261 = "チェック条件 表示するメッセージ";
	
	
	//month
	public static final String KAL003_262 = "固定チェック条件";
	public static final String KAL003_263 = "アラームリストのチェック条件 NO";
	public static final String KAL003_264 = "アラームリストのチェック条件 名称";
	public static final String KAL003_265 = "アラームリストのチェック条件 チェック項目";
	public static final String KAL003_266 = "アラームリストのチェック条件 休暇種類";
	public static final String KAL003_267 = "アラームリストのチェック条件 休暇項目";
	public static final String KAL003_268 = "アラームリストのチェック条件 チェック条件";
	public static final String KAL003_269 = "アラームリストのチェック条件 条件";
	public static final String KAL003_270 = "アラームリストのチェック条件 値１";
	public static final String KAL003_271 = "アラームリストのチェック条件 値２";
	public static final String KAL003_272 = "アラームリストのチェック条件 複合条件 グループ１";
	public static final String KAL003_273 = "アラームリストのチェック条件 複合条件 計算式1_1";
	public static final String KAL003_274 = "アラームリストのチェック条件 複合条件 計算式1_2";
	public static final String KAL003_275 = "アラームリストのチェック条件 複合条件 計算式1_3";
	public static final String KAL003_276 = "アラームリストのチェック条件 複合条件 グループ２";
	public static final String KAL003_277 = "アラームリストのチェック条件 複合条件 グループ２_1";
	public static final String KAL003_278 = "アラームリストのチェック条件 複合条件 計算式2_1";
	public static final String KAL003_279 = "アラームリストのチェック条件 複合条件 計算式2_2";
	public static final String KAL003_280 = "アラームリストのチェック条件 複合条件 計算式2_3";
	public static final String KAL003_281 = "アラームリストのチェック条件 複合条件 グループ1とグループ2の条件";
	public static final String KAL003_282 = "アラームリストのチェック条件 表示するメッセージ";
	
	//agree36
	public static final String KAL003_283 = "36協定エラーアラームのチェック条件 NO";
	public static final String KAL003_284 = "36協定エラーアラームのチェック条件 チェック内容";
	public static final String KAL003_285 = "36協定エラーアラームのチェック条件 表示するメッセージ";
	public static final String KAL003_286 = "36協定時間年間超過回数チェック  NO";
	public static final String KAL003_287 = "36協定時間年間超過回数チェック 超過時間";
	public static final String KAL003_288 = "36協定時間年間超過回数チェック 回数";
	public static final String KAL003_289 = "36協定時間年間超過回数チェック 表示するメッセージ";
	
	// annual holiday
	public static final String KAL003_317 = "年休のチェック対象者　次回年休付与日までの期間";
	public static final String KAL003_318 = "年休のチェック対象者　期間";
	public static final String KAL003_319 = "年休のチェック対象者　前回年休付与日数";
	public static final String KAL003_320 = "年休のチェック対象者　日数";
	
	public static final String KAL003_321 = "年間使用義務チェック条件　使用義務日数";
	public static final String KAL003_322 = "年間使用義務チェック条件　表示するメッセージ";
	public static final String KAL003_323 = "年間使用義務チェック条件　前回付与からの経過期間が１年未満の場合、期間按分する";
	
	public static final String TEXT_KAL003_324 = TextResource.localize("KAL003_324");
	
	public static final String TEXT_KAL003_325 = TextResource.localize("KAL003_325");
	
	public static final String TEXT_KAL003_326 = TextResource.localize("KAL003_326");
	
	
	public static String getCheckItemStr(Optional<Integer> checkItem) {
		if (checkItem.isPresent()) {
			switch (checkItem.get()) {
			case 0:
				return TextResource.localize("Enum_TypeCheckWorkRecord_TIME");
			case 1:
				return TextResource.localize("Enum_TypeCheckWorkRecord_TIMES");
			case 2:
				return TextResource.localize("Enum_TypeCheckWorkRecord_AMOUNT_OF_MONEY");
			case 3:
				return TextResource.localize("Enum_TypeCheckWorkRecord_TIME_OF_DAY");
			case 4:
				return TextResource.localize("Enum_TypeCheckWorkRecord_CONTINUOUS_TIME");
			case 5:
				return TextResource.localize("Enum_TypeCheckWorkRecord_CONTINUOUS_WORK");
			case 6:
				return TextResource.localize("Enum_TypeCheckWorkRecord_CONTINUOUS_TIME_ZONE");
			case 7:
				return TextResource.localize("Enum_TypeCheckWorkRecord_CONTINUOUS_CONDITION");
			default:
				return TextResource.localize("Enum_TypeCheckWorkRecord_TIME");
			}
		} else {
			return null;
		}
	}
	
	public static String getTargetServiceTypeStr(Optional<Integer> serviceType)  {
		if (serviceType.isPresent()) {
			switch (serviceType.get()) {
			case 0:
				return TextResource.localize("Enum_TargetServiceType_All");
			case 1:
				return TextResource.localize("Enum_TargetServiceType_Selection");
			case 2:
				return TextResource.localize("Enum_TargetServiceType_OtherSelection");
			default:
				return TextResource.localize("Enum_TargetServiceType_All");
			}
		} else {
			return null;
		}
	}
	
	public static String getCompareAtrStr(Optional<Integer> compareAtr)  {
		if (compareAtr.isPresent()) {
			switch (compareAtr.get()) {
			case 0:
				/* 等しくない（≠） */
				return TextResource.localize("Enum_SingleValueCompareType_NotEqual");
			case 1:
				/* 等しい（＝） */
				return TextResource.localize("Enum_SingleValueCompareType_Equal");
			case 2:
				/* 以下（≦） */
				return TextResource.localize("Enum_SingleValueCompareType_LessOrEqual");
			case 3:
				/* 以上（≧） */
				return TextResource.localize("Enum_SingleValueCompareType_GreaterOrEqual");
			case 4:
				/* より小さい（＜） */
				return TextResource.localize("Enum_SingleValueCompareType_LessThan");
			case 5:
				/* より大きい（＞） */
				return TextResource.localize("Enum_SingleValueCompareType_GreaterThan");
			case 6:
				/* 範囲の間（境界値を含まない）（＜＞） */
				return TextResource.localize("Enum_RangeCompareType_BetweenRangeOpen");
			case 7:
				/* 範囲の間（境界値を含む）（≦≧） */
				return TextResource.localize("Enum_RangeCompareType_BetweenRangeClosed");
			case 8:
				/* 範囲の外（境界値を含まない）（＞＜） */
				return TextResource.localize("Enum_RangeCompareType_OutsideRangeOpen");
			case 9:
				/* 範囲の外（境界値を含む）（≧≦） */
				return TextResource.localize("Enum_RangeCompareType_OutsideRangeClosed");
			default:
				return TextResource.localize("Enum_RangeCompareType_OutsideRangeClosed");
			}
		} else {
			return null;
		}
	}
	
	public static <T> String getCondition(Optional<String> targetAttendances,  Optional<Integer> compareAtr, Optional<Integer> conditionAtr,
			Optional<T> start, Optional<T> end, Optional<Integer> conditionType, Optional<Integer> attendanceItem, 
			Map<Integer, AttendanceNameDivergenceDto> attendanceNameDivergenceDtos) {
		StringBuffer condition = new StringBuffer("");
		String targetAttendancesStr = targetAttendances.isPresent() ? getAttendanceStrFromTarget(targetAttendances.get(), attendanceNameDivergenceDtos, false) : "";
		String attendanceItemStr = attendanceItem.isPresent() ?(attendanceNameDivergenceDtos.get(attendanceItem.get()) == null ? "" :attendanceNameDivergenceDtos.get(attendanceItem.get()).getAttendanceItemName()) : "";
		String startStr = start.isPresent() ? getValueWithConditionAtr(start.get(), conditionAtr.get()) : "";
		String endStr = end.isPresent() ? getValueWithConditionAtr(end.get(), conditionAtr.get()) : "";
		
		if (!StringUtil.isNullOrEmpty(targetAttendancesStr, true)) {
			if (conditionType.isPresent() && conditionType.get() == 0) {
				if (compareAtr.isPresent() && compareAtr.get() <= SingleValueCompareType.GREATER_THAN.value) {
					condition.append(targetAttendancesStr);
					if (!StringUtil.isNullOrEmpty(startStr, true)) {
						condition.append(getStartCompareAtrStr(compareAtr));
						condition.append(startStr);
					}
				}
				else if (compareAtr.isPresent() && (compareAtr.get() == RangeCompareType.BETWEEN_RANGE_OPEN.value 
						|| compareAtr.get() == RangeCompareType.BETWEEN_RANGE_CLOSED.value)) {
					if (!StringUtil.isNullOrEmpty(startStr, true)) {
						condition.append(startStr);
						condition.append(getEndCompareAtrStr(compareAtr));
					}
					
					condition.append(targetAttendancesStr);
					if (!StringUtil.isNullOrEmpty(endStr, true)) {
						condition.append(getEndCompareAtrStr(compareAtr));
						condition.append(endStr);
					}
				} else if (compareAtr.isPresent() && (compareAtr.get() == RangeCompareType.OUTSIDE_RANGE_OPEN.value 
						|| compareAtr.get() == RangeCompareType.OUTSIDE_RANGE_CLOSED.value)) {
					condition.append(targetAttendancesStr);
					condition.append(getEndCompareAtrStr(compareAtr));
					if (!StringUtil.isNullOrEmpty(startStr, true)) {
						condition.append(startStr);
						condition.append(",");
					}
					if (!StringUtil.isNullOrEmpty(endStr, true)) {
						condition.append(endStr);
					}
					condition.append(getEndCompareAtrStr(compareAtr));
					condition.append(targetAttendancesStr);
				}
			} else  {
				condition.append(targetAttendancesStr);
				condition.append(getStartCompareAtrStr(compareAtr));
				condition.append(attendanceItemStr);
			}
		}
		
		return condition.toString();
	}
	
	public static String getStartCompareAtrStr(Optional<Integer> compareAtr)  {
		if (compareAtr.isPresent()) {
			switch (compareAtr.get()) {
			case 0:
				return TextResource.localize("KAL003_298");
			case 1:
				return TextResource.localize("KAL003_299");
			case 2:
				return TextResource.localize("KAL003_300");
			case 3:
				return TextResource.localize("KAL003_301");
			case 4:
				return TextResource.localize("KAL003_302");
			case 5:
				return TextResource.localize("KAL003_303");
			default:
				return TextResource.localize("KAL003_303");
			}
		} else {
			return null;
		}
	}
	
	
	public static String getEndCompareAtrStr(Optional<Integer> compareAtr)  {
		if (compareAtr.isPresent()) {
			switch (compareAtr.get()) {
			case 6:
				return TextResource.localize("KAL003_302");
			case 7:
				return TextResource.localize("KAL003_300");
			case 8:
				return TextResource.localize("KAL003_302");
			case 9:
				return TextResource.localize("KAL003_300");
			default:
				return TextResource.localize("KAL003_302");
			}
		} else {
			return null;
		}
	}
	
	
	public static String getOperatorStr(Optional<Integer> operator)  {
		if (operator.isPresent()) {
			switch (operator.get()) {
			case 0:
				return TextResource.localize("Enum_LogicalOperator_And");
			case 1:
				return TextResource.localize("Enum_LogicalOperator_Or");
			default:
				return TextResource.localize("Enum_LogicalOperator_And");
			}
		} else {
			return null;
		}
	}
	
	public static String getUseAtrStr(Optional<Integer> useAtr)  {
		if (useAtr.isPresent()) {
			switch (useAtr.get()) {
			case 0:
				return TextResource.localize("KAL003_238");
			case 1:
				return TextResource.localize("KAL003_237");
			default:
				return TextResource.localize("KAL003_238");
			}
		} else {
			return null;
		}
	}
	
	public static String getFilterStr(Optional<Integer> filter)  {
		if (filter.isPresent()) {
			switch (filter.get()) {
			case 0:
				return TextResource.localize("KAL003_238");
			case 1:
				return TextResource.localize("KAL003_237");
			default:
				return TextResource.localize("KAL003_238");
			}

		} else {
			return TextResource.localize("KAL003_238");
		}
	}
	
	public static <T> String getValueWithConditionAtr(T value, int conditionAtr) {
		
		if (conditionAtr == ConditionAtr.AMOUNT_VALUE.value) {
			return (value instanceof Integer ? formatCurrency((Integer)value) : formatCurrency(((Double)value).intValue()));
		} else if (conditionAtr == ConditionAtr.TIME_DURATION.value) {
			CheckedTimeDuration timeDuration = new CheckedTimeDuration(value instanceof Integer ? (Integer)value : ((Double)value).intValue());
			return (timeDuration.hour() 
				+ ":" + (timeDuration.minute() >= 10 ? "" : "0") + timeDuration.minute());
		} else if (conditionAtr == ConditionAtr.TIME_WITH_DAY.value) {
			return (new TimeWithDayAttr(value instanceof Integer ? (Integer)value : ((Double)value).intValue())).getRawTimeWithFormat();
		}  else if (conditionAtr == ConditionAtr.TIMES.value) {
			return (new CheckedTimesValue(value instanceof Integer ? (Integer)value : ((Double)value).intValue())).toString();
		} else if (conditionAtr == ConditionAtr.DAYS.value) {
			return (String.format ("%.1f", (new CheckedTimesValueDay(
					value instanceof Integer ? ((Integer) value).doubleValue() : ((Double) value))).v())) ;
		}
		return "";
	}
	
	private static String formatCurrency(Integer value) {
		DecimalFormat formatter = new DecimalFormat("#,###");
		return formatter.format(value);
	}
	
	public static List<Integer> getAttendanceIds(List<DailyReportData> dailyReportDatas) {
		List<Integer> listAttendanceIds = new ArrayList<>();
		if (!CollectionUtil.isEmpty(dailyReportDatas)) {
			dailyReportDatas.stream().forEach(x-> {
				if (x.getUseAtrCond().isPresent() && x.getUseAtrCond().get() == 1) {
					if (x.getTargetAttendances().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances().get()));
					}
					if (x.getTargetAttendances1Group1().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances1Group1().get()));
					}
					if (x.getTargetAttendances2Group1().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances2Group1().get()));
					}
					if (x.getTargetAttendances3Group1().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances3Group1().get()));
					}
					
					if (x.getAttendanceItem1Group1().isPresent()) {
						listAttendanceIds.add(x.getAttendanceItem1Group1().get());
					}
					if (x.getAttendanceItem2Group1().isPresent()) {
						listAttendanceIds.add(x.getAttendanceItem2Group1().get());
					}
					if (x.getAttendanceItem3Group1().isPresent()) {
						listAttendanceIds.add(x.getAttendanceItem3Group1().get());
					}
					
					if (x.getGroup2UseAtrInt().isPresent() && x.getGroup2UseAtrInt().get() == 1) {
						if (x.getTargetAttendances1Group2().isPresent()) {
							listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances1Group2().get()));
						}
						if (x.getTargetAttendances2Group2().isPresent()) {
							listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances2Group2().get()));
						}
						if (x.getTargetAttendances3Group2().isPresent()) {
							listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances3Group2().get()));
						}

						if (x.getAttendanceItem1Group2().isPresent()) {
							listAttendanceIds.add(x.getAttendanceItem1Group2().get());
						}
						if (x.getAttendanceItem2Group2().isPresent()) {
							listAttendanceIds.add(x.getAttendanceItem2Group2().get());
						}
						if (x.getAttendanceItem3Group2().isPresent()) {
							listAttendanceIds.add(x.getAttendanceItem3Group2().get());
						}
					}
				}
			});
		}
		return listAttendanceIds;
	}
	
	public static List<Integer> getAttendanceIdsFromTarget(String targetAttendances) {
		List<Integer> listAttendanceIds = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(targetAttendances, true)) {
			Arrays.stream(targetAttendances.split(",")).collect(Collectors.toList()).stream().forEach(x-> {
				if (!StringUtil.isNullOrEmpty(x, true)) {
					String[] typeTargetAndAttendance = x.split("_");
					if (typeTargetAndAttendance.length == 2) {
						listAttendanceIds.add(Integer.valueOf(typeTargetAndAttendance[1]));
					}
				}
			});;
			
		}
		return listAttendanceIds;
	}
	
	public static String getAttendanceStrFromTarget(String targetAttendances, Map<Integer, AttendanceNameDivergenceDto> attendanceNameDivergenceDtos, boolean withDisplayNumber) {
		StringBuffer attendanceStrBuilder = new StringBuffer("");
		if (!StringUtil.isNullOrEmpty(targetAttendances, true)) {
			List<String> targets = Arrays.stream(targetAttendances.split(",")).collect(Collectors.toList());
			StringBuffer targetsStr = new StringBuffer("");
			targets.stream().forEach(x-> {
				targetsStr.append(x + ":");
			});
			targets.stream().forEach(x-> {
				if (!StringUtil.isNullOrEmpty(x, true)) {
					String[] typeTargetAndAttendance = x.split("_");
					if (typeTargetAndAttendance.length == 2) {
						AttendanceNameDivergenceDto dto = attendanceNameDivergenceDtos.get(Integer.valueOf(typeTargetAndAttendance[1]));
						if (dto != null) {
							attendanceStrBuilder.append(getTargetAtrStr(Integer.valueOf(typeTargetAndAttendance[0])));
							if (withDisplayNumber) {
								attendanceStrBuilder.append("" + dto.getAttendanceItemDisplayNumber());
							}
							
							attendanceStrBuilder.append(dto.getAttendanceItemName());
						}		
					}
				}
			});;
		}
		
		//remove start character "+"
		String attendanceStr = attendanceStrBuilder.toString();
		if (attendanceStr.startsWith("+")) {
			attendanceStr = attendanceStr.substring(1);
		}
		
		return attendanceStr;
	}
	
	public static String getTargetAtrStr(int targetAtr) {
		switch (targetAtr) {
		case 0:
			return "+";
		case 1:
			return "-";
		case 2:
			return "";
		default:
			return "";
		}
	}
	
	
	public static List<Integer> getAttendanceIdsMulMonth(List<MulMonthReportData> mulMonthReportDatas) {
		List<Integer> listAttendanceIds = new ArrayList<>();
		if (!CollectionUtil.isEmpty(mulMonthReportDatas)) {
			mulMonthReportDatas.stream().forEach(x-> {
					if (x.getTargetAttendances().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances().get()));
					}
			});
		}
		return listAttendanceIds;
	}
	
	
	public static String getCheckItemMulMonthStr(Optional<Integer> checkItem) {
		if (checkItem.isPresent()) {
			switch (checkItem.get()) {
			case 0:
				return TextResource.localize("KAL003_281");
			case 1:
				return TextResource.localize("KAL003_282");
			case 2:
				return TextResource.localize("KAL003_283");
			case 3:
				return TextResource.localize("KAL003_284");
			case 4:
				return TextResource.localize("KAL003_285");
			case 5:
				return TextResource.localize("KAL003_286");
			case 6:
				return TextResource.localize("KAL003_287");
			case 7:
				return TextResource.localize("KAL003_288");
			case 8:
				return TextResource.localize("KAL003_289");
			case 9:
				return TextResource.localize("KAL003_290");
			case 10:
				return TextResource.localize("KAL003_291");
			case 11:
				return TextResource.localize("KAL003_292");
			case 12:
				return TextResource.localize("KAL003_293");
			case 13:
				return TextResource.localize("KAL003_294");
			case 14:
				return TextResource.localize("KAL003_295");
			case 15:
				return TextResource.localize("KAL003_296");
			default:
				return TextResource.localize("KAL003_281");
			}
		} else {
			return null;
		}
	}
	
	
	public static String getCheckItemMonthStr(Optional<Integer> checkItem) {
		if (checkItem.isPresent()) {
			switch (checkItem.get()) {
			case 0:
				return TextResource.localize("Enum_TypeMonCheckItem_CertainDayOff");
			case 1:
				return TextResource.localize("Enum_TypeMonCheckItem_36AgrErrorTime");
			case 2:
				return TextResource.localize("Enum_TypeMonCheckItem_36AgrAlarmTime");
			case 3:
				return TextResource.localize("Enum_TypeMonCheckItem_CheckRemainNumber");
			case 4:
				return TextResource.localize("Enum_TypeMonCheckItem_Time");
			case 5:
				return TextResource.localize("Enum_TypeMonCheckItem_Days");
			case 6:
				return TextResource.localize("Enum_TypeMonCheckItem_Times");
			case 7:
				return TextResource.localize("Enum_TypeMonCheckItem_AmountOfMoney");
			case 8:
				return TextResource.localize("Enum_TypeMonCheckItem_CompoundCon");
			default:
				return TextResource.localize("Enum_TypeMonCheckItem_CertainDayOff");
			}
		} else {
			return null;
		}
	}
	
	public static <T> String getValueWithConditionAtrMonth(T value, int checkTypeItem) {
		if (checkTypeItem == 4) {
			CheckedTimeDuration timeDuration = new CheckedTimeDuration(
					value instanceof Integer ? (Integer) value : ((Double) value).intValue());
			return (timeDuration.hour() + ":"
					+ (timeDuration.minute() >= 10 ? "" : "0") + timeDuration.minute());
		} else if (checkTypeItem == 5) {
			return (String.format ("%.1f", (new CheckedTimesValueDay(
					value instanceof Integer ? ((Integer) value).doubleValue() : ((Double) value))).v()));
		} else if (checkTypeItem == 6) {
			return (new CheckedTimesValue(value instanceof Integer ? (Integer) value : ((Double) value).intValue()))
					.toString();
		} else if (checkTypeItem == 7) {
			return (value instanceof Integer ? formatCurrency((Integer)value) : formatCurrency(((Double)value).intValue()));
		}
		
		return "";
	}
	
	public static String getTypeCheckVacationStr(Optional<Integer> typeCheckVacation)  {
		if (typeCheckVacation.isPresent()) {
			switch (typeCheckVacation.get()) {
			case 0:
				return I18NText.getText("KAL003_112");
			case 1:
				return I18NText.getText("KAL003_113");
			case 2:
				return I18NText.getText("KAL003_114");
			case 3:
				return I18NText.getText("KAL003_115");
			case 4:
				return I18NText.getText("KAL003_116");
			case 5:
				return I18NText.getText("KAL003_117");
			case 6:
				return I18NText.getText("KAL003_118");
			case 7:
				return I18NText.getText("KAL003_119");
			default:
				return I18NText.getText("KAL003_112");
			}
		} else {
			return null;
		}
	}
	
	
	public static List<Integer> getAttendanceIdsMonth(List<MonthReportData> monthReportDatas) {
		List<Integer> listAttendanceIds = new ArrayList<>();
		if (!CollectionUtil.isEmpty(monthReportDatas)) {
			monthReportDatas.stream().forEach(x-> {
				if (x.getUseAtrCond().isPresent() && x.getUseAtrCond().get() == 1 && x.getCheckItemInt().isPresent()
						&& x.getCheckItemInt().get() != TypeMonCheckItem.CERTAIN_DAY_OFF.value
						&& x.getCheckItemInt().get() != TypeMonCheckItem.CHECK_REMAIN_NUMBER.value) {
					if (x.getTargetAttendances().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances().get()));
					}
					if (x.getTargetAttendances1Group1().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances1Group1().get()));
					}
					if (x.getTargetAttendances2Group1().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances2Group1().get()));
					}
					if (x.getTargetAttendances3Group1().isPresent()) {
						listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances3Group1().get()));
					}
					
					if (x.getAttendanceItem1Group1().isPresent()) {
						listAttendanceIds.add(x.getAttendanceItem1Group1().get());
					}
					if (x.getAttendanceItem2Group1().isPresent()) {
						listAttendanceIds.add(x.getAttendanceItem2Group1().get());
					}
					if (x.getAttendanceItem3Group1().isPresent()) {
						listAttendanceIds.add(x.getAttendanceItem3Group1().get());
					}
					
					if (x.getGroup2UseAtrInt().isPresent() && x.getGroup2UseAtrInt().get() == 1) {
						if (x.getTargetAttendances1Group2().isPresent()) {
							listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances1Group2().get()));
						}
						if (x.getTargetAttendances2Group2().isPresent()) {
							listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances2Group2().get()));
						}
						if (x.getTargetAttendances3Group2().isPresent()) {
							listAttendanceIds.addAll(getAttendanceIdsFromTarget(x.getTargetAttendances3Group2().get()));
						}

						if (x.getAttendanceItem1Group2().isPresent()) {
							listAttendanceIds.add(x.getAttendanceItem1Group2().get());
						}
						if (x.getAttendanceItem2Group2().isPresent()) {
							listAttendanceIds.add(x.getAttendanceItem2Group2().get());
						}
						if (x.getAttendanceItem3Group2().isPresent()) {
							listAttendanceIds.add(x.getAttendanceItem3Group2().get());
						}
					}
				}
			});
		}
		return listAttendanceIds;
	}
}
