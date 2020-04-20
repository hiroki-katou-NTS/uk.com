package nts.uk.ctx.bs.employee.app.command.employee.employeeinfo.workplacegroup;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupName;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupType;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceReplaceResult;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.ReplaceWorkplacesService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.shr.com.context.AppContexts;
/**
 * 職場グループを更新する
 * @author phongtq
 *
 */
@Stateless
public class UpdateWorkplaceGroupCommandHandler extends CommandHandlerWithResult<RegisterWorkplaceGroupCommand, RegisterWorkplaceGroupResult>{

	@Inject
	private WorkplaceGroupRespository repo;
	
	@Inject
	private AffWorkplaceGroupRespository affRepo;
	
	@Inject 
	private WorkplaceExportService service;
	
	@Override
	protected RegisterWorkplaceGroupResult handle(CommandHandlerContext<RegisterWorkplaceGroupCommand> context) {
		String CID = AppContexts.user().companyId();
		RegisterWorkplaceGroupCommand cmd = context.getCommand();
		
		// 1: get(会社ID, 職場グループコード)
		// return Optional<職場グループ>
		Optional<WorkplaceGroup> wpgrp = repo.getByCode(CID, cmd.getWKPGRPCode());
		
		// 2: set(職場グループ名称, 職場グループ種別)
		wpgrp.get().setWKPGRPName(new WorkplaceGroupName(cmd.getWKPGRPName()));
		wpgrp.get().setWKPGRPType(EnumAdaptor.valueOf(cmd.getWKPGRPType(), WorkplaceGroupType.class));
		
		ReplaceWorkplacesService.Require updateRequire = new UpdateWplOfWorkGrpRequireImpl(affRepo);
		
		// 3: 入れ替える(Require, 職場グループ, List<職場ID>): List<職場グループの職場入替処理結果>
		Map<String, WorkplaceReplaceResult> wplResult = ReplaceWorkplacesService.getWorkplace(updateRequire, wpgrp.get(), cmd.getLstWKPID());
		
		// 4: 職場グループ所属情報の永続化処理 = 処理結果リスト : filter $.永続化処理.isPresent
		// map $.永続化処理
		List<WorkplaceReplaceResult> resultProcess = wplResult.entrySet().stream()
				.filter(x->x.getValue().getPersistenceProcess().isPresent())
				.map(x -> (WorkplaceReplaceResult)x.getValue()).collect(Collectors.toList());
		
		GeneralDate baseDate = GeneralDate.today();
		// 5: [No.560]職場IDから職場の情報をすべて取得する
		List<WorkplaceInforParam> listWorkplaceInfo = service.getWorkplaceInforFromWkpIds(CID, cmd.getLstWKPID(), baseDate);
		
		this.repo.insert(wpgrp.get());
		
		// 6: 職場グループ所属情報の永続化処理
		resultProcess.forEach(x->{
			AtomTask atomTask = x.getPersistenceProcess().get();
			transaction.execute(() -> {
				atomTask.run();
			});
		});
		
		RegisterWorkplaceGroupResult groupResult = new RegisterWorkplaceGroupResult(cmd.getLstWKPID(), listWorkplaceInfo, resultProcess);
		
		return groupResult;
	}
	
	@AllArgsConstructor
	private static class UpdateWplOfWorkGrpRequireImpl implements ReplaceWorkplacesService.Require {
		
		@Inject
		private AffWorkplaceGroupRespository affRepo;
		
		@Override
		public Optional<AffWorkplaceGroup> getByWKPID(String WKPID) {
			String CID = AppContexts.user().companyId();
			return affRepo.getByWKPID(CID, WKPID);
		}

		@Override
		public void insert(AffWorkplaceGroup affWorkplaceGroup) {
			affRepo.insert(affWorkplaceGroup);
		}

		@Override
		public List<AffWorkplaceGroup> getByListWKPID(List<String> WKPID) {
			String CID = AppContexts.user().companyId();
			return affRepo.getByListWKPID(CID, WKPID);
		}

		@Override
		public void deleteByWKPID(String WKPID) {
			String CID = AppContexts.user().companyId();
			affRepo.deleteByWKPID(CID, WKPID);
		}
	}
}
