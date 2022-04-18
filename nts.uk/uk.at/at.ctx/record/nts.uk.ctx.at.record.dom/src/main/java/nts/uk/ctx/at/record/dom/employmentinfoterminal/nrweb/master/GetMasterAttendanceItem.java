package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.classification.RecClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.jobtitle.RecJobTitleAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.EnumCodeName;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.AllMasterAttItem.MasterAttItemDetail;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.repository.BusinessTypesRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * @author thanh_nx
 *
 *         勤怠項目のマスタの取得
 */
@Stateless
public class GetMasterAttendanceItem {

	@Inject
	private DailyAttendanceItemRepository daiAttItemRepo;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private WorkTimeSettingRepository workTimeRepo;

	@Inject
	private WorkLocationRepository workLocationRepository;

	@Inject
	private DivergenceReasonSelectRepository divergenceRepository;

	@Inject
	private BusinessTypesRepository businessTypesRepository;

	@Inject
	private BPSettingRepository bpSettingRepository;

	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;

	@Inject
	private RecClassificationAdapter recClassificationAdapter;

	@Inject
	private RecJobTitleAdapter recJobTitleAdapter;

	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;

	//RequestList707
	public List<AllMasterAttItem> getAllMaster(String companyId, List<Integer> lstAtt, GeneralDate baseDate) {

		// 日次の勤怠項目を取得する
		List<TypesMasterRelatedDailyAttendanceItem> lstAttSetting = daiAttItemRepo.getListById(companyId, lstAtt)
				.stream().filter(x -> x.getMasterType().isPresent()).map(x -> x.getMasterType().get()).distinct()
				.collect(Collectors.toList());
		List<AllMasterAttItem> result = new ArrayList<>();
		lstAttSetting.forEach(type -> {

			switch (type) {
			case WORK_TYPE:
				val masterWork = workTypeRepository.getAllWorkTypeNotAbolished(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getWorkTypeCode().v(), x.getName().v()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.WORK_TYPE, masterWork));
				break;

			// 就業時間帯
			case WORKING_HOURS:
				val masterTime = workTimeRepo.findByCId(companyId).stream().map(x -> new MasterAttItemDetail("",
						x.getWorktimeCode().v(), x.getWorkTimeDisplayName().getWorkTimeName().v()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.WORKING_HOURS, masterTime));
				break;
			// 勤務場所
			case WORK_LOCATION:
				val masterLocation = workLocationRepository.findAll(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getWorkLocationCD().v(), x.getWorkLocationName().v()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.WORK_LOCATION, masterLocation));
				break;
			// 乖離理由
			case REASON_DIVERGENCE:
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.REASON_DIVERGENCE,
						divergenceRepository.getAllWithCompany(companyId)));
				break;
			// 職場
			case WORK_PLACE:
				val masterWpl = syWorkplaceAdapter.getAllActiveWorkplaceInfor(companyId, baseDate).stream().map(
						x -> new MasterAttItemDetail(x.getWorkplaceId(), x.getWorkplaceCode(), x.getWorkplaceName()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.WORK_PLACE, masterWpl));
				break;
			// 分類
			case CLASSIFICATION:
				val masterClass = recClassificationAdapter.getClassificationByCompanyId(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getLeft(), x.getRight())).collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.CLASSIFICATION, masterClass));
				break;
			// 職位
			case POSITION:
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.POSITION,
						recJobTitleAdapter.findAll(companyId, baseDate)));
				break;
			// 雇用
			case EMPLOYMENT:
				val masterEmp = syEmploymentAdapter.findByCid(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getEmploymentCode(), x.getEmploymentName()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.EMPLOYMENT, masterEmp));
				break;
			// するしない区分
			case NOT_USE_ATR:
				val dowork = EnumCodeName.getDowork().stream()
						.map(x -> new MasterAttItemDetail("", String.valueOf(x.getCode()), x.getName()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.NOT_USE_ATR, dowork));
				break;
			// 外出理由
			case REASON_GOING_OUT:
				val reasonGoout = EnumCodeName.getReasonGoOut().stream()
						.map(x -> new MasterAttItemDetail("", String.valueOf(x.getCode()), x.getName()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.REASON_GOING_OUT, reasonGoout));
				break;
			// 時間外の自動計算区分
			case AUTO_CALC_CTG_OUTSIDE:
				val calcCompact = EnumCodeName.getCalcCompact().stream()
						.map(x -> new MasterAttItemDetail("", String.valueOf(x.getCode()), x.getName()))
						.collect(Collectors.toList());
				result.add(
						new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.AUTO_CALC_CTG_OUTSIDE, calcCompact));
				break;
			// 備考
			case REMARKS:
				break;
			// 時間外の上限設定
			case TIME_LIMIT_UPPER_SET:
				val timeLimit = EnumCodeName.getCalcCompact().stream()
						.map(x -> new MasterAttItemDetail("", String.valueOf(x.getCode()), x.getName()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.TIME_LIMIT_UPPER_SET, timeLimit));
				break;
			// 勤務種別
			case BUSINESS_TYPE:
				val masterBuss = businessTypesRepository.findAll(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getBusinessTypeCode().v(), x.getBusinessTypeName().v()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.BUSINESS_TYPE, masterBuss));

				break;
			// 作業
			case SUPPORT_WORK:
				// domain ???
				break;
			// 加給
			case BONUS_PAY:
				val masterBonus = bpSettingRepository.getAllBonusPaySetting(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getCode().v(), x.getName().v()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem.BONUS_PAY, masterBonus));
				break;
			default:
				break;
			}

		});

		return result;

	}

}
