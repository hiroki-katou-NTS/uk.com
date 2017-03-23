package nts.uk.ctx.basic.infra.repository.system.bank.personaccount;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccount;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonBankAccountRepository;
import nts.uk.ctx.basic.dom.system.bank.personaccount.PersonUseSetting;
import nts.uk.ctx.basic.infra.entity.system.bank.personaccount.PbamtPersonBankAccount;
import nts.uk.ctx.basic.infra.entity.system.bank.personaccount.PbamtPersonBankAccountPK;

@RequestScoped
public class JpaPersonBankAccountRepository extends JpaRepository implements PersonBankAccountRepository{
//    private final String SEL_1_1 = 
//    		"SELECT a FROM PbamtPersonBankAccount a"
//    		+ " WHERE a.pbamtPersonBankAccountPK.companyCode = :companyCode"
//    		+ " AND (a.toBankCd1 = :bankCode"
//    		+ " OR a.toBankCd2 = :bankCode"
//    		+ " OR a.toBankCd3 = :bankCode"
//    		+ " OR a.toBankCd4 = :bankCode"
//    		+ " OR a.toBankCd5 = :bankCode)";
    
    private final String SEL_1 = 
    		"SELECT COUNT(a) FROM PbamtPersonBankAccount a"
    		+ " WHERE a.pbamtPersonBankAccountPK.companyCode = :companyCode"
    		+ " AND (a.toBankCd1 IN :bankCode"
    		+ " OR a.toBankCd2 IN :bankCode"
    		+ " OR a.toBankCd3 IN :bankCode"
    		+ " OR a.toBankCd4 IN :bankCode"
    		+ " OR a.toBankCd5 IN :bankCode)";
    
	private final String SEL_2 = 
			"SELECT COUNT(a) FROM PbamtPersonBankAccount a"
			+ " WHERE a.pbamtPersonBankAccountPK.companyCode = :companyCode"
			+ " AND ((a.toBankCd1 IN :bankCode AND a.toBranchCd1 IN :branchCode)"
    		+ " OR (a.toBankCd2 IN :bankCode AND a.toBranchCd2 IN :branchCode)"
    		+ " OR (a.toBankCd3 IN :bankCode AND a.toBranchCd3 IN :branchCode)"
    		+ " OR (a.toBankCd4 IN :bankCode AND a.toBranchCd4 IN :branchCode)"
    		+ " OR (a.toBankCd5 IN :bankCode AND a.toBranchCd2 IN :branchCode))";	
	
	private final String SEL_6 = 
    		"SELECT a FROM PbamtPersonBankAccount a"
    		+ " WHERE a.pbamtPersonBankAccountPK.companyCode = :companyCode"
    		+ " AND (a.fromLineBankCd1 = :lineBankCode"
    		+ " OR a.fromLineBankCd2 = :lineBankCode"
    		+ " OR a.fromLineBankCd3 = :lineBankCode"
    		+ " OR a.fromLineBankCd4 = :lineBankCode"
    		+ " OR a.fromLineBankCd5 = :lineBankCode)";
	
	private final String SEL_6_1 = 
    		"SELECT COUNT(a) FROM PbamtPersonBankAccount a"
    		+ " WHERE a.pbamtPersonBankAccountPK.companyCode = :companyCode"
    		+ " AND (a.fromLineBankCd1 IN :lineBankCode"
    		+ " OR a.fromLineBankCd2 IN :lineBankCode"
    		+ " OR a.fromLineBankCd3 IN :lineBankCode"
    		+ " OR a.fromLineBankCd4 IN :lineBankCode"
    		+ " OR a.fromLineBankCd5 IN :lineBankCode)";
	
	private final String SEL_BY_BANKANDBRANCH = 
			"SELECT a FROM PbamtPersonBankAccount a"
			+ " WHERE a.pbamtPersonBankAccountPK.companyCode = :companyCode"
			+ " AND ((a.toBankCd1 = :bankCode AND a.toBranchCd1 = :branchCode)"
    		+ " OR (a.toBankCd2 = :bankCode AND a.toBranchCd2 = :branchCode)"
    		+ " OR (a.toBankCd3 = :bankCode AND a.toBranchCd3 = :branchCode)"
    		+ " OR (a.toBankCd4 = :bankCode AND a.toBranchCd4 = :branchCode)"
    		+ " OR (a.toBankCd5 = :bankCode AND a.toBranchCd2 = :branchCode))";
	
    @Override
	public List<PersonBankAccount> findAll(String companyCode) {
		
		return null;
	}

	@Override
	public Optional<PersonBankAccount> find(String conpanyCode, String personId, String historyID) {
		PbamtPersonBankAccountPK key = new PbamtPersonBankAccountPK(conpanyCode, personId, historyID);
        return this.queryProxy().find(key, PbamtPersonBankAccount.class)
        		.map(x -> toDomain(x));
	}

	@Override
	public List<PersonBankAccount> findAll(String companyCode, String bankCode) {
		return this.queryProxy().query(SEL_1, PbamtPersonBankAccount.class)
				.setParameter("companyCode", companyCode)
				.setParameter("bankCode", bankCode)
				.getList(x-> toDomain(x));
	}
	
	@Override
	public List<PersonBankAccount> findAllLineBank(String companyCode, String lineBankCode) {
		return this.queryProxy().query(SEL_6, PbamtPersonBankAccount.class)
				.setParameter("companyCode", companyCode)
				.setParameter("lineBankCode", lineBankCode)
				.getList(x-> toDomain(x));
	}
	
	/**
	 * Convert data entity to domain object
	 * @param x entity object
	 * @return domain object
	 */
	private PersonBankAccount toDomain(PbamtPersonBankAccount x) {
		return new PersonBankAccount(
				x.pbamtPersonBankAccountPK.companyCode,
				x.pbamtPersonBankAccountPK.personId,
				x.pbamtPersonBankAccountPK.historyId,
				x.startYearMonth,
				x.endYearMonth,
				new PersonUseSetting(x.useSet1, x.priorityOrder1, x.paymentMethod1, x.partialPaySet1, x.fixAmountMny1, x.fixRate1, x.fromLineBankCd1, x.toBankCd1, x.toBranchCd1, x.accountAtr1, x.accountNo1, x.accountHolderKnName1, x.accountHolderName1),
				new PersonUseSetting(x.useSet2, x.priorityOrder2, x.paymentMethod2, x.partialPaySet2, x.fixAmountMny2, x.fixRate2, x.fromLineBankCd2, x.toBankCd2, x.toBranchCd2, x.accountAtr2, x.accountNo2, x.accountHolderKnName2, x.accountHolderName2),
				new PersonUseSetting(x.useSet3, x.priorityOrder3, x.paymentMethod3, x.partialPaySet3, x.fixAmountMny3, x.fixRate3, x.fromLineBankCd3, x.toBankCd3, x.toBranchCd3, x.accountAtr3, x.accountNo3, x.accountHolderKnName3, x.accountHolderName3),
				new PersonUseSetting(x.useSet4, x.priorityOrder4, x.paymentMethod4, x.partialPaySet4, x.fixAmountMny4, x.fixRate4, x.fromLineBankCd4, x.toBankCd4, x.toBranchCd4, x.accountAtr4, x.accountNo4, x.accountHolderKnName4, x.accountHolderName4),
				new PersonUseSetting(x.useSet5, x.priorityOrder5, x.paymentMethod5, x.partialPaySet5, x.fixAmountMny5, x.fixRate5, x.fromLineBankCd5, x.toBankCd5, x.toBranchCd5, x.accountAtr5, x.accountNo5, x.accountHolderKnName5, x.accountHolderName5));
	}

	@Override
	public boolean checkExistsBankAccount(String companyCode, List<String> bankCodeList) {
		Optional<Long> numberOfPerson = this.queryProxy().query(SEL_1, Long.class)
				.setParameter("companyCode", companyCode)
				.setParameter("bankCode", bankCodeList)
				.getSingle();
		
		return numberOfPerson.get() > 0;
	}

	@Override
	public boolean checkExistsBranchAccount(String companyCode, List<String> bankCode, List<String> branchCode) {
		Optional<Long> numberOfPerson = this.queryProxy().query(SEL_2, Long.class)
				.setParameter("companyCode", companyCode)
				.setParameter("bankCode", bankCode)
				.setParameter("branchCode", branchCode)
				.getSingle();
		return numberOfPerson.get() > 0;
	}

	@Override
	public boolean checkExistsLineBankAccount(String companyCode, List<String> lineBankCode) {
		Optional<Long> numberOfPerson = this.queryProxy().query(SEL_6_1, Long.class)
										.setParameter("companyCode", companyCode)
										.setParameter("lineBankCode", lineBankCode)
										.getSingle();
		return numberOfPerson.get() > 0;
	}
	
	@Override
	public void update(PersonBankAccount personBankAccount) {
		 this.commandProxy().update(toEntity(personBankAccount));
	}
	
	private static PbamtPersonBankAccount toEntity (PersonBankAccount domain){
		PbamtPersonBankAccountPK key = new PbamtPersonBankAccountPK(domain.getCompanyCode(), domain.getPersonID(), domain.getHistId());
		PbamtPersonBankAccount entity = new PbamtPersonBankAccount(
				key,
				domain.getStartYearMonth(),
				domain.getEndYearMonth(),
				domain.getUseSet1().getUseSet(),
				domain.getUseSet1().getPriorityOrder(),
				domain.getUseSet1().getPaymentMethod(),
				domain.getUseSet1().getPartialPaySet(),
				domain.getUseSet1().getFixAmountMny(),
				domain.getUseSet1().getFixRate(),
				domain.getUseSet1().getFromLineBankCd(),
				domain.getUseSet1().getToBankCd(),
				domain.getUseSet1().getToBranchCd(),
				domain.getUseSet1().getAccountAtr(),
				domain.getUseSet1().getAccountNo(),
				domain.getUseSet1().getAccountHolderKnName(),
				domain.getUseSet1().getAccountHolderName(),
				domain.getUseSet2().getUseSet(),
				domain.getUseSet2().getPriorityOrder(),
				domain.getUseSet2().getPaymentMethod(),
				domain.getUseSet2().getPartialPaySet(),
				domain.getUseSet2().getFixAmountMny(),
				domain.getUseSet2().getFixRate(),
				domain.getUseSet2().getFromLineBankCd(),
				domain.getUseSet2().getToBankCd(),
				domain.getUseSet2().getToBranchCd(),
				domain.getUseSet2().getAccountAtr(),
				domain.getUseSet2().getAccountNo(),
				domain.getUseSet2().getAccountHolderKnName(),
				domain.getUseSet2().getAccountHolderName(),
				domain.getUseSet3().getUseSet(),
				domain.getUseSet3().getPriorityOrder(),
				domain.getUseSet3().getPaymentMethod(),
				domain.getUseSet3().getPartialPaySet(),
				domain.getUseSet3().getFixAmountMny(),
				domain.getUseSet3().getFixRate(),
				domain.getUseSet3().getFromLineBankCd(),
				domain.getUseSet3().getToBankCd(),
				domain.getUseSet3().getToBranchCd(),
				domain.getUseSet3().getAccountAtr(),
				domain.getUseSet3().getAccountNo(),
				domain.getUseSet3().getAccountHolderKnName(),
				domain.getUseSet3().getAccountHolderName(),
				domain.getUseSet4().getUseSet(),
				domain.getUseSet4().getPriorityOrder(),
				domain.getUseSet4().getPaymentMethod(),
				domain.getUseSet4().getPartialPaySet(),
				domain.getUseSet4().getFixAmountMny(),
				domain.getUseSet4().getFixRate(),
				domain.getUseSet4().getFromLineBankCd(),
				domain.getUseSet4().getToBankCd(),
				domain.getUseSet4().getToBranchCd(),
				domain.getUseSet4().getAccountAtr(),
				domain.getUseSet4().getAccountNo(),
				domain.getUseSet4().getAccountHolderKnName(),
				domain.getUseSet4().getAccountHolderName(),
				domain.getUseSet5().getUseSet(),
				domain.getUseSet5().getPriorityOrder(),
				domain.getUseSet5().getPaymentMethod(),
				domain.getUseSet5().getPartialPaySet(),
				domain.getUseSet5().getFixAmountMny(),
				domain.getUseSet5().getFixRate(),
				domain.getUseSet5().getFromLineBankCd(),
				domain.getUseSet5().getToBankCd(),
				domain.getUseSet5().getToBranchCd(),
				domain.getUseSet5().getAccountAtr(),
				domain.getUseSet5().getAccountNo(),
				domain.getUseSet5().getAccountHolderKnName(),
				domain.getUseSet5().getAccountHolderName());
		return entity;
	}

	@Override
	public List<PersonBankAccount> findAllBranchCode(String companyCode, String bankCode, String branchCode) {
		return this.queryProxy().query(SEL_BY_BANKANDBRANCH, PbamtPersonBankAccount.class)
				.setParameter("companyCode", companyCode)
				.setParameter("bankCode", bankCode)
				.setParameter("branchCode", branchCode)
				.getList(x-> toDomain(x));
	}
}
