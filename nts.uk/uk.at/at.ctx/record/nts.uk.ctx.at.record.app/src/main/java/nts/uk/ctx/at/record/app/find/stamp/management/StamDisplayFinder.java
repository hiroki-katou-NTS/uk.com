package nts.uk.ctx.at.record.app.find.stamp.management;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopierRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class StamDisplayFinder {

	@Inject
	private StampSetPerRepository repo;
	
	@Inject
	private StampSetCommunalRepository stampSetCommunalRepo;
	
	@Inject
	private StampSettingOfRICOHCopierRepository stampSettingOfRICOHCopierRepo;

	/**
	 * 打刻の前準備(個人)を表示する
	 * get Stamp Setting Person
	 * @return
	 */
	public StampSettingPersonDto getStampSet() {
		String companyId = AppContexts.user().companyId();
		Optional<StampSettingPersonDto> stampSet = repo.getStampSet(companyId)
				.map(x -> StampSettingPersonDto.fromDomain(x));
		if (!stampSet.isPresent())
			return null;

		return stampSet.get();
	}

	/**
	 * 打刻レイアウトの設定内容を取得する
	 * get Stamp Page Layout by Page No
	 * @param pageNo
	 * @return
	 */
	public StampPageLayoutDto getStampPage(int pageNo, int mode) {
		String companyId = AppContexts.user().companyId();
		if(mode == 1) {/*ver20　：「個人」　指定の場合*/
			Optional<StampPageLayoutDto> stampPage = repo.getStampSetPage(companyId, pageNo)
					.map(mapper -> StampPageLayoutDto.fromDomain(mapper));
			if (stampPage.isPresent()) {
				return stampPage.get();
			}
		}else if(mode == 0){/*ver20　：「共有」　指定の場合*/
			Optional<StampSetCommunal> domain = stampSetCommunalRepo.gets(companyId);
			if(domain.isPresent()) {
				Optional<StampPageLayout> result = domain.get().getLstStampPageLayout().stream().filter(c->c.getPageNo().v() == pageNo).findFirst();
				if(result.isPresent()) {
					return StampPageLayoutDto.fromDomain(result.get());
				}
			}
		}else if(mode == 5){/*5:RICOH打刻*/
			Optional<StampSettingOfRICOHCopier> domain = stampSettingOfRICOHCopierRepo.get(companyId);
			if(domain.isPresent()) {
				Optional<StampPageLayout> result = domain.get().getPageLayoutSettings().stream().filter(c->c.getPageNo().v() == pageNo).findFirst();
				if(result.isPresent()) {
					return StampPageLayoutDto.fromDomain(result.get());
				}
			}
		}
		return null;
	}

	/**
	 * get Stamp Page Layout
	 * @return
	 */
	public StampPageLayoutDto getStampPageByCid() {
		String companyId = AppContexts.user().companyId();
		Optional<StampPageLayoutDto> stampPage = repo.getStampSetPageByCid(companyId)
				.map(mapper -> StampPageLayoutDto.fromDomain(mapper));
		if (!stampPage.isPresent())
			return null;

		return stampPage.get();
	}

	/**
	 * get all Stamp Page Layout
	 * @return
	 */
	public List<StampPageLayoutDto> getAllStampPage() {
		String companyId = AppContexts.user().companyId();
		List<StampPageLayoutDto> stampPage = repo.getAllStampSetPage(companyId).stream()
				.map(mapper -> StampPageLayoutDto.fromDomain(mapper)).collect(Collectors.toList());
		return stampPage;
	}
}
