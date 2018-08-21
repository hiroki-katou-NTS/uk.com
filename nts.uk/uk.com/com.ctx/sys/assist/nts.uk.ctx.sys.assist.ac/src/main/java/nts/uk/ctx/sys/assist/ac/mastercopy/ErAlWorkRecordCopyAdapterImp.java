package nts.uk.ctx.sys.assist.ac.mastercopy;

import nts.uk.ctx.at.record.pub.mastercopy.MasterCopyPub;
import nts.uk.ctx.sys.assist.dom.mastercopy.ErAlWorkRecordCopyAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author locph
 */
@Stateless
public class ErAlWorkRecordCopyAdapterImp implements ErAlWorkRecordCopyAdapter {

    @Inject
    MasterCopyPub masterCopyPub;

    @Override
    public void copy(String companyId, int copyMethod) {
        masterCopyPub.doCopy(companyId, copyMethod);
    }
}
