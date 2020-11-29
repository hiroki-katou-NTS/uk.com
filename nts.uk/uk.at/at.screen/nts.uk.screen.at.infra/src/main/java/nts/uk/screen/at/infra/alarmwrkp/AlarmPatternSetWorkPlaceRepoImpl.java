package nts.uk.screen.at.infra.alarmwrkp;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.checkcondition.KfnmtWkpAlstchkConcat;
import nts.uk.screen.at.app.alarmwrkp.AlarmCheckCategoryList;
import nts.uk.screen.at.app.alarmwrkp.AlarmPatternSetDto;
import nts.uk.screen.at.app.alarmwrkp.AlarmPatternSetWorkPlaceRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AlarmPatternSetWorkPlaceRepoImpl extends JpaRepository implements AlarmPatternSetWorkPlaceRepo{

    private static final String GETALL = "SELECT s.ALARM_PATTERN_CD, s.ALARM_PATTERN_NAME "
                                    + " FROM KFNMT_ALSTWKP_PTN s "
                                    + " WHERE s.CID = ? ORDER BY  s.ALARM_PATTERN_CD";

    private static final String GET_BY_CID = "select f from KfnmtWkpAlstchkConcat f where f.pk.companyID = :cid  ORDER BY f.pk.categoryItemCD ";

    @Override
    public List<AlarmPatternSetDto> getAll() {
        try (PreparedStatement statement = this.connection().prepareStatement(GETALL)) {

            statement.setString(1, AppContexts.user().companyId());
            val result = new NtsResultSet(statement.executeQuery()).getList(rec -> {
                return new AlarmPatternSetDto(rec.getString("ALARM_PATTERN_CD"),rec.getString("ALARM_PATTERN_NAME"));
            });

            if(result.isEmpty()){
                return Collections.emptyList();
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AlarmCheckCategoryList> getAllCtg() {
        List<KfnmtWkpAlstchkConcat> entity = this.queryProxy().query(GET_BY_CID, KfnmtWkpAlstchkConcat.class)
                .setParameter("cid", AppContexts.user().companyId())
                .getList();
        return entity.stream().map(i-> toDomainObject(i)).collect(Collectors.toList());
    }

    private  AlarmCheckCategoryList toDomainObject(KfnmtWkpAlstchkConcat entity){
        return new AlarmCheckCategoryList(entity.pk.category,entity.pk.categoryItemCD,entity.alarmCdtName);
    }
}
