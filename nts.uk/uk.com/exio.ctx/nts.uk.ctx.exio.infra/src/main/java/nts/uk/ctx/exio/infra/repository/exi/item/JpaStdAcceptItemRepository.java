package nts.uk.ctx.exio.infra.repository.exi.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtStdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtStdAcceptItemPk;

@Stateless
public class JpaStdAcceptItemRepository extends JpaRepository implements StdAcceptItemRepository {

	private static final String SELECT_ALL = "SELECT f FROM OiomtStdAcceptItem f WHERE f.stdAcceptItemPk.cid = :cid AND f.stdAcceptItemPk.systemType = :systemType AND f.stdAcceptItemPk.conditionSetCd = :conditionSetCd ORDER BY f.stdAcceptItemPk.acceptItemNumber";

	@Override
	public Optional<StdAcceptItem> getStdAcceptItemById(String cid, int sysType, String conditionSetCd,
			int acceptItemNumber) {
		Optional<OiomtStdAcceptItem> entity = this.queryProxy().find(
				new OiomtStdAcceptItemPk(cid, sysType, conditionSetCd, acceptItemNumber), OiomtStdAcceptItem.class);
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
						new OiomtStdAcceptItemPk(domain.getCid(), domain.getSystemType().value,
								domain.getConditionSetCd().v(), domain.getAcceptItemNumber()),
						OiomtStdAcceptItem.class);
		if (entityOpt.isPresent()) {
			OiomtStdAcceptItem entity = entityOpt.get();
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void remove(String cid, int sysType, String conditionSetCd, int acceptItemNumber) {
		this.commandProxy().remove(OiomtStdAcceptItem.class,
				new OiomtStdAcceptItemPk(cid, sysType, conditionSetCd, acceptItemNumber));
	}

	@Override
	public void removeAll(String cid, int sysType, String conditionSetCd) {
		List<OiomtStdAcceptItem> listEntity = this.queryProxy().query(SELECT_ALL, OiomtStdAcceptItem.class)
				.setParameter("cid", cid).setParameter("systemType", sysType)
				.setParameter("conditionSetCd", conditionSetCd).getList();
		this.commandProxy().removeAll(listEntity);
		this.getEntityManager().flush();
	}

	@Override
	public List<StdAcceptItem> getListStdAcceptItems(String cid, int systemType, String conditionSetCd) {
		return this.queryProxy().query(SELECT_ALL, OiomtStdAcceptItem.class).setParameter("cid", cid)
				.setParameter("systemType", systemType).setParameter("conditionSetCd", conditionSetCd)
				.getList(c -> OiomtStdAcceptItem.toDomain(c));
	}

	@Override
	public void addList(List<StdAcceptItem> listItem) {
		this.commandProxy().insertAll(
				listItem.stream().map(item -> OiomtStdAcceptItem.fromDomain(item)).collect(Collectors.toList()));
	}

}
