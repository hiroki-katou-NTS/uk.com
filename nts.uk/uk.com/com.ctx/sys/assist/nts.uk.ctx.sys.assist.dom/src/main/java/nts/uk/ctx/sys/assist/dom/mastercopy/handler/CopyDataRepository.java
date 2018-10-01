package nts.uk.ctx.sys.assist.dom.mastercopy.handler;

import nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo;

/**
 * @author locph
 */
public interface CopyDataRepository {

    void copy(String companyId, TargetTableInfo targetTableInfo, Integer categoryCopyMethod);
}
