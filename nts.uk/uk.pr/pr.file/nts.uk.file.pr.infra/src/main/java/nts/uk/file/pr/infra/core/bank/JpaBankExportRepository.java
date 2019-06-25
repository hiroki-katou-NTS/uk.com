package nts.uk.file.pr.infra.core.bank;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.bank.BankExportRepository;

import javax.ejb.Stateless;
import java.util.List;


@Stateless
public class JpaBankExportRepository extends JpaRepository implements BankExportRepository {

    private static final String EXPORT_FILE = "SELECT * " +
            "FROM " +
            "(SELECT " +
            " b.CD, " +
            " b.NAME, " +
            " b.KANA_NAME, " +
            " bb.CD AS CD_BRANCH, " +
            " bb.NAME AS NAME_BRANCH, " +
            " bb.KANA_NAME AS KANA_BRANCH, " +
            " ROW_NUMBER () OVER ( PARTITION BY b.CD ORDER BY b.CD, bb.CD) AS ROW_NUMBER  " +
            "FROM " +
            " QBTMT_BANK b " +
            " LEFT JOIN QBTMT_BANK_BRANCH bb ON b.CID = bb.CID " +
            " AND b.CD = bb.BANK_CD " +
            "WHERE b.CID = ?cid) temp ";

    @Override
    public List<Object[]> getAllBankByCid(String cid) {
        return this.getEntityManager().createNativeQuery(EXPORT_FILE).setParameter("cid",cid).getResultList();
    }
}
