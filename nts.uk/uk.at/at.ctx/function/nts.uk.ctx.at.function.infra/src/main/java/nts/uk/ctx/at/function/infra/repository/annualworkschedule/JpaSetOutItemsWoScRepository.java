package nts.uk.ctx.at.function.infra.repository.annualworkschedule;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtSetOutItemsWoSc;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtSetOutItemsWoScPk;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaSetOutItemsWoScRepository extends JpaRepository implements SetOutItemsWoScRepository
{
	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnrtSetOutItemsWoSc f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.setOutItemsWoScPk.cid =:cid AND  f.setOutItemsWoScPk.cd =:cd ";
	private static final String SELECT_ALL_BY_COMPANY = SELECT_ALL_QUERY_STRING + " WHERE f.setOutItemsWoScPk.cid = :cid ORDER BY f.setOutItemsWoScPk.cd ASC";

	@Override
	public List<SetOutItemsWoSc> getAllSetOutItemsWoSc(String companyId){
		return this.queryProxy().query(SELECT_ALL_BY_COMPANY, KfnrtSetOutItemsWoSc.class).setParameter("cid", companyId).getList(item -> item.toDomain());
	}

	@Override
	public Optional<SetOutItemsWoSc> getSetOutItemsWoScById(String cid, String cd){
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KfnrtSetOutItemsWoSc.class)
		.setParameter("cid", cid)
		.setParameter("cd", cd)
		.getSingle(c->c.toDomain());
	}

	@Override
	public void add(SetOutItemsWoSc domain){
		this.commandProxy().insert(KfnrtSetOutItemsWoSc.toEntity(domain));
	}

	@Override
	public void update(SetOutItemsWoSc domain){
		this.commandProxy().update(KfnrtSetOutItemsWoSc.toEntity(domain));
	}

	@Override
	public void remove(String cid, String cd){
		this.commandProxy().remove(KfnrtSetOutItemsWoSc.class, new KfnrtSetOutItemsWoScPk(cid, cd)); 
	}
}