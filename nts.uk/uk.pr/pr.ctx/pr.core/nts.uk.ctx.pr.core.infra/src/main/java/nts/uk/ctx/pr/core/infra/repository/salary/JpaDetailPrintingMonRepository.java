package nts.uk.ctx.pr.core.infra.repository.salary;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.salary.DetailPrintingMon;
import nts.uk.ctx.pr.core.dom.salary.DetailPrintingMonRepository;
import nts.uk.ctx.pr.core.infra.entity.salary.QpbmtDetailPrintingMon;
import nts.uk.ctx.pr.core.infra.entity.salary.QpbmtDetailPrintingMonPk;

@Stateless
public class JpaDetailPrintingMonRepository extends JpaRepository implements DetailPrintingMonRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtDetailPrintingMon f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.detailPrintingMonPk.processCateNo =:processCateNo AND  f.detailPrintingMonPk.cid =:cid ";

    @Override
    public List<DetailPrintingMon> getAllDetailPrintingMon(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtDetailPrintingMon.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<DetailPrintingMon> getDetailPrintingMonById(int processCateNo, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtDetailPrintingMon.class)
        .setParameter("processCateNo", processCateNo)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(DetailPrintingMon domain){
        this.commandProxy().insert(QpbmtDetailPrintingMon.toEntity(domain));
    }

    @Override
    public void update(DetailPrintingMon domain){
        this.commandProxy().update(QpbmtDetailPrintingMon.toEntity(domain));
    }

    @Override
    public void remove(int processCateNo, String cid){
        this.commandProxy().remove(QpbmtDetailPrintingMon.class, new QpbmtDetailPrintingMonPk(processCateNo, cid)); 
    }
}
