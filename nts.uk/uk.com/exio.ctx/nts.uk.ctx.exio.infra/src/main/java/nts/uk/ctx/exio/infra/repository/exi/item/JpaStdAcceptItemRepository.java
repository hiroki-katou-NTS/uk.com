package nts.uk.ctx.exio.infra.repository.exi.item;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtStdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtStdAcceptItemPk;

@Stateless
public class JpaStdAcceptItemRepository extends JpaRepository implements StdAcceptItemRepository {

	private static final String SELECT_ALL = "SELECT f FROM OiomtStdAcceptItem WHERE f.stdAcceptItemPk.cid = :cid AND f.stdAcceptItemPk.systemType = :systemType AND f.stdAcceptItemPk.conditionSetCd = :conditionSetCd";

	@Override
	public Optional<StdAcceptItem> getStdAcceptItemById(String cid, int sysType, String conditionSetCd,
			String categoryId, int acceptItemNumber) {
		Optional<OiomtStdAcceptItem> entity = this.queryProxy().find(
				new OiomtStdAcceptItemPk(cid, sysType, conditionSetCd, categoryId, acceptItemNumber),
				OiomtStdAcceptItem.class);
		if (entity.isPresent()) {
			return Optional.of(OiomtStdAcceptItem.toDomain(entity.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void add(StdAcceptItem domain) {
		this.commandProxy().insert(OiomtStdAcceptItem.fromDomain(domain));
	}

	@Override
	public void update(StdAcceptItem domain) {
		Optional<OiomtStdAcceptItem> entityOpt = this
				.queryProxy().find(
						new OiomtStdAcceptItemPk(domain.getCid(), domain.getSystemType(),
								domain.getConditionSetCd().v(), "", domain.getAcceptItemNumber()),
						OiomtStdAcceptItem.class);
		if (entityOpt.isPresent()) {
			OiomtStdAcceptItem entity = entityOpt.get();
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void remove(String cid, int sysType, String conditionSetCd, String categoryId, int acceptItemNumber) {
		this.commandProxy().remove(OiomtStdAcceptItem.class,
				new OiomtStdAcceptItemPk(cid, sysType, conditionSetCd, categoryId, acceptItemNumber));
	}

	@Override
	public List<StdAcceptItem> getListStdAcceptItems(String cid, int systemType, String conditionSetCd) {
		return this.queryProxy().query(SELECT_ALL, OiomtStdAcceptItem.class).setParameter("cid", cid)
				.setParameter("systemType", systemType).setParameter("conditionSetCd", conditionSetCd)
				.getList(c -> OiomtStdAcceptItem.toDomain(c));
	}

}
