package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;

@RunWith(JMockit.class)
public class ChildNursingSettingCheckTest {
    
    @Injectable
    ChildNursingSettingCheck.Require require;
    
    /**
     * 子の看護設定が存在するかチェック.require -> Optional<子の看護休暇基本情報> = not empty
     * return true;
     */
    @Test
    public void testCheckExist() {
        String empExist = "001";
        
        Optional<ChildCareLeaveRemainingInfo> itemExist = Optional.of(new ChildCareLeaveRemainingInfo(empExist, null, false, null, Optional.empty(), Optional.empty()));
        
        new Expectations() {
            {
                require.getChildCareByEmpId(empExist);
                result = itemExist;
            }
        };
        
        assertThat(ChildNursingSettingCheck.check(require, empExist)).isTrue();
    }
    
    /**
     * 子の看護設定が存在するかチェック.require -> Optional<子の看護休暇基本情報> = empty
     * return false;
     */
    @Test
    public void textCheckNotExist() {
        String empNotExist = "002";
        
        Optional<ChildCareLeaveRemainingInfo> itemNotExist = Optional.empty();
        
        new Expectations() {
            {
                require.getChildCareByEmpId(empNotExist);
                result = itemNotExist;
            }
        };
        
        assertThat(ChildNursingSettingCheck.check(require, empNotExist)).isFalse();
    }

}
