package nts.uk.ctx.at.aggregation.infra.repository.form9;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Code;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;
import nts.uk.ctx.at.aggregation.dom.form9.Form9LayoutRepository;
import nts.uk.ctx.at.aggregation.infra.entity.form9.KagmtForm9OutputLayout;
import nts.uk.ctx.at.aggregation.infra.entity.form9.KagmtForm9OutputLayoutPk;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaForm9LayoutRepository extends JpaRepository implements Form9LayoutRepository {

    private static final String SELECT;

    private static final String FIND_BY_CID;

    private static final String FIND_BY_CID_CD;

    private static final String FIND_BY_CID_FIXED;

    private static final String FIND_BY_CID_IS_USE;

    private static final String FIND_BY_CID_CD_FIXED;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM KagmtForm9OutputLayout a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" AND a.pk.code = :code ");
        FIND_BY_CID_CD = FIND_BY_CID + builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" AND a.isFixed = :isFixed ");
        FIND_BY_CID_FIXED = FIND_BY_CID + builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" AND a.isUse = :isUse ");
        FIND_BY_CID_IS_USE = FIND_BY_CID + builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" AND a.isFixed = :isFixed ");
        FIND_BY_CID_CD_FIXED = FIND_BY_CID_CD + builderString.toString();
    }

    /**
     * get
     *
     * @param companyId ??????ID
     * @param form9Code ?????????
     * @return
     */
    @Override
    public Optional<Form9Layout> get(String companyId, Form9Code form9Code) {

        Optional<KagmtForm9OutputLayout> result = this.queryProxy().query(FIND_BY_CID_CD, KagmtForm9OutputLayout.class)
                .setParameter("companyId", companyId)
                .setParameter("code", form9Code.v())
                .getSingle();
        if (!result.isPresent()) {
            return Optional.empty();
        }
        KagmtForm9OutputLayout entity = result.get();
        return Optional.of(entity.toDomain(entity));
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param companyId     ??????ID
     * @param isSystemFixed ?????????????????????
     * @return
     */
    @Override
    public List<Form9Layout> getLayoutBySystemFixedAttr(String companyId, boolean isSystemFixed) {

        List<KagmtForm9OutputLayout> result = this.queryProxy().query(FIND_BY_CID_FIXED, KagmtForm9OutputLayout.class)
                .setParameter("companyId", companyId)
                .setParameter("isFixed", isSystemFixed)
                .getList();
        return result.stream().map(x -> x.toDomain(x)).collect(Collectors.toList());
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param companyId ??????ID
     * @return
     */
    @Override
    public List<Form9Layout> getAllLayoutUse(String companyId) {
        List<KagmtForm9OutputLayout> result = this.queryProxy().query(FIND_BY_CID_IS_USE, KagmtForm9OutputLayout.class)
                .setParameter("companyId", companyId)
                .setParameter("isUse", true)
                .getList();
        return result.stream().map(x -> x.toDomain(x)).collect(Collectors.toList());
    }

    /**
     * ???????????????????????????????????????????????????(??????ID, ????????????????????????????????????
     *
     * @param companyId ??????ID
     * @param layout    ?????????????????????????????????
     */
    @Override
    public void insertLayoutOfUser(String companyId, Form9Layout layout) {
        commandProxy().insert(KagmtForm9OutputLayout.toEntity(companyId, layout));
    }

    /**
     * ???????????????????????????????????????????????????(??????ID, ????????????????????????????????????
     *
     * @param companyId ??????ID
     * @param layout    ?????????????????????????????????
     */
    @Override
    public void updateLayoutOfUser(String companyId, Form9Layout layout) {
        commandProxy().update(KagmtForm9OutputLayout.toEntity(companyId, layout));
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @param companyId ??????ID
     * @param code      ?????????????????????
     */
    @Override
    public void deleteLayoutOfUser(String companyId, Form9Code code) {
        this.getEntityManager().createQuery("DELETE FROM KagmtForm9OutputLayout a WHERE a.pk.companyId = :companyId AND a.pk.code = :code ",
                KagmtForm9OutputLayout.class)
                .setParameter("companyId", companyId)
                .setParameter("code", code.v())
                .executeUpdate();
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @param companyId ??????ID
     * @param code      ??????????????????
     * @param isUse     ????????????
     */
    @Override
    public void updateUseAttrOfSystemLayout(String companyId, Form9Code code, boolean isUse) {
        List<KagmtForm9OutputLayout> fromDb = this.queryProxy().query(FIND_BY_CID_CD_FIXED, KagmtForm9OutputLayout.class)
                .setParameter("companyId", companyId)
                .setParameter("code", code.v())
                .setParameter("isFixed", true)
                .getList();
        fromDb.forEach(x -> {
            x.isUse = isUse;
        });
        commandProxy().updateAll(fromDb);
    }
}
