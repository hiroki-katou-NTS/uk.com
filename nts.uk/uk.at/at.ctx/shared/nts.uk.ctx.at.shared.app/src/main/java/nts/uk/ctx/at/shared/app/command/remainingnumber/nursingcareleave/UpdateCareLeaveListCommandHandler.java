package nts.uk.ctx.at.shared.app.command.remainingnumber.nursingcareleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareLeaveRemainingDataService;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
@Transactional
public class UpdateCareLeaveListCommandHandler extends CommandHandlerWithResult<List<UpdateCareLeaveCommand>, List<MyCustomizeException>>
implements PeregUpdateListCommandHandler<UpdateCareLeaveCommand>{
	@Inject
	private ChildCareLeaveRemainingDataService  service;

	@Inject
	private CareLeaveRemainingInfoRepository careInfoRepo;

	@Inject
	private ChildCareLeaveRemInfoRepository childCareInfoRepo;

	@Inject
	private ChildCareUsedNumberRepository childCareDataRepo;

	@Inject
	private CareUsedNumberRepository careDataRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00036";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateCareLeaveCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateCareLeaveCommand>> context) {
		List<UpdateCareLeaveCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<CareUsedNumberData> leaveCareDataInsert = new ArrayList<>();
		List<CareUsedNumberData> leaveCareDataUpdate = new ArrayList<>();
		List<CareLeaveRemainingInfo> leaveCareInfoInsert = new ArrayList<>();
		List<CareLeaveRemainingInfo> leaveCareInfoUpdate = new ArrayList<>();
		List<ChildCareUsedNumberData> childCareDataInsert = new ArrayList<>();
		List<ChildCareUsedNumberData> childCareDataUpdate = new ArrayList<>();
		List<ChildCareLeaveRemainingInfo> childCareLeaveInfoInsert = new ArrayList<>();
		List<ChildCareLeaveRemainingInfo> childCareLeaveInfoUpdate = new ArrayList<>();

		createData(cmd, childCareDataInsert, childCareDataUpdate, leaveCareDataInsert, leaveCareDataUpdate,
				childCareLeaveInfoInsert, childCareLeaveInfoUpdate, leaveCareInfoInsert, leaveCareInfoUpdate);

		service.addData(cid, childCareDataInsert, leaveCareDataInsert, childCareLeaveInfoInsert, leaveCareInfoInsert);

		service.updateData(cid, childCareDataUpdate, leaveCareDataUpdate, childCareLeaveInfoUpdate, leaveCareInfoUpdate);
		 return new ArrayList<MyCustomizeException>();
	}

	private void createData(List<UpdateCareLeaveCommand> cmd,
			List<ChildCareUsedNumberData> childCareDataInsert,
			List<ChildCareUsedNumberData> childCareDataUpdate,
			List<CareUsedNumberData> leaveCareDataInsert,
			List<CareUsedNumberData> leaveCareDataUpdate,
			List<ChildCareLeaveRemainingInfo> childCareLeaveInfoInsert,
			List<ChildCareLeaveRemainingInfo> childCareLeaveInfoUpdate,
			List<CareLeaveRemainingInfo> leaveCareInfoInsert,
			List<CareLeaveRemainingInfo> leaveCareInfoUpdate) {

		String cid = AppContexts.user().companyId();
		List<String> sids = cmd.stream().map(c -> c.getSId()).collect(Collectors.toList());
		List<ChildCareUsedNumberData> checkChildCareDatailsLst = childCareDataRepo.find(sids);
		List<ChildCareLeaveRemainingInfo> checkChildCareInfoLst = childCareInfoRepo.getChildCareByEmpIdsAndCid(cid, sids);
		List<CareUsedNumberData> checkCareDatailsLst = careDataRepo.find(sids);
		List<CareLeaveRemainingInfo> checkCareInfoLst = careInfoRepo.getCareByEmpIdsAndCid(cid, sids);

		cmd.stream().forEach(c ->{

			// 子の看護-使用数
			ChildCareUsedNumberData childUsedNumberData = new ChildCareUsedNumberData(
					c.getSId(),
					new ChildCareNurseUsedNumber(
							new DayNumberOfUse(c.getChildCareUsedDays().doubleValue())
							,Optional.empty()
					)
			);

			Optional<ChildCareUsedNumberData> childCareUsedOpt= checkChildCareDatailsLst.stream().filter(item -> item.getEmployeeId().equals(c.getSId())).findFirst();
			if (childCareUsedOpt.isPresent()) {
				childCareDataUpdate.add(childCareUsedOpt.get());
			} else {
				if (c.getChildCareUsedDays() != null)
					childCareDataInsert.add(childUsedNumberData);
			}

			// 介護-使用数
			CareUsedNumberData careUsedNumberData = new CareUsedNumberData(
					c.getSId(),
					new ChildCareNurseUsedNumber(
							new DayNumberOfUse(c.getCareUsedDays().doubleValue())
							,Optional.empty()
					)
			);

			Optional<CareUsedNumberData> careUsedOpt= checkCareDatailsLst.stream().filter(item -> item.getEmployeeId().equals(c.getSId())).findFirst();
			if (careUsedOpt.isPresent()) {
				leaveCareDataUpdate.add(careUsedOpt.get());
			} else {
				if (c.getCareUsedDays() != null)
					leaveCareDataInsert.add(careUsedNumberData);
			}

			// 子の看護 - 上限情報
			ChildCareLeaveRemainingInfo childCareInfo = ChildCareLeaveRemainingInfo.createChildCareLeaveInfo(c.getSId(),
					c.getChildCareUseArt().intValue(),
					c.getChildCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
							: c.getChildCareUpLimSet().intValue(),
					c.getChildCareThisFiscal() == null ? null : c.getChildCareThisFiscal().intValue(),
					c.getChildCareNextFiscal() == null ? null : c.getChildCareNextFiscal().intValue());
			Optional<ChildCareLeaveRemainingInfo> childCareInfoOpt = checkChildCareInfoLst.stream().filter(item -> item.getSId().equals(c.getSId())).findFirst();
			if (childCareInfoOpt.isPresent()) {
				childCareLeaveInfoUpdate.add(childCareInfo);
			} else {
				childCareLeaveInfoInsert.add(childCareInfo);
			}

			// 介護-上限情報
			CareLeaveRemainingInfo careInfo = CareLeaveRemainingInfo.createCareLeaveInfo(c.getSId(),
					c.getCareUseArt().intValue(),
					c.getCareUpLimSet() == null ? UpperLimitSetting.FAMILY_INFO.value
							: c.getCareUpLimSet().intValue(),
					c.getCareThisFiscal() == null ? null : c.getCareThisFiscal().intValue(),
					c.getCareNextFiscal() == null ? null : c.getCareNextFiscal().intValue());
			Optional<CareLeaveRemainingInfo> careInfoOpt = checkCareInfoLst.stream().filter(item -> item.getSId().equals(c.getSId())).findFirst();
			if (careInfoOpt.isPresent()) {
				leaveCareInfoUpdate.add(careInfo);
			} else {
				leaveCareInfoInsert.add(careInfo);
			}

		});
	}

}
