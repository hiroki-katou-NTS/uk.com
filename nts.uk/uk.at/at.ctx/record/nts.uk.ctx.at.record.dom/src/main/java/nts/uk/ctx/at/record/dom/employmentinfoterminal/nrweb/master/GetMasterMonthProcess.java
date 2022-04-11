package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.classification.RecClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentAdapter;
import nts.uk.ctx.at.record.dom.adapter.jobtitle.RecJobTitleAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.AllMasterAttItem.MasterAttItemDetail;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.repository.BusinessTypesRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;

/**
 * @author thanh_nx
 *
 */
@Stateless
public class GetMasterMonthProcess {

	@Inject
	private BusinessTypesRepository businessTypesRepository;

	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;

	@Inject
	private RecClassificationAdapter recClassificationAdapter;

	@Inject
	private RecJobTitleAdapter recJobTitleAdapter;

	@Inject
	private SyEmploymentAdapter syEmploymentAdapter;

	// 月次の勤怠項目のマスタの取得
	public List<AllMasterAttItemMonth> getMasterCodeName(String companyId, GeneralDate baseDate,
			List<PrimitiveValueOfAttendanceItem> itemTypes) {
		List<AllMasterAttItemMonth> result = new ArrayList<>();
		itemTypes.forEach(type -> {
			switch (type) {
			// 職場
			case WORKPLACE_CD:
				val masterWpl = syWorkplaceAdapter.getAllActiveWorkplaceInfor(companyId, baseDate).stream().map(
						x -> new MasterAttItemDetail(x.getWorkplaceId(), x.getWorkplaceCode(), x.getWorkplaceName()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItemMonth(PrimitiveValueOfAttendanceItem.WORKPLACE_CD, masterWpl));
				break;
			// 分類
			case CLASSIFICATION_CD:
				val masterClass = recClassificationAdapter.getClassificationByCompanyId(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getLeft(), x.getRight())).collect(Collectors.toList());
				result.add(new AllMasterAttItemMonth(PrimitiveValueOfAttendanceItem.CLASSIFICATION_CD, masterClass));
				break;
			// 職位
			case POSITION_CD:
				result.add(new AllMasterAttItemMonth(PrimitiveValueOfAttendanceItem.POSITION_CD,
						recJobTitleAdapter.findAll(companyId, baseDate)));
				break;
			// 雇用
			case EMP_CTG_CD:
				val masterEmp = syEmploymentAdapter.findByCid(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getEmploymentCode(), x.getEmploymentName()))
						.collect(Collectors.toList());
				result.add(new AllMasterAttItemMonth(PrimitiveValueOfAttendanceItem.EMP_CTG_CD, masterEmp));
				break;

			// 勤務種別
			case WORK_TYPE_DIFFERENT_CD:
				val masterBuss = businessTypesRepository.findAll(companyId).stream()
						.map(x -> new MasterAttItemDetail("", x.getBusinessTypeCode().v(), x.getBusinessTypeName().v()))
						.collect(Collectors.toList());
				result.add(
						new AllMasterAttItemMonth(PrimitiveValueOfAttendanceItem.WORK_TYPE_DIFFERENT_CD, masterBuss));

				break;

			default:
				break;
			}

		});
		return result;
	}

	@Getter
	public class AllMasterAttItemMonth {

		// 種類
		private PrimitiveValueOfAttendanceItem type;

		// 勤怠項目のマスタ
		private List<MasterAttItemDetail> lstDetail;

		public AllMasterAttItemMonth(PrimitiveValueOfAttendanceItem type, List<MasterAttItemDetail> lstDetail) {
			this.type = type;
			this.lstDetail = lstDetail;
		}

	}
}
