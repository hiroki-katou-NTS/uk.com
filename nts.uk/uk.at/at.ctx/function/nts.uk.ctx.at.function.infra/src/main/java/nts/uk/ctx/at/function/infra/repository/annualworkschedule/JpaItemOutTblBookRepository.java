package nts.uk.ctx.at.function.infra.repository.annualworkschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.ItemOutTblBookRepository;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtItemOutTblBook;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtItemOutTblBookPk;

@Stateless
public class JpaItemOutTblBookRepository extends JpaRepository implements ItemOutTblBookRepository
{
	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnrtItemOutTblBook f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.itemOutTblBookPk.cid =:cid AND  f.itemOutTblBookPk.setOutCd =:setOutCd AND  f.itemOutTblBookPk.cd =:cd ";
	private static final String SELECT_BY_SET_OUT_CD = SELECT_ALL_QUERY_STRING + " WHERE  f.itemOutTblBookPk.cid =:cid AND f.itemOutTblBookPk.setOutCd =:setOutCd ";

	@Override
	public List<ItemOutTblBook> getAllItemOutTblBook(){
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KfnrtItemOutTblBook.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ItemOutTblBook> getItemOutTblBookById(String cid, String setOutCd, String cd){
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KfnrtItemOutTblBook.class)
		.setParameter("cid", cid)
		.setParameter("setOutCd", setOutCd)
		.setParameter("cd", cd)
		.getSingle(c->c.toDomain());
	}

	@Override
	public List<ItemOutTblBook> getItemOutTblBookBySetOutCd(String cid, String setOutCd) {
		return this.queryProxy().query(SELECT_BY_SET_OUT_CD, KfnrtItemOutTblBook.class)
				.setParameter("cid", cid)
				.setParameter("setOutCd", setOutCd)
				.getList(item -> item.toDomain());
	}

	@Override
	public void add(ItemOutTblBook domain){
		this.commandProxy().insert(KfnrtItemOutTblBook.toEntity(domain));
	}

	@Override
	public void update(ItemOutTblBook domain){
		this.commandProxy().update(KfnrtItemOutTblBook.toEntity(domain));
	}

	@Override
	public void remove(String cid, String setOutCd, String cd) {
		this.commandProxy().remove(KfnrtItemOutTblBook.class, new KfnrtItemOutTblBookPk(cid, setOutCd, cd)); 
	}
}
