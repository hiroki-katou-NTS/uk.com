package nts.uk.ctx.sys.tenant.dom;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.gateway.dom.tenantlogin.TenantAuthentication;
import nts.uk.shr.com.tenant.event.CreatedTenantEvent;

/**
 * テナントを作る
 */
public class CreateTenant {

    public static AtomTask create(
            Require require,
            String tenantCode,
            GeneralDate startDate,
            String tenantPassword) {

        if (require.existsTenant(tenantCode)) {
            throw new BusinessException(new RawErrorMessage("テナントコード " + tenantCode + " は既に存在します。"));
        }

        val tenantAuthentication = TenantAuthentication.create(tenantCode, tenantPassword, startDate);
        val event = new CreatedTenantEvent(tenantCode);

        return AtomTask.of(() -> {
            require.save(tenantAuthentication);
            event.publish();
        });
    }

    public static interface Require {
        boolean existsTenant(String tenantCode);
        void save(TenantAuthentication tenantAuthentication);
    }
}
