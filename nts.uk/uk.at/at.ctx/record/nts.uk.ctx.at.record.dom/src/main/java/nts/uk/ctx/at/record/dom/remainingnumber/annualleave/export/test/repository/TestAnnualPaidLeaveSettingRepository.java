package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test.repository;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;

/**
 * 年休設定
 * @author masaaki_jinno
 *
 */
public class TestAnnualPaidLeaveSettingRepository implements AnnualPaidLeaveSettingRepository{

    
	private static final String Throw = null;

	/**
     * Adds the.
     *
     * @param setting the setting
     */
	public void add(AnnualPaidLeaveSetting setting){
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
    
    /**
     * Update.
     *
     * @param setting the setting
     */
	public void update(AnnualPaidLeaveSetting setting){
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
    
    /**
     * Find by company id.
     *
     * @param companyId the company id
     * @return the annual vacation setting
     */
	public AnnualPaidLeaveSetting findByCompanyId(String companyId){
		
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
//		return TestAnnualPaidLeaveSettingFactory.create("1");
	}
    
    /**
	 * Copy master data.
	 *
	 * @param sourceCid the source cid
	 * @param targetCid the target cid
	 * @param isReplace the is replace
	 */
	public void copyMasterData(String sourceCid, String targetCid, boolean isReplace){
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
	}
	
	
	
}

