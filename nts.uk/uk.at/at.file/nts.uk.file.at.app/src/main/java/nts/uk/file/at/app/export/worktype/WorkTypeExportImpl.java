package nts.uk.file.at.app.export.worktype;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.i18n.custom.IInternationalization;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameRepository;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;

@Stateless
@DomainID(value = "WorkType")
public class WorkTypeExportImpl implements MasterListData {
	@Inject
	private IInternationalization internationalization;

	@Inject
	private WorkTypeReportRepository workTypeReportRepository;

	@Inject
	private AbsenceFrameRepository absenceFrameRepository;

	@Inject
	private SpecialHolidayFrameRepository specialHolidayFrameRepository;

	

	@Override
	public List<MasterHeaderColumn> getHeaderColumns() {

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", internationalization.getItemName("KMK007_66").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("名称", internationalization.getItemName("KMK007_67").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("略名", internationalization.getItemName("KMK007_68").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("記号", internationalization.getItemName("KMK007_69").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("備考", internationalization.getItemName("KMK007_70").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("廃止区分", internationalization.getItemName("KMK007_71").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("出勤率の計算方法", internationalization.getItemName("KMK007_72").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("対象範囲", internationalization.getItemName("KMK007_73").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日", internationalization.getItemName("KMK007_74").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午前", internationalization.getItemName("KMK007_75").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午後", internationalization.getItemName("KMK007_76").get(),
				ColumnTextAlign.CENTER, "", true));

		columns.add(new MasterHeaderColumn("１日の休日区分", internationalization.getItemName("KMK007_77").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日の日勤・夜勤時間を求める", internationalization.getItemName("KMK007_78").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日の出勤時刻を直行とする", internationalization.getItemName("KMK007_79").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日の退勤時刻を直帰とする", internationalization.getItemName("KMK007_80").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日の公休を消化する", internationalization.getItemName("KMK007_81").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日の代休を発生させる", internationalization.getItemName("KMK007_82").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日の欠勤の集計枠", internationalization.getItemName("KMK007_83").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日の特別休暇の集計枠", internationalization.getItemName("KMK007_84").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("1日の休業区分", internationalization.getItemName("KMK007_85").get(),
				ColumnTextAlign.CENTER, "", true));

		columns.add(new MasterHeaderColumn("午前の日勤・夜勤時間を求める", internationalization.getItemName("KMK007_86").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午前の出勤時刻を直行とする", internationalization.getItemName("KMK007_87").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午前の退勤時刻を直帰とする", internationalization.getItemName("KMK007_88").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午前の休日日数を数える", internationalization.getItemName("KMK007_89").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午前の公休を消化する", internationalization.getItemName("KMK007_90").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午前の代休を発生させる", internationalization.getItemName("KMK007_91").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午前の欠勤の集計枠", internationalization.getItemName("KMK007_92").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午前の特別休暇の集計枠", internationalization.getItemName("KMK007_93").get(),
				ColumnTextAlign.CENTER, "", true));

		columns.add(new MasterHeaderColumn("午後の日勤・夜勤時間を求める", internationalization.getItemName("KMK007_94").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午後の出勤時刻を直行とする", internationalization.getItemName("KMK007_95").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午後の退勤時刻を直帰とする", internationalization.getItemName("KMK007_96").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午後の休日日数を数える", internationalization.getItemName("KMK007_97").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午後の公休を消化する", internationalization.getItemName("KMK007_98").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午後の代休を発生させる", internationalization.getItemName("KMK007_99").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午後の欠勤の集計枠", internationalization.getItemName("KMK007_100").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("午後の特別休暇の集計枠", internationalization.getItemName("KMK007_101").get(),
				ColumnTextAlign.CENTER, "", true));

		columns.add(new MasterHeaderColumn("他言語名称", internationalization.getItemName("KMK007_102").get(),
				ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("他言語略名", internationalization.getItemName("KMK007_103").get(),
				ColumnTextAlign.CENTER, "", true));
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas() {
		String companyId = AppContexts.user().companyId();
		
		List<MasterData> datas = new ArrayList<>();
		List<WorkTypeReportData> listWorkTypeReport = workTypeReportRepository.findAllWorkType(companyId);
		listWorkTypeReport = listWorkTypeReport.stream().sorted(
				Comparator.comparing(WorkTypeReportData::getDispOrder, Comparator.nullsLast(Integer::compareTo)))
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(listWorkTypeReport)) {
			throw new BusinessException("Msg_393");
		} else {
			List<AbsenceFrame> datasAbsenceFrame = absenceFrameRepository.findAll(companyId);
			Map<Integer, AbsenceFrame> datasAbsenceFrameMap = datasAbsenceFrame.stream()
					.collect(Collectors.toMap(AbsenceFrame::getAbsenceFrameNo, Function.identity()));
			List<SpecialHolidayFrame> datasSpecialHlFrame = specialHolidayFrameRepository.findAll(companyId);
			Map<Integer, SpecialHolidayFrame> datasSpecialHlFrameMap = datasSpecialHlFrame.stream()
					.collect(Collectors.toMap(SpecialHolidayFrame::getSpecialHdFrameNo, Function.identity()));

			listWorkTypeReport.stream().forEach(c -> {

				Map<String, Object> data = new HashMap<>();
				data.put("コード", c.getWorkTypeCode());
				data.put("名称", c.getName());
				data.put("略名", c.getAbbreviationName());
				data.put("記号", c.getSymbolicName());
				data.put("備考", c.getMemo());
				if (c.getDeprecate() == 1) {
					data.put("廃止区分", "✓");
				} else {
					data.put("廃止区分", "");
				}
				data.put("出勤率の計算方法", internationalization.getItemName(c.getCalculateMethod().nameId).get());
				data.put("対象範囲", internationalization.getItemName(c.getWorkAtr().nameId).get());
				data.put("1日", internationalization.getItemName(c.getOneDayCls().nameId).get());
				data.put("午前", internationalization.getItemName(c.getMorningCls().nameId).get());
				data.put("午後", internationalization.getItemName(c.getAfternoonCls().nameId).get());

				data.put("１日の休日区分", internationalization.getItemName(c.getOneDayHolidayAtr().nameId).get());
				data.put("1日の日勤・夜勤時間を求める", checkButtonCheck(c.getOneDayDayNightTimeAsk()));
				data.put("1日の出勤時刻を直行とする", checkButtonCheck(c.getOneDayAttendanceTime()));
				data.put("1日の退勤時刻を直帰とする", checkButtonCheck(c.getOneDayTimeLeaveWork()));
				data.put("1日の公休を消化する", checkButtonCheck(c.getOneDayDigestPublicHd()));
				data.put("1日の代休を発生させる", checkButtonCheck(c.getOneDayGenSubHodiday()));
				data.put("1日の欠勤の集計枠", "");
				AbsenceFrame absenceFrameOneDay = datasAbsenceFrameMap.get(c.getOneDaySumAbsenseNo());
				if (absenceFrameOneDay != null) {
					data.put("1日の欠勤の集計枠", absenceFrameOneDay.getAbsenceFrameName());
				} else {
					data.put("1日の欠勤の集計枠", "");
				}
				SpecialHolidayFrame specialHlFrameOneDay = datasSpecialHlFrameMap.get(c.getOneDaySumSpHodidayNo());
				if (specialHlFrameOneDay != null) {
					data.put("1日の特別休暇の集計枠", specialHlFrameOneDay.getSpecialHdFrameName());
				} else {
					data.put("1日の特別休暇の集計枠", "");
				}
				data.put("1日の休業区分", internationalization.getItemName(c.getOneDaycloseAtr().nameId).get());

				data.put("午前の日勤・夜勤時間を求める", checkButtonCheck(c.getMorningDayNightTimeAsk()));
				data.put("午前の出勤時刻を直行とする", checkButtonCheck(c.getMorningAttendanceTime()));
				data.put("午前の退勤時刻を直帰とする", checkButtonCheck(c.getMorningTimeLeaveWork()));
				data.put("午前の休日日数を数える", checkButtonCheck(c.getMorningCountHodiday()));
				data.put("午前の公休を消化する", checkButtonCheck(c.getMorningDigestPublicHd()));
				data.put("午前の代休を発生させる", checkButtonCheck(c.getMorningGenSubHodiday()));
				AbsenceFrame absenceFrameMorning = datasAbsenceFrameMap.get(c.getMorningSumAbsenseNo());
				if (absenceFrameMorning != null) {
					data.put("午前の欠勤の集計枠", absenceFrameMorning.getAbsenceFrameName());
				} else {
					data.put("午前の欠勤の集計枠", "");
				}
				SpecialHolidayFrame specialHlFrameMorning = datasSpecialHlFrameMap.get(c.getMorningSumSpHodidayNo());
				if (specialHlFrameMorning != null) {
					data.put("午前の特別休暇の集計枠", specialHlFrameMorning.getSpecialHdFrameName());
				} else {
					data.put("午前の特別休暇の集計枠", "");
				}

				data.put("午後の日勤・夜勤時間を求める", checkButtonCheck(c.getAfternoonDayNightTimeAsk()));
				data.put("午後の出勤時刻を直行とする", checkButtonCheck(c.getAfternoonAttendanceTime()));
				data.put("午後の退勤時刻を直帰とする", checkButtonCheck(c.getAfternoonTimeLeaveWork()));
				data.put("午後の休日日数を数える", checkButtonCheck(c.getAfternoonCountHodiday()));
				data.put("午後の公休を消化する", checkButtonCheck(c.getAfternoonDigestPublicHd()));
				data.put("午後の代休を発生させる", checkButtonCheck(c.getAfternoonGenSubHodiday()));
				data.put("午後の欠勤の集計枠", "");
				data.put("午後の特別休暇の集計枠", "");
				AbsenceFrame absenceFrameAfternoon = datasAbsenceFrameMap.get(c.getAfternoonSumAbsenseNo());
				if (absenceFrameAfternoon != null) {
					data.put("午前の欠勤の集計枠", absenceFrameAfternoon.getAbsenceFrameName());
				} else {
					data.put("午前の欠勤の集計枠", "");
				}
				SpecialHolidayFrame specialHlFrameAfternoon = datasSpecialHlFrameMap
						.get(c.getAfternoonSumSpHodidayNo());
				if (specialHlFrameAfternoon != null) {
					data.put("午前の特別休暇の集計枠", specialHlFrameAfternoon.getSpecialHdFrameName());
				} else {
					data.put("午前の特別休暇の集計枠", "");
				}
				data.put("他言語名称", c.getOtherLangName());
				data.put("他言語略名", c.getOtherLangShortName());
				datas.add(new MasterData(data, null, ""));
			});
		}
		return datas;
	}

	public static String checkButtonCheck(int check) {
		if (check == 1) {
			return "○";
		} else {
			return "ー";
		}
	}
}
