package nts.uk.ctx.at.function.infra.repository.annualworkschedule;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtItemOutTblBook;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtItemOutTblBookPk;
import nts.uk.ctx.at.function.dom.annualworkschedule.ItemOutTblBook;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.ItemOutTblBookRepository;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaItemOutTblBookRepository extends JpaRepository implements ItemOutTblBookRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnrtItemOutTblBook f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.itemOutTblBookPk.cid =:cid AND  f.itemOutTblBookPk.cd =:cd ";

    @Override
    public List<ItemOutTblBook> getAllItemOutTblBook(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KfnrtItemOutTblBook.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<ItemOutTblBook> getItemOutTblBookById(String cid, int cd){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, KfnrtItemOutTblBook.class)
        .setParameter("cid", cid)
        .setParameter("cd", cd)
        .getSingle(c->c.toDomain());
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
    public void remove(String cid, int cd){
        this.commandProxy().remove(KfnrtItemOutTblBook.class, new KfnrtItemOutTblBookPk(cid, cd)); 
    }
}
