package nts.uk.ctx.sys.auth.dom.grant.roleindividual;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.sys.auth.dom.role.RoleType;

/**
 * システム管理者権限を付与する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.付与.ロール個人別付与.システム管理者権限を付与する
 * @author lan_lt
 *
 */
public class GrantSystemAdminPrivilegesService {
	
	/**
	 * 付与する
	 * @param require
	 * @param userId ユーザID
	 * @param expirationPeriod 有効期間
	 * @return
	 */
	public static AtomTask grant( Require require
			,	String userId
			,	DatePeriod expirationPeriod ) {
		
		val grantInfo = RoleIndividualGrant.createSystemManangerOfGrantInfo( require, userId, expirationPeriod );
		grantInfo.checkStatusNormal( require );
		
		return AtomTask.of(() ->{
			require.registerGrantInfo( grantInfo );
		});
	}
	
	/**
	 * 有効期間を更新する
	 * @param require
	 * @param userId ユーザID
	 * @param expirationPeriod 有効期間
	 * @return
	 */
	public static AtomTask updateExpirationPeriod( Require require
			,	String userId
			,	DatePeriod expirationPeriod ) {
		
		val grantInfo = require.getGrantInfoByRoleTypeOfUser( userId, RoleType.SYSTEM_MANAGER );
		grantInfo.setValidPeriod(expirationPeriod);
		
		grantInfo.checkStatusNormal( require );
		
		if( !isAlwaysASystemAdmin( require, grantInfo.getUserId(), grantInfo.getCorrectedValidPeriodByUserInfo(require) ) )
			throw new BusinessException( "Msg_330" );
		
		return AtomTask.of( () ->{
			require.updateGrantInfo( grantInfo );
		});
	}
	
	/**
	 * 剥奪する
	 * @param require
	 * @param userId ユーザID
	 * @return
	 */
	public static AtomTask deprive( Require require, String userId) {
		
		val grantInfo = require.getGrantInfoByRoleTypeOfUser( userId, RoleType.SYSTEM_MANAGER );
		
		if( !isAlwaysASystemAdmin(require, grantInfo.getUserId(), grantInfo.getCorrectedValidPeriodByUserInfo(require)))
			throw new BusinessException( "Msg_331" );
		
		return AtomTask.of(() ->{
			require.deleteGrantInfo( grantInfo.getUserId(), grantInfo.getCompanyId(), grantInfo.getRoleType() );
		});
	}
	
	
	/**
	 * システム管理者が常に存在するか		
	 * @param require
	 * @param userId ユーザID
	 * @param expirationPeriod 有効期間
	 * @return
	 */
	private static boolean isAlwaysASystemAdmin( Require require
			,	String userId
			,	Optional<DatePeriod> expirationPeriod ) {
		
		val expirationDates = require.getGrantInfoByRoleType( RoleType.SYSTEM_MANAGER ).stream()
				.filter( role -> !role.getUserId().equals( userId ) )
				.map( role -> role.getCorrectedValidPeriodByUserInfo( require ) )
				.flatMap( OptionalUtil::stream )
				.collect( Collectors.toList() );
		
		if( expirationPeriod.isPresent() ) {
			expirationDates.add( expirationPeriod.get() );
		}
		
		val checkTargetPeriod = new DatePeriod( GeneralDate.today(), GeneralDate.ymd(9999, 12, 31) );
		
		val sortExpirationDates = expirationDates.stream()
				.filter( period  -> checkTargetPeriod.compare( period ).isDuplicated() )
				.sorted(Comparator.comparing( DatePeriod::start ))
				.collect(Collectors.toList());
		
		val systemManagerPeriod = sortExpirationDates.stream().reduce((prev, next) -> {
			//連続しない場合
			if( prev.end().before(next.start()) &&  prev.end().before(next.start().addDays(-1))) {
				return next;
			}
			
			//連続する場合
			return next.end().after(prev.end())? new DatePeriod(prev.start(), next.end()): prev;
		});
		
		return systemManagerPeriod.get().compare(checkTargetPeriod).isFullyIncluding();
		
	}
	
	
	public static interface Require extends RoleIndividualGrant.Require {

		/**
		 * ユーザのロール種類別付与情報を取得する
		 * @param userId ユーザID
		 * @param roleType ロール種類
		 * @return
		 */
		RoleIndividualGrant getGrantInfoByRoleTypeOfUser( String userId, RoleType roleType );
	
		/**
		 * ロール種類から付与情報を取得する
		 * @param userId ユーザID
		 * @param roleType ロール種類
		 * @return
		 */
		List<RoleIndividualGrant> getGrantInfoByRoleType( RoleType roleType );
		
		/**
		 * 付与情報を登録する
		 * @param roleIndividualGrant ロール個人別付与
		 */
		void registerGrantInfo( RoleIndividualGrant roleIndividualGrant );
		
		/**
		 * 付与情報を更新する	
		 * @param roleIndividualGrant ロール個人別付与
		 */
		void updateGrantInfo( RoleIndividualGrant roleIndividualGrant );	
	
		/**
		 * 付与情報を削除する
		 * @param userId ユーザID
		 * @param companyId 会社ID
		 * @param roleType ロール種類
		 */
		void deleteGrantInfo( String userId, String companyId, RoleType roleType );
	
	}
	
}
