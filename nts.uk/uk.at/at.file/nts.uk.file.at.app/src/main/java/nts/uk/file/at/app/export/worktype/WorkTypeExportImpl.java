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

import nts.arc.i18n.custom.IInternationalization;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameRepository;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.file.at.app.export.worktype.data.WorkTypeReportData;
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
	
	private AbsenceFrameRepository  absenceFrameRepository;
	
	private SpecialHolidayFrameRepository  specialHolidayFrameRepository;

	String companyId = AppContexts.user().companyId();

	@Override
	public List<MasterHeaderColumn> getHeaderColumns() {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("1", "コード", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("2", "名称", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("3", "略名", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("4", "記号", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("5", "備考", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("6", "廃止区分", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("7", "出勤率の計算方法", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("8", "対象範囲", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("9", "1日", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("10", "午前", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("11", "午後", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("12", "日勤・夜勤時間を求める", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("13", "出勤時刻を直行とする", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("14", "退勤時刻を直帰とする", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("15", "休日日数を数える", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("16", "公休を消化する", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("17", "代休を発生させる", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("18", "欠勤の集計枠", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("19", "特別休暇の集計枠", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("20", "休業区分", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("21", "他言語名称", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("22", "他言語略名", ColumnTextAlign.CENTER, "", true));
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas() {
		List<MasterData> datas = new ArrayList<>();
		List<WorkTypeReportData> listWorkTypeReport = workTypeReportRepository.findAllWorkType(companyId).stream()
				.sorted(Comparator.comparing(WorkTypeReportData::getDispOrder,
						Comparator.nullsLast(Integer::compareTo)))
				.collect(Collectors.toList());
		
		List<AbsenceFrame> datasAbsenceFrame = absenceFrameRepository.findAll(companyId);
		Map<Integer, AbsenceFrame> datasAbsenceFrameMap = datasAbsenceFrame.stream().collect(Collectors.toMap(AbsenceFrame::getAbsenceFrameNo, Function.identity()));
		List<SpecialHolidayFrame> datasSpecialHlFrame = specialHolidayFrameRepository.findAll(companyId);
		Map<Integer, SpecialHolidayFrame> datasSpecialHlFrameMap = datasSpecialHlFrame.stream().collect(Collectors.toMap(SpecialHolidayFrame::getSpecialHdFrameNo, Function.identity()));

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
			data.put("出勤率の計算方法", internationalization.getItemName(c.getCalculateMethod().nameId));
			data.put("対象範囲", internationalization.getItemName(c.getWorkAtr().nameId));
			data.put("1日", internationalization.getItemName(c.getOneDayCls().nameId));
			data.put("午前", internationalization.getItemName(c.getMorningCls().nameId));
			data.put("午後", internationalization.getItemName(c.getAfternoonCls().nameId));
			data.put("日勤・夜勤時間を求める", checkButtonCheck(c.getDayNightTimeAsk()));
			data.put("出勤時刻を直行とする", checkButtonCheck(c.getAttendanceTime()));
			data.put("退勤時刻を直帰とする", checkButtonCheck(c.getTimeLeaveWork()));
			data.put("休日日数を数える", checkButtonCheck(c.getCountHodiday()));
			data.put("公休を消化する", checkButtonCheck(c.getDigestPublicHd()));
			data.put("代休を発生させる", checkButtonCheck(c.getGenSubHodiday()));
			
			AbsenceFrame absenceFrame = datasAbsenceFrameMap.get(c.getSumAbsenseNo());
			if (absenceFrame != null) {
				data.put("欠勤の集計枠",  absenceFrame.getAbsenceFrameName());
			} else {
				data.put("欠勤の集計枠",  "");
			}
			
			SpecialHolidayFrame specialHlFrame = datasSpecialHlFrameMap.get(c.getSumSpHodidayNo());
			if (specialHlFrame != null) {
				data.put("特別休暇の集計枠", specialHlFrame.getSpecialHdFrameName());
			} else {
				data.put("特別休暇の集計枠", "");
			}
			data.put("休業区分", internationalization.getItemName(c.getCloseAtr().nameId));
			data.put("他言語名称", c.getOtherLangName());
			data.put("他言語略名", c.getOtherLangShortName());
			datas.add(new MasterData(data, null, ""));
		});
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
