package nts.uk.ctx.at.aggregation.infra.repository.form9;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSetting;
import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSettingRepository;
import nts.uk.ctx.at.aggregation.infra.entity.form9.KagmtForm9OutputSettings;
import nts.uk.ctx.at.aggregation.infra.entity.form9.KagmtForm9OutputSettingsPk;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaForm9DetailoutputSettingsRepository extends JpaRepository implements Form9DetailOutputSettingRepository {

    private static final String SELECT;

    private static final String FIND_BY_CID;

    private static final String FIND_BY_CID_CD;

    private static final String FIND_BY_CID_FIXED;

    private static final String FIND_BY_CID_IS_USE;

    private static final String FIND_BY_CID_CD_FIXED;


    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM KagmtForm9OutputSettings a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" AND a.pk.CODE = :code ");
        FIND_BY_CID_CD = FIND_BY_CID + builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" AND a.IS_FIXED = :isFixed ");
        FIND_BY_CID_FIXED = FIND_BY_CID + builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" AND a.IS_USE = :isUse ");
        FIND_BY_CID_IS_USE = FIND_BY_CID + builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" AND a.IS_FIXED = :isFixed ");
        FIND_BY_CID_CD_FIXED = FIND_BY_CID_CD + builderString.toString();
    }

    /**
     * get
     *
     * @param companyId 会社ID
     * @return
     */
    @Override
    public Form9DetailOutputSetting get(String companyId) {
        Optional<KagmtForm9OutputSettings> result = this.queryProxy().find(new KagmtForm9OutputSettingsPk(companyId), KagmtForm9OutputSettings.class);
        if (!result.isPresent()) {
            return null;
        }
        return KagmtForm9OutputSettings.toDomain(result.get());
    }

    /**
     * update
     *
     * @param companyId           会社ID
     * @param detailOutputSetting 様式９の詳細出力設定
     */
    @Override
    public void update(String companyId, Form9DetailOutputSetting detailOutputSetting) {
        this.commandProxy().update(KagmtForm9OutputSettings.toEntity(companyId, detailOutputSetting));
    }
}
