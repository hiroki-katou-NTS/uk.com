package nts.uk.ctx.bs.employee.app.command.employee.employeeinfo.workplacegroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.AffWorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupRespository;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceReplaceResult;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.AddWplOfWorkGrpService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceExportService;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.shr.com.context.AppContexts;
/**
 * 職場グループを登録する
 * @author phongtq
 *
 */
@Stateless
public class RegisterWorkplaceGroupCommandHandler extends CommandHandlerWithResult<RegisterWorkplaceGroupCommand, RegisterWorkplaceGroupResult>{

	@Inject
	private WorkplaceGroupRespository repo;
	
	@Inject
	private AffWorkplaceGroupRespository affRepo;
	
	@Inject 
	private WorkplaceExportService service;
	
	@Override
	protected RegisterWorkplaceGroupResult handle(CommandHandlerContext<RegisterWorkplaceGroupCommand> context) {
		String CID = AppContexts.user().companyId();
		String WKPGRPID = IdentifierUtil.randomUniqueId();
		RegisterWorkplaceGroupCommand cmd = context.getCommand();
		
		// 1: get(会社ID, 職場グループコード)
		// return Optional<職場グループ>
		Optional<WorkplaceGroup> wpgrp = repo.getByCode(CID, cmd.getWkpGrCD());
		
		// 2: 職場グループ.isPresent()
		if (wpgrp.isPresent())
			throw new BusinessException("Msg_3");
		
		if (CollectionUtil.isEmpty(cmd.getLstWKPID())) {
			throw new BusinessException("MsgB_2", I18NText.getText("Com_Workplace"));
		}
		
		// 3: 職場グループを作成する([mapping]): 職場グループ
		WorkplaceGroup group = cmd.toDomain(CID, WKPGRPID);
		
		AddWplOfWorkGrpService.Require addRequire = new AddWplOfWorkGrpRequireImpl(affRepo);
		List<WorkplaceReplaceResult> wplResult = new ArrayList<>();
		
		// 4: 追加する(Require, 職場グループ, 職場ID):職場グループの職場入替処理結果
		// loop： 職場ID in 職場IDリスト
		cmd.getLstWKPID().forEach(x->{
			wplResult.add(AddWplOfWorkGrpService.addWorkplace(addRequire, group, x));
		});
		
		// 5: 職場グループ所属情報の永続化処理 = 処理結果リスト : filter $.永続化処理.isPresent
		// map $.永続化処理
		List<WorkplaceReplaceResult> resultProcess = wplResult.stream().filter(x->x.getPersistenceProcess().isPresent()).collect(Collectors.toList());
		
		GeneralDate baseDate = GeneralDate.today();
		// 6: [No.560]職場IDから職場の情報をすべて取得する
		List<WorkplaceInforParam> listWorkplaceInfo = service.getWorkplaceInforFromWkpIds(CID, cmd.getLstWKPID(), baseDate);
		
		// persits
		repo.insert(group);
		
		// 7: 職場グループ所属情報の永続化処理
		resultProcess.forEach(x->{
			AtomTask atomTask = x.getPersistenceProcess().get();
			transaction.execute(() -> {
				atomTask.run();
			});
		});
		
		RegisterWorkplaceGroupResult groupResult = new RegisterWorkplaceGroupResult(cmd.getLstWKPID(), listWorkplaceInfo, resultProcess, WKPGRPID);
		
		return groupResult;
	}
	
	@AllArgsConstructor
	private static class AddWplOfWorkGrpRequireImpl implements AddWplOfWorkGrpService.Require {
		
		@Inject
		private AffWorkplaceGroupRespository affRepo;
		
		// get ( 会社ID, 職場ID )
		@Override
		public Optional<AffWorkplaceGroup> getByWKPID(String WKPID) {
			String CID = AppContexts.user().companyId();
			return affRepo.getByWKPID(CID, WKPID);
		}

		// insert ( 職場グループ所属情報 )
		@Override
		public void insert(AffWorkplaceGroup affWorkplaceGroup) {
			affRepo.insert(affWorkplaceGroup);
		}
		
	}
}
