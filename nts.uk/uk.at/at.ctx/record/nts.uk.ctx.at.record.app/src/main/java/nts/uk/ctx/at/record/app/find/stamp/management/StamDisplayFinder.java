package nts.uk.ctx.at.record.app.find.stamp.management;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
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
	public StampPageLayoutDto getStampPage(int pageNo) {
		String companyId = AppContexts.user().companyId();
		Optional<StampPageLayoutDto> stampPage = repo.getStampSetPage(companyId, pageNo)
				.map(mapper -> StampPageLayoutDto.fromDomain(mapper));
		if (!stampPage.isPresent())
			return null;

		return stampPage.get();
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
