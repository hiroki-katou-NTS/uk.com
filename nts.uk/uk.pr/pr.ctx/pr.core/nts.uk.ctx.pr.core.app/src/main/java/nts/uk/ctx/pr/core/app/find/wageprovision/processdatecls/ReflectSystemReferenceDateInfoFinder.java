package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ReflectSystemReferenceDateInfoFinder {
	@Inject
	private ProcessInformationRepository finderProcessInformation;
	@Inject
	private ValPayDateSetRepository finderValPayDateSet;

	public ReflectSystemReferenceDateInfoDto getReflectSystemReferenceDateInfoDto(int processCateNo, int processDate) {
		String cid = AppContexts.user().companyId();
		Optional<ProcessInformation> optProcessInformation = finderProcessInformation.getProcessInformationById(cid,
				processCateNo);
		Optional<ValPayDateSet> optValPayDateSet = finderValPayDateSet.getValPayDateSetById(cid, processCateNo);
		ProcessInformationDto informationDto = optProcessInformation.map(x -> new ProcessInformationDto(x.getCid(),
				x.getProcessCateNo(), x.getDeprecatCate().value, x.getProcessCls().v())).orElse(null);

		BasicSettingDto basicSettingDto = BasicSettingDto
				.fromDomain(optValPayDateSet.isPresent() ? optValPayDateSet.get().getBasicSetting() : null);
		AdvancedSettingDto advancedSettingDto = AdvancedSettingDto
				.fromDomain(optValPayDateSet.isPresent() ? optValPayDateSet.get().getAdvancedSetting() : null);

		ValPayDateSetDto valPayDateSetDto = optValPayDateSet
				.map(x -> new ValPayDateSetDto(x.getCid(), x.getProcessCateNo(), basicSettingDto, advancedSettingDto))
				.orElse(null);

		ReflectSystemReferenceDateInfoDto returnData = new ReflectSystemReferenceDateInfoDto(informationDto,
				valPayDateSetDto);

		return returnData;
	}
}
