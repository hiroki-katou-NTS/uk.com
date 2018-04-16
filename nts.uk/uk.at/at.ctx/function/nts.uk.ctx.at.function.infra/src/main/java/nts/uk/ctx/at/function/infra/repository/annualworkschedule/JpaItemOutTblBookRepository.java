package nts.uk.ctx.at.function.infra.repository.annualworkschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.repostory.ItemOutTblBookRepository;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KrcmtItemOutTblBookPk;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KrcmtItemOutTblBook;

@Stateless
public class JpaItemOutTblBookRepository extends JpaRepository implements ItemOutTblBookRepository {
	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KrcmtItemOutTblBook f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.itemOutTblBookPk.cid =:cid AND  f.itemOutTblBookPk.code =:code AND  f.itemOutTblBookPk.sortBy =:sortBy ";

	@Override
	public List<ItemOutTblBook> getAllItemOutTblBook(){
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KrcmtItemOutTblBook.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ItemOutTblBook> getItemOutTblBookById(String cid, int code, int sortBy){
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KrcmtItemOutTblBook.class)
		.setParameter("cid", cid)
		.setParameter("code", code)
		.setParameter("sortBy", sortBy)
		.getSingle(c->c.toDomain());
	}

	@Override
	public void add(ItemOutTblBook domain){
		this.commandProxy().insert(KrcmtItemOutTblBook.toEntity(domain));
	}

	@Override
	public void update(ItemOutTblBook domain){
		this.commandProxy().update(KrcmtItemOutTblBook.toEntity(domain));
	}

	@Override
	public void remove(String cid, int code, int sortBy){
		this.commandProxy().remove(KrcmtItemOutTblBook.class, new KrcmtItemOutTblBookPk(cid, code, sortBy)); 
	}
}
