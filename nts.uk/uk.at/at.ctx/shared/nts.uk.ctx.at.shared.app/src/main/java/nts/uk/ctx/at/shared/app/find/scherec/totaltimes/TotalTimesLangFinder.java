package nts.uk.ctx.at.shared.app.find.scherec.totaltimes;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto.TotalTimesLangDto;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLang;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.language.TotalTimesLangRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class TotalTimesLangFinder {
	
	@Inject
	private TotalTimesLangRepository repo; 

	public List<TotalTimesLangDto> getTotalTimesDetails(String langId) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「回数集計の他言語表示名」を取得する(Lấy dữ liệu từ domain 「回数集計の他言語表示名」)
		List<TotalTimesLang> workTypeLanguage = repo.findAll(companyId, langId);
		return workTypeLanguage.stream().map(x -> {
			TotalTimesLang wT = new TotalTimesLang(companyId, x.getTotalCountNo(),langId, x.getTotalTimesNameEng());
			return TotalTimesLangDto.fromDomain(wT);
		}).collect(Collectors.toList());
	}
}
