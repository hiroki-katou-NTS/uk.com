package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;

@RunWith(JMockit.class)
public class CareNursingSettingCheckTest {
    
    @Injectable
    CareNursingSettingCheck.Require require;

    /**
     * 介護設定が存在するかチェック.require -> Optional<介護休暇基本情報> = not empty
     * return true;
     */
    @Test
    public void testCheckExist() {
        String empExist = "001";
        
        Optional<CareLeaveRemainingInfo> itemExist = Optional.of(new CareLeaveRemainingInfo());
                
        new Expectations() {
            {
                require.getCareByEmpId(empExist);
                result = itemExist;
            }
        };
        
        assertThat(CareNursingSettingCheck.check(require, empExist)).isTrue();
    }
    
    /**
     * 介護設定が存在するかチェック.require -> Optional<介護休暇基本情報> = empty
     * return false;
     */
    @Test
    public void testCheckNotExist() {
        String empNotExist = "002";
        Optional<CareLeaveRemainingInfo> itemNotExist = Optional.empty();
      
        new Expectations() {
            {
                require.getCareByEmpId(empNotExist);
                result = itemNotExist;
            }
        };
        
        assertThat(CareNursingSettingCheck.check(require, empNotExist)).isFalse();
    }
}
