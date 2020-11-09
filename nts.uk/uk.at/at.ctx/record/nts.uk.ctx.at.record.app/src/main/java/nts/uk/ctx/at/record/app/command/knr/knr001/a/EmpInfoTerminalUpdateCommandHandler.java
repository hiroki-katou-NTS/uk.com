package nts.uk.ctx.at.record.app.command.knr.knr001.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ConvertEmbossCategory;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerMemo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.IPAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.端末情報.APP.就業情報端末の更新をする.就業情報端末の更新をする
 * @author dungbn
 *
 */
@Stateless
public class EmpInfoTerminalUpdateCommandHandler extends CommandHandler<EmpInfoTerminalResgiterAndUpdateCommand> {

	@Inject
	private EmpInfoTerminalRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<EmpInfoTerminalResgiterAndUpdateCommand> context) {
		
		String contractCode = AppContexts.user().contractCode();

		EmpInfoTerminalResgiterAndUpdateCommand command = context.getCommand();

		// 5: set()
		EmpInfoTerminal empInfoTerminal = new EmpInfoTerminal.EmpInfoTerminalBuilder(
				Optional.ofNullable(command.getIpAddress()).map(e -> new IPAddress(e)), new MacAddress(command.getMacAddress()),
				new EmpInfoTerminalCode(command.getEmpInfoTerCode()), Optional.ofNullable(command.getTerSerialNo()).map(e -> new EmpInfoTerSerialNo(e)),
				new EmpInfoTerminalName(command.getEmpInfoTerName()), new ContractCode(contractCode))
						.createStampInfo(new CreateStampInfo(
								new OutPlaceConvert(NotUseAtr.valueOf(command.getReplace()),
										Optional.ofNullable(command.getGoOutReason() == null ? null
												: GoingOutReason.valueOf(command.getGoOutReason()))),
								new ConvertEmbossCategory(NotUseAtr.valueOf(command.getEntranceExit()),
										NotUseAtr.valueOf(command.getOutSupport())),
								Optional.ofNullable(command.getWorkLocationCode() == null ? null
										: new WorkLocationCD(command.getWorkLocationCode()))))
						.modelEmpInfoTer(ModelEmpInfoTer.valueOf(command.getModelEmpInfoTer()))
						.intervalTime(new MonitorIntervalTime(command.getIntervalTime()))
						.empInfoTerMemo(Optional.ofNullable(command.getMemo()).map(e -> new EmpInfoTerMemo(e)))
						.build();

		// 1: [就業情報端末コード＜＞端末No]: get(MACアドレス):就業情報端末
		Optional<EmpInfoTerminal> empInfoTerminalWithMac = repository
				.getEmpInfoTerWithMac(new MacAddress(command.getMacAddress()), new ContractCode(contractCode));

		// 2: [就業情報端末 not empty]:
		if (empInfoTerminalWithMac.isPresent()) {
			throw new BusinessException("Msg_1931");
		}
		
		// 3: get(ログイン契約コード、端末No): 就業情報端末
		Optional<EmpInfoTerminal> empInfoTerminalWithCode = repository.getEmpInfoTerminal(
				new EmpInfoTerminalCode(command.getEmpInfoTerCode()), new ContractCode(contractCode));

		// 4: [就業情報端末=Empty]:
		if (!empInfoTerminalWithCode.isPresent()) {
			throw new BusinessException("Msg_1896");
		}
		
		// 6: persist()
		this.repository.update(empInfoTerminal);
	}
}
