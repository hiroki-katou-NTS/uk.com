package nts.uk.screen.at.infra.alarmwrkp;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
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

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AlarmPatternSetWorkPlaceRepoImpl extends JpaRepository implements AlarmPatternSetWorkPlaceRepo{

    private static final String GETALL = "SELECT s.ALARM_PATTERN_CD, s.ALARM_PATTERN_NAME "
                                    + " FROM KFNMT_ALSTWKP_PTN s "
                                    + " WHERE s.CID = ?";

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
}
