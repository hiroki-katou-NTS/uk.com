package nts.uk.ctx.at.function.infra.repository.annualworkschedule;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtSetOutItemsWoSc;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtSetOutItemsWoScPk;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoScRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaSetOutItemsWoScRepository extends JpaRepository implements SetOutItemsWoScRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnrtSetOutItemsWoSc f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.setOutItemsWoScPk.cid =:cid AND  f.setOutItemsWoScPk.cd =:cd ";

	@Override
	public List<SetOutItemsWoSc> getAllSetOutItemsWoSc(){
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KfnrtSetOutItemsWoSc.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<SetOutItemsWoSc> getSetOutItemsWoScById(String cid, int cd){
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
	public void remove(String cid, int cd){
		this.commandProxy().remove(KfnrtSetOutItemsWoSc.class, new KfnrtSetOutItemsWoScPk(cid, cd)); 
	}
}
