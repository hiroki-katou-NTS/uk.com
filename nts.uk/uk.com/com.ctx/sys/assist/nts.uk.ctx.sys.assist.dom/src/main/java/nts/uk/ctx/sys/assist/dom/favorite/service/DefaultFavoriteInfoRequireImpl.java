package nts.uk.ctx.sys.assist.dom.favorite.service;

import java.util.List;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.uk.ctx.sys.assist.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.sys.assist.dom.favorite.FavoriteSpecifyRepository;
import nts.uk.ctx.sys.assist.dom.favorite.service.FavoriteInformationDomainService.FavoriteInformationRequire;

@RequiredArgsConstructor
public class DefaultFavoriteInfoRequireImpl implements FavoriteInformationRequire {

	@Inject
	private FavoriteSpecifyRepository favoriteSpecifyRepository;
	
	@Override
	public List<FavoriteSpecify> getBySid(String sid) {
		return favoriteSpecifyRepository.getBySid(sid);
	}
	
}