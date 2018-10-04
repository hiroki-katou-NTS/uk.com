package nts.uk.ctx.exio.infra.repository.exo.outcnddetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtOutCndDetailItem;
import nts.uk.ctx.exio.infra.entity.exo.outcnddetail.OiomtOutCndDetailItemPk;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtStdOutItem;
import nts.uk.ctx.exio.infra.entity.exo.outputitem.OiomtStdOutItemPk;

@Stateless
public class JpaOutCndDetailItemRepository extends JpaRepository implements OutCndDetailItemRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtOutCndDetailItem f";
	//private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
	//		+ " WHERE  f.outCndDetailItemPk.categoryId =:categoryId AND  f.outCndDetailItemPk.categoryItemNo =:categoryItemNo ";
	private static final String SELECT_BY_CODE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.outCndDetailItemPk.conditionSettingCd =:conditionSettingCd  ";
	private static final String SELECT_BY_CID_AND_CODE = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.outCndDetailItemPk.conditionSettingCd =:conditionSettingCd  and f.outCndDetailItemPk.cid =:cid ORDER BY f.outCndDetailItemPk.seriNum";
	
	@Override
	public List<OutCndDetailItem> getAllOutCndDetailItem() {
		return null;
	}

	@Override
	public Optional<OutCndDetailItem> getOutCndDetailItemById(int categoryId, int categoryItemNo, String conditionSettingCd) {
		return null;
	}
	
	@Override
	public List<OutCndDetailItem> getOutCndDetailItemByCode(String code) {
		return this.queryProxy().query(SELECT_BY_CODE, OiomtOutCndDetailItem.class)
				.setParameter("conditionSettingCd", code)
				.getList(item -> toDomain(item));
	}
	
	@Override
	public List<OutCndDetailItem> getOutCndDetailItemByCidAndCode(String cid, String code) {
		return this.queryProxy().query(SELECT_BY_CID_AND_CODE, OiomtOutCndDetailItem.class)
				.setParameter("conditionSettingCd", code)
				.setParameter("cid", cid)
				.getList(item -> toDomain(item));
	}

	@Override
	public void add(OutCndDetailItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}
	
	@Override
	public void add(List<OutCndDetailItem> domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(OutCndDetailItem domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void remove(String cid, String conditionSettingCd, int categoryId, int categoryItemNo, int seriNum) {
		this.commandProxy().remove(OiomtOutCndDetailItem.class,
				new OiomtOutCndDetailItemPk(cid, conditionSettingCd, categoryId, categoryItemNo, seriNum));
	}

	public static OiomtOutCndDetailItem toEntity(OutCndDetailItem domain) {
		return new OiomtOutCndDetailItem(
				domain.getCategoryId().v(),
				domain.getCategoryItemNo().v(),
				domain.getSeriNum(),
				domain.getCid().isPresent()? domain.getCid().get():null,
				domain.getUserId().isPresent()?domain.getUserId().get():null,
				domain.getConditionSettingCd().v(),
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
				domain.getSearchTimeStartVal().isPresent()?domain.getSearchTimeStartVal().get().v():null,
				domain.getListSearchCodeList());
	}

	public static OutCndDetailItem toDomain(OiomtOutCndDetailItem entity) {
		return new OutCndDetailItem(
				entity.outCndDetailItemPk.conditionSettingCd,
				entity.outCndDetailItemPk.categoryId,
				entity.outCndDetailItemPk.categoryItemNo,
				entity.outCndDetailItemPk.seriNum,
				entity.outCndDetailItemPk.cid,
				entity.userId,				
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
				entity.searchTimeStartVal,
				entity.getListSearchCodeList());
	}
	
	public static List<OiomtOutCndDetailItem> toEntity(List<OutCndDetailItem> listDomain) {
		List<OiomtOutCndDetailItem> listOiomtStdOutItem = new ArrayList<OiomtOutCndDetailItem>();
		for (OutCndDetailItem outCndDetailItem : listDomain) {
			listOiomtStdOutItem.add(toEntity(outCndDetailItem));
		}
		return listOiomtStdOutItem;

	}

	@Override
	public void remove(List<OutCndDetailItem> listOutCndDetailItem) {
		this.commandProxy().removeAll(OiomtOutCndDetailItem.class, toEntity(listOutCndDetailItem).stream()
				.map(i -> new OiomtOutCndDetailItemPk(
						i.outCndDetailItemPk.cid, 
						i.outCndDetailItemPk.conditionSettingCd, 
						i.outCndDetailItemPk.categoryId,
						i.outCndDetailItemPk.categoryItemNo,
						i.outCndDetailItemPk.seriNum
						))
				.collect(Collectors.toList()));
	}
}
