package nts.uk.ctx.at.function.infra.repository.annualworkschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.CalcFormulaItemRepository;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtCalcFormulaItem;
import nts.uk.ctx.at.function.infra.entity.annualworkschedule.KfnrtCalcFormulaItemPk;

@Stateless
public class JpaCalcFormulaItemRepository extends JpaRepository implements CalcFormulaItemRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnrtCalcFormulaItem f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.calcFormulaItemPk.cid =:cid ";

    @Override
    public List<CalcFormulaItem> getAllCalcFormulaItem(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, KfnrtCalcFormulaItem.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<CalcFormulaItem> getCalcFormulaItemById(String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, KfnrtCalcFormulaItem.class)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(CalcFormulaItem domain){
        this.commandProxy().insert(KfnrtCalcFormulaItem.toEntity(domain));
    }

    @Override
    public void update(CalcFormulaItem domain){
        this.commandProxy().update(KfnrtCalcFormulaItem.toEntity(domain));
    }

    @Override
    public void remove(String cid){
        this.commandProxy().remove(KfnrtCalcFormulaItem.class, new KfnrtCalcFormulaItemPk(cid)); 
    }
}
