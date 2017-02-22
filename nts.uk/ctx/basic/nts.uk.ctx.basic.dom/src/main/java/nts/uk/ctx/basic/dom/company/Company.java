package nts.uk.ctx.basic.dom.company;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.company.address.Address;

public class Company extends AggregateRoot{
	
	private CompanyCode companyCode;

	private Address address;
	
	private CompanyName companyName;
	
}
