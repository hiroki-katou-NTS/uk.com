package nts.uk.ctx.at.record.app.command.worklocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.app.find.worklocation.Ipv4AddressDto;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.NarrowDownIPAddressDS;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.net.Ipv4Address;

/**
 * IPアドレス登録
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM040_勤務場所の登録.B：IPアドレス設定.メニュー別OCD.IPアドレス登録
 * @author tutk
 *
 */
@Stateless
public class AddIPAddressCmdHandler extends CommandHandlerWithResult<AddIPAddressCmd,List<Ipv4AddressDto>> {
	
	@Inject
	private WorkLocationRepository repo;

	@Override
	protected List<Ipv4AddressDto> handle(CommandHandlerContext<AddIPAddressCmd> context) {
		AddIPAddressCmd command = context.getCommand();
		String contractCode = AppContexts.user().contractCode();
		List<Ipv4Address> listAdd = new ArrayList<>();
		List<Ipv4Address> listError = new ArrayList<>();
		Optional<WorkLocation> opt = repo.findByCode(contractCode, command.getWorkLocationCode());
		if(opt.isPresent()) {
			RequireImpl require = new RequireImpl(repo);
			Map<Ipv4Address, Boolean> listData = NarrowDownIPAddressDS.narrowDownIPAddressDS(command.getNet1(),
					command.getNet2(), command.getHost1(), command.getHost2(), command.getIpEnd(), require);
			 
			listData.forEach((k,v) -> {
				if(v) {
					listAdd.add(k);
				}else {
					listError.add(k);
				}
				
			});
			
			repo.insertListIP(contractCode, command.getWorkLocationCode(), listAdd);
		}else {
			throw new BusinessException("Msg_2152");
		}
		
		return listError.stream().map(c-> new Ipv4AddressDto(c)).collect(Collectors.toList());
	}
	@AllArgsConstructor
	private static class RequireImpl implements NarrowDownIPAddressDS.Require {
		@Inject
		private WorkLocationRepository repo;
		
		@Override
		public List<Ipv4Address> getIPAddressByStartEndIP(int net1, int net2, int host1, int host2, int endIP) {
			String contractCode = AppContexts.user().contractCode();
			List<Ipv4Address> data = repo.getIPAddressByStartEndIP(contractCode, net1, net2, host1, host2, endIP);
			return data;
		}
		
	}

}
