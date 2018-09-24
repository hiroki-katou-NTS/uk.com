package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheduleCorrectionParameter.ScheduleCorrectedItem;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheduleCorrectionParameter.ScheduleCorrectionTarget;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.employment.ScEmploymentAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItem;
import nts.uk.ctx.at.schedule.dom.scheduleitemmanagement.ScheduleItemManagementRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessorContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogProcessor;

@Stateless
public class BasicScheduleCorrectionProcessor extends DataCorrectionLogProcessor {
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Inject
	private SyJobTitleAdapter syJobTitleAdapter;
	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;
	@Inject
	private SyClassificationAdapter syClassificationAdapter;
	@Inject
	private ScEmploymentAdapter scEmploymentAdapter;
	@Inject
	private ScheduleItemManagementRepository scheduleItemManagementRepository;

	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.SCHEDULE;
	}

	@Override
	protected void buildLogContents(CorrectionLogProcessorContext context) {
		BasicScheduleCorrectionParameter parameter = context.getParameter();
		ItemInfo itemInfo = new ItemInfo(null, null, null, null);
		String companyId = AppContexts.user().companyId();
		List<String> listWorkTypeCd =  new ArrayList<>();
		List<String> listWorkTimeCd =  new ArrayList<>();
		List<String> listEmploymentCd =  new ArrayList<>();
		List<String> listClassCd =  new ArrayList<>();
		List<String> listJobId =  new ArrayList<>();
		List<String> listWorkplaceCd =  new ArrayList<>();
		List<GeneralDate> listDate =  new ArrayList<>();
		
		List<ScheduleItem> listScheduleItem = scheduleItemManagementRepository.findAllScheduleItem(companyId);
		
		parameter.getTargets().forEach(target -> {
			listDate.add(target.getDate());
			target.getCorrectedItems().forEach(y -> {
				switch (y.getItemNo()) {
				case 1:
					listWorkTypeCd.add(y.getBefore());
					listWorkTypeCd.add(y.getAfter());
					break;
				case 2:
					listWorkTimeCd.add(y.getBefore());
					listWorkTimeCd.add(y.getAfter());
					break;
				case 63:
					listEmploymentCd.add(y.getBefore());
					listEmploymentCd.add(y.getAfter());
					break;
				case 64:
					listClassCd.add(y.getBefore());
					listClassCd.add(y.getAfter());
					break;
				case 65:
					listWorkplaceCd.add(y.getBefore());
					listWorkplaceCd.add(y.getAfter());
					break;
				case 66:
					listJobId.add(y.getBefore());
					listJobId.add(y.getAfter());
					break;

				default:
					break;
				}
			});
		});
		
		// remove value = null in list
		List<String> listWorkTypeCode = listWorkTypeCd.stream().filter(x-> x != null).collect(Collectors.toList());
		List<String> listWorkTimeCode = listWorkTimeCd.stream().filter(x-> x != null).collect(Collectors.toList());
		List<String> listJobIdd = listJobId.stream().filter(x-> x != null).collect(Collectors.toList());
		List<String> listWorkplace = listWorkplaceCd.stream().filter(x-> x != null).collect(Collectors.toList());
		List<String> listClass = listClassCd.stream().filter(x-> x != null).collect(Collectors.toList());
		List<String> listEmploym = listEmploymentCd.stream().filter(x-> x != null).collect(Collectors.toList());
		
		// get Code/Id-Name
		Map<String, String> listWorkTypeCodeName = listWorkTypeCode.size() > 0
				? workTypeRepository.getCodeNameWorkType(companyId, listWorkTypeCode) : new HashMap<>();
		Map<String, String> listWorkTimeCodeName = listWorkTimeCode.size() > 0
				? workTimeSettingRepository.getCodeNameByListWorkTimeCd(companyId, listWorkTimeCode) : new HashMap<>();
		Map<Pair<String, GeneralDate>, String> listWorkplaceIdDateName = (listWorkplace.size() > 0
				&& listDate.size() > 0)
						? syWorkplaceAdapter.getWorkplaceMapCodeBaseDateName(companyId, listWorkplace, listDate)
						: new HashMap<>();
		Map<Pair<String, GeneralDate>, String> listJobTitleIdDateName = (listJobIdd.size() > 0 && listDate.size() > 0)
				? syJobTitleAdapter.getJobTitleMapIdBaseDateName(companyId, listJobIdd, listDate) : new HashMap<>();
		Map<String, String> listClassification = listClass.size() > 0
				? syClassificationAdapter.getClassificationMapCodeName(companyId, listClass) : new HashMap<>();
		Map<String, String> listEmp = listEmploym.size() > 0
				? scEmploymentAdapter.getEmploymentMapCodeName(companyId, listEmploym) : new HashMap<>();

		List<ScheduleCorrectionTarget> targets = parameter.getTargets();
		
		for (val target : targets) {
			
			UserInfo targetUser = this.userInfoAdaptor.findByEmployeeId(target.getEmployeeId());
			List<ScheduleCorrectedItem> correctedItems = target.getCorrectedItems();
			GeneralDate date = target.getDate();
			
			for (val correctedItem : correctedItems) {
				
				switch (correctedItem.getItemNo().intValue()) {
				case 1:
					String workTypeNameBefore = listWorkTypeCodeName.get(correctedItem.getBefore());
					String workTypeNameAfter = listWorkTypeCodeName.get(correctedItem.getAfter());
					itemInfo = correctedItem.toItemInfo(workTypeNameBefore, workTypeNameAfter);
					break;
				case 2:
					String workTimeNameBefore = listWorkTimeCodeName.get(correctedItem.getBefore());
					String workTimeNameAfter = listWorkTimeCodeName.get(correctedItem.getAfter());
					itemInfo = correctedItem.toItemInfo(workTimeNameBefore, workTimeNameAfter);
					break;
				case 27:
				case 30:
					String nameChildCareAtrBef = correctedItem.getBefore() == null ? null
							: ChildCareAtr.valueOf(Integer.valueOf(correctedItem.getBefore())).description;
					String nameChildCareAtrAft = correctedItem.getAfter() == null ? null
							: ChildCareAtr.valueOf(Integer.valueOf(correctedItem.getAfter())).description;
					itemInfo = correctedItem.toItemInfo(nameChildCareAtrBef, nameChildCareAtrAft);
					break;
				case 40:
					String nameConfirmedAtr = correctedItem.getBefore() == null ? null
							: ConfirmedAtr.valueOf(Integer.valueOf(correctedItem.getBefore())).description;
					itemInfo = correctedItem.toItemInfo(nameConfirmedAtr,
							ConfirmedAtr.valueOf(Integer.valueOf(correctedItem.getAfter())).description);
					break;
				case 41:
				case 42:
					String nameBounceAtrBef = correctedItem.getBefore() == null ? null
							: BounceAtr.valueOf(Integer.valueOf(correctedItem.getBefore())).description;
					String nameBounceAtrAft = correctedItem.getAfter() == null ? null
							: BounceAtr.valueOf(Integer.valueOf(correctedItem.getAfter())).description;
					itemInfo = correctedItem.toItemInfo(nameBounceAtrBef, nameBounceAtrAft);
					break;
				case 63:
					String employmentNameBef = listEmp.get(correctedItem.getBefore());
					String employmentNameAft = listEmp.get(correctedItem.getAfter());
					itemInfo = correctedItem.toItemInfo(employmentNameBef, employmentNameAft);
					break;
				case 64:
					String classificationNameBef = listClassification.get(correctedItem.getBefore());
					String classificationNameAft = listClassification.get(correctedItem.getAfter());
					itemInfo = correctedItem.toItemInfo(classificationNameBef, classificationNameAft);
					break;
				case 65:
					String workplaceNameBef = listWorkplaceIdDateName.get(Pair.of(correctedItem.getBefore(), date));
					String workplaceNameAft = listWorkplaceIdDateName.get(Pair.of(correctedItem.getAfter(), date));
					itemInfo = correctedItem.toItemInfo(workplaceNameBef, workplaceNameAft);
					break;
				case 66:
					String jobIdNameBef = listJobTitleIdDateName.get(Pair.of(correctedItem.getBefore(), date));
					String jobIdNameAft = listJobTitleIdDateName.get(Pair.of(correctedItem.getAfter(), date));
					itemInfo = correctedItem.toItemInfo(jobIdNameBef, jobIdNameAft);
					break;

				default:
					itemInfo = correctedItem.toItemInfo(correctedItem.getBefore(), correctedItem.getAfter());
					break;
				}
				
				int showOrder = listScheduleItem.stream().filter(x -> x.getScheduleItemId().equals(correctedItem.getItemNo().toString())).findFirst().get().getDispOrder();
				
				val correction = this.newCorrection(
						targetUser,
						correctedItem.getAttr(),
						itemInfo,
						correctedItem.getRemark(),
						target.getDate(),
						showOrder);
				
				context.addCorrection(correction);
			}
		}
	}
	
	private BasicScheduleCorrection newCorrection(
			UserInfo targetUser,
			CorrectionAttr correctionAttr,
			ItemInfo correctedItem,
			String remark,
			GeneralDate date,
			int showOrder) {
		
		return new BasicScheduleCorrection(correctionAttr,correctedItem,remark,date,showOrder,targetUser);
	}

}
