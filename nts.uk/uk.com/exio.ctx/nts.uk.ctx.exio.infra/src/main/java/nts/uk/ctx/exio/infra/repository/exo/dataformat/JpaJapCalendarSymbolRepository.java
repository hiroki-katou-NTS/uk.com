package nts.uk.ctx.exio.infra.repository.exo.dataformat;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.dataformat.init.JapCalendarSymbol;
import nts.uk.ctx.exio.dom.exo.dataformat.init.JapCalendarSymbolRepository;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtJapCalendarSymbol;
import nts.uk.ctx.exio.infra.entity.exo.dataformat.init.OiomtJapCalendarSymbolPk;

@Stateless
public class JpaJapCalendarSymbolRepository extends JpaRepository implements JapCalendarSymbolRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtJapCalendarSymbol f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    @Override
    public List<JapCalendarSymbol> getAllJapCalendarSymbol(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtJapCalendarSymbol.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<JapCalendarSymbol> getJapCalendarSymbolById(){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtJapCalendarSymbol.class)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(JapCalendarSymbol domain){
        this.commandProxy().insert(OiomtJapCalendarSymbol.toEntity(domain));
    }

    @Override
    public void update(JapCalendarSymbol domain){
        this.commandProxy().update(OiomtJapCalendarSymbol.toEntity(domain));
    }

    @Override
    public void remove(){
        this.commandProxy().remove(OiomtJapCalendarSymbol.class, new OiomtJapCalendarSymbolPk()); 
    }
}
