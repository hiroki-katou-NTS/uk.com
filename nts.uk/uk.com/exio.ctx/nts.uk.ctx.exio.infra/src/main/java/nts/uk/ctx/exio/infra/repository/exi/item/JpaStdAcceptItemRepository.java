package nts.uk.ctx.exio.infra.repository.exi.item;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.exio.infra.entity.exi.item.OiomtStdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtStdAcceptItemPk;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItemRepository;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaStdAcceptItemRepository extends JpaRepository implements StdAcceptItemRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtStdAcceptItem f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.stdAcceptItemPk.cid =:cid AND  f.stdAcceptItemPk.conditionSetCd =:conditionSetCd AND  f.stdAcceptItemPk.categoryId =:categoryId AND  f.stdAcceptItemPk.acceptItemNumber =:acceptItemNumber ";

    @Override
    public List<StdAcceptItem> getAllStdAcceptItem(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtStdAcceptItem.class)
                .getList(item -> toDomain(item));
    }

    @Override
    public Optional<StdAcceptItem> getStdAcceptItemById(String cid, String conditionSetCd, String categoryId, int acceptItemNumber){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtStdAcceptItem.class)
        .setParameter("cid", cid)
        .setParameter("conditionSetCd", conditionSetCd)
        .setParameter("categoryId", categoryId)
        .setParameter("acceptItemNumber", acceptItemNumber)
        .getSingle(c->toDomain(c));
    }

    @Override
    public void add(StdAcceptItem domain){
        this.commandProxy().insert(toEntity(domain));
    }

    @Override
    public void update(StdAcceptItem domain){
        OiomtStdAcceptItem newStdAcceptItem = toEntity(domain);
        OiomtStdAcceptItem updateStdAcceptItem = this.queryProxy().find(newStdAcceptItem.stdAcceptItemPk, OiomtStdAcceptItem.class).get();
        if (null == updateStdAcceptItem) {
            return;
        }
        updateStdAcceptItem.version = newStdAcceptItem.version;
        updateStdAcceptItem.systemType = newStdAcceptItem.systemType;
        updateStdAcceptItem.csvItemNumber = newStdAcceptItem.csvItemNumber;
        updateStdAcceptItem.csvItemName = newStdAcceptItem.csvItemName;
        updateStdAcceptItem.itemType = newStdAcceptItem.itemType;
        updateStdAcceptItem.categoryItemNo = newStdAcceptItem.categoryItemNo;
        this.commandProxy().update(updateStdAcceptItem);
    }

    @Override
    public void remove(String cid, String conditionSetCd, String categoryId, int acceptItemNumber){
        this.commandProxy().remove(OiomtStdAcceptItemPk.class, new OiomtStdAcceptItemPk(cid, conditionSetCd, categoryId, acceptItemNumber)); 
    }

    private static StdAcceptItem toDomain(OiomtStdAcceptItem entity) {
        return StdAcceptItem.createFromJavaType(entity.version, entity.stdAcceptItemPk.cid, entity.stdAcceptItemPk.conditionSetCd, entity.stdAcceptItemPk.categoryId, entity.stdAcceptItemPk.acceptItemNumber, entity.systemType, entity.csvItemNumber, entity.csvItemName, entity.itemType, entity.categoryItemNo);
    }

    private OiomtStdAcceptItem toEntity(StdAcceptItem domain) {
        return new OiomtStdAcceptItem(domain.getVersion(), new OiomtStdAcceptItemPk(domain.getCid(), domain.getConditionSetCd(), domain.getCategoryId(), domain.getAcceptItemNumber()), domain.getSystemType(), domain.getCsvItemNumber(), domain.getCsvItemName(), domain.getItemType(), domain.getCategoryItemNo());
    }

}
