package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.role.Role;
import nts.uk.ctx.at.record.dom.workrecord.role.RoleAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 * UKDesign.UniversalK.オフィス.KMR_予約.KMR001_予約の前準備.B:予約の設定.ロール名称を取得する
 *
 */
@Stateless
public class GetRoleNameQuery {
    
    @Inject
    public RoleAdapter roleAdapter;
    
    public List<Role> getRoleName(List<String> roleIds) {
        
        // 1.ロール名称を取得する(List＜ロールID＞)
        List<Role> roles = roleAdapter.getRolesById(AppContexts.user().companyId(), roleIds);
        
        // 2.return(ロールID、名称): List＜ロール名称＞
        return roles;
    }

}
