package nts.uk.ctx.at.record.ac.eligibleemployees;

import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.SyWorkplaceAdapter;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SyWorkplaceAdapterImpl implements SyWorkplaceAdapter {
}
