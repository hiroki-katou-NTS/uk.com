package nts.uk.ctx.at.schedule.app.command.shift.management.shifttable;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTable;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTableRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 対象組織公開情報を登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.App.対象組織公開情報を登録する
 * 
 * @author quytb
 *
 */

@Stateless
public class RegisterShiftTableCommandHandler extends CommandHandler<ShiftTableSaveCommand> {
	@Inject
	private PublicManagementShiftTableRepository shiftTableRepository;

	@Override
	protected void handle(CommandHandlerContext<ShiftTableSaveCommand> context) {
		ShiftTableSaveCommand command = context.getCommand();
//		TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(
//				TargetOrganizationUnit.valueOf(command.getUnit()), Optional.ofNullable(command.getWorkplaceId()),
//				Optional.ofNullable(command.getWorkplaceGroupId()));
		
		TargetOrgIdenInfor targetOrgIdenInfor = command.getUnit() == 0
				? TargetOrgIdenInfor.creatIdentifiWorkplace(command.getWorkplaceId())
				: TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(command.getWorkplaceGroupId());

		/** 1: get(対象組織): シフト表の公開管理 **/
		Optional<PublicManagementShiftTable> optShiftTable = shiftTableRepository.get(targetOrgIdenInfor);
		
		/** 2: シフト表の公開管理.isPresent: set(公開期間の終了日, 編集開始日) **/
		if (optShiftTable.isPresent()) {
			PublicManagementShiftTable publicManagementShiftTable = new PublicManagementShiftTable(targetOrgIdenInfor,
					command.toPublicDate(), Optional.ofNullable(command.toEditDate()));
			
			/** 4: persist() **/
			shiftTableRepository.update(publicManagementShiftTable);
			
		/** 3: シフト表の公開管理.isEmpty: create()**/
		} else {		 
			
			/** 4: persist() **/
			shiftTableRepository.insert(PublicManagementShiftTable.createPublicManagementShiftTable(targetOrgIdenInfor,
					command.toPublicDate(), Optional.ofNullable(command.toEditDate())));
		}
	}
}
