package nts.uk.ctx.exio.app.find.exo.condset;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.commonalgorithm.AcquisitionSettingList;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * 出力条件設定（定型）
 */
public class StdOutputCondSetFinder {

	@Inject
	private AcquisitionSettingList acquisitionSettingList;
	
	@Inject 
	private StdOutputCondSetRepository finder;
	
	public List<StdOutputCondSetDto> getAllStdOutputCondSet() {
		return finder.getAllStdOutputCondSet().stream().map(item -> StdOutputCondSetDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	
	public Optional<StdOutputCondSet> getCndSet() {
		String cId = AppContexts.user().companyId();
		return acquisitionSettingList.getAcquisitionSettingList(cId, null);		
	}
	

}
