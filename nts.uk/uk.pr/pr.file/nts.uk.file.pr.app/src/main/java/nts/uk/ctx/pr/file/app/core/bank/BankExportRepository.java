package nts.uk.ctx.pr.file.app.core.bank;

import java.util.List;

public interface BankExportRepository {
    List<Object[]> getAllBankByCid(String cid);
}
