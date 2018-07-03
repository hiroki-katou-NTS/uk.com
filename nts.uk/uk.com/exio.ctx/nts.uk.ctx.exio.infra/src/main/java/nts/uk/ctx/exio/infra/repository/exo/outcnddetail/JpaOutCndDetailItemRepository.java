package nts.uk.ctx.exio.infra.repository.exo.outcnddetail;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtOutCndDetailItem;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtOutCndDetailItemPk;

@Stateless
public class JpaOutCndDetailItemRepository extends JpaRepository implements OutCndDetailItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtOutCndDetailItem f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.outCndDetailItemPk.categoryId =:categoryId AND  f.outCndDetailItemPk.categoryItemNo =:categoryItemNo ";

	@Override
	public List<OutCndDetailItem> getAllOutCndDetailItem() {
		return null;
	}

	@Override
	public Optional<OutCndDetailItem> getOutCndDetailItemById(String categoryId, int categoryItemNo) {
		return null;
	}

	@Override
	public void add(OutCndDetailItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(OutCndDetailItem domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(String categoryId, int categoryItemNo) {
		this.commandProxy().remove(OiomtOutCndDetailItem.class,
				new OiomtOutCndDetailItemPk(categoryId, categoryItemNo));
	}

	public static OiomtOutCndDetailItem toEntity(OutCndDetailItem domain) {
		return new OiomtOutCndDetailItem(
				domain.getCategoryId(),
				domain.getCategoryItemNo().v(),
				domain.getCid().isPresent()? domain.getCid().get():null,
				domain.getCompanyCndSetCd().isPresent()?domain.getCompanyCndSetCd().get().v(): null, 
				domain.getUserId().isPresent()?domain.getUserId().get():null,
				domain.getConditionSettingCd().isPresent()? domain.getConditionSettingCd().get().v():null,
				domain.getConditionSymbol().value, 
				domain.getSearchNum().isPresent() ?domain.getSearchNum().get().v():null,
				domain.getSearchNumEndVal().isPresent()?domain.getSearchNumEndVal().get().v():null,
				domain.getSearchNumStartVal().isPresent()?domain.getSearchNumStartVal().get().v():null, 
				domain.getSearchChar().isPresent()?domain.getSearchChar().get().v():null, 
				domain.getSearchCharEndVal().isPresent()?domain.getSearchCharEndVal().get().v():null,
				domain.getSearchCharStartVal().isPresent()?domain.getSearchCharStartVal().get().v():null,
				domain.getSearchDate().isPresent()?domain.getSearchDate().get():null, 
				domain.getSearchDateEnd().isPresent()?domain.getSearchDateEnd().get():null,
				domain.getSearchDateStart().isPresent()?domain.getSearchDateStart().get():null, 
				domain.getSearchClock().isPresent()?domain.getSearchClock().get().v():null, 
				domain.getSearchClockEndVal().isPresent()?domain.getSearchClockEndVal().get().v():null,
				domain.getSearchClockStartVal().isPresent()?domain.getSearchClockStartVal().get().v():null, 
				domain.getSearchTime().isPresent()?domain.getSearchTime().get().v():null, 
				domain.getSearchTimeEndVal().isPresent()?domain.getSearchTimeEndVal().get().v():null,
				domain.getSearchTimeStartVal().isPresent()?domain.getSearchTimeStartVal().get().v():null);
	}

	public static OutCndDetailItem toDomain(OiomtOutCndDetailItem entity) {
		return new OutCndDetailItem(
				entity.outCndDetailItemPk.categoryId,
				entity.outCndDetailItemPk.categoryItemNo,
				entity.cid,
				entity.companyCndSetCd,
				entity.userId,
				entity.conditionSettingCd,
				entity.conditionSymbol,
				entity.searchNum,
				entity.searchNumEndVal,
				entity.searchNumStartVal,
				entity.searchChar,
				entity.searchCharEndVal,
				entity.searchCharStartVal,
				entity.searchDate,
				entity.searchDateEnd,
				entity.searchDateStart,
				entity.searchClock, 
				entity.searchClockEndVal,
				entity.searchClockStartVal,
				entity.searchTime,
				entity.searchTimeEndVal,
				entity.searchTimeStartVal);
	}
}
